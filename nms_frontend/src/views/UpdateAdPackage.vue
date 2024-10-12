<template>
    <div class="container">
      <div class="form-box">
        <h2>회사 광고 패키지 변경</h2>
        <br />
        <div v-if="companies.length === 0 || adPackages.length === 0">
          <p>회사를 불러오거나 광고 패키지를 불러오는 중입니다...</p>
        </div>
        <div v-else>
          <form @submit.prevent="updateAdPackage">
            <div class="form-group">
              <label for="company">회사 선택</label>
              <select v-model="selectedCompanyId" id="company" class="dropdown">
                <option v-for="company in companies" :key="company.companyId" :value="company.companyId">
                  {{ company.name }}
                </option>
              </select>
            </div>
            <div class="form-group">
              <label for="adPackage">광고 패키지 선택</label>
              <select v-model="selectedAdPackageId" id="adPackage" class="dropdown">
                <option v-for="adPackage in adPackages" :key="adPackage.packageId" :value="adPackage.packageId">
                  {{ adPackage.name }}
                </option>
              </select>
            </div>
            <br />
            <button type="submit" class="submit-btn">광고 패키지 변경</button>
          </form>
        </div>
      </div>
    </div>
  </template>
  
  <script>
  import axios from "axios";
  
  export default {
    data() {
      return {
        companies: [], // 회사 리스트
        adPackages: [], // 광고 패키지 리스트
        selectedCompanyId: null, // 선택된 회사 ID
        selectedAdPackageId: null, // 선택된 광고 패키지 ID
      };
    },
    mounted() {
      this.fetchCompanies();
      this.fetchAdPackages();
    },
    methods: {
      fetchCompanies() {
        axios
          .get("http://localhost:8080/api/companies")
          .then((response) => {
            this.companies = response.data;
          })
          .catch((error) => {
            console.error("Error fetching companies:", error);
            alert("회사를 불러오는 중 오류가 발생했습니다.");
          });
      },
      fetchAdPackages() {
        axios
          .get("http://localhost:8080/api/adpackage")
          .then((response) => {
            this.adPackages = response.data;
          })
          .catch((error) => {
            console.error("Error fetching ad packages:", error);
            alert("광고 패키지를 불러오는 중 오류가 발생했습니다.");
          });
      },
      updateAdPackage() {
        if (!this.selectedCompanyId || !this.selectedAdPackageId) {
          alert("회사와 광고 패키지를 모두 선택해주세요.");
          return;
        }
  
        axios
          .put(
            `http://localhost:8080/api/companies/${this.selectedCompanyId}/adpackage/${this.selectedAdPackageId}`
          )
          .then((response) => {
            alert("광고 패키지가 성공적으로 변경되었습니다.");
          })
          .catch((error) => {
            console.error("Error updating ad package:", error);
            alert("광고 패키지를 변경하는 중 오류가 발생했습니다.");
          });
      },
    },
  };
  </script>
  
  <style scoped>
  .container {
    display: flex;
    justify-content: center;
    align-items: center;
    height: 100vh;
  }
  
  .form-box {
    border: 1px solid #ccc;
    padding: 20px;
    border-radius: 10px;
    width: 350px;
    box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
    background-color: #f9f9f9;
  }
  
  h2 {
    text-align: center;
  }
  
  .form-group {
    margin-bottom: 15px;
  }
  
  label {
    display: block;
    margin-bottom: 5px;
    font-weight: bold;
  }
  
  .dropdown {
    width: 100%;
    padding: 10px;
    border-radius: 5px;
    border: 1px solid #ccc;
    font-size: 14px;
  }
  
  .submit-btn {
    width: 100%;
    padding: 10px;
    background-color: #f83823;;
    color: white;
    border: none;
    border-radius: 5px;
    cursor: pointer;
    font-size: 16px;
  }
  
  .submit-btn:hover {
    background-color: #45a049;
  }
  </style>
  