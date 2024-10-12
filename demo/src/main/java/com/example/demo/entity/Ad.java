package com.example.demo.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import lombok.Data;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

@Data
@Entity
public class Ad {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long adId;              // Primary key, 자동으로 생성 
    
    // 광고가 한 회사에만 속한다
    @ManyToOne
    @JoinColumn(name = "company_id", nullable = false)
    private Company company;   
        
    private String name;
    private Integer adTime;
    private Integer channelRanking;
}