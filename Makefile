# 定义变量
DOCKER_COMPOSE=docker-compose
MVN_IMAGE=maven:3.8.4-jdk-8
SSADMIN_VOLUME=ssadmin-maven-repo
SSADMIN_DIR=$(PWD)/ssadmin-server

# 默认目标
all: build

# 构建前端和后端镜像
build: build_back build_front

# 构建后端镜像
build_back: jar
	docker-compose build server

# 构建 Java 应用的 jar 文件
jar:
	# 检查 Maven 缓存 volume
	@if docker volume inspect $(SSADMIN_VOLUME) &> /dev/null; then \
		echo "Maven 缓存 volume 已存在。"; \
	else \
		echo "创建 Maven 缓存 volume..."; \
		docker volume create --name $(SSADMIN_VOLUME); \
	fi
	# 通过容器构建 jar 包
	docker run -it --rm --name ssadmin-maven \
		-v $(SSADMIN_VOLUME):/root/.m2 \
		-v "$(SSADMIN_DIR)":/usr/src/mymaven \
		-w /usr/src/mymaven \
		$(MVN_IMAGE) mvn clean install package -Dmaven.test.skip=true

# 构建前端镜像
build_front:
	docker-compose build web

# 启动所有服务
start:
	docker-compose up -d

# 停止所有服务
stop:
	docker-compose down

# 查看日志
logs:
	docker-compose logs

# 清理
clean:
	docker-compose down --rmi all --volumes --remove-orphans

# 帮助
help:
	@echo "可用的目标："
	@echo "  all       - 构建前端和后端镜像"
	@echo "  build     - 构建前端和后端镜像"
	@echo "  build_back - 构建后端镜像"
	@echo "  jar       - 构建 Java 应用的 jar 文件"
	@echo "  build_front - 构建前端镜像"
	@echo "  start     - 启动所有服务"
	@echo "  stop      - 停止所有服务"
	@echo "  logs      - 查看容器日志"
	@echo "  clean     - 清理所有生成的文件和容器"
	@echo "  help      - 显示帮助信息"
