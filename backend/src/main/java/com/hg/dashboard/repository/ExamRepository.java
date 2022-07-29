package com.hg.dashboard.repository;

import com.hg.dashboard.domain.ExamResult;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExamRepository extends JpaRepository<ExamResult, Long> {
    ExamResult findByUserId(Long userId);
}
