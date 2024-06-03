<template>
  <div class="login-container">
    <div class="login-box">
      <!-- 返回按钮 -->
      <el-button class="back-button" @click="goBack" icon="el-icon-arrow-left"></el-button>
      <h1 class="title">Immunology-Test-Helper</h1>
      <div class="login-form">
        <h3>用户登录</h3>
        <el-form ref="form" :model="loginForm" :rules="rules" label-position="left" label-width="100px">
          <el-form-item label="用户名" prop="username">
            <el-input v-model="loginForm.username" placeholder="请输入用户名" ></el-input>
          </el-form-item>
          <el-form-item label="密码" prop="password">
            <el-input type="password" v-model="loginForm.password" placeholder="请输入密码" ></el-input>
          </el-form-item>
          <el-form-item class="button-group">
            <el-button type="primary" @click="do_login" :loading="loading">登录</el-button>
            <el-button type="success" @click="goToRegister">注册</el-button>
          </el-form-item>
        </el-form>
      </div>
    </div>
  </div>
</template>

<script>
import {login} from '@/api/login.js'
import { setToken } from '@/utils/auth'
export default {
  data() {
    return {
      loginForm: {
        username: 'user',
        password: '1234abc'
      },
      rules: {
        username: [{ required: true, message: '请输入用户名', trigger: 'blur' }],
        password: [{ required: true, message: '请输入密码', trigger: 'blur' }]
      },
      loading: false
    }
  },
  methods: {
    do_login() {
      this.$refs.form.validate(valid => {
        if (valid) {
          this.loading = true
          // 登录处理逻辑中增加网络请求
          login(this.loginForm.username,this.loginForm.password).then(res =>{
            // 假设res.data.token是从后端接口返回的token
            console.log(res.data.accessToken)
            setToken(res.data.accessToken)
            this.$router.push({ path: '/' })
          }).catch(() => {
            // 登录失败，显示错误提示
            this.$message.error('用户名或密码错误')
            this.loading = false
          })
        }
      })
    },
    goToRegister(){
      this.$router.push('/Submit');
    },
    goBack() {
      this.$router.push('/'); // 替换为实际的返回页面路径
    }
  }
}
</script>

<style lang="scss">
.login-container {
  display: flex;
  justify-content: center;
  align-items: center;
  min-height: 100vh; /* 确保容器高度为视口高度 */
  background: url('../assets/医学出题系统背景图.png') no-repeat center center; /* 添加背景图片 */
  background-size: cover; /* 确保背景图片覆盖整个容器 */
  position: relative; /* 使子元素中的伪元素定位相对于这个父元素 */

  .login-box {
    width: 460px;
    height: 60vh; /* 高度为页面的3/4 */
    border-radius: 5px;
    box-shadow: 0 0 10px #ccc;
    background-color: rgba(255, 255, 255, 0.9); /* 设置白色背景并增加透明度 */
    display: flex;
    flex-direction: column;
    justify-content: center; /* 垂直居中 */
    align-items: center; /* 水平居中 */
    position: relative; /* 使内容在伪元素之上 */

    .back-button {
      position: absolute;
      top: 10px;
      left: 10px;
      font-size: 16px;
      padding: 0 10px;
      background-color: transparent;
      border: none;
      color: #333;

      &:hover {
        color: #1e90ff;
      }
    }

    .title {
      text-align: center;
      font-size: 26px;
      margin-bottom: 5px;
    }

    .login-form {
      padding: 20px;

      h3 {
        font-size: 28px;
        margin-bottom: 20px;
        text-align: center;
      }

      .el-form-item__label {
        font-size: 20px; /* 增大标签字体 */
      }

      .el-input__inner {
        font-size: 20px; /* 增大输入框字体 */
      }

      .el-button {
        font-size: 16px; /* 增大按钮字体 */
      }
    }
  }
}
</style>
