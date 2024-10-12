<script setup>
import axios from 'axios'
import { ref, onMounted } from 'vue'

const adPackages = ref([])

onMounted(async () => {
  try {
    const response = await axios.get('/api/adpackages')
    adPackages.value = response.data
  } catch (error) {
    console.error('Error fetching ad packages:', error)
  }
})
</script>

<template>
  <div class="ad-package-list">
    <h2>Ad Package List</h2>
    <table>
      <thead>
        <tr>
          <th>Name</th>
          <th>On Time</th>
          <th>Price</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="adPackage in adPackages" :key="adPackage.packageId">
          <td>{{ adPackage.name }}</td>
          <td>{{ adPackage.onTime.join(', ') }}</td>
          <td>{{ adPackage.price.join(', ') }}</td>
        </tr>
      </tbody>
    </table>
  </div>
</template>

<style scoped>
.ad-package-list {
  margin: 20px;
}

table {
  width: 100%;
  border-collapse: collapse;
}

th, td {
  border: 1px solid #ddd;
  padding: 8px;
}

th {
  background-color: #f2f2f2;
}
</style>
