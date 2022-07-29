package com.hg.dashboard.controller;

import com.hg.dashboard.domain.ExamResult;
import com.hg.dashboard.domain.User;
import com.hg.dashboard.dto.LoginData;
import com.hg.dashboard.service.ExamService;
import com.hg.dashboard.service.UserService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;



import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class UserController {

    @Autowired
    ExamService examService;

    @Autowired
    private final UserService userService;

    @GetMapping("/intro")
    public String test() {

        return "Yes!";
    }

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.CREATED)
    public String login() {
        return "test success!!";
    }

    @GetMapping("/exam/result")
    public ExamResult getResult() {
        //Long loggedInUserId = authenticationFacade.getAuthenticatedUserId();
        return examService.getResultForUser(1L);
    }

}
