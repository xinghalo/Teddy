#!/bin/sh

# Teddy Home
case "`uname`" in
    Linux)
		bin_absolute_path=$(readlink -f $(dirname $0))
		;;
	*)
		bin_absolute_path=`cd $(dirname $0); pwd`
		;;
esac

teddy_home=${bin_absolute_path}/..

export TEDDY_HOME=$teddy_home

`chmod -R 755 $teddy_home`

# Java
if [ -z "$JAVA" ] ; then
  JAVA=$(which java)
fi

# 检查 pid 文件
if [ -f $teddy_home/bin/teddy.pid ] ; then
	echo "错误：服务已启动，请先停止之前的服务" 2>&2
    exit 1
fi

# 启动服务
mkdir -p $teddy_home/logs
$JAVA -jar $teddy_home/teddy.jar -p $teddy_home/conf/teddy.properties 1>>$teddy_home/logs/teddy.log 2>&1 &
echo $! > $teddy_home/bin/teddy.pid