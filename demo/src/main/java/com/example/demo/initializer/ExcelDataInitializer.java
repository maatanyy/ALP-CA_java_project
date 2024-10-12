package com.example.demo.initializer;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Iterator;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.example.demo.service.*;
import com.example.demo.dto.*;

/*
 *   엑셀을 통해 초기 Dummy Data를 읽고 데이터를 생성하는 ExcelDataInitializer 
 */

@Component
@Order(2) // 순서 지정, DataInitializer 진행 후 진행
public class ExcelDataInitializer {

    @Autowired
    private CompanyService companyService;

    @Autowired
    private AdService adService; // Service for Ad

    @Autowired
    private CustomerService customerService; // Service for Customer
    
    @Autowired
    private WatchHistoryService watchHistoryService; // Service for WatchHistory

    @Bean
    public CommandLineRunner initDataFromExcel() {
        return args -> {
        	String excelFilePath = "C:/Users/KT/Desktop/Company.xlsx";
            try (FileInputStream fis = new FileInputStream(excelFilePath);
                 Workbook workbook = new XSSFWorkbook(fis)) {


                // Sheet 1: Company
                Sheet companySheet = workbook.getSheetAt(0);
                if (companyService.getAllCompanies().isEmpty()) { // 회사가 없으면 데이터 추가
                    readCompanyData(companySheet);
                }

                // Sheet 2: Ad
                Sheet adSheet = workbook.getSheetAt(1);
                if (adService.getAllAds().isEmpty()) { // 광고가 없으면 데이터 추가
                    readAdData(adSheet);
                }

                // Sheet 3: Customer
                Sheet customerSheet = workbook.getSheetAt(2);
                if (customerService.getAllCustomers().isEmpty()) { // 고객이 없으면 데이터 추가
                    readCustomerData(customerSheet);
                }

                // Sheet 4: WatchHistory
                Sheet watchHistorySheet = workbook.getSheetAt(3);
                if (watchHistoryService.getAllWatchHistories().isEmpty()) { // 시청 기록이 없으면 데이터 추가
                    readWatchHistoryData(watchHistorySheet);
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        };
    }

    private void readCompanyData(Sheet sheet) {
        Iterator<Row> rows = sheet.iterator();
        while (rows.hasNext()) {
            Row currentRow = rows.next();
            if (currentRow.getRowNum() == 0) continue; // Skip header row

            CompanyDTO companyDTO = new CompanyDTO();
            companyDTO.setName(currentRow.getCell(0).getStringCellValue());
            companyDTO.setSubscriptionDate(currentRow.getCell(1).getLocalDateTimeCellValue().toLocalDate());
            companyDTO.setTotalFee((int) currentRow.getCell(2).getNumericCellValue());
            companyDTO.setAdPackageId((long) currentRow.getCell(3).getNumericCellValue());
            companyService.saveCompany(companyDTO);
        }
    }

    private void readAdData(Sheet sheet) {
        Iterator<Row> rows = sheet.iterator();
        while (rows.hasNext()) {
            Row currentRow = rows.next();
            if (currentRow.getRowNum() == 0) continue; // Skip header row

            AdDTO adDTO = new AdDTO();
            adDTO.setCompanyId((long) currentRow.getCell(0).getNumericCellValue());
            adDTO.setName(currentRow.getCell(1).getStringCellValue());
            adDTO.setAdTime((int) currentRow.getCell(2).getNumericCellValue());
            adDTO.setChannelRanking((int) currentRow.getCell(3).getNumericCellValue());
            adService.saveAd(adDTO);
        }
    }

    private void readCustomerData(Sheet sheet) {
        Iterator<Row> rows = sheet.iterator();
        while (rows.hasNext()) {
            Row currentRow = rows.next();
            if (currentRow.getRowNum() == 0) continue; // Skip header row

            CustomerDTO customerDTO = new CustomerDTO();
            
            // 이름 읽기 (문자열)
            if (currentRow.getCell(0).getCellType() == CellType.STRING) {
                customerDTO.setName(currentRow.getCell(0).getStringCellValue());
            }

            // 생년월일 읽기 (문자열로 처리 후 LocalDate로 변환)
            if (currentRow.getCell(1).getCellType() == CellType.STRING) {
                customerDTO.setBirthDate(LocalDate.parse(currentRow.getCell(1).getStringCellValue()));
            } else if (DateUtil.isCellDateFormatted(currentRow.getCell(1))) {
                customerDTO.setBirthDate(currentRow.getCell(1).getLocalDateTimeCellValue().toLocalDate());
            }

            // 성별 읽기 (문자열)
            if (currentRow.getCell(2).getCellType() == CellType.STRING) {
                customerDTO.setGender(currentRow.getCell(2).getStringCellValue().charAt(0));
            }

            // 도시 읽기 (문자열)
            if (currentRow.getCell(3).getCellType() == CellType.STRING) {
                customerDTO.setCity(currentRow.getCell(3).getStringCellValue());
            }

            // Customer 데이터 저장
            customerService.saveCustomer(customerDTO);
        }
    }
    private void readWatchHistoryData(Sheet sheet) {
        Iterator<Row> rows = sheet.iterator();
        while (rows.hasNext()) {
            Row currentRow = rows.next();
            if (currentRow.getRowNum() == 0) continue; // Skip header row

            WatchHistoryDTO watchHistoryDTO = new WatchHistoryDTO();
            watchHistoryDTO.setCustomerId((long) currentRow.getCell(0).getNumericCellValue());
            watchHistoryDTO.setAdId((long) currentRow.getCell(1).getNumericCellValue());
            watchHistoryDTO.setWatchingDate(currentRow.getCell(2).getLocalDateTimeCellValue().toLocalDate());
            watchHistoryService.saveWatchHistory(watchHistoryDTO);
        }
    }
}
