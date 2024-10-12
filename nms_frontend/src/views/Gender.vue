<template>
    <div>
      <h1>성별에 따른 광고 시청 통계</h1>
      <div v-if="loading">로딩 중...</div>
      <div v-if="error">{{ errorMessage }}</div>
      <div v-if="!loading && !error">
        <div class="chart-container">
          <div class="chart-item">
            <h2>시청 광고 수</h2>
            <Doughnut v-bind:data="adCountData"  :options="optionsAdCount" />
          </div>
          <div class="chart-item">
            <h2>평균 광고 시청 시간</h2>
            <Doughnut v-bind:data="avgAdTimeData"  :options="optionsAvgAdTime" />
          </div>
        </div>
      </div>
    </div>
  </template>
  
  <script>
  import axios from 'axios';
  import {
    Chart as ChartJS,
    Title,
    Tooltip,
    Legend,
    ArcElement,
  } from 'chart.js';
  import { Doughnut } from 'vue-chartjs';
  import ChartDataLabels from 'chartjs-plugin-datalabels'; // 데이터 레이블 플러그인 추가
  
  ChartJS.register(Title, Tooltip, Legend, ArcElement, ChartDataLabels); // 플러그인 등록
  
  export default {
  name: 'GenderStatsChart',
  components: {
    Doughnut,
  },
  data() {
    return {
      stats: [],
      loading: true,
      error: false,
      errorMessage: '',
      adCountData: {
        labels: [],
        datasets: [
          {
            label: '시청 광고 수',
            backgroundColor: ['#f83823', '#23f8e4'],
            data: [],
          },
        ],
      },
      avgAdTimeData: {
        labels: [],
        datasets: [
          {
            label: '평균 광고 시청 시간',
            backgroundColor: ['#f83823', '#23f8e4'],
            data: [],
          },
        ],
      },
      optionsAdCount: {
        responsive: true,
        maintainAspectRatio: false,
        plugins: {
          datalabels: {
            color: '#fff',
            formatter: (value) => {
              console.log('formatter called for Ad Count:', value);
              return value; // 소숫점 2자리
            },
            font: {
              weight: 'bold',
              size: 14,
            },
          },
          tooltip: {
            callbacks: {
              label: (tooltipItem) => {
                return `시청 광고 수: ${tooltipItem.raw}개`;
              },
            },
          },
        },
      },
      optionsAvgAdTime: {
        responsive: true,
        maintainAspectRatio: false,
        plugins: {
          datalabels: {
            color: '#fff',
            formatter: (value) => {
              console.log('formatter called for Avg Ad Time:', value);
              return value.toFixed(2); // 소숫점 2자리
            },
            font: {
              weight: 'bold',
              size: 14,
            },
          },
          tooltip: {
            callbacks: {
              label: (tooltipItem) => {
                return `평균 광고 시청 시간: ${tooltipItem.raw.toFixed(2)}초`;
              },
            },
          },
        },
      },
    };
  },
  mounted() {
    axios
      .get('http://localhost:8080/api/watch-histories/stats')
      .then((response) => {
        this.stats = response.data;

        this.adCountData.labels = this.stats.map((stat) => stat.gender);
        this.adCountData.datasets[0].data = this.stats.map(
          (stat) => stat.adCount
        );

        this.avgAdTimeData.labels = this.stats.map((stat) => stat.gender);
        this.avgAdTimeData.datasets[0].data = this.stats.map(
          (stat) => stat.avgAdTime
        );

        this.loading = false;
      })
      .catch((error) => {
        this.error = true;
        this.errorMessage = '데이터를 불러오는 중 오류가 발생했습니다.';
        console.error('API 요청 오류:', error.response || error.message || error);
      });
  },
};
  </script>
  
  <style scoped>
  h1 {
    text-align: center;
  }
  
  h2 {
    text-align: center;
    margin-top: 20px;
  }
  
  .chart-container {
    display: flex;
    justify-content: space-around;
    align-items: center;
    margin: 20px 0;
  }
  
  .chart-item {
    width: 50%;
    height: 300px;
  }
  </style>
  