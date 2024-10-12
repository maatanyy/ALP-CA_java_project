package com.example.demo.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.demo.dto.GenderStatsDTO;
import com.example.demo.dto.WatchHistoryDTO;
import com.example.demo.entity.Ad;
import com.example.demo.entity.Customer;
import com.example.demo.entity.WatchHistory;
import com.example.demo.repository.AdRepository;
import com.example.demo.repository.CustomerRepository;
import com.example.demo.repository.WatchHistoryRepository;

import jakarta.transaction.Transactional;

/*
 * 시청이력 관련 service  
 */

@Service
@Transactional 
public class WatchHistoryService {

    @Autowired
    private WatchHistoryRepository watchHistoryRepository;
    
    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private AdRepository adRepository;

    // 전체 시청 이력 조회 (DTO로 반환)
    @Transactional
    public List<WatchHistoryDTO> getAllWatchHistories() {
    	 // watchingDate 기준으로 오름차순 정렬
        Sort sort = Sort.by(Sort.Direction.ASC, "watchingDate");
        return watchHistoryRepository.findAll(sort).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    // 페이지네이션된 시청 이력 리스트 조회
    public Page<WatchHistoryDTO> getWatchHistories(int page, int size) {
    	Pageable pageable = PageRequest.of(page, size, Sort.by("watchingDate").ascending());
        return watchHistoryRepository.findAll(pageable)
                .map(this::convertToDTO); // 페이지네이션된 데이터를 DTO로 변환
    }

    // 특정 시청 이력 조회 (DTO로 반환)
    @Transactional
    public WatchHistoryDTO getWatchHistoryById(Long id) {
        WatchHistory watchHistory = watchHistoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Watch history not found"));
        return convertToDTO(watchHistory);
    }

    // 시청 이력 추가 (DTO로 받음)
    @Transactional
    public WatchHistoryDTO createWatchHistory(WatchHistoryDTO watchHistoryDTO) {
        WatchHistory watchHistory = convertToEntity(watchHistoryDTO);
        WatchHistory savedWatchHistory = watchHistoryRepository.save(watchHistory);
        return convertToDTO(savedWatchHistory);
    }

    // 시청 이력 삭제 (자동 생성된 id로 삭제)
    @Transactional
    public void deleteWatchHistoryById(Long id) {
        watchHistoryRepository.deleteById(id);
    }

    // 시청 이력 삭제 (고객 ID로 삭제)
    @Transactional
    public void deleteWatchHistoryByCustomerId(Long customerId) {
        watchHistoryRepository.deleteByCustomerId(customerId);
    }

    // 시청 이력 통계 가져오기
    public List<GenderStatsDTO> getAdStatsByGender() {
        List<Object[]> stats = watchHistoryRepository.findAdStatisticsByGender();
        return stats.stream().map(stat -> new GenderStatsDTO(
        		 (Character) stat[0],  // gender
                (Long) stat[1],   // count of watched ads
                (Double) stat[2]  // average ad time
        )).collect(Collectors.toList());
    }

    // 엔티티를 DTO로 변환
    private WatchHistoryDTO convertToDTO(WatchHistory watchHistory) {
    	WatchHistoryDTO watchHistoryDTO = new WatchHistoryDTO();
        watchHistoryDTO.setHistoryId(watchHistory.getHistoryId());
        watchHistoryDTO.setCustomerId(watchHistory.getCustomer().getCustomerId());
        watchHistoryDTO.setCustomerName(watchHistory.getCustomer().getName()); // 고객 이름 설정
        watchHistoryDTO.setAdId(watchHistory.getAd().getAdId());
        watchHistoryDTO.setAdName(watchHistory.getAd().getName()); // 광고 이름 설정
        watchHistoryDTO.setWatchingDate(watchHistory.getWatchingDate());
        return watchHistoryDTO;
    }

    // DTO를 엔티티로 변환
    private WatchHistory convertToEntity(WatchHistoryDTO watchHistoryDTO) {
        WatchHistory watchHistory = new WatchHistory();

        Customer customer = customerRepository.findById(watchHistoryDTO.getCustomerId())
                .orElseThrow(() -> new RuntimeException("Customer not found"));
        Ad ad = adRepository.findById(watchHistoryDTO.getAdId())
                .orElseThrow(() -> new RuntimeException("Ad not found"));

        watchHistory.setCustomer(customer);
        watchHistory.setAd(ad);
        watchHistory.setWatchingDate(watchHistoryDTO.getWatchingDate());

        return watchHistory;
    }
    
    // 저장
    public void saveWatchHistory(WatchHistoryDTO watchHistoryDTO) {
        WatchHistory watchHistory = convertToEntity(watchHistoryDTO);
        watchHistoryRepository.save(watchHistory); 
    }

}
