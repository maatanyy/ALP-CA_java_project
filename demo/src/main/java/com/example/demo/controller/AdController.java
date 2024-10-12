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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.AdDTO;
import com.example.demo.service.AdService;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.RequestBody;
import io.swagger.v3.oas.annotations.tags.Tag;


/*
 * Ad Controller
 * 전체 광고 조회, 특정 광고 조회(id), 광고 등록, 광고 수정, 광고 삭제
 */


@RestController
@RequestMapping("/api/ads")
@Tag(name = "Ad", description = "광고 관리 API")
@CrossOrigin
public class AdController {

    @Autowired
    private AdService adService;

    // 모든 광고 조회
    @GetMapping
    @Operation(summary = "모든 광고 조회", description = "모든 광고를 조회합니다.")
    public ResponseEntity<List<AdDTO>> getAllAds() {
        List<AdDTO> ads = adService.getAllAds();
        return ResponseEntity.ok(ads);
    }

    // 광고 조회 by ID
    @GetMapping("/{adId}")
    @Operation(summary = "특정 광고 조회", description = "특정 광고를 조회합니다.")
    public ResponseEntity<AdDTO> getAdById(@PathVariable("adId") Long adId) {
        AdDTO ad = adService.getAdById(adId);
        if (ad != null) {
            return ResponseEntity.ok(ad);
        }
        return ResponseEntity.notFound().build();
    }

    // 광고 추가
    @PostMapping
    @Operation(summary = "광고 추가", description = "새로운 광고를 추가합니다.")
    public ResponseEntity<AdDTO> createAd(@RequestBody AdDTO adDTO) {
        AdDTO savedAd = adService.saveAd(adDTO);
        return ResponseEntity.ok(savedAd);
    }

    // 광고 수정
    @PutMapping("/{adId}")
    @Operation(summary = "광고 수정", description = "특정 광고를 수정합니다.")
    public ResponseEntity<AdDTO> updateAd(@PathVariable("adId") Long adId, @RequestBody AdDTO adDTO) {
        AdDTO updatedAd = adService.updateAd(adId, adDTO);
        if (updatedAd != null) {
            return ResponseEntity.ok(updatedAd);
        }
        return ResponseEntity.notFound().build();
    }

    // 광고 삭제
    @DeleteMapping("/{adId}")
    @Operation(summary = "광고 삭제", description = "특정 광고를 삭제합니다.")
    public ResponseEntity<Void> deleteAd(@PathVariable("adId") Long adId) {
        adService.deleteAd(adId);
        return ResponseEntity.noContent().build();
    }
}