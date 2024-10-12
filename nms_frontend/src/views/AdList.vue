<template>
    <div>
      <h1>광고 목록</h1>
        <br>
      <div v-if="loadingAds">로딩 중...</div>
      <div v-if="error">{{ errorMessage }}</div>
  
      <!-- 광고 목록 카드 형태로 표시 -->
      <div v-if="!loadingAds && paginatedAds.length > 0" class="ad-grid">
        <div v-for="ad in paginatedAds" :key="ad.adId" class="ad-card">
          <div class="ad-info">
            <p>{{ ad.name }}</p>
            <p><strong>광고 시간 :</strong> {{ ad.adTime }}초</p>
            <p><strong>채널 순위 :</strong> {{ ad.channelRanking }}</p>
            <p><strong>회사 :</strong> {{ ad.companyName }}</p>
          </div>
          <div class="button-group">
            <button @click="editAd(ad)">수정</button>
            <button @click="deleteAd(ad.adId)">삭제</button>
          </div>
        </div>
      </div>
  
      <!-- 페이지네이션 버튼 -->
      <div v-if="!loadingAds && totalPages > 1" class="pagination">
        <button :disabled="currentPage === 1" @click="prevPage">이전</button>
        <span>페이지 {{ currentPage }} / {{ totalPages }}</span>
        <button :disabled="currentPage === totalPages" @click="nextPage">다음</button>
      </div>
  
      <div v-if="editingAd" class="edit-form">
        <h2>광고 수정</h2>
        <form @submit.prevent="updateAd">
          <label for="name">광고 이름:</label>
          <input type="text" v-model="editingAd.name" id="name" required />
  
          <label for="adTime">광고 시간 (초):</label>
          <input type="number" v-model="editingAd.adTime" id="adTime" required />
  
          <label for="channelRanking">채널 순위:</label>
          <input type="number" v-model="editingAd.channelRanking" id="channelRanking" required />
  
          <div class="button-group">
            <button type="submit">수정 완료</button>
            <button @click="cancelEdit">취소</button>
          </div>
        </form>
      </div>
    </div>
  </template>
  
  <script>
  import axios from 'axios';
  
  export default {
    data() {
      return {
        ads: [],
        loadingAds: true,
        error: false,
        errorMessage: '',
        editingAd: null,
        currentPage: 1,
        itemsPerPage: 6, // 한 페이지에 6개씩 표시
      };
    },
    computed: {
      paginatedAds() {
        const start = (this.currentPage - 1) * this.itemsPerPage;
        const end = start + this.itemsPerPage;
        return this.ads.slice(start, end);
      },
      totalPages() {
        return Math.ceil(this.ads.length / this.itemsPerPage);
      }
    },
    mounted() {
      this.fetchAds();
    },
    methods: {
      fetchAds() {
        axios.get('http://localhost:8080/api/ads')
          .then(response => {
            this.ads = response.data;
            this.loadingAds = false;
          })
          .catch(error => {
            this.error = true;
            this.errorMessage = '광고 목록을 불러오는 중 오류가 발생했습니다.';
            console.error(error);
            this.loadingAds = false;
          });
      },
      editAd(ad) {
        this.editingAd = { ...ad };
      },
      updateAd() {
        axios.put(`http://localhost:8080/api/ads/${this.editingAd.adId}`, this.editingAd)
          .then(response => {
            alert('광고가 성공적으로 수정되었습니다.');
            this.editingAd = null;
            this.fetchAds();
          })
          .catch(error => {
            this.error = true;
            this.errorMessage = '광고 수정 중 오류가 발생했습니다.';
            console.error(error);
          });
      },
      deleteAd(adId) {
        if (confirm('정말로 삭제하시겠습니까?')) {
          axios.delete(`http://localhost:8080/api/ads/${adId}`)
            .then(response => {
              alert('광고가 성공적으로 삭제되었습니다.');
              this.fetchAds();
            })
            .catch(error => {
              this.error = true;
              this.errorMessage = '광고 삭제 중 오류가 발생했습니다.';
              console.error(error);
            });
          }
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
      cancelEdit() {
        this.editingAd = null;
      },
    },
  };
  </script>
  
  <style scoped>
  .ad-grid {
    display: grid;
    grid-template-columns: repeat(auto-fit, minmax(250px, 1fr)); /* 6개씩 고르게 배치 */
    gap: 15px;
  }
  
  .ad-card {
    display: flex;
    justify-content: space-between;
    align-items: flex-start;
    border: 1px solid #ccc;
    border-radius: 8px;
    padding: 15px;
    background-color: #f9f9f9;
    box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
  }
  
  .ad-info {
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
  
  .error {
    color: red;
    margin-bottom: 20px;
  }
  </style>
  