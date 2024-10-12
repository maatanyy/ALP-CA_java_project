package com.example.demo.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GenderStatsDTO {
    private Character gender;   // 성별
    private Long adCount;       // 광고 수 count
    private Double avgAdTime;   // 평균광고시청시간

}