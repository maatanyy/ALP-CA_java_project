package com.example.demo.service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.AdDTO;
import com.example.demo.entity.Ad;
import com.example.demo.entity.Company;
import com.example.demo.repository.AdRepository;
import com.example.demo.repository.CompanyRepository;
import com.example.demo.repository.WatchHistoryRepository;

import jakarta.transaction.Transactional;

/*
 * Ad 관련 service  
 */


@Service
public class AdService {

    @Autowired
    private AdRepository adRepository;

    @Autowired
    private CompanyRepository companyRepository;  // CompanyRepository 주입
    
    @Autowired
    private WatchHistoryRepository watchHistoryRepository;
    
    
    // 전체 광고 목록을 조회하는 메서드
    public List<AdDTO> getAllAds() {
        return adRepository.findAll().stream()
                .map(this::convertToDTO)
                .sorted(Comparator.comparing(AdDTO::getCompanyName)) // 회사명 기준으로 오름차순 정렬
                .collect(Collectors.toList());
    }
    
    // 광고 추가 메서드
    public AdDTO addAd(AdDTO adDTO) {
        // 회사가 존재하는지 확인
        Optional<Company> companyOptional = companyRepository.findById(adDTO.getCompanyId());
        if (companyOptional.isPresent()) {
            Company company = companyOptional.get();

            // 광고 엔티티 생성 및 설정
            Ad ad = new Ad();
            ad.setName(adDTO.getName());
            ad.setAdTime(adDTO.getAdTime());
            ad.setChannelRanking(adDTO.getChannelRanking());
            ad.setCompany(company);

            // 광고 저장
            Ad savedAd = adRepository.save(ad);
            return convertToDTO(savedAd);
            
            // 예외 처리
        } else {
            throw new RuntimeException("회사를 찾을 수 없습니다.");
        }
    }

    // 광고 저장
    public AdDTO saveAd(AdDTO adDTO) {
        Ad ad = new Ad();
        ad.setName(adDTO.getName());
        ad.setAdTime(adDTO.getAdTime());
        ad.setChannelRanking(adDTO.getChannelRanking());

        Optional<Company> company = companyRepository.findById(adDTO.getCompanyId());
        company.ifPresent(ad::setCompany);


        Ad savedAd = adRepository.save(ad);
        return convertToDTO(savedAd);
    }

    // 광고 조회 by ID
    public AdDTO getAdById(Long adId) {
        return adRepository.findById(adId)
            .map(this::convertToDTO)
            .orElse(null);
    }

    // 광고 삭제
    @Transactional
    public void deleteAd(Long adId) {
        watchHistoryRepository.deleteByAd_AdId(adId);
        adRepository.deleteById(adId);
    }

    // 광고 업데이트
    public AdDTO updateAd(Long adId, AdDTO adDTO) {
        Optional<Ad> existingAdOpt = adRepository.findById(adId);

        if (existingAdOpt.isPresent()) {
            Ad existingAd = existingAdOpt.get();
            existingAd.setName(adDTO.getName());
            existingAd.setAdTime(adDTO.getAdTime());
            existingAd.setChannelRanking(adDTO.getChannelRanking());

            Optional<Company> company = companyRepository.findById(adDTO.getCompanyId());
            company.ifPresent(existingAd::setCompany);


            Ad updatedAd = adRepository.save(existingAd);
            return convertToDTO(updatedAd);
        }
        return null;
    }

    // 엔티티를 DTO로 변환
    private AdDTO convertToDTO(Ad ad) {
        AdDTO adDTO = new AdDTO();
        adDTO.setAdId(ad.getAdId());
        adDTO.setName(ad.getName());
        adDTO.setAdTime(ad.getAdTime());
        adDTO.setChannelRanking(ad.getChannelRanking());
        adDTO.setCompanyId(ad.getCompany().getCompanyId());
        
     // Company entity에서 회사 이름 가져오기
        Optional<Company> companyOptional = companyRepository.findById(ad.getCompany().getCompanyId());
        if (companyOptional.isPresent()) {
            adDTO.setCompanyName(companyOptional.get().getName());
        } else {
            adDTO.setCompanyName("회사 없음");  // 회사 정보가 없을 때 처리
        }
        
        return adDTO;
    }
}