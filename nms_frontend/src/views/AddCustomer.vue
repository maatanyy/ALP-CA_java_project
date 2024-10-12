<template>
    <div class="container">
      <div class="form-box">
        <h1>고객 추가</h1>
        <br />
        <form @submit.prevent="addCustomer">
          <div class="form-group">
            <label for="name">고객 이름</label>
            <input type="text" v-model="customer.name" id="name" required class="input-field"/>
          </div>
  
          <div class="form-group">
            <label for="birthDate">생년월일</label>
            <input type="date" v-model="customer.birthDate" id="birthDate" required class="input-field"/>
          </div>
  
          <div class="form-group">
            <label for="gender">성별</label>
            <select v-model="customer.gender" id="gender" required class="input-field">
              <option value="M">남성</option>
              <option value="F">여성</option>
            </select>
          </div>
  
          <div class="form-group">
            <label for="city">도시</label>
            <input type="text" v-model="customer.city" id="city" required class="input-field"/>
          </div>
  
          <button type="submit" class="submit-btn">고객 추가</button>
        </form>
      </div>
    </div>
  </template>
  
  <script>
  import axios from 'axios';
  
  export default {
    data() {
      return {
        customer: {
          name: '',
          birthDate: '',
          gender: '',
          city: ''
        }
      };
    },
    methods: {
      // 고객을 추가하는 메서드
      addCustomer() {
        axios.post('http://localhost:8080/api/customers', this.customer)
          .then(() => {
            alert('고객이 성공적으로 추가되었습니다.'); // 성공 시 경고창 표시
            this.resetForm(); // 폼 초기화
          })
          .catch(error => {
            alert('고객 추가 중 오류가 발생했습니다.'); // 오류 시 경고창 표시
            console.error(error);
          });
      },
      // 폼 초기화
      resetForm() {
        this.customer = {
          name: '',
          birthDate: '',
          gender: '',
          city: ''
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
  