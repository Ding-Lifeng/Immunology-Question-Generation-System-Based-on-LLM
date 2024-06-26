<template>
  <div id="generated-exams">
    <div class="header">
      <h2>试题</h2>
      <div class="button-group-header">
        <el-button type="primary" @click="saveExams">保存试题</el-button>
        <el-button type="success" @click="downloadExams">下载试题</el-button>
      </div>
    </div>
    <div class="cards-container">
      <div class="card" v-for="(exam, index) in parsedExams" :key="index">
        <div class="card-content">
          <p v-if="!exam.isEditing" v-html="exam.content"></p>
          <el-input
              v-if="exam.isEditing"
              type="textarea"
              v-model="exam.content"
              size="large"
              autosize
              class="edit-textarea"
          ></el-input>
          <div class="button-group">
            <el-button v-if="exam.isEditing" type="success" icon="el-icon-check" circle @click="endEditing(exam)"></el-button>
            <el-button type="primary" icon="el-icon-edit" circle @click="startEditing(exam)"></el-button>
            <el-button type="danger" icon="el-icon-delete" circle @click="clearContent(exam)"></el-button>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { saveExamContent } from '@/api/dealWithExamContent';

export default {
  name: 'Exam_Generated',
  props: {
    examData: {
      type: String,
      required: true
    },
    keyPoints:{
      type: String,
      required: true
    }
  },
  data() {
    return {
      parsedExams: [],
    };
  },
  created() {
    this.parseExamData();
  },
  methods: {
    async saveExams() {
      // 保存试题的逻辑
      try {
        await saveExamContent(this.examData ,this.keyPoints); // 调用 saveExamContent 方法
        console.log('保存试题成功!');
        this.$message.success('试题保存成功!');
      } catch (error) {
        console.error('保存试题失败:', error);
        this.$message.error('试题保存失败!');
      }
    },
    downloadExams() {
      // 下载试题的逻辑
      const blob = new Blob([this.parsedExams.map(e => e.content).join('\n\n')], { type: 'text/plain;charset=utf-8' });
      const url = URL.createObjectURL(blob);
      const a = document.createElement('a');
      a.href = url;
      a.download = 'exams.txt';
      a.click();
      URL.revokeObjectURL(url);
      console.log('下载试题');
    },
    parseExamData() {
      // 使用双换行符和换行符分割试题内容
      this.parsedExams = this.examData.split(/\n\s*\n/).map(content => ({
        content: content.trim().split('\n').map(line => line.trim()).join('<br>'),
        isEditing: false,
      }));
    },
    startEditing(exam) {
      exam.isEditing = true;
    },
    endEditing(exam) {
      exam.isEditing = false;
    },
    clearContent(exam) {
      this.parsedExams = this.parsedExams.filter(e => e !== exam);
    }
  }
}
</script>

<style scoped>
#generated-exams {
  text-align: center;
  background-color: #f8f9fa;
  padding: 2rem;
  border-radius: 10px;
  width: 80%;
  margin: 2rem auto;
}

h2 {
  font-size: 2rem;
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
  overflow-wrap: break-word; /* 增加此行以处理长文本 */
}

.card-content {
  text-align: left;
  position: relative;
  padding-bottom: 4rem; /* 增加底部填充距离 */
}

.button-group {
  position: absolute;
  right: 1rem;
  bottom: 1rem; /* 调整到距离底部更远的位置 */
  display: flex;
  gap: 0.5rem; /* 按钮之间的间距 */
}

.card-content p {
  font-size: 1rem;
  margin-bottom: 1rem;
}

.card-content .edit-textarea {
  width: 100%;
}

.header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.button-group-header {
  display: flex;
  gap: 1rem;
}
</style>