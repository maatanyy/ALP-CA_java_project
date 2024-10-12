package com.example.demo.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.WatchHistory;

import jakarta.transaction.Transactional;

@Repository
public interface WatchHistoryRepository extends JpaRepository<WatchHistory, Long> {
	
	void deleteByAd_AdId(Long adId);
	
	 // 날짜로 정렬된 시청 이력 조회 (Sort를 이용)
    List<WatchHistory> findAll(Sort sort);
    
	// 광고 ID(adId)로 시청 기록을 찾는 메서드
    List<WatchHistory> findByAd_AdId(Long adId);
    
    // 페이지네이션된 시청 이력 리스트 조회
    Page<WatchHistory> findAll(Pageable pageable);
    
    // 고객 ID로 시청 이력 삭제
    @Modifying
    @Transactional
    @Query("DELETE FROM WatchHistory w WHERE w.customer.customerId = :customerId")
    void deleteByCustomerId(Long customerId);
    
    @Query("SELECT w.customer.gender, COUNT(w), AVG(a.adTime) FROM WatchHistory w " +
            "JOIN Ad a ON w.ad.adId = a.adId " +
            "GROUP BY w.customer.gender")
     List<Object[]> findAdStatisticsByGender();

}