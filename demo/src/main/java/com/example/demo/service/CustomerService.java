package com.example.demo.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.dto.CustomerDTO;
import com.example.demo.entity.Customer;
import com.example.demo.repository.CustomerRepository;

/*
 * Customer 관련 service  
 */

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    // 모든 고객 조회 - DTO로 변환
    @Transactional(readOnly = true)
    public List<CustomerDTO> getAllCustomers() {
    	return customerRepository.findAll(Sort.by(Sort.Direction.ASC, "name")) // 오름차순 정렬
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // 특정 고객 조회 - DTO로 변환
    public CustomerDTO getCustomerById(Long id) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Customer not found"));
        return convertToDTO(customer);
    }

    // 고객 생성 - DTO를 엔티티로 변환 후 저장
    @Transactional
    public CustomerDTO createCustomer(CustomerDTO customerDTO) {
        Customer customer = convertToEntity(customerDTO);
        Customer savedCustomer = customerRepository.save(customer);
        return convertToDTO(savedCustomer);
    }

    // 고객 수정
    @Transactional
    public CustomerDTO updateCustomer(Long id, CustomerDTO customerDetails) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        customer.setName(customerDetails.getName());
        customer.setBirthDate(customerDetails.getBirthDate());
        customer.setGender(customerDetails.getGender());
        customer.setCity(customerDetails.getCity());

        Customer updatedCustomer = customerRepository.save(customer);
        return convertToDTO(updatedCustomer);
    }

    // 고객 삭제
    @Transactional
    public void deleteCustomer(Long id) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Customer not found"));
        customerRepository.delete(customer);
    }

    // 엔티티를 DTO로 변환
    private CustomerDTO convertToDTO(Customer customer) {
        return new CustomerDTO(
                customer.getCustomerId(),
                customer.getName(),
                customer.getBirthDate(),
                customer.getGender(),
                customer.getCity()
        );
    }

    // DTO를 엔티티로 변환
    private Customer convertToEntity(CustomerDTO customerDTO) {
        Customer customer = new Customer();
        customer.setName(customerDTO.getName());
        customer.setBirthDate(customerDTO.getBirthDate());
        customer.setGender(customerDTO.getGender());
        customer.setCity(customerDTO.getCity());
        return customer;
    }
    
    // 저장
    public void saveCustomer(CustomerDTO customerDTO) {
        Customer customer = new Customer();
        customer.setName(customerDTO.getName());
        customer.setBirthDate(customerDTO.getBirthDate());
        customer.setGender(customerDTO.getGender());
        customer.setCity(customerDTO.getCity());
        customerRepository.save(customer);
    }
}