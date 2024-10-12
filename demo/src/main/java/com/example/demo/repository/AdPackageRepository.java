package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.AdPackage;

@Repository
public interface AdPackageRepository extends JpaRepository<AdPackage, Long> {}