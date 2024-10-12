package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.AdPackageDTO;
import com.example.demo.service.AdPackageService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

/*
 * AdPackageController
 * KT의 광고 패지키 기반
 * https://enterprise.kt.com/pd/P_PD_MD_AD_001.do 에 있는 패지키 데이터 정보랑 일치하게 만들 예정
 * 전체 패키지 조회, 생성, 조회, 수정, 삭제 
 */



@RestController
@RequestMapping("/api/adpackage")
@CrossOrigin
@Tag(name = "Adpackage", description = "광고패키지 관리 API")
public class AdPackageController {

    @Autowired
    private AdPackageService adPackageService;

    // 모든 광고 패키지 조회
    @GetMapping
    @Operation(summary = "전체 광고 패키지 목록 조회", description = "등록된 모든 광고 패키지를 조회합니다.")
    public List<AdPackageDTO> getAllAdPackages() {
        return adPackageService.getAllAdPackages();
    }

    // 광고 패키지 조회 by ID
    @GetMapping("/{id}")
    @Operation(summary = "광고 패키지 조회", description = "특정 광고 패키지를 조회합니다.")
    public ResponseEntity<AdPackageDTO> getAdPackageById(@PathVariable("id") Long id) {
        AdPackageDTO adPackage = adPackageService.getAdPackageById(id);
        if (adPackage == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(adPackage);
    }

    // 광고 패키지 추가
    @PostMapping
    @Operation(summary = "광고 패키지 추가", description = "새로운 광고 패키지를 추가합니다.")
    public ResponseEntity<AdPackageDTO> addAdPackage(@RequestBody AdPackageDTO adPackageDTO) {
        AdPackageDTO savedAdPackage = adPackageService.saveAdPackage(adPackageDTO);
        return ResponseEntity.ok(savedAdPackage);
    }

    // 광고 패키지 수정
    @PutMapping("/{id}")
    @Operation(summary = "광고 패키지 수정", description = "특정 광고 패키지를 수정합니다.")
    public ResponseEntity<AdPackageDTO> updateAdPackage(@PathVariable("id") Long id, @RequestBody AdPackageDTO adPackageDTO) {
        AdPackageDTO updatedAdPackage = adPackageService.updateAdPackage(id, adPackageDTO);
        if (updatedAdPackage == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updatedAdPackage);
    }

    // 광고 패키지 삭제
    @DeleteMapping("/{id}")
    @Operation(summary = "광고 패키지 삭제", description = "특정 광고 패키지를 삭제합니다.")
    public ResponseEntity<Void> deleteAdPackage(@PathVariable("id") Long id) {
        adPackageService.deleteAdPackage(id);
        return ResponseEntity.noContent().build();
    }
}