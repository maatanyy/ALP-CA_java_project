package com.example.demo.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.AdPackageDTO;
import com.example.demo.entity.AdPackage;
import com.example.demo.repository.AdPackageRepository;

/*
 * AdPackage 관련 service  
 */

@Service
public class AdPackageService {

    @Autowired
    private AdPackageRepository adPackageRepository;
    
    // 전체 AdPackage 목록 조회
    public List<AdPackageDTO> getAllAdPackages() {
        return adPackageRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // AdPackage 저장
    public AdPackageDTO saveAdPackage(AdPackageDTO adPackageDTO) {
        AdPackage adPackage = convertToEntity(adPackageDTO);
        AdPackage savedPackage = adPackageRepository.save(adPackage);
        return convertToDTO(savedPackage);
    }

    // AdPackage 조회 by ID
    public AdPackageDTO getAdPackageById(Long id) {
        Optional<AdPackage> adPackage = adPackageRepository.findById(id);
        return adPackage.map(this::convertToDTO).orElse(null);
    }

    // AdPackage 삭제
    public void deleteAdPackage(Long id) {
        adPackageRepository.deleteById(id);
    }

    // AdPackage 업데이트
    public AdPackageDTO updateAdPackage(Long id, AdPackageDTO adPackageDTO) {
        Optional<AdPackage> existingAdPackage = adPackageRepository.findById(id);
        if (existingAdPackage.isPresent()) {
            AdPackage adPackage = existingAdPackage.get();
            adPackage.setName(adPackageDTO.getName());
            adPackage.setOnTime(adPackageDTO.getOnTime());
            adPackage.setPrice(adPackageDTO.getPrice());
            AdPackage updatedPackage = adPackageRepository.save(adPackage);
            return convertToDTO(updatedPackage);
        }
        return null;
    }

    // 엔티티를 DTO로 변환
    private AdPackageDTO convertToDTO(AdPackage adPackage) {
        AdPackageDTO adPackageDTO = new AdPackageDTO();
        adPackageDTO.setPackageId(adPackage.getPackageId());
        adPackageDTO.setName(adPackage.getName());
        adPackageDTO.setOnTime(adPackage.getOnTime());
        adPackageDTO.setPrice(adPackage.getPrice());
        return adPackageDTO;
    }

    // DTO를 엔티티로 변환
    private AdPackage convertToEntity(AdPackageDTO adPackageDTO) {
        AdPackage adPackage = new AdPackage();
        if (adPackageDTO.getPackageId() != null) {
            adPackage.setPackageId(adPackageDTO.getPackageId());
        }
        adPackage.setName(adPackageDTO.getName());
        adPackage.setOnTime(adPackageDTO.getOnTime());
        adPackage.setPrice(adPackageDTO.getPrice());
        return adPackage;
    }
}