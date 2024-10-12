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

import com.example.demo.dto.CustomerDTO;
import com.example.demo.service.CustomerService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

/*
 * Customer 관련 Controller
 * 모든 고객 조회, 특정 고객 조회(id), 고객 생성, 고객 수정, 고객 삭제 
 */


@RestController
@RequestMapping("/api/customers")
@Tag(name = "Customer", description = "고객 관리 API")
@CrossOrigin
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    // 모든 고객 조회
    @GetMapping
    @Operation(summary = "전체 고객 목록 조회", description = "등록된 모든 고객을 조회합니다.")
    public List<CustomerDTO> getAllCustomers() {
    	return customerService.getAllCustomers();
    }

    // 특정 고객 조회
    @GetMapping("/{id}")
    @Operation(summary = "특정 고객 조회", description = "고객 ID를 이용해 특정 고객 정보를 조회합니다.")
    public ResponseEntity<CustomerDTO> getCustomerById(@PathVariable("id") Long id) {
        CustomerDTO customer = customerService.getCustomerById(id);
        return ResponseEntity.ok(customer);
    }

    // 고객 생성
    @PostMapping
    @Operation(summary = "고객 등록", description = "새로운 고객을 등록합니다.")
    public ResponseEntity<CustomerDTO> createCustomer(@RequestBody CustomerDTO customerDTO) {
        CustomerDTO newCustomer = customerService.createCustomer(customerDTO);
        return ResponseEntity.ok(newCustomer);
    }

    // 고객 수정
    @PutMapping("/{id}")
    @Operation(summary = "고객 수정", description = "고객 ID를 이용해 고객 정보를 수정합니다.")
    public ResponseEntity<CustomerDTO> updateCustomer(@PathVariable("id") Long id, @RequestBody CustomerDTO customerDetails) {
        CustomerDTO updatedCustomer = customerService.updateCustomer(id, customerDetails);
        return ResponseEntity.ok(updatedCustomer);
    }


    // 고객 삭제
    @DeleteMapping("/{id}")
    @Operation(summary = "고객 삭제", description = "고객 ID를 이용해 고객을 삭제합니다.")
    public ResponseEntity<Void> deleteCustomer(@PathVariable("id") Long id) {
        customerService.deleteCustomer(id);
        return ResponseEntity.noContent().build();
    }
}