<template>
    <div>
      <h1>시청 이력 목록</h1>
        <br>
      <div v-if="loading">로딩 중...</div>
      <div v-if="error">{{ errorMessage }}</div>
  
      <!-- 시청 이력 목록 카드 형태로 표시 -->
      <div v-if="!loading && watchHistories.length > 0" class="history-grid">
        <div v-for="history in watchHistories" :key="history.historyId" class="history-card">
          <div class="history-info">
            <h2>{{ history.customerName }}</h2>
            <p><strong>시청 광고 :</strong> {{ history.adName }}</p>
            <p><strong>시청 날짜 :</strong> {{ history.watchingDate }}</p>
          </div>
        </div>
      </div>
  
      <div v-if="!loading && watchHistories.length === 0">시청 이력이 없습니다.</div>
  
      <!-- 페이지네이션 -->
      <div v-if="!loading && totalPages > 1" class="pagination">
        <button @click="prevPage" :disabled="page === 0">이전</button>
        <span>페이지 {{ page + 1 }} / {{ totalPages }}</span>
        <button @click="nextPage" :disabled="page === totalPages - 1">다음</button>
      </div>
    </div>
  </template>
  
  <script>
  import axios from 'axios';
  
  export default {
    data() {
      return {
        watchHistories: [],
        page: 0,
        totalPages: 1,
        loading: true,
        error: false,
        errorMessage: ''
      };
    },
    mounted() {
      this.fetchWatchHistories();
    },
    methods: {
      fetchWatchHistories() {
        this.loading = true;
        axios.get(`http://localhost:8080/api/watch-histories/${this.page}/6`) // 한 페이지에 6개씩
          .then(response => {
            this.watchHistories = response.data.content;
            this.totalPages = response.data.totalPages;
            this.loading = false;
          })
          .catch(error => {
            this.error = true;
            this.errorMessage = '시청 이력을 불러오는 중 오류가 발생했습니다.';
            console.error(error);
            this.loading = false;
          });
      },
      prevPage() {
        if (this.page > 0) {
          this.page--;
          this.fetchWatchHistories();
        }
      },
      nextPage() {
        if (this.page < this.totalPages - 1) {
          this.page++;
          this.fetchWatchHistories();
        }
      }
    }
  };
  </script>
  
  <style scoped>
  .history-grid {
    display: grid;
    grid-template-columns: repeat(auto-fit, minmax(250px, 1fr)); /* 카드의 크기 동일하게 */
    gap: 15px;
  }
  
  .history-card {
    border: 1px solid #ccc;
    border-radius: 8px;
    padding: 15px;
    background-color: #f9f9f9;
    box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
  }
  
  .history-info {
    flex-grow: 1;
  }
  
  .pagination {
    margin: 20px 0;
    display: flex;
    justify-content: center;
    align-items: center;
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
  
  .error {
    color: red;
    margin-bottom: 20px;
  }
  </style>
  