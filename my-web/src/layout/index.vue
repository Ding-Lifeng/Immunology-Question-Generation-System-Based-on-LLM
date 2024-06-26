<template>
  <div>
      <div class="side-bar">
            <el-menu class="el-menu-vertical-demo" background-color="#545c64" text-color="#fff" active-text-color="#ffd04b">
              <div class="user-avatar-container" @click="handleAvatarClick">
                <div class="demo-type">
                  <div>
                    <el-avatar> user </el-avatar>
                  </div>
                </div>
              </div>
              <el-menu-item index="/" @click="$router.push('/')">
                <i class="el-icon-document"></i>
                <span slot="title">智能出题</span>
              </el-menu-item>
              <el-menu-item index="/user/exam-history" @click="$router.push('/user/exam-history')">
                <i class="el-icon-collection"></i>
                <span slot="title">出卷历史</span>
              </el-menu-item>
              <el-menu-item index="/user/change-info" @click="$router.push('/user/change-info')">
                <i class="el-icon-user"></i>
                <span slot="title">用户信息</span>
              </el-menu-item>
              <el-menu-item index="/logout" @click="logout">
                <i class="el-icon-setting"></i>
                <span slot="title">退出</span>
              </el-menu-item>
          </el-menu>

          <div class="main-content">
              <router-view/>
          </div>
      </div>
  </div>
</template>

<script>
import { getAccessToken,removeToken } from '@/utils/auth'

export default {
    data(){
      return{
        userAvatar: '',
        isLoggedIn: false
      };
    },
    created() {
      this.isLoggedIn = !!getAccessToken();
    },
    methods: {
      handleAvatarClick() {
        if (this.isLoggedIn) {
          this.$router.push('/user/change-info');
        } else {
          this.$router.push('/login');
        }
      },
      logout() {
        this.$confirm('确定注销并退出系统吗？', '提示').then(() => {
          removeToken() // 清除token
          this.$router.push({ path: '/login' }) // 重定向到登录页面
        }).catch(() => {});
      },
    },
};
</script>


<style lang="scss" scoped>
.side-bar {
  display: flex;
  height: 100vh;
}

.user-avatar-container {
  cursor: pointer;
}

.demo-type {
  display: flex;
  align-items: center;
  justify-content: center;
  margin-top: 20px;
}

.menu-item-text {
  display: block;
  margin-top: 20px;
}

.main-content {
flex: 1;
padding: 20px;
}

</style>
