package com.example.demo.entity;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Data;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

@Data
@Entity
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long companyId;   // Primary key, 자동으로 생성 
    
    private String name;      // 이름
    private LocalDate subscriptionDate;   //구독 날짜
    private Integer totalFee = 0;   //총 요금
    
    // 하나의 회사가 여러 광고를 가질 수 있음 (1:N 관계)
    @OneToMany(mappedBy = "company", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Ad> ads = new ArrayList<>();   // 회사가 가진 광고 목록

    @ManyToOne
    @JoinColumn(name = "package_id")
    private AdPackage adPackage;
}

