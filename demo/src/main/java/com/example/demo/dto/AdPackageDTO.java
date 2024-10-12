package com.example.demo.dto;


import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdPackageDTO {

    private Long packageId;   // 패키지 id
    private String name;      // 패키지 이름
    private List<Integer> onTime;   // 패키지의 시간 예를 들어 [15,20,30,40,50] 과 같이 크기가 5인 배열로 존재
    private List<Integer> price;    // 패지키 시간 별 가격 [1,2,3,4,5] 와 같이 크기가 5인 배열로 존재

}