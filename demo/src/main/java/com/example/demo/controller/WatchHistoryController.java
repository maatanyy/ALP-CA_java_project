package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.GenderStatsDTO;
import com.example.demo.dto.WatchHistoryDTO;
import com.example.demo.entity.Ad;
import com.example.demo.entity.Customer;
import com.example.demo.entity.WatchHistory;
import com.example.demo.repository.AdRepository;
import com.example.demo.repository.CustomerRepository;
import com.example.demo.service.WatchHistoryService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

/*
 * WatchHistory (시청이력) 관련 Controller
 * 모든 시청이력 조회, 특정 시청이력 조회(id), 시청이력 삭제, 시청이력 추가
 * 시청이력 수정은 만들지 않았음 (필요 없다고 판단)
 */


@RestController
@RequestMapping("/api/watch-histories")
@Tag(name = "Watch history", description = "시청이력 관리 API")
@CrossOrigin
public class WatchHistoryController {

    @Autowired
    private WatchHistoryService watchHistoryService;

    @Autowired
    private CustomerRepository customerRepository;
    
    @Autowired
    private AdRepository adRepository;


    // 전체 시청 이력 조회 (전체)
    @GetMapping
    @Operation(summary = "전체 시청 이력 조회", description = "모든 시청 이력을 조회합니다.")
    public List<WatchHistoryDTO> getAllWatchHistories() {
        return watchHistoryService.getAllWatchHistories();
    }
    
    // 페이지네이션된 시청 이력 조회
    @GetMapping("/{page}/{size}")
    public Page<WatchHistoryDTO> getWatchHistories(@PathVariable("page") int page, @PathVariable("size") int size) {
        return watchHistoryService.getWatchHistories(page, size);
    }
    
    // 특정 시청 이력 조회 (자동 생성된 id로 조회)
    @GetMapping("/{id}")
    @Operation(summary = "특정 시청 이력 조회", description = "ID로 특정 시청 이력을 조회합니다.")
    public ResponseEntity<WatchHistoryDTO> getWatchHistoryById(@PathVariable("id") Long id) {
        WatchHistoryDTO watchHistory = watchHistoryService.getWatchHistoryById(id);
        //비어있을 경우
        if (watchHistory == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(watchHistory);
    }

    // 시청 이력 추가 API
    @PostMapping
    @Operation(summary = "시청 이력 추가", description = "새로운 시청 이력을 추가합니다.")
    public ResponseEntity<WatchHistoryDTO> addWatchHistory(@RequestBody WatchHistoryDTO watchHistoryDTO) {
        WatchHistoryDTO savedHistory = watchHistoryService.createWatchHistory(watchHistoryDTO);
        return ResponseEntity.ok(savedHistory);
    }

    // 시청 이력 삭제 (자동 생성된 id로 삭제)
    @DeleteMapping("/{id}")
    @Operation(summary = "시청 이력 삭제", description = "ID로 시청 이력을 삭제합니다.")
    public ResponseEntity<Void> deleteWatchHistoryById(@PathVariable("id") Long id) {
        watchHistoryService.deleteWatchHistoryById(id);
        return ResponseEntity.noContent().build();
    }
    

    // DTO로 변환하는 메서드
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
    
    // DTO를 엔티티로 변환하는 메서드
    private WatchHistory convertToEntity(WatchHistoryDTO watchHistoryDTO) {
        WatchHistory watchHistory = new WatchHistory();
        watchHistory.setWatchingDate(watchHistoryDTO.getWatchingDate());

        // Customer 설정
        Customer customer = customerRepository.findById(watchHistoryDTO.getCustomerId())
            .orElseThrow(() -> new RuntimeException("Customer not found with id: " + watchHistoryDTO.getCustomerId()));
        watchHistory.setCustomer(customer);

        // Ad 설정
        Ad ad = adRepository.findById(watchHistoryDTO.getAdId())
            .orElseThrow(() -> new RuntimeException("Ad not found with id: " + watchHistoryDTO.getAdId()));
        watchHistory.setAd(ad);

        return watchHistory;
    }
    
    // 성별별 시청 통계 조회
    @GetMapping("/stats")
    public List<GenderStatsDTO> getAdStatsByGender() {
        return watchHistoryService.getAdStatsByGender();
    }
}
