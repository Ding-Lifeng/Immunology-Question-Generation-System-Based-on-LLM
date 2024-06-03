# 使用Docker Compose部署

## 下载ssadmin-server源码

```bash
git clone https://gitee.com/OSABC/ssadmin
cd ssadmin/
```

## 创建maven缓存volume

**在同一台机器上，只需要执行一次！**

```bash
# 创建maven缓存volume
docker volume create --name ssadmin-maven-repo
```

## 通过容器构建jar包

```bash
docker run -it --rm --name ssadmin-maven \
    -v ssadmin-maven-repo:/root/.m2 \
    -v $PWD/ssadmin-server:/usr/src/mymaven \
    -w /usr/src/mymaven \
    maven:3.8.4-jdk-8 mvn clean install package -e '-Dmaven.test.skip=true'
```

编译成功，结果如下：

```bash
[INFO] ------------------------------------------------------------------------
[INFO] Reactor Summary for SSAdmin 基于RBAC的后台管理系统 1.0:
[INFO] 
[INFO] SSAdmin 基于RBAC的后台管理系统 .............................. SUCCESS [ 12.837 s]
[INFO] ssadmin-common ..................................... SUCCESS [02:37 min]
[INFO] ssadmin-module-security ............................ SUCCESS [  2.930 s]
[INFO] ssadmin-system ..................................... SUCCESS [ 28.531 s]
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time:  03:22 min
[INFO] Finished at: 2023-11-02T02:21:03Z
[INFO] ------------------------------------------------------------------------
```

## 构建镜像

如果需要构建，则执行如下命令，构建前后端镜像。

```bash
export DOCKER_BUILDKIT=1
docker-compose build
```

## 启动服务

```bash
docker-compose up -d
```

## 服务器端口映射

- Admin UI: http://hostname
- Api Server: http://localhost:28080
- Mysql: root/123456, Port: 3306
- Redis: Port: 6379

## 常见问题

### 如何查看容器日志

```bash
docker logs ssadmin-mysql
```

或

```bash
docker-compose logs
```

### 删除已经初始化的数据库卷

初始化脚本仅在数据库第一次初始化时运行。如果你需要重新运行初始化脚本，你可以删除现有的数据卷，这样MySQL在下一次启动时会认为它是第一次启动并运行这些脚本。

警告：**这将删除现有的所有数据库数据**，请谨慎操作！

```bash
docker-compose down
docker volume rm [volume_name]
docker-compose up
```

### 如何进入数据库容器进行操作

```bash
docker ps
```

```bash
docker exec -it ssadmin-mysql bash
```

```bash
mysql -u root -p
```

### CentOS8安装Docker及Docker-compose

**1、更新系统依赖：**

```bash
dnf update -y
```

**2、关闭防火墙:**

```bash
# 关闭防火墙：
$ systemctl stop firewalld
# 关闭防火墙自启
$ systemctl disable firewalld
```

**3、安装Docker:**

```bash
# 所有节点服务器执行
# 安装必要依赖
yum install -y yum-utils device-mapper-persistent-data lvm2
#添加aliyun docker-ce yum源
yum-config-manager --add-repo http://mirrors.aliyun.com/docker-ce/linux/centos/docker-ce.repo
#重建yum缓存
yum clean all
yum makecache
# 查看可用的docker版本
yum list docker-ce.x86_64 --showduplicates | sort -r
#安装指定版本docker
yum install -y docker-ce-20.10.6-* docker-ce-cli-20.10.6-*.x86_64
```

**4、配置Docker并添加镜像:**

```bash
# 设置Docker开机启动
systemctl enable docker && systemctl start docker
# 查看docker配置信息
docker info

# 修改镜像仓库
cat > /etc/docker/daemon.json << EOF
{
  "registry-mirrors": ["https://******.mirror.aliyuncs.com"]
}
EOF

# systemctl daemon-reload 
# systemctl restart docker

```

**5、安装Docker-Compose:**

```bash
sudo curl -L "https://github.com/docker/compose/releases/download/1.25.5/docker-compose-$(uname -s)-$(uname -m)" -o /usr/local/bin/docker-compose
```

以下是国内镜像源

```bash
sudo curl -L "https://get.daocloud.io/docker/compose/releases/download/1.29.1/docker-compose-$(uname -s)-$(uname -m)" > /usr/local/bin/docker-compose
```

添加操作权限

```bash
sudo chmod +x /usr/local/bin/docker-compose
```

设置快捷

```bash
sudo ln -s /usr/local/bin/docker-compose /usr/bin/docker-compose
```

查看docker-compose 版本

```bash
docker-compose --version
```

```bash
# docker-compose --version
docker-compose version 1.25.5, build 8a1c60f6
```
