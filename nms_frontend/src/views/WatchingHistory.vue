<template>
    <div class="container">
      <div class="form-box">
        <h1>시청 이력 추가</h1>
        <br />
        <form @submit.prevent="addWatchHistory">
          <div class="form-group">
            <label for="customer">고객 선택</label>
            <select v-model="watchHistory.customerId" id="customer" required class="input-field">
              <option v-for="customer in customers" :key="customer.customerId" :value="customer.customerId">
                {{ customer.name }}
              </option>
            </select>
          </div>
  
          <div class="form-group">
            <label for="ad">광고 선택</label>
            <select v-model="watchHistory.adId" id="ad" required class="input-field">
              <option v-for="ad in ads" :key="ad.adId" :value="ad.adId">
                {{ ad.name }}
              </option>
            </select>
          </div>
  
          <div class="form-group">
            <label for="watchingDate">시청 날짜</label>
            <input type="date" v-model="watchHistory.watchingDate" id="watchingDate" required class="input-field"/>
          </div>
  
          <button type="submit" class="submit-btn">시청 이력 추가</button>
        </form>
      </div>
    </div>
  </template>
  
  <script>
  import axios from 'axios';
  
  export default {
    data() {
      return {
        watchHistory: {
          customerId: null,
          adId: null,
          watchingDate: ''
        },
        customers: [], // 고객 목록
        ads: [], // 광고 목록
      };
    },
    mounted() {
      this.fetchCustomers();
      this.fetchAds();
    },
    methods: {
      // 고객 목록을 불러오는 메서드
      fetchCustomers() {
        axios.get('http://localhost:8080/api/customers')
          .then(response => {
            this.customers = response.data;
          })
          .catch(error => {
            alert('고객 목록을 불러오는 중 오류가 발생했습니다.'); // 오류 알림창
            console.error(error);
          });
      },
      // 광고 목록을 불러오는 메서드
      fetchAds() {
        axios.get('http://localhost:8080/api/ads')
          .then(response => {
            this.ads = response.data;
          })
          .catch(error => {
            alert('광고 목록을 불러오는 중 오류가 발생했습니다.'); // 오류 알림창
            console.error(error);
          });
      },
      // 시청 이력을 추가하는 메서드
      addWatchHistory() {
        axios.post('http://localhost:8080/api/watch-histories', this.watchHistory)
          .then(() => {
            alert('시청 이력이 성공적으로 추가되었습니다.'); // 성공 알림창
            this.resetForm();
          })
          .catch(error => {
            alert('시청 이력 추가 중 오류가 발생했습니다.'); // 오류 알림창
            console.error(error);
          });
      },
      // 폼 초기화
      resetForm() {
        this.watchHistory = {
          customerId: null,
          adId: null,
          watchingDate: ''
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
  