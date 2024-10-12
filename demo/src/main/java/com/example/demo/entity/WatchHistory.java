package com.example.demo.entity;

import java.time.LocalDate;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Entity
@Data
public class WatchHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  
    private Long historyId;    // Primary key, 자동으로 생성

    @ManyToOne   
    @JoinColumn(name = "customer_id")    //customer_id 를 foreign key로 참조
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "ad_id")       //channel_id를 foreign key로 참조
    private Ad ad;

    private LocalDate watchingDate;        // 광고를 시청한 시간을 저장하기 위한 변수 

}