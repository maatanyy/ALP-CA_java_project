<template>
    <div>
      <h1>기업 목록</h1>
      <div v-if="loadingCompanies">로딩 중...</div>
      <div v-if="errorCompanies">{{ errorMessageCompanies }}</div>
      
      <!-- 기업 목록 -->
      <ul v-if="!loadingCompanies && !errorCompanies && paginatedCompanies.length > 0">
        <li v-for="company in paginatedCompanies" :key="company.companyId" @click="fetchAds(company)">
          <button>{{ company.name }}</button>
        </li>
      </ul>
  
      <!-- 페이지네이션 -->
      <div v-if="!loadingCompanies && !errorCompanies && companies.length > 0" class="pagination">
        <button @click="previousPage" :disabled="currentPage === 1">이전</button>
        <span>페이지 {{ currentPage }} / {{ totalPages }}</span>
        <button @click="nextPage" :disabled="currentPage === totalPages">다음</button>
      </div>
    
      <!-- 광고 리스트 -->
      <div v-if="selectedCompany">
        <h1>{{ selectedCompany.name }} 광고 리스트</h1>
        <div v-if="loadingAds">로딩 중...</div>
        <div v-if="errorAds">{{ errorMessageAds }}</div>
        <div v-if="!loadingAds && !errorAds && ads.length > 0">
          <ul>
            <li v-for="ad in ads" :key="ad.adId">
              <h3>{{ ad.name }}</h3>
              <p>광고 시간: {{ ad.adTime }}초</p>
              <p>채널 순위: {{ ad.channelRanking }}</p>
            </li>
          </ul>
        </div>
        <div v-if="!loadingAds && ads.length === 0">해당 기업의 광고가 없습니다.</div>
      </div>
    </div>
  </template>
  
  <script>
  import axios from 'axios';
  
  export default {
    name: 'CompanyAds',
    data() {
      return {
        companies: [], // 기업 리스트
        selectedCompany: null, // 선택된 회사
        ads: [], // 선택된 회사의 광고 리스트
        loadingCompanies: true, // 회사 목록 로딩 상태
        errorCompanies: false, // 회사 목록 로딩 에러 상태
        errorMessageCompanies: '', // 회사 목록 로딩 에러 메시지
  
        loadingAds: false, // 광고 리스트 로딩 상태
        errorAds: false, // 광고 리스트 로딩 에러 상태
        errorMessageAds: '', // 광고 리스트 로딩 에러 메시지
  
        currentPage: 1, // 현재 페이지
        itemsPerPage: 5, // 페이지당 항목 수
      };
    },
    computed: {
      // 현재 페이지에 해당하는 기업 리스트를 반환
      paginatedCompanies() {
        const start = (this.currentPage - 1) * this.itemsPerPage;
        const end = start + this.itemsPerPage;
        return this.companies.slice(start, end);
      },
      // 총 페이지 수 계산
      totalPages() {
        return Math.ceil(this.companies.length / this.itemsPerPage);
      },
    },
    mounted() {
      this.fetchCompanies(); // 컴포넌트 마운트 시 회사 목록 불러오기
    },
    methods: {
      // 회사 목록을 불러오는 메서드
      fetchCompanies() {
        axios
          .get('http://localhost:8080/api/companies')
          .then((response) => {
            this.companies = response.data;
            this.loadingCompanies = false;
          })
          .catch((error) => {
            this.errorCompanies = true;
            this.errorMessageCompanies = '회사 목록을 불러오는 중 오류가 발생했습니다.';
            console.error('API 요청 오류:', error.response || error.message || error);
            this.loadingCompanies = false;
          });
      },
      // 특정 회사의 광고 리스트를 불러오는 메서드
      fetchAds(company) {
        this.selectedCompany = company; // 선택된 회사 설정
        this.ads = []; // 광고 리스트 초기화
        this.loadingAds = true;
        this.errorAds = false;
  
        axios
          .get(`http://localhost:8080/api/companies/${company.companyId}/ads`)
          .then((response) => {
            this.ads = response.data;
            this.loadingAds = false;
          })
          .catch((error) => {
            this.errorAds = true;
            this.errorMessageAds = '광고 리스트를 불러오는 중 오류가 발생했습니다.';
            console.error('API 요청 오류:', error.response || error.message || error);
            this.loadingAds = false;
          });
      },
      // 이전 페이지로 이동
      previousPage() {
        if (this.currentPage > 1) {
          this.currentPage--;
        }
      },
      // 다음 페이지로 이동
      nextPage() {
        if (this.currentPage < this.totalPages) {
          this.currentPage++;
        }
      },
    },
  };
  </script>
  
  <style scoped>
  h1 {
    text-align: center;
  }
  
  ul {
    list-style-type: none;
    padding: 0;
  }
  
  li {
    margin: 10px 0;
  }
  
  button {
    background-color: #4CAF50;
    color: white;
    padding: 10px 20px;
    border: none;
    cursor: pointer;
  }
  
  button:hover {
    background-color: #45a049;
  }
  
  .pagination {
    text-align: center;
    margin: 20px 0;
  }
  
  .pagination button {
    margin: 0 10px;
  }
  </style>
  