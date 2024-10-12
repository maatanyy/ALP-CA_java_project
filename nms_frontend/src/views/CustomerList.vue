<template>
    <div>
      <h1>고객 목록</h1>
      <br>
      <div v-if="loading">로딩 중...</div>
      <div v-if="error" class="error">{{ errorMessage }}</div>
  
      <!-- 고객 목록 카드 형태로 표시 -->
      <div v-if="!loading && paginatedCustomers.length > 0" class="customer-grid">
        <div v-for="customer in paginatedCustomers" :key="customer.customerId" class="customer-card">
          <div class="customer-info">
            <h2>{{ customer.name }}</h2>
            <p><strong>생년월일 :</strong> {{ customer.birthDate }}</p>
            <p><strong>성별 :</strong> {{ customer.gender === 'M' ? '남성' : '여성' }}</p>
            <p><strong>도시 :</strong> {{ customer.city }}</p>
          </div>
          <div class="button-group">
            <button @click="editCustomer(customer)">수정</button>
            <button @click="deleteCustomer(customer.customerId)">삭제</button>
          </div>
        </div>
      </div>
  
      <div v-if="customers.length > 0" class="pagination">
        <button :disabled="currentPage === 1" @click="prevPage">이전</button>
        <span>{{ currentPage }} / {{ totalPages }}</span>
        <button :disabled="currentPage === totalPages" @click="nextPage">다음</button>
      </div>
  
      <!-- 고객 수정 폼 -->
      <div v-if="editingCustomer" class="edit-form">
        <h2>고객 수정</h2>
        <form @submit.prevent="updateCustomer">
          <label for="name">고객 이름:</label>
          <input type="text" v-model="editingCustomer.name" id="name" required />
  
          <label for="birthDate">생년월일:</label>
          <input type="date" v-model="editingCustomer.birthDate" id="birthDate" required />
  
          <label for="gender">성별:</label>
          <select v-model="editingCustomer.gender" id="gender" required>
            <option value="M">남성</option>
            <option value="F">여성</option>
          </select>
  
          <label for="city">도시:</label>
          <input type="text" v-model="editingCustomer.city" id="city" required />
  
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
        customers: [], // 전체 고객 목록
        loading: true, // 로딩 상태
        error: false, // 에러 상태
        errorMessage: '', // 에러 메시지
        editingCustomer: null, // 수정 중인 고객 정보
        currentPage: 1, // 현재 페이지
        itemsPerPage: 6, // 페이지당 보여줄 고객 수
      };
    },
    computed: {
      paginatedCustomers() {
        const startIndex = (this.currentPage - 1) * this.itemsPerPage;
        const endIndex = startIndex + this.itemsPerPage;
        return this.customers.slice(startIndex, endIndex);
      },
      totalPages() {
        return Math.ceil(this.customers.length / this.itemsPerPage);
      }
    },
    mounted() {
      this.fetchCustomers();
    },
    methods: {
      fetchCustomers() {
        axios.get('http://localhost:8080/api/customers')
          .then(response => {
            this.customers = response.data;
            this.loading = false;
          })
          .catch(error => {
            this.error = true;
            this.errorMessage = '고객 목록을 불러오는 중 오류가 발생했습니다.';
            console.error(error);
            this.loading = false;
          });
      },
      editCustomer(customer) {
        this.editingCustomer = { ...customer };
      },
      updateCustomer() {
        axios.put(`http://localhost:8080/api/customers/${this.editingCustomer.customerId}`, this.editingCustomer)
          .then(response => {
            alert('고객 정보가 성공적으로 수정되었습니다.');
            this.editingCustomer = null;
            this.fetchCustomers();
          })
          .catch(error => {
            this.error = true;
            this.errorMessage = '고객 수정 중 오류가 발생했습니다.';
            console.error(error);
          });
      },
      deleteCustomer(customerId) {
        if (confirm('정말로 삭제하시겠습니까?')) {
          axios.delete(`http://localhost:8080/api/customers/${customerId}`)
            .then(response => {
              alert('고객이 성공적으로 삭제되었습니다.');
              this.fetchCustomers();
            })
            .catch(error => {
              this.error = true;
              this.errorMessage = '고객 삭제 중 오류가 발생했습니다.';
              console.error(error);
            });
        }
      },
      cancelEdit() {
        this.editingCustomer = null;
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
      }
    }
  };
  </script>
  
  <style scoped>
  .customer-grid {
    display: grid;
    grid-template-columns: repeat(auto-fit, minmax(250px, 1fr)); /* 카드의 크기 줄임 */
    gap: 15px;
  }
  
  .customer-card {
    display: flex;
    justify-content: space-between;
    align-items: flex-start;
    border: 1px solid #ccc;
    border-radius: 8px;
    padding: 15px;
    background-color: #f9f9f9;
    box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
  }
  
  .customer-info {
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
  
  .edit-form input, .edit-form select {
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
  