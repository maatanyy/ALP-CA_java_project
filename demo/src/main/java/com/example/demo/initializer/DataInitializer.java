package com.example.demo.initializer;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.example.demo.dto.AdPackageDTO;
import com.example.demo.service.AdPackageService;

/*
 *   초기 패키지 정보 생성하는 Initializer 
 *   if문을 통해 ad_package 테이블에 데이가 있는지 확인 후, 없는 경우만 생성
 */
@Component
@Order(1)   // 순서 지정, ExcelDataInitializer 보다 먼저 진행되어야 해서 1로 설정
public class DataInitializer {

    @Autowired
    private AdPackageService adPackageService;

    @Bean
    public CommandLineRunner init() {
        return args -> {
            // ad_package 테이블에 데이터가 있는지 확인
            if (adPackageService.getAllAdPackages().isEmpty()) {
                // 데이터가 없으면 패키지를 생성
                AdPackageDTO normalPackageBasic = new AdPackageDTO();
                normalPackageBasic.setName("기본 패키지 베이직");
                normalPackageBasic.setOnTime(Arrays.asList(15, 20, 30, 40, 60)); // onTime 배열
                normalPackageBasic.setPrice(Arrays.asList(5, 7, 10, 15, 20));   // price 배열
                adPackageService.saveAdPackage(normalPackageBasic);

                AdPackageDTO normalPackagePremium = new AdPackageDTO();
                normalPackagePremium.setName("기본 패키지 프리미엄");
                normalPackagePremium.setOnTime(Arrays.asList(15, 20, 30, 40, 60)); // onTime 배열
                normalPackagePremium.setPrice(Arrays.asList(6, 8, 12, 18, 24));  // price 배열
                adPackageService.saveAdPackage(normalPackagePremium);

                AdPackageDTO specialPackageNightPremium = new AdPackageDTO();
                specialPackageNightPremium.setName("스페셜 패키지 나이트 프리미엄");
                specialPackageNightPremium.setOnTime(Arrays.asList(15, 20, 30, 40, 60)); // onTime 배열
                specialPackageNightPremium.setPrice(Arrays.asList(5, 7, 10, 15, 20));  // price 배열
                adPackageService.saveAdPackage(specialPackageNightPremium);

                AdPackageDTO specialPackageNightKids = new AdPackageDTO();
                specialPackageNightKids.setName("스페셜 패키지 나이트 키즈/애니");
                specialPackageNightKids.setOnTime(Arrays.asList(15, 20, 30, 40, 60)); // onTime 배열
                specialPackageNightKids.setPrice(Arrays.asList(5, 7, 10, 15, 20));  // price 배열
                adPackageService.saveAdPackage(specialPackageNightKids);

                AdPackageDTO sosangPakcageBasic = new AdPackageDTO();
                sosangPakcageBasic.setName("소상공인 패키지 베이직");
                sosangPakcageBasic.setOnTime(Arrays.asList(15, 20, 30, 40, 60)); // onTime 배열
                sosangPakcageBasic.setPrice(Arrays.asList(5, 7, 10, 15, 20));  // price 배열
                adPackageService.saveAdPackage(sosangPakcageBasic);
            }
        };
    }
}

