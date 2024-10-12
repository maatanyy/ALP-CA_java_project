package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdDTO {
    private Long adId;          // 광고 ID
    private String name;        // 광고 이름
    private Integer adTime;     // 광고 시간
    private Integer channelRanking;   // 랭킹이 30등안에 드냐 안드냐

    private Long companyId;     // 광고를 소유한 회사 ID
    private String companyName;  // 회사 이름 (추가)

}