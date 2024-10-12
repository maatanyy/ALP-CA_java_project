package com.example.demo.entity;

import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Data;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

@Data
@Entity
public class Customer {
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long customerId;             // Primary key, 자동으로 생성 
    
    private String name;
    private LocalDate birthDate;      
    private Character gender;
    private String city;
    
    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)   //하나의 고객이 여러개의 시청기록을 가질 수 있음
    private List<WatchHistory> watchHistory;
}