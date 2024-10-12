package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Ad;

@Repository
public interface AdRepository extends JpaRepository<Ad, Long> {
	 List<Ad> findByCompany_CompanyId(Long companyId); // 엔티티 필드명을 반영한 메서드
}
