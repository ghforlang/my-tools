#!/bin/bash

# 环境变量配置，确保以下路径与你的实际环境相匹配
GIT_REPO_URL="https://github.com/your-username/your-repo.git"
CLONE_DIR="/path/to/clone/repo"
PROJECT_DIR="$CLONE_DIR/your-project"
JAR_NAME="your-springboot-app.jar"
DEPLOY_DIR="/path/to/deploy"
JAVA_HOME="/path/to/java_home"
MAVEN_HOME="/path/to/maven_home"

# 确保JAVA_HOME和MAVEN_HOME已经设置
export JAVA_HOME=$JAVA_HOME
export MAVEN_HOME=$MAVEN_HOME
export PATH=$PATH:$JAVA_HOME/bin:$MAVEN_HOME/bin

# 克隆或拉取最新代码,检查克隆目录中是否存在.git文件夹
#
# 此代码段用于判断指定的克隆目录（$CLONE_DIR）中是否存在一个名为.git的文件夹。
# 如果存在，则表示该目录已经是一个Git仓库，可能是一个已经克隆下来的Git仓库。
#
# 参数说明：
# - $CLONE_DIR: 要检查的克隆目录的路径。
#
# 如果.git文件夹存在，则条件为真（true），接下来的命令或代码块将会被执行。
# 如果.git文件夹不存在，则条件为假（false），跳过接下来的命令或代码块。
if [ -d "$CLONE_DIR/.git" ]; then
    echo "Updating existing repository..."
    cd "$CLONE_DIR"
    git pull origin master
else
    echo "Cloning repository..."
    git clone "$GIT_REPO_URL" "$CLONE_DIR"
    cd "$CLONE_DIR"
fi

# 使用Maven构建项目
echo "Building project using Maven..."
mvn clean package -Dmaven.test.skip=true

#
#!表示逻辑非，-f用于判断紧跟其后的路径是否为一个常规文件，
# "$PROJECT_DIR/target/$JAR_NAME"是我们要检查的文件的完整路径
if [ ! -f "$PROJECT_DIR/target/$JAR_NAME" ]; then
    echo "JAR file not found: $PROJECT_DIR/target/$JAR_NAME"
    exit 1
fi

# 停止原有服务（如果有运行的话）

# 获取指定JAR文件对应进程的PID（进程ID）
#
# 该命令通过以下步骤实现：
# 1. 使用ps -ef命令列出所有正在运行的进程。
# 2. 使用grep命令过滤出包含$JAR_NAME的行，其中$JAR_NAME是环境变量，代表要查找的JAR文件名。
# 3. 使用grep -v grep命令排除掉包含grep命令本身的行，以避免将grep命令本身也作为结果返回。
# 4. 使用awk '{print $2}'命令提取每行的第二个字段，即进程ID（PID）。
# 5. 将提取到的PID赋值给环境变量PID。
PID=$(ps -ef | grep $JAR_NAME | grep -v grep | awk '{print $2}')
# 检查环境变量PID是否为空
#
# -z 用于检查字符串长度是否为零。
# 如果PID环境变量的值为空（即未设置或为空字符串），则条件为真。
# 这通常用于检查某个进程是否尚未启动，因为如果进程已启动，其PID通常会被存储在环境变量中。
if [ -z $PID ]; then
    echo "No existing service to stop."
else
    echo "Stopping existing service..."
    kill -9 $PID
    echo "Service stopped."
fi

# 复制JAR包到部署目录并启动新服务
cp "$PROJECT_DIR/target/$JAR_NAME" "$DEPLOY_DIR"
cd "$DEPLOY_DIR"
echo "Starting new service..."

# 使用nohup命令在后台运行Java程序，忽略所有挂断信号
# - nohup: 使程序忽略挂断信号，即使终端关闭，程序依然会继续运行
# - java -jar "$JAR_NAME": 使用Java运行指定的JAR文件
#   - $JAR_NAME: 这是一个环境变量，代表要运行的JAR文件的路径和名称
# - > /dev/null: 将标准输出重定向到/dev/null，即丢弃所有标准输出
# - 2>&1: 将标准错误重定向到标准输出的目的地，即/dev/null
# - &: 将命令放到后台执行
nohup java -jar "$JAR_NAME" > /dev/null 2>&1 &
echo "Service started."