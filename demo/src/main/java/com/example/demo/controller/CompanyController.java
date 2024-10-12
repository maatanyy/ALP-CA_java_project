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

import com.example.demo.dto.AdDTO;
import com.example.demo.dto.CompanyDTO;
import com.example.demo.service.CompanyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

/*
 * Company 관련 Controller
 * 모든 회사 조회, 특정 회사 조회(id), 회사 추가, 회사 삭제, 수정 컨트롤러 정의
 */


@RestController
@RequestMapping("/api/companies")
@Tag(name = "Company", description = "회사 관리 API")
@CrossOrigin
public class CompanyController {
    @Autowired
    private CompanyService companyService;

    // 모든 회사 조회
    @GetMapping
    @Operation(summary = "전체 회사 목록 조회", description = "등록된 모든 회사를 조회합니다.")
    public List<CompanyDTO> getAllCompanies() {
        return companyService.getAllCompanies();
    }

    // 회사 조회 by ID
    @GetMapping("/{companyId}")
    @Operation(summary = "특정 회사 조회", description = "회사 ID를 이용해 특정 회사를 조회합니다.")
    public ResponseEntity<CompanyDTO> getCompanyById(@PathVariable("companyId") Long companyId) {
        CompanyDTO company = companyService.getCompanyById(companyId);
        if (company == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(company);
    }

    // 회사 추가
    @PostMapping
    @Operation(summary = "회사 등록", description = "새로운 회사를 등록합니다.")
    public ResponseEntity<CompanyDTO> addCompany(@RequestBody CompanyDTO companyDTO) {
        CompanyDTO savedCompany = companyService.addCompany(companyDTO);
        return ResponseEntity.ok(savedCompany);
    }

    // 회사 수정
    @PutMapping("/{companyId}")
    @Operation(summary = "회사 수정", description = "회사 ID를 이용해 회사 정보를 수정합니다.")
    public ResponseEntity<CompanyDTO> updateCompany(@PathVariable("companyId") Long companyId, @RequestBody CompanyDTO companyDTO) {
        CompanyDTO updatedCompany = companyService.updateCompany(companyId, companyDTO);
        if (updatedCompany == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updatedCompany);
    }

    // 회사 삭제
    @DeleteMapping("/{companyId}")
    @Operation(summary = "회사 삭제", description = "회사 ID를 이용해 회사를 삭제합니다.")
    public ResponseEntity<Void> deleteCompany(@PathVariable("companyId") Long companyId) {
        companyService.deleteCompany(companyId);
        return ResponseEntity.noContent().build();
    }
    
    // 전체 Company 목록과 Total Fee 계산 결과 반환
    @GetMapping("/total-fees")
    @Operation(summary = "전체 회사의 총 비용 조회", description = "회사의 광고 시청 기록을 기반으로 총 비용을 계산하고 반환합니다.")
    public ResponseEntity<List<CompanyDTO>> getAllCompaniesWithTotalFees() {
        List<CompanyDTO> companyDTOs = companyService.calculateTotalFeesForCompanies();
        return ResponseEntity.ok(companyDTOs);
    }
    
    // 특정 회사의 광고 리스트 조회
    @GetMapping("/{companyId}/ads")
    @Operation(summary = "특정 회사의 광고 리스트 조회", description = "회사 ID를 이용해 해당 회사가 등록한 광고 목록을 조회합니다.")
    public ResponseEntity<List<AdDTO>> getAdsByCompany(@PathVariable("companyId") Long companyId) {
        List<AdDTO> ads = companyService.getAdsByCompany(companyId);
        // 비어있을 경우 처리
        if (ads.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
       return ResponseEntity.ok(ads);
    
    }
    
    // 회사 이름 중복 체크 API
    @GetMapping("/exists/{name}")
    @Operation(summary = "회사 이름 중복 체크", description = "회사 이름이 이미 존재하는지 확인합니다.")
    public ResponseEntity<Boolean> checkCompanyExists(@PathVariable("name") String name) {
        boolean exists = companyService.companyExistsByName(name);
        return ResponseEntity.ok(exists);
    }
    
    // 회사의 AdPackage 수정
    @PutMapping("/{companyId}/adpackage/{adPackageId}")
    @Operation(summary = "회사 광고 패키지 수정", description = "특정 회사의 광고 패키지를 수정합니다.")
    public ResponseEntity<CompanyDTO> updateCompanyAdPackage(
            @PathVariable("companyId") Long companyId,
            @PathVariable("adPackageId") Long adPackageId) {
    	// 수정 진행
        try {
            CompanyDTO updatedCompany = companyService.updateAdPackageForCompany(companyId, adPackageId);
            return ResponseEntity.ok(updatedCompany);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }
}