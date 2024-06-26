# SSAdmin

本项目参考了以下开源项目：

- RuoYi-Vue 全新 Pro 版本： https://gitee.com/zhijiantianya/ruoyi-vue-pro
- eladmin-jpa 版本：https://gitee.com/elunez/eladmin

对以上开源项目及贡献者表示感谢！

## 介绍

SSAdmin 是一个基于 Spring Boot、Mybatis、Spring Security、Mysql、Redis 和 Vue2 的前后端分离的通用后台管理系统。

项目以教学为主要目的，帮助开发者更好地学习和理解当前流行的开发技术和实践。

## 主要模块

- 用户管理：
- 角色管理：
- 权限管理：
- 菜单管理：
- 字典管理：
- 站点管理：

## 技术栈

- Springboot: 2.7.17
- Lombok: 1.18.20
- MyBatis: 2.2.0
- vue: 2.6.14
- vue-router: 3.5.1
- vuex: 3.6.2

## 部署说明

## 参与贡献

1. Fork 本仓库
2. 新建 Feat_xxx 分支
3. 提交代码
4. 新建 Pull Request

## 🚀 版本清单及说明

### 🌟 当前版本 v0.2

#### 🚀 New Features

- **后端**
  - 🔰 **Common 模块**: 整合公共功能，如统一返回处理、异常处理等。
  - 🔰 **Security 模块**: 提供`token` 生成、认证功能。
  - 🔰 **System 模块**: 管理用户、角色和权限等基础资源。
  - 🛠 **用户登录**: 实现后台管理用户登录及刷新 `token` 接口。

- **前端**
  - 🎨 **Layout 布局**: 定义了基础的页面布局和导航。
  - 🌐 **封装Axios**: 提供与后端 API 的通信功能。
  - 🔐 **用户认证**: 包括用户登录、登出和 token 管理。
  - 🚦 **路由守卫**: 控制页面的访问权限。

- **通用**
  - 🌐 **多环境支持**: 提供了开发、测试和生产环境的配置。
  - 🔧 **模块化设计**: 按功能模块组织代码，提高代码的可维护性。

#### 🐞 Bug Fixes

- 无。

#### 🧩 Dependency Upgrades

- **后端**
  - **MyBatis**: mybatis-spring-boot-starter 2.2.0
  - **Lombok**: 1.18.24
  - **Knife4j**: knife4j-openapi2-spring-boot-starter 4.3.0
  - **JWT**: jjwt 0.9.1
  - **BCrypt**: jbcrypt 0.4

- **前端**
  - **Element-UI**: 2.15.14
  - **Axios**: 1.6.0

#### 🗃 Database

- **MySQL**
  - 新增:
    - **用户表** (system_users)
  - 修改:
    - 无

### 🌟 版本 v0.1 （2023-10-26）

#### 🚀 New Features

- **项目初始化**
  - 建立基础的框架和结构，删除HelloWorld及about组件。

#### 🐞 Bug Fixes

- 无。

#### 🧩 Dependency Upgrades

- **后端**
  - **Spring Boot**: 2.7.17

- **前端**
  - **vue**: 2.15.14
  - **vue-router**: 3.5.1
  - **vuex**: 3.6.2
