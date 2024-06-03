#!/bin/bash

# 启动服务
echo -e "开始部署......"

# 当任何一个命令返回非零值时，退出脚本
set -e

# 定义颜色
RED='\033[0;31m'
GREEN='\033[0;32m'
NC='\033[0m' # 无颜色


# 检查docker命令是否可用
if ! command -v docker &> /dev/null
then
    echo -e "${RED}docker 命令未找到，请先安装 Docker。${NC}"
    exit 1
fi

echo -e "检查docker命令是否可用......${GREEN}通过${NC}"

# 检查docker-compose命令是否可用
if ! command -v docker-compose &> /dev/null
then
    echo -e "${RED}docker-compose 命令未找到，请先安装 Docker Compose。${NC}"
    exit 1
fi

echo -e "检查docker-compose 命令......${GREEN}通过${NC}"

# 检查 Maven 缓存 volume 是否已经存在
if docker volume inspect ssadmin-maven-repo &> /dev/null; then
    echo -e "${GREEN}Maven 缓存 volume 已存在。${NC}"
else
    echo -e "${GREEN}创建 Maven 缓存 volume...${NC}"
    docker volume create --name ssadmin-maven-repo
fi

# 通过容器构建 jar 包

echo -e "开始构建 jar 包..."

docker run -it --rm --name ssadmin-maven \
    -v ssadmin-maven-repo:/root/.m2 \
    -v "$PWD/ssadmin-server":/usr/src/mymaven \
    -w /usr/src/mymaven \
    maven:3.8.4-jdk-8 mvn clean install package -e '-Dmaven.test.skip=true' || { echo -e "${RED}构建 jar 包失败。${NC}"; exit 1; }

echo -e "通过容器构建 jar 包......${GREEN}通过${NC}"

# 构建镜像
echo -e "开始构建镜像..."
docker-compose build --no-cache || { echo -e "${RED}构建 Docker 镜像失败。${NC}"; exit 1; }

echo -e "构建 Docker 镜像......${GREEN}通过${NC}"

# 启动服务
echo -e "检查是否有正在运行的服务..."

# 检查是否有正在运行的 docker-compose 服务
if docker-compose ps | grep "ssadmin"; then
    echo -e "${GREEN}停止正在运行的服务...${NC}"
    docker-compose down || { echo -e "${RED}停止服务失败。${NC}"; exit 1; }
    echo -e "${GREEN}服务已成功停止。${NC}"
else
    echo -e "${GREEN}没有找到正在运行的服务。${NC}"
fi

echo -e "启动新服务..."

docker-compose up -d || { echo -e "${RED}启动服务失败。${NC}"; exit 1; }

echo -e "${GREEN}恭喜你！系统部署完成！${NC}"
echo -e ""
echo -e "**********************************"
echo -e "***  Admin UI: http://hostname ***"
echo -e "**********************************"
echo -e ""
