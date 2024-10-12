<template>
    <div>
      <h1>새로운 회사 등록</h1>
      <form @submit.prevent="addCompany">
        <div>
          <label for="name">회사 이름:</label>
          <input type="text" v-model="company.name" id="name" required />
        </div>
  
        <div>
          <label for="subscriptionDate">구독 날짜:</label>
          <input type="date" v-model="company.subscriptionDate" id="subscriptionDate" required />
        </div>
  
        <div>
          <label for="totalFee">총 요금:</label>
          <input type="number" v-model="company.totalFee" id="totalFee" required />
        </div>
  
        <button type="submit">등록</button>
      </form>
  
      <div v-if="error">{{ errorMessage }}</div>
      <div v-if="successMessage">{{ successMessage }}</div>
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
          totalFee: 0,
        },
        error: false,
        errorMessage: '',
        successMessage: '',
      };
    },
    methods: {
      addCompany() {
        axios
          .post('http://localhost:8080/api/companies', this.company)
          .then((response) => {
            this.successMessage = '회사가 성공적으로 등록되었습니다.';
            this.error = false;
            // 폼 초기화
            this.company.name = '';
            this.company.subscriptionDate = '';
            this.company.totalFee = 0;
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
  form {
    display: flex;
    flex-direction: column;
    max-width: 300px;
    margin: 0 auto;
  }
  
  label {
    margin-top: 10px;
  }
  
  input {
    margin-bottom: 10px;
  }
  
  button {
    margin-top: 20px;
    background-color: #4CAF50;
    color: white;
    padding: 10px;
    border: none;
    cursor: pointer;
  }
  
  button:hover {
    background-color: #45a049;
  }
  
  div {
    text-align: center;
  }
  
  .error {
    color: red;
  }
  
  .success {
    color: green;
  }
  </style>
  