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
        <button class="clear-button" @click="clearSelection">清除所选题目</button>
        <button class="generate-button" @click="openDialog">题目生成</button>
      </div>

      <!-- 弹出页面 -->
      <el-dialog :visible.sync="popup" width="75%" title="生成结果">
        <component :is="currentComponent"></component>
      </el-dialog>
    </div>
  </div>
</template>

<script>
import 'element-ui/lib/theme-chalk/index.css';
import { Dialog, Cascader, Input, Table, TableColumn, Button, Select, Option } from 'element-ui';

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
      selectedOptions: [],
      search: '',
      cascaderOptions: [
        {
          value: 'knowledge-point-1',
          label: '知识点1',
          children: [
            {
              value: 'disease-1',
              label: '疾病1',
            },
            {
              value: 'disease-2',
              label: '疾病2',
            },
          ],
        },
        {
          value: 'knowledge-point-2',
          label: '知识点2',
          children: [
            {
              value: 'disease-3',
              label: '疾病3',
            },
            {
              value: 'disease-4',
              label: '疾病4',
            },
          ],
        },
      ],
      selectedItems: [],
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
              questionType: '请选择题型',
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
    openDialog() {
      this.popup = true;
      this.currentComponent = '/rbac/user/exam-generated';
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