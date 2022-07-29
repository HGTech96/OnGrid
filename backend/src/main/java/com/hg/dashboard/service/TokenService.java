package com.hg.dashboard.service;


import com.mikayelaghasyan.toptal.quiz.app.dto.TokenDto;
import com.mikayelaghasyan.toptal.quiz.app.exception.TokenNotFoundException;
import com.mikayelaghasyan.toptal.quiz.app.model.Token;
import com.mikayelaghasyan.toptal.quiz.app.model.User;
import com.mikayelaghasyan.toptal.quiz.app.repository.TokenRepository;
import com.mikayelaghasyan.toptal.quiz.app.security.JwtTokenUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.Instant;
import java.util.Date;
import java.util.UUID;

@Service
public class TokenService {
    private static final Logger LOGGER = LoggerFactory.getLogger(TokenService.class);

    private TokenRepository tokenRepository;

    private JwtTokenUtil jwtTokenUtil;

    @Value("${app.access-token-lifetime}")
    private long accessTokenLifetime;

    @Value("${app.refresh-token-lifetime}")
    private long refreshTokenLifetime;

    @Autowired
    public void setTokenRepository(TokenRepository tokenRepository) {
        this.tokenRepository = tokenRepository;
    }

    @Autowired
    public void setJwtTokenUtil(JwtTokenUtil jwtTokenUtil) {
        this.jwtTokenUtil = jwtTokenUtil;
    }

    @Transactional
    public TokenDto createToken(User user) {
        Instant now = Instant.now();
        Instant accessTokenExpiresOn = now.plusSeconds(accessTokenLifetime);
        Token token = new Token();
        token.setUser(user);
        token.setRefreshToken(UUID.randomUUID().toString());
        token.setRefreshTokenExpiresOn(now.plusSeconds(refreshTokenLifetime));
        token = tokenRepository.saveAndFlush(token);
        TokenDto tokenDto = getTokenDto(token.getId());
        tokenDto.setAccessToken(jwtTokenUtil.generateAccessToken(user, Date.from(now), Date.from(accessTokenExpiresOn)));
        tokenDto.setAccessTokenExpiresOn(accessTokenExpiresOn);
        return tokenDto;
    }

    private TokenDto getTokenDto(UUID tokenId) {
        return tokenRepository.findById(tokenId, TokenDto.class)
                .orElseThrow(() -> new TokenNotFoundException(tokenId));
    }

    @Transactional
    public TokenDto refreshToken(String refreshToken) {
        Token oldToken = tokenRepository.findByRefreshTokenAndRefreshTokenExpiresOnGreaterThan(refreshToken, Instant.now())
                .orElseThrow(() -> new TokenNotFoundException(refreshToken));
        TokenDto tokenDto = createToken(oldToken.getUser());
        tokenRepository.delete(oldToken);
        return tokenDto;
    }

    @Scheduled(cron = "@midnight")
    @Transactional
    public void cleanExpiredTokensJob() {
        int deletedTokens = tokenRepository.deleteByRefreshTokenExpiresOnLessThan(Instant.now());
        LOGGER.info("Token cleanup job: {} tokens deleted", deletedTokens);
    }
}
