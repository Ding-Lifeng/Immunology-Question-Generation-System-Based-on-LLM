# **数据库表结构设计**

## **核心表结构及说明**

### **用户表** - `system_user`

存储用户的基本信息，包括用户名、密码、联系方式等，还有用户的状态，创建和更新信息。每个用户都有一个唯一的ID。

| 名称            | 数据类型   | 长度 | 允许空值 | 默认值 | 说明       |
|-----------------|------------|------|----------|--------|------------|
| id              | bigint     | 20   | N        |        | 用户编码   |
| username        | varchar    | 64   | N        |        | 用户名     |
| password        | varchar    | 64   | N        |        | 密码       |
| real_name       | varchar    | 64   | Y        |        | 真实姓名   |
| avatar          | varchar    | 256  | Y        |        | 头像       |
| email           | varchar    | 128  | Y        |        | 邮箱       |
| mobile          | varchar    | 20   | Y        |        | 手机号     |
| status          | tinyint    | 4    | N        | 0      | 状态       |
| create_time     | datetime   |      | N        | CURRENT_TIMESTAMP | 创建时间   |
| update_time     | datetime   |      | N        | CURRENT_TIMESTAMP | 更新时间   |
| last_login_time | datetime   |      | Y        |        | 最后登录时间 |
| creator         | varchar    | 64   | Y        |        | 创建者     |
| updater         | varchar    | 64   | Y        |        | 更新者     |
| deleted         | bit        | 1    | N        | b'0'   | 是否删除   |

### **角色表** - `system_role`

定义系统中的角色，角色可以被分配不同的权限。角色也包括创建者和更新时间等信息。

| 名称            | 数据类型   | 长度 | 允许空值 | 默认值 | 说明       |
|-----------------|------------|------|----------|--------|------------|
| id              | bigint     | 20   | N        |        | 角色编码   |
| name            | varchar    | 100  | N        |        | 角色名称   |
| remark          | varchar    | 500  | Y        |        | 备注       |
| creator         | varchar    | 64   | Y        |        | 创建者     |
| create_time     | datetime   |      | N        | CURRENT_TIMESTAMP | 创建时间   |
| updater         | varchar    | 64   | Y        |        | 更新者     |
| update_time     | datetime   |      | N        | CURRENT_TIMESTAMP | 更新时间   |
| deleted         | bit        | 1    | N        | b'0'   | 是否删除   |

### **用户角色关联表** - `system_user_role`

用户和角色之间的关联表，一个用户可以有多个角色，一个角色可以被分配给多个用户。

| 名称        | 数据类型 | 长度 | 允许空值 | 默认值              | 说明       |
|-------------|----------|------|----------|---------------------|------------|
| id          | bigint   | 20   | N        |                     | 自增编号   |
| user_id     | bigint   | 20   | N        |                     | 用户ID     |
| role_id     | bigint   | 20   | N        |                     | 角色ID     |
| creator     | varchar  | 64   | Y        |                     | 创建者     |
| create_time | datetime |      | Y        | CURRENT_TIMESTAMP   | 创建时间   |
| updater     | varchar  | 64   | Y        |                     | 更新者     |
| update_time | datetime |      | Y        | CURRENT_TIMESTAMP   | 更新时间   |
| deleted     | bit      | 1    | Y        | b'0'                | 是否删除   |

### **菜单权限表** - `system_menu`

在`system_menu`表中，`type`字段用于区分是菜单项还是按钮/权限标识。

| 名称        | 数据类型 | 长度 | 允许空值 | 默认值              | 说明         |
|-------------|----------|------|----------|---------------------|--------------|
| id          | bigint   | 20   | N        |                     | 菜单ID       |
| parent_id   | bigint   | 20   | N        | 0                   | 父菜单ID     |
| name        | varchar  | 50   | N        |                     | 菜单名称     |
| path        | varchar  | 200  | Y        |                     | 菜单路径     |
| component   | varchar  | 255  | Y        |                     | 前端组件地址 |
| perms       | varchar  | 100  | Y        |                     | 权限标识     |
| type        | int      |      | N        |                     | 菜单类型     |
| icon        | varchar  | 50   | Y        |                     | 菜单图标     |
| order_num   | int      |      | N        |                     | 菜单排序     |
| status      | tinyint  | 1    | N        | 0                   | 菜单状态     |
| create_by   | bigint   | 20   | Y        |                     | 创建者       |
| create_time | datetime |      | N        | CURRENT_TIMESTAMP   | 创建时间     |
| update_by   | bigint   | 20   | Y        |                     | 更新者       |
| update_time | datetime |      | Y        | CURRENT_TIMESTAMP   | 更新时间     |
| remark      | varchar  | 500  | Y        |                     | 备注         |

### **角色权限表** - `system_role_menu`

将`system_menu`表中定义的菜单或权限项与角色关联起来。

| 名称        | 数据类型 | 长度 | 允许空值 | 默认值              | 说明       |
|-------------|----------|------|----------|---------------------|------------|
| role_id     | bigint   | 20   | N        |                     | 角色ID     |
| menu_id     | bigint   | 20   | N        |                     | 菜单ID     |
| create_by   | bigint   | 20   | Y        |                     | 创建者     |
| create_time | datetime |      | N        | CURRENT_TIMESTAMP   | 创建时间   |
| update_by   | bigint   | 20   | Y        |                     | 更新者     |
| update_time | datetime |      | Y        | CURRENT_TIMESTAMP   | 更新时间   |

### **操作日志表** - `system_operation_log`

记录用户的操作历史。

| 名称           | 数据类型 | 长度 | 允许空值 | 默认值              | 说明               |
|----------------|----------|------|----------|---------------------|--------------------|
| id             | bigint   | 20   | N        |                     | 日志编号           |
| user_id        | bigint   | 20   | N        |                     | 操作用户ID         |
| operation      | varchar  | 255  | N        |                     | 操作描述           |
| method         | varchar  | 100  | N        |                    | 请求方法           |
| request_uri    | varchar  | 255  | N        |                     | 请求URI            |
| request_param  | text     |      | Y        |                     | 请求参数           |
| request_type   | varchar  | 10   | N        |                     | 请求类型           |
| user_agent     | varchar  | 255  | N        |                     | 用户代理信息       |
| ip             | varchar  | 64   | N        |                     | IP地址             |
| cost_time      | int      |      | N        |                     | 请求处理时间（毫秒）|
| create_time    | datetime |      | N        | CURRENT_TIMESTAMP   | 创建时间           |
| remark         | varchar  | 500  | Y        |                     | 备注               |

### **字典类型表** - `system_dict_type`

此表用于定义不同的字典类型，每种类型可以包含多个字典数据项。

| 名称         | 数据类型  | 长度 | 允许空值 | 默认值              | 说明         |
|--------------|-----------|------|----------|---------------------|--------------|
| id           | bigint    | 20   | N        |                     | 类型主键ID   |
| name         | varchar   | 100  | N        |                     | 类型名称     |
| type         | varchar   | 100  | N        |                     | 类型代码     |
| status       | tinyint   | 1    | N        | 1                   | 状态(0禁用 1启用) |
| create_by    | bigint    | 20   | Y        |                     | 创建者       |
| create_time  | datetime  |      | N        | CURRENT_TIMESTAMP   | 创建时间     |
| update_by    | bigint    | 20   | Y        |                     | 更新者       |
| update_time  | datetime  |      | Y        | CURRENT_TIMESTAMP   | 更新时间     |
| remark       | varchar   | 255  | Y        |                     | 备注         |

### **字典数据表** - `system_dict_data`

此表用于存储每个类型下的字典数据项。

| 名称         | 数据类型  | 长度 | 允许空值 | 默认值              | 说明         |
|--------------|-----------|------|----------|---------------------|--------------|
| id           | bigint    | 20   | N        |                     | 数据主键ID   |
| type_id      | bigint    | 20   | N        |                     | 字典类型ID   |
| label        | varchar   | 100  | N        |                     | 数据标签     |
| value        | varchar   | 100  | N        |                     | 数据值       |
| order_num    | int       |      | N        |                     | 排序号       |
| status       | tinyint   | 1    | N        | 1                   | 状态(0禁用 1启用) |
| create_by    | bigint    | 20   | Y        |                     | 创建者       |
| create_time  | datetime  |      | N        | CURRENT_TIMESTAMP   | 创建时间     |
| update_by    | bigint    | 20   | Y        |                     | 更新者       |
| update_time  | datetime  |      | Y        | CURRENT_TIMESTAMP   | 更新时间     |

### **登录日志表** - `system_login_log`

此表用于记录用户登录系统的活动，包括成功和失败的尝试。

| 名称             | 数据类型  | 长度 | 允许空值 | 默认值              | 说明               |
|------------------|-----------|------|----------|---------------------|--------------------|
| id               | bigint    | 20   | N        |                     | 日志主键ID         |
| user_id          | bigint    | 20   | Y        |                     | 登录用户ID         |
| username         | varchar   | 64   | Y        |                     | 登录用户名         |
| status           | tinyint   | 1    | N        |                     | 登录状态(0失败 1成功) |
| ip_addr          | varchar   | 128  | Y        |                     | 登录IP地址         |
| login_location   | varchar   | 255  | Y        |                     | 登录地点           |
| browser          | varchar   | 100  | Y        |                     | 登录时使用的浏览器 |
| os               | varchar   | 100  | Y        |                     | 登录时使用的操作系统 |
| login_time       | datetime  |      | N        | CURRENT_TIMESTAMP   | 登录时间           |
| message          | varchar   | 255  | Y        |                     | 登录消息（如登录失败原因）|

### **备注解释**

- `status` 字段表示的是当前条目的状态，其中0代表正常，1代表停用。
- `deleted`字段是一个布尔值，表示记录是否被标记为删除。在很多系统中，不是真正地从数据库中删除记录，而是将该字段标记为`true`（或1），这样可以保留记录的历史数据，同时在查询时可以排除这些记录。
- `creator`和`updater`字段分别用来记录创建者和更新者的用户名，用于追踪记录的生命周期。
- `create_time`和`update_time`字段用来存储记录被创建和最后更新的时间。通常这两个字段在记录创建时被自动设置，`update_time`在每次记录更新时被自动更新。
- `remark`字段用于存储关于记录的任何备注或附加信息，可以是对字段值的解释、特别说明等。
- `sort`字段通常用于控制记录的显示顺序。
- `deleted_time`字段记录的是数据被标记为删除的时间，这有助于在后续的数据恢复或审计时使用。
- `color_type`和`css_class`可能用于前端显示，以控制视觉样式，如按钮颜色或CSS样式类。
- `value`和`label`在字典表中通常用于存储键值对，比如状态代码和对应的显示文本。
- `parent_id`用于表达层次或树形结构，常见于菜单或类似的分层数据。

## **附录：建表语句**

```sql
-- 用户表 system_user
CREATE TABLE `system_user` (
  `id` BIGINT(20) NOT NULL,
  `username` VARCHAR(64) NOT NULL,
  `password` VARCHAR(64) NOT NULL,
  `real_name` VARCHAR(64) DEFAULT NULL,
  `avatar` VARCHAR(256) DEFAULT NULL,
  `email` VARCHAR(128) DEFAULT NULL,
  `mobile` VARCHAR(20) DEFAULT NULL,
  `status` TINYINT(4) NOT NULL DEFAULT '0',
  `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `last_login_time` DATETIME DEFAULT NULL,
  `creator` VARCHAR(64) DEFAULT NULL,
  `updater` VARCHAR(64) DEFAULT NULL,
  `deleted` BIT(1) NOT NULL DEFAULT b'0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 角色表 system_role
CREATE TABLE `system_role` (
  `id` BIGINT(20) NOT NULL,
  `name` VARCHAR(100) NOT NULL,
  `remark` VARCHAR(500) DEFAULT NULL,
  `creator` VARCHAR(64) DEFAULT NULL,
  `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updater` VARCHAR(64) DEFAULT NULL,
  `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `deleted` BIT(1) NOT NULL DEFAULT b'0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 用户角色关联表 system_user_role
CREATE TABLE `system_user_role` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `user_id` BIGINT(20) NOT NULL,
  `role_id` BIGINT(20) NOT NULL,
  `creator` VARCHAR(64) DEFAULT NULL,
  `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP,
  `updater` VARCHAR(64) DEFAULT NULL,
  `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `deleted` BIT(1) DEFAULT b'0',
  PRIMARY KEY (`id`),
  KEY `idx_user_id` (`user_id`),
  KEY `idx_role_id` (`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 菜单权限表 system_menu
CREATE TABLE `system_menu` (
  `id` BIGINT(20) NOT NULL,
  `parent_id` BIGINT(20) NOT NULL DEFAULT '0',
  `name` VARCHAR(50) NOT NULL,
  `path` VARCHAR(200) DEFAULT NULL,
  `component` VARCHAR(255) DEFAULT NULL,
  `perms` VARCHAR(100) DEFAULT NULL,
  `type` INT NOT NULL,
  `icon` VARCHAR(50) DEFAULT NULL,
  `order_num` INT NOT NULL,
  `status` TINYINT(1) NOT NULL DEFAULT '0',
  `create_by` BIGINT(20) DEFAULT NULL,
  `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_by` BIGINT(20) DEFAULT NULL,
  `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `remark` VARCHAR(500) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 角色权限表 system_role_menu
CREATE TABLE `system_role_menu` (
  `role_id` BIGINT(20) NOT NULL,
  `menu_id` BIGINT(20) NOT NULL,
  `create_by` BIGINT(20) DEFAULT NULL,
  `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_by` BIGINT(20) DEFAULT NULL,
  `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`role_id`,`menu_id`),
  KEY `idx_menu_id` (`menu_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 操作日志表 system_operation_log
CREATE TABLE `system_operation_log` (
  `id` BIGINT(20) NOT NULL,
  `user_id` BIGINT(20) NOT NULL,
  `operation` VARCHAR(255) NOT NULL,
  `method` VARCHAR(100) NOT NULL,
  `request_uri` VARCHAR(255) NOT NULL,
  `request_param` TEXT,
  `request_type` VARCHAR(10) NOT NULL,
  `user_agent` VARCHAR(255) NOT NULL,
  `ip` VARCHAR(64) NOT NULL,
  `cost_time` INT NOT NULL,
  `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `remark` VARCHAR(500) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `idx_user_id` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 字典类型表 system_dict_type
CREATE TABLE `system_dict_type` (
  `id` BIGINT(20) NOT NULL,
  `name` VARCHAR(100) NOT NULL,
  `type` VARCHAR(100) NOT NULL,
  `status` TINYINT(1) NOT NULL DEFAULT '1',
  `create_by` BIGINT(20) DEFAULT NULL,
  `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_by` BIGINT(20) DEFAULT NULL,
  `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `remark` VARCHAR(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 字典数据表 system_dict_data
CREATE TABLE `system_dict_data` (
  `id` BIGINT(20) NOT NULL,
  `type_id` BIGINT(20) NOT NULL,
  `label` VARCHAR(100) NOT NULL,
  `value` VARCHAR(100) NOT NULL,
  `order_num` INT NOT NULL,
  `status` TINYINT(1) NOT NULL DEFAULT '1',
  `create_by` BIGINT(20) DEFAULT NULL,
  `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_by` BIGINT(20) DEFAULT NULL,
  `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `idx_type_id` (`type_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 登录日志表 system_login_log
CREATE TABLE `system_login_log` (
  `id` BIGINT(20) NOT NULL,
  `user_id` BIGINT(20) DEFAULT NULL,
  `username` VARCHAR(64) DEFAULT NULL,
  `status` TINYINT(1) NOT NULL,
  `ip_addr` VARCHAR(128) DEFAULT NULL,
  `login_location` VARCHAR(255) DEFAULT NULL,
  `browser` VARCHAR(100) DEFAULT NULL,
  `os` VARCHAR(100) DEFAULT NULL,
  `login_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `message` VARCHAR(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
```
