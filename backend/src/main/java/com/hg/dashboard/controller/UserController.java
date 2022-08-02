package com.hg.dashboard.controller;




import com.hg.dashboard.domain.User;
import com.hg.dashboard.dto.LoginData;
import com.hg.dashboard.responses.LoginResponse;
import com.hg.dashboard.security.JwtTokenFilter;
import com.hg.dashboard.security.JwtTokenUtil;
import com.hg.dashboard.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;


@RestController
@RequestMapping("/auth")
public class UserController extends BaseController {

    private UserService userService;
//    private TokenService tokenService;
    private AuthenticationManager authManager;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

//    @Autowired
//    public void setTokenService(TokenService tokenService) {
//        this.tokenService = tokenService;
//    }

    @Autowired
    public void setAuthManager(AuthenticationManager authManager) {
        this.authManager = authManager;
    }

    @GetMapping("/test")
    public User tests() {
        Long loggedInUserId = authenticationFacade.getAuthenticatedUserId();
        return userService.getUser(loggedInUserId);
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?>  register(@Valid @RequestBody User userData) {
        User registeredUser = userService.createUser(userData);
        String jwtToken=jwtTokenUtil.generateAccessToken(registeredUser);
        LoginResponse response=new LoginResponse();
        response.setToken(jwtToken);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.CREATED)
    public String login(@Valid @RequestBody LoginData loginData) {
        return "test";
    }
}

