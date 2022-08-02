package com.hg.dashboard.controller;

import com.hg.dashboard.domain.ExamResult;
import com.hg.dashboard.service.ExamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/exam")
public class ExamController extends BaseController {

    @Autowired
    private ExamService examService;

    @GetMapping("")
    @ResponseStatus(HttpStatus.OK)
    public ExamResult getExamResults() {
        Long loggedInUserId = authenticationFacade.getAuthenticatedUserId();
        //Long loggedInUserId = 1L;
        return examService.getResultsForUser(loggedInUserId);
    }
}
