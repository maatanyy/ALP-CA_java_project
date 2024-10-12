<template>
    <div class="container">
      <div class="form-box">
        <h1>광고 추가</h1>
        <br />
        <form @submit.prevent="addAd">
          <div class="form-group">
            <label for="company">회사 선택</label>
            <select v-model="ad.companyId" id="company" required class="input-field">
              <option v-for="company in companies" :key="company.companyId" :value="company.companyId">
                {{ company.name }}
              </option>
            </select>
          </div>
    
          <div class="form-group">
            <label for="name">광고 이름</label>
            <input type="text" v-model="ad.name" id="name" required class="input-field"/>
          </div>
    
          <div class="form-group">
            <label for="adTime">광고 시간 (초)</label>
            <input type="number" v-model="ad.adTime" id="adTime" required class="input-field"/>
          </div>
    
          <div class="form-group">
            <label for="channelRanking">채널 순위</label>
            <input type="number" v-model="ad.channelRanking" id="channelRanking" required class="input-field"/>
          </div>
    
          <button type="submit" class="submit-btn">광고 추가</button>
        </form>
      </div>
    </div>
  </template>
  
  <script>
  import axios from 'axios';
  
  export default {
    data() {
      return {
        ad: {
          companyId: null,
          name: '',
          adTime: 0,
          channelRanking: 0
        },
        companies: [], // 회사 목록
      };
    },
    mounted() {
      this.fetchCompanies(); // 회사 목록을 불러옴
    },
    methods: {
      // 회사 목록을 서버에서 가져오는 메서드
      fetchCompanies() {
        axios.get('http://localhost:8080/api/companies')
          .then(response => {
            this.companies = response.data;
          })
          .catch(error => {
            alert('회사 목록을 불러오는 중 오류가 발생했습니다.'); // 경고창으로 표시
            console.error(error);
          });
      },
      // 광고를 서버로 전송하는 메서드
      addAd() {
        axios.post('http://localhost:8080/api/ads', this.ad)
          .then(() => {
            alert('광고가 성공적으로 추가되었습니다.'); // 성공 시 경고창 표시
            this.resetForm(); // 폼 초기화
          })
          .catch(error => {
            alert('광고 추가 중 오류가 발생했습니다.'); // 오류 시 경고창 표시
            console.error(error);
          });
      },
      // 폼 초기화
      resetForm() {
        this.ad = {
          companyId: null,
          name: '',
          adTime: 0,
          channelRanking: 0
        };
      }
    }
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
  
  h1 {
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
  
  .input-field {
    width: 100%;
    padding: 10px;
    border-radius: 5px;
    border: 1px solid #ccc;
    font-size: 14px;
  }
  
  .submit-btn {
    width: 100%;
    padding: 10px;
    background-color: #f83823;
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
  