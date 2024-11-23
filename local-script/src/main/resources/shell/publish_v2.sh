#!/bin/bash

# 检查是否提供了必要的参数
# 检查传递给脚本的参数数量是否为4个
# $# 是一个特殊变量，它表示传递给脚本的位置参数数量
# 该脚本要求用户必须提供恰好4个参数才能继续执行。
# 如果参数数量不等于4，则脚本将不会继续执行后续命令，并可能提示用户错误信息。
if [ "$#" -ne 4 ]; then
    echo "Usage: $0 <REPO_URL> <BRANCH> <APP_NAME> <BUILD_DIR>"
    exit 1
fi

# 配置变量
REPO_URL=$1
BRANCH=$2
APP_NAME=$3
BUILD_DIR=$4

# 创建构建目录（如果不存在）
mkdir -p $BUILD_DIR

# 进入构建目录
cd $BUILD_DIR || exit

# 拉取或更新代码
# 检查指定名称的目录是否存在
# -d选项用于判断紧跟其后的路径是否为一个目录
# 此代码段用于判断一个名为$APP_NAME的目录是否存在于当前工作目录中。
# 参数说明：
# - $APP_NAME: 要检查的目录名称。
# 如果目录存在，则条件判断为真（true），随后会执行相应的代码块。
# 如果目录不存在，则条件判断为假（false），跳过相应的代码块
if [ -d "$APP_NAME" ]; then
    echo "Updating existing repository..."
    cd $APP_NAME || exit
    git pull origin $BRANCH
else
    echo "Cloning new repository..."
    git clone --branch $BRANCH $REPO_URL .
fi

# 使用 Maven 构建项目
echo "Building project with Maven..."
mvn clean install -DskipTests

# 获取Java版本的主版本号
#
# 此命令通过以下步骤获取Java的主版本号：
# 1. 执行 `java -version` 命令并捕获其标准错误输出（由于Java版本信息通常输出到标准错误，因此使用 2>&1 重定向标准错误到标准输出）。
# 2. 使用 `awk` 命令处理输出，以双引号（"）作为字段分隔符，并查找包含 "version" 的行。
# 3. 提取包含版本号的字段（通常是第二个字段，即 `$2`），该字段由双引号包围。
# 4. 使用 `cut` 命令以点（.）作为分隔符，并提取第一个字段，即主版本号。
#
# 结果存储在环境变量 `JAVA_VERSION` 中。
JAVA_VERSION=$(java -version 2>&1 | awk -F '"' '/version/ {print $2}' | cut -d. -f1)

# 获取 JAR 文件路径
JAR_FILE=$(find target -name "*.jar")

# 停止正在运行的应用（如果存在）
echo "Stopping running application (if any)..."
# 使用pgrep命令查找并获取与指定应用名称匹配的进程的PID（进程ID）
#
# - pgrep: 是一个在Unix和类Unix系统中用于查找匹配特定模式的进程的命令。
# - -f: 是pgrep命令的一个选项，表示匹配完整的命令行，而不仅仅是进程名。
# - $APP_NAME: 是一个环境变量，代表要查找的应用名称。
# - PID: 是一个变量，用于存储pgrep命令找到的进程ID。
# - $(...): 是命令替换的语法，用于执行括号内的命令，并将输出赋值给变量。
PID=$(pgrep -f $APP_NAME)
if [ ! -z "$PID" ]; then
    kill -9 $PID
    sleep 5
fi

# 启动应用
echo "Starting application..."
# 该命令将Java应用程序的输出重定向到app.log文件中，包括标准输出和标准错误。
# - nohup: 忽略挂断信号，允许程序在终端关闭后继续运行。
# - java -jar $JAR_FILE: 使用Java运行指定的JAR文件。
#   - $JAR_FILE: 环境变量，表示要运行的JAR文件的路径。
# > app.log: 将标准输出重定向到app.log文件中。
# 2>&1: 将标准错误也重定向到标准输出的目的地，即app.log文件。
# &: 将命令放到后台执行。
nohup java -jar $JAR_FILE > app.log 2>&1 &

echo "Application started. Logs are available in app.log."






