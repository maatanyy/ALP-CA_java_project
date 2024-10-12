package com.example.demo.dto;


import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WatchHistoryDTO {

    private Long historyId;  // 새로운 ID 필드 추가
    private Long customerId;   // 고객 ID 필드 추가
    private Long adId;       // 광고 ID 필드 추가
    private LocalDate watchingDate;  // 시청날짜
    private String customerName; // 고객 이름 추가
    private String adName;     // 광고 이름 추가
}