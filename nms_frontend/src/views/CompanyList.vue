<template>
    <div>
      <h1>전체 회사 목록</h1>
      <br />
      <div v-if="loadingCompanies">로딩 중...</div>
      <div v-if="error">{{ errorMessage }}</div>
  
      <!-- 회사 목록 카드 형태로 표시 -->
      <div v-if="!loadingCompanies && paginatedCompanies.length > 0" class="company-grid">
        <div v-for="company in paginatedCompanies" :key="company.companyId" class="company-card">
          <div class="company-info">
            <h2>{{ company.name }}</h2>
            <p><strong>구독 날짜 :</strong> {{ company.subscriptionDate }}</p>
          </div>
          <div class="button-group">
            <button @click="editCompany(company)">수정</button>
            <button @click="deleteCompany(company.companyId)">삭제</button>
            <button @click="viewCompanyInfo(company.companyId)">정보보기</button>
          </div>
        </div>
      </div>
  
      <div v-if="companies.length > 0" class="pagination">
        <button :disabled="currentPage === 1" @click="prevPage">이전</button>
        <span>{{ currentPage }} / {{ totalPages }}</span>
        <button :disabled="currentPage === totalPages" @click="nextPage">다음</button>
      </div>
  
      <!-- 회사 정보 보기 -->
      <div v-if="selectedCompany" class="info-box">
        <h2>{{ selectedCompany.name }}</h2>
        <div class="info-item">
            <br>
          <p>가입 상품 : {{ selectedCompany.adPackageName || '가입된 광고 패키지가 없습니다.' }}</p>
        </div>
        <div class="info-item">
          <h3>광고 목록</h3>
          <br>
          <ul>
            <li v-for="ad in selectedCompany.ads" :key="ad.adId">{{ ad.name }}</li>
          </ul>
        </div>
        <button @click="closeCompanyInfo" class="close-btn">닫기</button>
      </div>
  
      <!-- 회사 수정 모달 -->
      <div v-if="editingCompany" class="edit-form">
        <h2>회사 수정</h2>
        <form @submit.prevent="updateCompany">
          <label for="name">회사 이름:</label>
          <input type="text" v-model="editingCompany.name" id="name" required />
          <label for="subscriptionDate">구독 날짜:</label>
          <input type="date" v-model="editingCompany.subscriptionDate" id="subscriptionDate" required />
          <button type="submit">수정 완료</button>
          <button @click="cancelEdit">취소</button>
        </form>
      </div>
    </div>
  </template>
  
  <script>
  import axios from "axios";
  
  export default {
    data() {
      return {
        companies: [],
        loadingCompanies: true,
        error: false,
        errorMessage: "",
        editingCompany: null,
        selectedCompany: null,
        currentPage: 1,
        itemsPerPage: 6, // 페이지당 6개씩 표시
      };
    },
    computed: {
      paginatedCompanies() {
        const startIndex = (this.currentPage - 1) * this.itemsPerPage;
        const endIndex = startIndex + this.itemsPerPage;
        return this.companies.slice(startIndex, endIndex);
      },
      totalPages() {
        return Math.ceil(this.companies.length / this.itemsPerPage);
      },
    },
    mounted() {
      this.fetchCompanies();
    },
    methods: {
      fetchCompanies() {
        axios
          .get("http://localhost:8080/api/companies")
          .then((response) => {
            this.companies = response.data;
            this.loadingCompanies = false;
          })
          .catch((error) => {
            this.error = true;
            this.errorMessage = "회사 목록을 불러오는 중 오류가 발생했습니다.";
            console.error(error);
            this.loadingCompanies = false;
          });
      },
      viewCompanyInfo(companyId) {
        axios
          .get(`http://localhost:8080/api/companies/${companyId}`)
          .then((response) => {
            this.selectedCompany = response.data;
          })
          .catch((error) => {
            console.error("회사 정보 불러오기 오류:", error);
          });
      },
      closeCompanyInfo() {
        this.selectedCompany = null;
      },
      editCompany(company) {
        this.editingCompany = { ...company };
      },
      updateCompany() {
        axios
          .put(
            `http://localhost:8080/api/companies/${this.editingCompany.companyId}`,
            this.editingCompany
          )
          .then((response) => {
            alert("회사 정보가 성공적으로 수정되었습니다.");
            this.editingCompany = null;
            this.fetchCompanies();
          })
          .catch((error) => {
            this.error = true;
            this.errorMessage = "회사 수정 중 오류가 발생했습니다.";
            console.error(error);
          });
      },
      deleteCompany(companyId) {
        if (confirm("정말로 삭제하시겠습니까?")) {
          axios
            .delete(`http://localhost:8080/api/companies/${companyId}`)
            .then((response) => {
              alert("회사가 성공적으로 삭제되었습니다.");
              this.fetchCompanies();
            })
            .catch((error) => {
              this.error = true;
              this.errorMessage = "회사 삭제 중 오류가 발생했습니다.";
              console.error(error);
            });
        }
      },
      cancelEdit() {
        this.editingCompany = null;
      },
      prevPage() {
        if (this.currentPage > 1) {
          this.currentPage--;
        }
      },
      nextPage() {
        if (this.currentPage < this.totalPages) {
          this.currentPage++;
        }
      },
    },
  };
  </script>
  
  <style scoped>
  .company-grid {
    display: grid;
    grid-template-columns: repeat(auto-fit, minmax(250px, 1fr)); /* 카드 크기 균일화 */
    gap: 15px;
  }
  
  .company-card {
    display: flex;
    justify-content: space-between;
    align-items: flex-start;
    border: 1px solid #ccc;
    border-radius: 8px;
    padding: 15px;
    background-color: #f9f9f9;
    box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
  }
  
  .company-info {
    flex-grow: 1;
  }
  
  .button-group {
    display: flex;
    flex-direction: column;
    gap: 10px;
  }
  
  button {
    padding: 5px 10px;
    border: none;
    background-color: #f83823;
    color: white;
    cursor: pointer;
    border-radius: 4px;
  }
  
  button:hover {
    background-color: green;
  }
  
  .edit-form {
    margin-top: 20px;
  }
  
  .edit-form form {
    display: flex;
    flex-direction: column;
  }
  
  .edit-form label {
    margin-top: 10px;
  }
  
  .edit-form input {
    padding: 8px;
    margin-top: 5px;
  }
  
  .pagination {
    margin: 20px 0;
    display: flex;
    justify-content: center;
    align-items: center;
    gap: 10px;
  }
  
  /* 정보 보기 박스 */
  .info-box {
    border: 1px solid #ccc;
    border-radius: 8px;
    padding: 20px;
    margin-top: 20px;
    background-color: #eef2f3;
    box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
  }
  
  .info-item {
    margin-bottom: 15px;
  }
  
  .close-btn {
    background-color: #4CAF50;
    color: white;
    padding: 8px 16px;
    border: none;
    border-radius: 5px;
    cursor: pointer;
  }
  
  .close-btn:hover {
    background-color: #45a049;
  }
  
  .error {
    color: red;
    margin-bottom: 20px;
  }
  </style>
  