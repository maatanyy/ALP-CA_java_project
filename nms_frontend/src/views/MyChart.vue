<script>
// 차트를 구성하는 요소를 모듈 가져오기 
import axios from 'axios';
import {
    Chart as ChartJS, // as 별칭, 차트 본체
    // 나머지 요소는 차트를 구성하는 요소:제목, 툴팁, 범례,...
    Title,
    Tooltip,
    Legend,
    BarElement,
    CategoryScale,
    LinearScale
} from 'chart.js'

// 구체화된 실제 차트 -> 바차트
import { Bar } from 'vue-chartjs'

// 차트에 요소들 등록
ChartJS.register(CategoryScale, LinearScale, BarElement, Title, Tooltip, Legend)

// 컴포넌트 구성
export default {
    name: 'App',
    components: {
        Bar
    },
    data() {
        console.log( "데이터 세팅" )
        return {
            loading:true, 
            category: {
                labels: [], // 항목 (집계기준 카테고리값)
                datasets: [
                    {
                        label:"차트",
                        backgroundColor:'#f83823',
                        data:[] // 특정항목(카운트, 평균가격,..)
                    }
                ]
            },
            options: {
                maintainAspectRatio: false,
                responsive: true,
                scales: {
                    y: {
                        min: 0, 
                        max: null, 
                    }
                }
            }
        }
    },
    mounted () {
        console.log( "컴포넌트 mounted call" )
        try {
        setTimeout(async () => {
            // Spring Boot API 호출
            const { data } = await axios.get('http://localhost:8080/api/companies/total-fees');
            console.log(data);

            // 총 비용 기준으로 오름차순 정렬
            const sortedData = data.sort((a, b) => a.totalFee - b.totalFee);

            const values = sortedData.map(company => company.totalFee);
            const minValue = Math.min(...values);
            const maxValue = Math.max(...values);

            // 차트 데이터 설정
            this.category = {
                labels: sortedData.map(company => company.name),
                datasets: [
                    {
                        label: "회사별 총 비용",
                        backgroundColor: '#f83823',
                        data: values
                    }
                ]
            };

            // Y축 범위 설정
            this.options.scales.y.min = 0;
            this.options.scales.y.max = maxValue;

        }, 1000 * 0.5);
    } catch (error) {
        console.log(error);
    }
}

}
</script>

<template>
    <div style="width: 100%; height: 200px;">
        <Bar v-bind:data="category" :options="options"/>
        <div v-if="!loading">로딩중</div>
    </div>
</template>

<style></style>
