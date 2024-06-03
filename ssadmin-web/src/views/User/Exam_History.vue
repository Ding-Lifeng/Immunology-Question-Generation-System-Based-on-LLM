<template>
  <div id="history-test">
    <h1>历史试卷</h1>
    <h3>PS:被收藏的试卷会保存在用户信息下的优秀试卷界面</h3>
    <div class="cards-container">
      <div class="card" v-for="exam in exams" :key="exam.id">
        <div class="card-content">
          <h2>试卷题目: {{ exam.summary  }}</h2>
          <h3>日期: {{ exam.date }}</h3>
          <button @click="showDetails(exam)" class="details-button">详情</button>
          <button @click="addToFavorites(exam)" class="favorite-button">收藏</button>
        </div>
      </div>
    </div>
    <el-dialog :visible.sync="popup" width="75%">
      <component :is="currentComponent" :details="currentDetails"></component>
    </el-dialog>
  </div>
</template>

<script>
import 'element-ui/lib/theme-chalk/index.css';
import { Dialog } from 'element-ui';

export default {
  name: 'HistoryExams',
  components: {
    'el-dialog': Dialog,
  },
  data() {
    return {
      exams: [
        { id: 1, summary: '免疫学第一章' , date: '2024-01-01' },
        { id: 2, summary: '免疫学第二章' , date: '2024-02-15' },
        // 可以根据需求添加更多历史规划
      ],
      popup: false,
      currentComponent: null,
      currentDetails: {},
    };
  },
  methods: {
    async showDetails(exam) {
      const component = await import('./Side_Page/Exam_Generated.vue');
      this.currentComponent = component.default;
      this.currentDetails = exam;
      this.popup = true;
    },
    addToFavorites(exam) {
      // 添加收藏功能的逻辑
      console.log('收藏: ', exam);
      // 例如，可以将收藏的试卷保存在本地存储或发送到服务器
    },
  },
};
</script>

<style scoped>
#history-test {
  text-align: center;
  background-color: #f8f9fa;
  padding: 2rem;
  border-radius: 10px;
  width: 80%;
  margin: 2rem auto;
}

h1 {
  font-size: 2.5rem;
  font-weight: bold;
  margin-bottom: 2rem;
}

.cards-container {
  display: flex;
  flex-wrap: wrap;
  justify-content: center;
}

.card {
  background-color: white;
  border: 1px solid #ddd;
  border-radius: 10px;
  margin: 1rem;
  padding: 1rem;
  width: 300px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
}

.card-content {
  text-align: left;
}

.card-content h2 {
  font-size: 1.5rem;
  margin-bottom: 0.5rem;
}

.card-content p {
  font-size: 1rem;
  margin-bottom: 1rem;
}

.card-content button {
  padding: 0.5rem 1rem;
  border: none;
  border-radius: 5px;
  color: white;
  cursor: pointer;
  margin-right: 10px; /* 为按钮添加间隔 */
}

.details-button {
  background-color: #17a2b8;
}

.favorite-button {
  background-color: #dc3545; /* 黄色 */
  margin-right: 0; /* 去除多余的右边距 */
}
</style>
