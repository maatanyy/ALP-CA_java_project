package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.dto.AdDTO;
import com.example.demo.dto.CompanyDTO;
import com.example.demo.entity.Ad;
import com.example.demo.entity.AdPackage;
import com.example.demo.entity.Company;
import com.example.demo.entity.WatchHistory;
import com.example.demo.repository.AdPackageRepository;
import com.example.demo.repository.AdRepository;
import com.example.demo.repository.CompanyRepository;
import com.example.demo.repository.WatchHistoryRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/*
 * Company 관련 service  
 */

@Service
public class CompanyService {

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private AdPackageRepository adPackageRepository;
    
    @Autowired
    private WatchHistoryRepository watchHistoryRepository;
    
    @Autowired
    private AdRepository adRepository; // AdRepository 주입


    // 전체 Company 목록 조회
    @Transactional(readOnly = true)  // 트랜잭션 설정
    public List<CompanyDTO> getAllCompanies() {
        return companyRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    // 이름으로 회사 조회 메서드 추가
    public boolean companyExistsByName(String name) {
        return companyRepository.existsByName(name);
    }


    // Company의 totalFee 계산 메서드 추가
    public List<CompanyDTO> calculateTotalFeesForCompanies() {
        List<Company> companies = companyRepository.findAll();

        for (Company company : companies) {
            int totalFee = 0;

            // 회사가 소유한 광고들 가져오기
            List<Ad> ads = company.getAds();

            for (Ad ad : ads) {
                // 광고에 관련된 시청 기록 가져오기
                List<WatchHistory> watchHistories = watchHistoryRepository.findByAd_AdId(ad.getAdId());

                for (WatchHistory watchHistory : watchHistories) {
                    // 광고 패키지 정보 가져오기
                    AdPackage adPackage = company.getAdPackage();
                    if (adPackage != null) {
                        List<Integer> onTimeList = adPackage.getOnTime();
                        List<Integer> priceList = adPackage.getPrice();

                        // 시청 시간과 on_time 비교하여 적절한 가격 찾기
                        int watchTime = ad.getAdTime();
                        int maxOnTimeIndex = findMaxOnTimeIndex(onTimeList, watchTime);

                        if (maxOnTimeIndex >= 0 && maxOnTimeIndex < priceList.size()) {
                            totalFee += priceList.get(maxOnTimeIndex);
                        }
                    }
                }
            }

            // 회사의 totalFee 업데이트
            company.setTotalFee(totalFee);
            companyRepository.save(company);
        }

        // 각 회사를 DTO로 변환하여 반환
        return companies.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // on_time 리스트에서 광고 시청 시간에 맞는 인덱스 찾는 메서드
    private int findMaxOnTimeIndex(List<Integer> onTimeList, int watchTime) {
        int index = -1;
        for (int i = 0; i < onTimeList.size(); i++) {
            if (watchTime <= onTimeList.get(i)) {
                return i;
            }
        }
        
        if (index == -1)
        	index = 4;
        	
        return index;
    }


    // 기업별 광고 리스트 조회 메서드 추가
    public List<AdDTO> getAdsByCompany(Long companyId) {
        Optional<Company> companyOptional = companyRepository.findById(companyId);
        if (companyOptional.isPresent()) {
            Company company = companyOptional.get();
            List<Ad> ads = company.getAds();
            return ads.stream()
                      .map(this::convertAdToDTO)
                      .collect(Collectors.toList());
        } else {
            return List.of(); // 회사가 없으면 빈 리스트 반환
        }
    }
    
    
    // Company 저장 또는 업데이트
    public CompanyDTO addCompany(CompanyDTO companyDTO) {
        Company company = convertToEntity(companyDTO);
        Company savedCompany = companyRepository.save(company);
        return convertToDTO(savedCompany);
    }

 // Company 조회 by ID
    public CompanyDTO getCompanyById(Long id) {
        Optional<Company> companyOptional = companyRepository.findById(id);
        if (companyOptional.isPresent()) {
            Company company = companyOptional.get();
            return convertToDTO(company); // 회사 정보와 광고 패키지, 광고 목록 반환
        }
        return null;
    }

    // Company 삭제
    public void deleteCompany(Long id) {
        companyRepository.deleteById(id);
    }

    // Company 업데이트
    public CompanyDTO updateCompany(Long id, CompanyDTO companyDTO) {
        Optional<Company> existingCompany = companyRepository.findById(id);
        if (existingCompany.isPresent()) {
            Company company = existingCompany.get();
            company.setName(companyDTO.getName());
            company.setSubscriptionDate(companyDTO.getSubscriptionDate());
            company.setTotalFee(companyDTO.getTotalFee());

            // 회사가 구독한 AdPackage 설정
            if (companyDTO.getAdPackageId() != null) {
                Optional<AdPackage> adPackage = adPackageRepository.findById(companyDTO.getAdPackageId());
                adPackage.ifPresent(company::setAdPackage);
            }

            Company updatedCompany = companyRepository.save(company);
            return convertToDTO(updatedCompany);
        }
        return null;
    }

    // 엔티티를 DTO로 변환
    private CompanyDTO convertToDTO(Company company) {
        CompanyDTO companyDTO = new CompanyDTO();
        companyDTO.setCompanyId(company.getCompanyId());
        companyDTO.setName(company.getName());
        companyDTO.setSubscriptionDate(company.getSubscriptionDate());
        companyDTO.setTotalFee(company.getTotalFee());
        companyDTO.setAdPackageId(company.getAdPackage() != null ? company.getAdPackage().getPackageId() : null);
        
     // 광고 패키지 이름 설정 (광고 패키지가 있을 경우)
        AdPackage adPackage = company.getAdPackage();
        companyDTO.setAdPackageName(adPackage != null ? adPackage.getName() : "패키지 없음");
        
        // ads 필드가 null이면 빈 리스트로 처리
        List<AdDTO> adDTOs = (company.getAds() != null) ? 
            company.getAds().stream().map(this::convertAdToDTO).collect(Collectors.toList()) : 
            new ArrayList<>();

        companyDTO.setAds(adDTOs);

        return companyDTO;
    }
    
    private AdDTO convertAdToDTO(Ad ad) {
        AdDTO adDTO = new AdDTO();
        adDTO.setAdId(ad.getAdId());
        adDTO.setName(ad.getName());
        adDTO.setAdTime(ad.getAdTime());
        adDTO.setChannelRanking(ad.getChannelRanking());
        return adDTO;
    }
    
    // saveCompany 메서드 정의
    public CompanyDTO saveCompany(CompanyDTO companyDTO) {
        // DTO를 엔티티로 변환
        Company company = convertToEntity(companyDTO);
        
        // AdPackage 설정
        if (companyDTO.getAdPackageId() != null) {
            Optional<AdPackage> adPackage = adPackageRepository.findById(companyDTO.getAdPackageId());
            if (adPackage.isPresent()) {
                company.setAdPackage(adPackage.get());
            } else {
                throw new IllegalArgumentException("AdPackage not found with id: " + companyDTO.getAdPackageId());
            }
        }
        
        // 엔티티 저장
        company = companyRepository.save(company);
        
        // 다시 DTO로 변환하여 반환
        return convertToDTO(company);
    }
    
    // DTO를 엔티티로 변환
    private Company convertToEntity(CompanyDTO companyDTO) {
        Company company = new Company();
        company.setName(companyDTO.getName());
        company.setSubscriptionDate(companyDTO.getSubscriptionDate());
        company.setTotalFee(companyDTO.getTotalFee() != null ? companyDTO.getTotalFee() : 0);  // null일 경우 0으로 설정

        return company;
    }
    
    // 회사의 광고 패키지를 업데이트하는 메서드
    public CompanyDTO updateAdPackageForCompany(Long companyId, Long adPackageId) {
        // 회사 조회
        Optional<Company> companyOptional = companyRepository.findById(companyId);
        if (!companyOptional.isPresent()) {
            throw new IllegalArgumentException("Company not found with id: " + companyId);
        }

        // 광고 패키지 조회
        Optional<AdPackage> adPackageOptional = adPackageRepository.findById(adPackageId);
        if (!adPackageOptional.isPresent()) {
            throw new IllegalArgumentException("AdPackage not found with id: " + adPackageId);
        }

        // 회사 정보 업데이트
        Company company = companyOptional.get();
        AdPackage adPackage = adPackageOptional.get();
        company.setAdPackage(adPackage); // 광고 패키지 변경

        // 변경된 회사 정보 저장
        Company updatedCompany = companyRepository.save(company);

        // 변경된 회사 정보를 DTO로 변환하여 반환
        return convertToDTO(updatedCompany);
    }
 
}