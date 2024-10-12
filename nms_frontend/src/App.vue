<script setup>
import { RouterLink, RouterView } from 'vue-router' // Vue Router의 RouterLink, RouterView import
</script>

<template>
  <header>
    <!-- KT 로고 이미지 설정 -->
    <img alt="Kt" class="logo" src="@/assets/ktlogo.jpg" width="100" height="100" />
    <div class="wrapper">
      <h1>Genie TV LiveAD+</h1>

      <!-- 내비게이션 메뉴 설정 -->
      <nav>
        <ul class="main-menu">
          <!-- Home 메뉴 -->
          <li class="menu-item">
            <span>Home</span>
            <ul class="dropdown-menu">
              <li><RouterLink to="/">Home</RouterLink></li>
            </ul>
          </li>

          <!-- 전체 조회 -->
          <li class="menu-item">
            <span>전체 조회</span>
            <ul class="dropdown-menu">
              <li><RouterLink to="/updateadpackage">UpdateAdPackage</RouterLink></li>
              <li><RouterLink to="/chart">TotalCost</RouterLink></li>
              <li><RouterLink to="/gender">Gender</RouterLink></li>
            </ul>
          </li>

          <!-- 고객사 관리 -->
          <li class="menu-item">
            <span>고객사 관리</span>
            <ul class="dropdown-menu">
              <li><RouterLink to="/companylist">CompanyList</RouterLink></li>
              <li><RouterLink to="/addcompany">AddCompany</RouterLink></li>
            </ul>
          </li>

          <!-- 광고 관리 -->
          <li class="menu-item">
            <span>광고 관리</span>
            <ul class="dropdown-menu">
              <li><RouterLink to="/adlist">AdList</RouterLink></li>
              <li><RouterLink to="/addad">AddAd</RouterLink></li>
            </ul>
          </li>

          <!-- 고객 관리 -->
          <li class="menu-item">
            <span>고객 관리</span>
            <ul class="dropdown-menu">
              <li><RouterLink to="/customerlist">CustomerList</RouterLink></li>
              <li><RouterLink to="/addcustomer">AddCustomer</RouterLink></li>
            </ul>
          </li>

          <!-- 시청 기록 관리 -->
          <li class="menu-item">
            <span>시청 기록 관리</span>
            <ul class="dropdown-menu">
              <li><RouterLink to="/watchinghistorylist">WatchingHistoryList</RouterLink></li>
              <li><RouterLink to="/watchinghistory">AddWatchingHistory</RouterLink></li>
            </ul>
          </li>

        </ul>
      </nav>
    </div>
  </header>

  <RouterView />
</template>

<style scoped>
header {
  line-height: 1.5;
  max-height: 100vh;
}

.logo {
  display: block;
  margin: 0 auto 1.5rem;
}

.wrapper {
  text-align: center;
}

nav {
  width: 100%;
  margin-top: 1.5rem;
}

.main-menu {
  list-style: none;
  padding: 0;
  margin: 0;
  display: flex;
  flex-direction: column; /* 메뉴 항목을 세로로 배치 */
  align-items: center; /* 중앙 정렬 */
  gap: 1rem; /* 메뉴 항목 간 간격 조정 */
}

.menu-item {
  position: relative;
  display: block;
  cursor: pointer;
}

.menu-item:hover .dropdown-menu {
  display: block; /* 마우스를 올렸을 때 드롭다운 메뉴 표시 */
}

.menu-item span {
  text-decoration: none;
  color: inherit;
  font-weight: bold;
  font-size: 1rem;
}

.menu-item span:hover {
  color: white;
}

.dropdown-menu {
  display: none; /* 기본적으로 숨김 */
  list-style: none;
  position: absolute;
  left: 100%; /* 드롭다운 메뉴를 오른쪽에 표시 */
  top: 0;
  background-color: #f83823;
  padding: 10px;
  border-radius: 5px;
  z-index: 1;
}

.dropdown-menu li {
  margin-bottom: 5px;
}

.dropdown-menu li:last-child {
  margin-bottom: 0;
}

.dropdown-menu a {
  color: white;
  font-size: 0.9rem;
}

.dropdown-menu a:hover {
  color: yellow; /* 드롭다운 항목 호버 시 색상 변경 */
}

@media (min-width: 1024px) {
  header {
    display: flex;
    align-items: center;
    padding-right: calc(var(--section-gap) / 2);
  }

  .logo {
    margin: 0 1.5rem 0 0;
  }

  header .wrapper {
    display: flex;
    align-items: flex-start;
    flex-wrap: wrap;
  }

  nav {
    text-align: left;
    margin-left: -1rem;
    font-size: 1rem;
    padding: 1rem 0;
    margin-top: 1rem;
  }
}
</style>
