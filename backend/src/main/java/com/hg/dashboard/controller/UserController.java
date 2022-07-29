package com.hg.dashboard.controller;

import com.hg.dashboard.domain.ExamResult;
import com.hg.dashboard.service.ExamService;
import com.hg.dashboard.service.UserService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("")
public class UserController {
    @Autowired
    ExamService examService;

    @Autowired
    private final UserService userService;

    @GetMapping("/intro")
    public String test() {

        return "Yes!";
    }

    @GetMapping("/exam/result")
    public ExamResult getResult() {
        //Long loggedInUserId = authenticationFacade.getAuthenticatedUserId();
        return examService.getResultForUser(1L);
    }

}
