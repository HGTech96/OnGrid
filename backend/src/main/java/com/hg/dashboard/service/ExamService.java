package com.hg.dashboard.service;

import com.hg.dashboard.domain.ExamResult;
import com.hg.dashboard.repository.ExamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExamService {
    @Autowired
    ExamRepository examRepository;

    public ExamResult getResultsForUser(Long userId) {
        return examRepository.findByUserId(userId);
    }
}
