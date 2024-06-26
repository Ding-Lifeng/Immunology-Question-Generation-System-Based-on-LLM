<template>
  <div id="history-tests">
    <h1>历史试卷</h1>
    <div class="cards-container">
      <div class="card" v-for="exam in exams" :key="exam.exam_id">
        <div class="card-content">
          <h2>出题时间:
            <span v-if="!exam.isEditing">{{ exam.time }}</span>
            <el-input v-if="exam.isEditing" v-model="exam.time" size="small"></el-input>
          </h2>
          <p v-if="!exam.isEditing">{{ exam.keyPoints }}</p>
          <el-input
              v-if="exam.isEditing"
              type="textarea"
              v-model="exam.content"
              size="large"
              autosize
              class="edit-textarea"
          ></el-input>
          <!-- 修改后的按钮 -->
          <el-button type="primary" icon="el-icon-edit" circle @click="showExamDetails(exam)"></el-button>
          <el-button type="danger" icon="el-icon-delete" circle @click="deleteExam(exam.exam_id)"></el-button>
          <el-button type="success" icon="el-icon-download" circle @click="downloadExam(exam)"></el-button>
        </div>
      </div>
    </div>

    <!-- 弹出页面 -->
    <el-dialog :visible.sync="dialogVisible" width="50%" :before-close="handleClose">
      <template v-if="selectedExam">
        <h2>出题时间: {{ selectedExam.time }}</h2>
        <h3>{{ selectedExam.keyPoints }}</h3>
        <div class="cards-container">
          <div class="card" v-for="(content, index) in parsedExams" :key="index">
            <div class="card-content">
              <div v-if="!content.isEditing" v-html="content.content"></div>
              <el-input
                  v-if="content.isEditing"
                  type="textarea"
                  v-model="content.content"
                  size="large"
                  autosize
                  class="edit-textarea"
              ></el-input>
              <div class="button-group">
                <el-button v-if="content.isEditing" type="success" icon="el-icon-check" circle @click="endEditing(content)"></el-button>
                <el-button type="primary" icon="el-icon-edit" circle @click="startEditing(content)"></el-button>
                <el-button type="danger" icon="el-icon-delete" circle @click="clearContent(content)"></el-button>
              </div>
            </div>
          </div>
        </div>
        <div slot="footer" class="dialog-footer">
          <el-button type="primary" @click="saveExams">保存修改</el-button>
        </div>
      </template>
    </el-dialog>
  </div>
</template>

<script>
import 'element-ui/lib/theme-chalk/index.css';
import { Dialog } from 'element-ui';
import { getExams, deleteExam, updateExam } from '@/api/dealWithExamContent';

export default {
  name: 'Exam_History',
  components: {
    'el-dialog': Dialog,
  },
  data() {
    return {
      exams: [],
      dialogVisible: false,
      selectedExam: null,
      parsedExams: [],
    };
  },
  async created() {
    try {
      const response = await getExams();
      this.exams = response.data.map(exam => ({
        ...exam,
        isEditing: false,
      }));
    } catch (error) {
      console.error('获取试卷内容失败!', error);
    }
  },
  methods: {
    startEditing(content) {
      content.isEditing = true;
    },
    endEditing(content) {
      content.isEditing = false;
    },
    async deleteExam(examId) {
      try {
        await deleteExam(examId);
        this.exams = this.exams.filter(exam => exam.exam_id !== examId);
        this.$message.success('试卷删除成功!');
      } catch (error) {
        console.error('删除试卷失败!', error);
      }
    },
    showExamDetails(exam) {
      this.selectedExam = exam;
      this.dialogVisible = true;
      this.parseExamData();
    },
    handleClose() {
      this.dialogVisible = false;
    },
    parseExamData() {
      if (this.selectedExam) {
        this.parsedExams = this.selectedExam.content.split(/\n\s*\n/).map(content => ({
          content: content.trim().split('\n').map(line => line.trim()).join('<br>'),
          isEditing: false,
        }));
      }
    },
    clearContent(content) {
      this.parsedExams = this.parsedExams.filter(c => c !== content);
    },
    downloadExam(exam) {
      const blob = new Blob([exam.content], { type: 'text/plain;charset=utf-8' });
      const url = URL.createObjectURL(blob);
      const link = document.createElement('a');
      link.href = url;
      link.setAttribute('download', `exam_${exam.exam_id}.txt`);
      document.body.appendChild(link);
      link.click();
      document.body.removeChild(link);
      URL.revokeObjectURL(url);
    },
    async saveExams() {
      if (this.selectedExam) {
        this.selectedExam.content = this.parsedExams.map(c => c.content.replace(/<br>/g, '\n')).join('\n\n');
        try {
          await updateExam(this.selectedExam);
          this.$message.success('试卷内容修改成功!');
          this.dialogVisible = false;
        } catch (error) {
          this.$message.error('试卷内容修改失败!');
          console.error('修改试卷内容失败!', error);
        }
      }
    }
  }
};
</script>

<style scoped>
#history-tests {
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
  color: white;
  cursor: pointer;
}

.dialog-footer {
  text-align: right;
}

.edit-textarea {
  width: 100%;
}
</style>
