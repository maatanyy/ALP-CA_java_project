<template>
    <div class="container">
      <div class="form-box">
        <h1>새로운 회사 등록</h1>
        <br />
        <form @submit.prevent="checkAndAddCompany">
          <div class="form-group">
            <label for="name">회사 이름</label>
            <input type="text" v-model="company.name" id="name" required class="input-field"/>
          </div>
    
          <div class="form-group">
            <label for="subscriptionDate">구독 날짜</label>
            <input type="date" v-model="company.subscriptionDate" id="subscriptionDate" required class="input-field"/>
          </div>
    
          <button type="submit" class="submit-btn">등록</button>
        </form>
    
        <div v-if="error" class="error">{{ errorMessage }}</div>
      </div>
    </div>
  </template>
    
  <script>
  import axios from 'axios';
  
  export default {
    data() {
      return {
        company: {
          name: '',
          subscriptionDate: '',
          totalFee: 0, // 자동으로 0으로 설정
        },
        error: false,
        errorMessage: '',
      };
    },
    methods: {
      // 회사 등록 전 이름 중복 확인
      checkAndAddCompany() {
        axios
          .get(`http://localhost:8080/api/companies/exists/${this.company.name}`)
          .then((response) => {
            if (response.data) {
              // 이미 회사가 존재하는 경우 경고창 표시
              alert('이미 존재하는 회사입니다.');
            } else {
              // 회사가 존재하지 않으면 추가
              this.addCompany();
            }
          })
          .catch((error) => {
            this.error = true;
            this.errorMessage = '중복 확인 중 오류가 발생했습니다.';
            console.error(error);
          });
      },
      // 회사 추가
      addCompany() {
        this.company.totalFee = 0; // totalFee는 자동으로 0으로 설정
        axios
          .post('http://localhost:8080/api/companies', this.company)
          .then(() => {
            this.error = false;
            // 등록 성공 후 확인창 띄우기
            alert('등록되었습니다.');
            // 폼 초기화
            this.company.name = '';
            this.company.subscriptionDate = '';
          })
          .catch((error) => {
            this.error = true;
            this.errorMessage = '회사 등록 중 오류가 발생했습니다.';
            console.error(error);
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
  
  .error {
    color: red;
    text-align: center;
    margin-top: 10px;
  }
  </style>
  