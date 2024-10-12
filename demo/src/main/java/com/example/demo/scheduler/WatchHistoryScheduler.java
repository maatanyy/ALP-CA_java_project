package com.example.demo.scheduler;

import java.time.LocalDate;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.example.demo.entity.Ad;
import com.example.demo.entity.Customer;
import com.example.demo.entity.WatchHistory;
import com.example.demo.repository.AdRepository;
import com.example.demo.repository.CustomerRepository;
import com.example.demo.repository.WatchHistoryRepository;


// 시청기록 생성하는 Scheduler
// 고객 + 광고 정보를 가져와서 1분에 3개씩 랜덤으로 생성

@Component
public class WatchHistoryScheduler {

    @Autowired
    private AdRepository adRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private WatchHistoryRepository watchHistoryRepository;

    private final Random random = new Random();

    // 1분마다 실행되는 스케줄러
    @Scheduled(fixedRate = 10000)
    public void generateRandomWatchHistories() {
        List<Ad> ads = adRepository.findAll();
        List<Customer> customers = customerRepository.findAll();

        if (ads.isEmpty() || customers.isEmpty()) {
            System.out.println("광고나 고객 목록이 비어있습니다.");
            return;
        }

        // 3개의 랜덤 시청 기록 생성
        for (int i = 0; i < 3; i++) {
            WatchHistory watchHistory = createRandomWatchHistory(ads, customers);
            watchHistoryRepository.save(watchHistory);
            System.out.println("랜덤 시청 기록이 생성되었습니다: " + watchHistory);
        }
    }

    private WatchHistory createRandomWatchHistory(List<Ad> ads, List<Customer> customers) {
        Ad randomAd = ads.get(random.nextInt(ads.size()));
        Customer randomCustomer = customers.get(random.nextInt(customers.size()));

        WatchHistory watchHistory = new WatchHistory();
        watchHistory.setAd(randomAd);
        watchHistory.setCustomer(randomCustomer);
        watchHistory.setWatchingDate(LocalDate.now());

        return watchHistory;
    }
}



