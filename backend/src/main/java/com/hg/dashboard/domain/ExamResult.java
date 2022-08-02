package com.hg.dashboard.domain;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "t_exam_result")
public class ExamResult {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    //@ManyToOne(targetEntity = User.class,cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Long userId;
    private String examResult;
    private String maxResult;
}
