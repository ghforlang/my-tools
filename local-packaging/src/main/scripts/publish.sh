#! /usr/bin/env bash

#可变参数变量
languageType="javac"

#参数值，由pom文件传递
baseZipName="${package-name}-${activeProfile}" #压缩包名称
packageName="${package-name}-${project.version}" # 命令启动包名 如 xx.jar 中的xx
mainClass="${boot-main}"  # java -cp启动时，指定main入口类，命令格式: java -cp conf:lib\*.jar;${packageName}.jar ${mainClass}

# 固定变量
basePath=$(cd `dirname $0`/; pwd)
baseZipPath="${basePath}/${baseZipName}.zip"
baseDirPath="${basePath}"  # 解压部署自盘路径

# 进程id
pid=

function demo_unzip()
{
    echo "解压————————————————————————————————————————"
    echo "压缩包路径：${baseZipPath}"
    if [ ! `find $baseZipPath` ]
    then
        echo "不存在压缩包 ${baseZipPath}"
    else
        echo "加压磁盘路径: ${baseDirPath/$baseZipName}"
        echo "解压开始..."

        unzip -od ${baseDirPath}/${baseZipName} ${baseZipPath}
        # 设置执行权限
        chmod +x ${baseDirPath}/${baseZipName} ${baseZipPath}

        echo "解压完成。"
    fi
}

function get_pid()
{
    echo "检测状态----------------------------------------"
    pid=$(ps -ef | grep -n ${packageName} | grep -v  grep | awk '{print $2}')
    if [ ${pid} ]
    then
        echo "进程id： ${pid}"
    else
        echo "未运行"
    fi
}

function  start()
{
    stop
    if [ ${pid} ]
    then
        echo "程序停止失败，无法启动"
    else
        echo "启动程序-----------------------------------"

        # 选择语言类型,并写入read_languageType
        read -p "输入程序类型(java,javac),并按回车键确认(默认${languageType}):" read_languageType
        if [ "${read_languageType}" ]
        then
            languageType=${read_languageType}
        fi
        echo "选择程序类型: ${languageType}"

        # 进入运行包目录
        cd ${baseDirPath}/${baseZipName}

        # 运行程序
        if [ "${languageType}" == "javac" ]
        then
            if [ "${mainClass}" ]
            then
                nohup java -cp conf:lib/*.jar:${packageName}.jar ${mainClass} > ${baseDirPath}/${pacakgeName}/${mainClass}.log 2>&1 &
            fi
        elif [ "${languageType}" == "java" ]
        then
            nohup java -jar ${baseDirPath}/${baseZipName}/${packageName}.jar > ${baseDirPath}/${pacakgeName}/${mainClass}.log  2>&1 &
#        elif [ "${languageType}" == "netcore" ]
#        then
#            nohup ${baseDirPath}/${baseZipName}/${packageName}.jar > ${baseDirPath}/${pacakgeName}/${mainClass}.log  2>&1 2>&1 &
        fi

        get_pid
        if [ ${pid} ]
        then
            echo "程序启动成功"
            tail -n 50 -f ${baseDirPath}/${pacakgeName}/${mainClass}.log
        else
            echo "程序启动失败"
        fi
       fi
}

function stop()
{
    get_pid
    if [ ${pid} ]
    then
        echo "停止程序-------------------------------------"
        kill -9 ${pid}

        get_pid
        if [ ${pid} ]
        then
            echo "程序停止失败"
        else
            echo "程序停止成功"
        fi
    fi
}

# 启动时，带参数执行

echo "----------------------tips start----------------------------"
echo "zip包名称：${baseZipName}"
echo "package名称: ${packageName}"
echo "mainClass: ${mainClass}"
echo "日志目录: ${baseDirPath}/${pacakgeName}/${mainClass}.log"
echo "----------------------tips end -----------------------------"
echo ""

if [ ${#} -ge 1 ]
then
    case ${1} in
        "start")
            start
        ;;
        "restart")
            start
        ;;
        "stop")
            stop
        ;;
        "unzip")
            demo_unzip
            start
         ;;
         *)
          echo "无任何操作!"
         ;;
    esac
else
    echo "
    command如下命令:
    unzip: 加压并启动
    start：启动
    stop： 停止进程
    restart： 重启

    示例： ./publish.sh start
    "

fi
