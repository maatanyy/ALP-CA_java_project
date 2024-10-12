package com.example.demo.dto;


import java.time.LocalDate;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CompanyDTO {
    private Long companyId;     // 회사 id
    private String name;        // 회사 이름
    private LocalDate subscriptionDate;   // 구독 날짜
    private Integer totalFee = 0;   // 전체 요금
    private List<AdDTO> ads;      // 광고 목록 필드 추가
    private Long adPackageId;     // 광고 id 
    private String adPackageName; // 광고 패키지 이름 필드 추가
}

