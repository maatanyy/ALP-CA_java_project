package com.example.demo.dto;


import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDTO {
    private Long customerId;   // 고객 id
    private String name;       // 고객 이름
    private LocalDate birthDate;   // 생년월일
    private Character gender;  // 성별
    private String city;  // 도시
}
