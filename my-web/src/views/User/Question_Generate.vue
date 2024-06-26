<template>
  <div>
    <div id="welcome-screen">
      <div class="welcome-container">
        <div class="welcome-text">
          <h2>人工出题太烦啦!</h2>
          <h1>我们让 <span>AI</span> 来出题</h1>
          <p>只需要简单地选择知识点/疾病名称+出题题型，就可以一键生成医学试题，不用再为繁琐的出题工作烦恼~</p>
        </div>
        <div class="welcome-image">
          <img src="../../assets/出题页面.png" alt="Welcome Image">
        </div>
      </div>
    </div>

    <div id="Question-Generate">
      <!-- 标题和描述 -->
      <h1>Immunology-Test-Helper</h1>
      <p>免疫学智能出题系统</p>

      <!-- 级联选择器和添加按钮 -->
      <div class="cascader-container">
        <el-cascader
            v-model="selectedOptions"
            :options="cascaderOptions"
            :props="{ multiple: true }"
            placeholder="请选择知识点/疾病名称"
            style="margin: 1rem 0;"
        ></el-cascader>
        <el-button type="primary" @click="addSelectedKey" style="margin-left: 1rem;">添加知识点</el-button>
      </div>

      <!-- 搜索框 -->
      <el-input
          v-model="search"
          placeholder="搜索已选题目"
          prefix-icon="el-icon-search"
          style="margin: 1rem 0;"
      ></el-input>

      <!-- 选择结果列表 -->
      <el-table :data="filteredSelectedItems" style="width: 100%">
        <el-table-column prop="label" label="题目"></el-table-column>
        <el-table-column label="题目数量">
          <template v-slot="scope">
            <el-input v-model="scope.row.count" type="text" min="1"></el-input>
          </template>
        </el-table-column>
        <el-table-column label="题型">
          <template v-slot="scope">
            <el-select v-model="scope.row.questionType" placeholder="请选择题型">
              <el-option label="A1型单选题" value="A1型单选题"></el-option>
              <el-option label="A2型单选题" value="A2型单选题"></el-option>
              <el-option label="简答题" value="简答题"></el-option>
            </el-select>
          </template>
        </el-table-column>
        <el-table-column label="操作">
          <template v-slot="scope">
            <el-button size="mini" type="danger" @click="removeItem(scope.$index)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>

      <!-- 按钮 -->
      <div class="button-container">
        <el-button class="clear-button" @click="clearSelection">清除所选题目</el-button>
        <el-button class="generate-button" :loading="isLoading" @click="generateExam">题目生成</el-button>
      </div>

      <!-- 弹出页面并传递信息 -->
      <el-dialog :visible.sync="popup" width="75%" title="生成结果">
        <component ref="dynamicComponent" :is="currentComponent" :examData="data" :keyPoints="keyPoints"></component>
      </el-dialog>
    </div>
  </div>
</template>

<script>
import 'element-ui/lib/theme-chalk/index.css';
import { Dialog, Cascader, Input, Table, TableColumn, Button, Select, Option } from 'element-ui';
import { cascaderOptions } from '@/utils/keyPoints';
import { sendMessageToGpt } from '@/api/chatWithGpt';
import { v4 as uuidv4 } from 'uuid';

export default {
  name: 'Question-Generate',
  components: {
    'el-dialog': Dialog,
    'el-cascader': Cascader,
    'el-input': Input,
    'el-table': Table,
    'el-table-column': TableColumn,
    'el-button': Button,
    'el-select': Select,
    'el-option': Option,
  },
  data() {
    return {
      popup: false,
      currentComponent: null,
      data: null,
      keyPoints: null,
      selectedOptions: [],
      search: '',
      cascaderOptions,  // 免疫学知识点选择
      selectedItems: [],
      sessionId: uuidv4(),
      isLoading: false, // 新增加载状态
    };
  },
  computed: {
    filteredSelectedItems() {
      return this.selectedItems.filter(item =>
          item.label.toLowerCase().includes(this.search.toLowerCase())
      );
    },
  },
  methods: {
    findOption(options, value) {
      for (let option of options) {
        if (option.value === value) {
          return option;
        }
        if (option.children) {
          const found = this.findOption(option.children, value);
          if (found) {
            return found;
          }
        }
      }
      return null
    },
    addSelectedKey() {
      if (this.selectedOptions.length) {
        this.selectedOptions.forEach(optionPath => {
          const item = this.findOption(this.cascaderOptions, optionPath[optionPath.length - 1]);
          if (item) {
            this.selectedItems.push({
              label: item.label,
              count: 1,
              questionType: "A1型单选题",
            });
          }
        });
        this.selectedOptions = [];  // 清空选中的内容
      }
    },
    clearSelection() {
      this.selectedOptions = [];
      this.selectedItems = [];
    },
    async generateExam() {
      this.isLoading = true; // 设置加载状态为 true
      // 拼接知识点信息、题目数量和题型信息
      const examContent = this.selectedItems.map(item =>
          `知识点: ${item.label}, 数量: ${item.count}, 题型: ${item.questionType}`
      ).join('\n');

      // 传递题目信息
      this.keyPoints = examContent;

      // 增加题目类型介绍和出题引导
      let frontInfo = '请按以下要求生成相应的免疫学题目';
      let backInfo = '其中A1型单选题由1个题干和5个选项组成，题干通常为简明扼要的陈述句，备选答案中只有1个为最佳选项；' +
          'A2型单选题，又称为情景型最佳选择题试题由1个题干和5个备选答案组成。题干通常为简单病历或者情境的描述，备选答案中只有1个为最佳选项；' +
          '简答题，以病例、情景型案例等为题干，考核学生分析问题和解决问题的能力。';
      const fullContent = `${frontInfo}\n${examContent}\n${backInfo}`
      // console.log(fullContent);

      // 调用 sendMessageToGpt 函数发送请求
      try {
        const response = await sendMessageToGpt(fullContent, this.sessionId);
        // 动态加载 Exam_Generated.vue 组件并显示返回结果
        const component = await import('./Side_Page/Exam_Generated.vue');
        this.currentComponent = component.default;
        this.popup = true;
        // 将响应结果传递给 Exam_Generated 组件
        this.data = response.data.content;
        this.$message.success('题目生成成功!');
      } catch (error) {
        this.$message.error('题目生成失败!');
        console.error('Error generating exam:', error);
      } finally {
        this.isLoading = false; // 设置加载状态为 false
      }
    },
    removeItem(index) {
      this.selectedItems.splice(index, 1);
    },
  }
}
</script>

<style scoped>
  #welcome-screen {
    text-align: center;
    padding: 2rem;
    border-bottom: 1px solid #ccc;
    margin-bottom: 2rem;
    height: 25vh; /* 限制高度为页面的1/4 */
    display: flex;
    flex-direction: column; /* 改为纵向排列 */
    justify-content: space-between; /* 垂直居中 */
    overflow: hidden; /* 确保内容不会超出容器 */
  }

  .welcome-container {
    display: flex;
    flex-direction: row;
    justify-content: center;
    align-items: center;
    height: 100%; /* 确保容器填满父元素 */
    overflow: hidden; /* 防止内容溢出 */
  }

  .welcome-text {
    flex: 1;
    padding: 1rem;
    overflow: hidden; /* 防止内容溢出 */
  }

  .welcome-text h1 {
    font-size: 1.5rem; /* 调整字体大小 */
    margin: 0.5rem 0;
  }

  .welcome-text h2 {
    font-size: 1.2rem; /* 调整字体大小 */
    margin: 0.5rem 0;
  }

  .welcome-text p {
    font-size: 0.9rem; /* 调整字体大小 */
    color: #666;
    margin: 0.5rem 0;
  }

  .welcome-image {
    flex: 1;
    display: flex;
    justify-content: center;
    align-items: center;
    padding: 1rem;
    overflow: hidden; /* 防止内容溢出 */
  }

  .welcome-image > * {
    max-width: 100%;
    max-height: 100%; /* 确保图片保持比例 */
    height: auto;
    width: auto;
    object-fit: contain; /* 确保图片适应容器 */
  }

  #Question-Generate {
    text-align: center;
    background-color: #2ccfe8;
    padding: 2rem;
    border-radius: 10px;
    width: 80%;
    margin: 2rem auto;
    color: white;
  }

  .cascader-container {
    display: flex;
    align-items: center;
  }

  h1 {
    font-size: 2.5rem;
    font-weight: bold;
    margin: 0;
  }

  p {
    font-size: 1.2rem;
    margin: 1rem 0;
  }

  input[type="text"] {
    width: 60%;
    padding: 1rem;
    border-radius: 30px 0 0 30px;
    border: none;
    outline: none;
    font-size: 1rem;
  }

  .action-button i {
    margin-right: 0.5rem;
  }

  .button-container {
    display: flex;
    justify-content: space-around;
    margin-top: 2rem;
  }

  .clear-button,
  .generate-button {
    padding: 1rem 2rem;
    border: none;
    border-radius: 5px;
    cursor: pointer;
  }

  .clear-button {
    background-color: #dc3545;
    color: white;
  }

  .generate-button {
    background-color: #40e164;
    color: white;
  }
</style>