package com.example.demo.entity;


import java.util.List;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import lombok.Data;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

@Data
@Entity
public class AdPackage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long packageId;   // Primary key, 자동으로 생성
    
    private String name;

    // 여러 회사가 이 광고 패키지를 사용할 수 있음
    @OneToMany(mappedBy = "adPackage")
    private List<Company> companies;

    @ElementCollection
    @CollectionTable(name = "adpackage_ontime", joinColumns = @JoinColumn(name = "package_id"))
    @Column(name = "onTime")
    private List<Integer> onTime;

    @ElementCollection
    @CollectionTable(name = "adpackage_price", joinColumns = @JoinColumn(name = "package_id"))
    @Column(name = "price")
    private List<Integer> price;
}