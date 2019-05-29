#/bin/bash

# constant
WAR_FILE=$(ls *.war)
SERVICE_NAME="${WAR_FILE%%.war*}"
TOMCAT_DIR_NAME="${SERVICE_NAME}-tomcat"
CLASSES_DIR="webapps/${SERVICE_NAME}/WEB-INF/classes"
bakTime="bak$(date +%Y%m%d)"
SERVICE_DIR=`pwd`

DEPLOY_ACTION=""

function blue_echo()
{
    echo -e "\033[36m shell info--> $1 \033[0m"
}
function red_echo()
{
    echo -e "\033[31m shell info--> $1 \033[0m"
}
function error_echo()
{
    echo -e "\033[41;33m ### $1 ### \033[0m"
}

function doStartOnly()
{
    blue_echo "starting ${TOMCAT_DIR_NAME}..."
    sh ${TOMCAT_DIR_NAME}/bin/startup.sh
}

function doStart()
{
    doStartOnly
    
    double_echo "starting sleep 5s..."
    sleep 5
    tail -n100 ${TOMCAT_DIR_NAME}/logs/catalina.out
}

function doStop()
{
    blue_echo "stoping ${TOMCAT_DIR_NAME}...."
    sh ./${TOMCAT_DIR_NAME}/bin/shutdown.sh
    sleep 4
    
    servicePid=`ps -ef|grep -v grep|grep "java"|grep "${TOMCAT_DIR_NAME}"|awk '{print $2}'`
    if [[ "x${servicePid}" != "x" ]];then
        error_echo "do stop failed, use kill to stop task!"
        kill -9 ${servicePid}
    fi
    sleep 2
    ps -ef | grep ${TOMCAT_DIR_NAME}
    blue_echo "pre log is execute 'ps -ef|grep ${SERVICE_NAME}' result, if task is still there, use 'kill pid' to stop it."
    
}

function doBackup()
{
    blue_echo "package the old tomcat start..."
    tar -czvf ${TOMCAT_DIR_NAME}.${bakTime}.tar.gz ${TOMCAT_DIR_NAME} --exclude ${TOMCAT_DIR_NAME}/logs
    blue_echo "package the old tomcat end."
}

function doUpdate()
{
    pwd
    
	if [[ "$DEPLOY_ACTION" == "install" ]];then
        unzip apache-tomcat-8.5.20-security.zip
	    mv apache-tomcat-8.5.20 ${TOMCAT_DIR_NAME}
	    chmod 500 ${TOMCAT_DIR_NAME}/bin/*
    fi
	
    mv ${TOMCAT_DIR_NAME}/conf/server.xml ${TOMCAT_DIR_NAME}/conf/server.xml.original
    cp server.xml ${TOMCAT_DIR_NAME}/conf/server.xml
	
    rm -R ${TOMCAT_DIR_NAME}/webapps/*
	echo "" > ${TOMCAT_DIR_NAME}/logs/update${bakTime}.log 
	
	cp -a ${SERVICE_NAME}.war ${TOMCAT_DIR_NAME}/webapps/
    cd ${TOMCAT_DIR_NAME}/webapps/
    unzip ${SERVICE_NAME}.war -d ${SERVICE_NAME}
	
	cd /opt/myservices/${SERVICE_NAME}/${TOMCAT_DIR_NAME}
	
	
	modifyEnvConfiguration 'application.properties' 'logging.level.root=INFO'
	modifyEnvConfiguration 'application.properties' 'spring.datasource.url=jdbc:mysql://172.18.61.63:3306/mvcrawler?characterEncoding=utf-8&autoReconnect=true&failOverReadOnly=false&allowMultiQueries=true&useSSL=false'
	modifyEnvConfiguration 'application.properties' 'spring.datasource.username=root'
	modifyEnvConfiguration 'application.properties' 'spring.datasource.password=SIFlxCpWlF0VeNDBtLOkTw=='
	
	modifyEnvConfiguration 'mvcrawler-config.properties' 'mvcrawler.url=http://47.106.134.165/spideroperateservice/mvcrawler'
	
	modifyEnvConfiguration 'redis-config.properties' 'redis.host=172.18.61.63'
	modifyEnvConfiguration 'redis-config.properties' 'redis.password=fPjeBLE8mTMVeNDBtLOkTw=='	
	
	###############################################
	
	dos2unix ${CLASSES_DIR}/*.properties
	
	echo_result
    cd ..
	
    blue_echo "updating ${SERVICE_NAME} finished"
	
}

function echo_result()
{
	double_echo ""
	grep "WARNING" logs/update${bakTime}.log
	double_echo ""
	grep "ERROR" logs/update${bakTime}.log
	if [ $? -ne 0 ]; then
		double_echo ""
		double_blue_echo "#########################"
		double_blue_echo "###  ALL SUCCESS!!!   ###"
		double_blue_echo "#########################"	
	else
		double_echo ""
		double_red_echo "#########################"
		double_red_echo "### THERE IS ERROR!!! ###"
		double_red_echo "#########################"	
	fi
	double_echo "sleep 5s..."
	sleep 5
}

function double_red_echo()
{
	echo -e "\033[31m$1"
	echo $1 >> logs/update${bakTime}.log
}

function double_blue_echo()
{
	echo -e "\033[36m$1"
	echo $1 >> logs/update${bakTime}.log
}

function double_echo()
{
	if [[ "$2" == "continue" ]];then
		echo -n $1
		echo -n $1 >> logs/update${bakTime}.log
	else
		echo $1
		echo $1 >> logs/update${bakTime}.log
	fi
}

function doRollback()
{
    tar -czvf ${TOMCAT_DIR_NAME}.updateFailed.${bakTime}.tar.gz ${TOMCAT_DIR_NAME}
    rm -R ${TOMCAT_DIR_NAME}
    
    tar -xzvf ${TOMCAT_DIR_NAME}.${bakTime}.tar.gz
    cd ${TOMCAT_DIR_NAME}
    mkdir logs
    cd ..
}

# 语法： modifyEnvConfiguration 目标文件名 待刷新key=value
function modifyEnvConfiguration()
{
	double_echo ""	
	arg_name=$(echo $2 | cut -d "=" -f 1)"="
	
	double_echo "before modifyEnvConfiguration:" "continue"
	grep "^$arg_name" ${CLASSES_DIR}/$1
	if [ $? -ne 0 ]; then
		double_echo "【ERROR】modifyEnvConfiguration error!!! file: \"$1\", configuration:\"$2\". do not find \"$arg_name\""			
	else
		sed -i "/^$arg_name/c $2" ${CLASSES_DIR}/$1
	
		double_echo "after modifyEnvConfiguration:" "continue"	
		result=$(grep "^$arg_name" ${CLASSES_DIR}/$1)
		if [[ "$result" != "$2" ]]; then
			double_echo "【ERROR】modifyEnvConfiguration error!!! file: \"$1\", configuration:\"$2\".  "	
		else
			double_echo "modifyEnvConfiguration success. file: \"$1\", configuration:\"$2\".  "	
		fi
	fi
}

function doModifyPermission()
{
	cd ${SERVICE_DIR}/${TOMCAT_DIR_NAME}
	chmod 500 -R bin
	chmod 700 conf lib logs temp webapps work
	cd ${SERVICE_DIR}/${TOMCAT_DIR_NAME}/conf 
	find ./ -type f | xargs -i chmod 600 {}
	find ./ -type d | xargs -i chmod 700 {}
	cd ${SERVICE_DIR}/${TOMCAT_DIR_NAME}/lib
	find ./ -type f | xargs -i chmod 500 {}
	find ./ -type d | xargs -i chmod 700 {}
	cd ${SERVICE_DIR}/${TOMCAT_DIR_NAME}/logs
	find ./ -type f | xargs -i chmod 600 {}
	find ./ -type d | xargs -i chmod 700 {}
	cd ${SERVICE_DIR}/${TOMCAT_DIR_NAME}/temp
	find ./ -type f | xargs -i chmod 600 {}
	cd ${SERVICE_DIR}/${TOMCAT_DIR_NAME}/webapps
	find ./ -type f | xargs -i chmod 600 {}
	find ./ -type d | xargs -i chmod 700 {}
	cd ${SERVICE_DIR}/${TOMCAT_DIR_NAME}/work
	find ./ -type f | xargs -i chmod 600 {}
	find ./ -type d | xargs -i chmod 700 {}
	cd ${SERVICE_DIR}/${TOMCAT_DIR_NAME}
	
	double_blue_echo "####################################"
	double_blue_echo "###  MODIFY_PERMISSION OVER!!!   ###"
	double_blue_echo "####################################"	
	
}

if [[ $1 == start ]];then
    doStart
elif [[ $1 == stop ]];then
    doStop
elif [[ $1 == install ]];then
    DEPLOY_ACTION="install"
    doUpdate
    doStart
elif [[ $1 == update ]];then
    DEPLOY_ACTION="update"
    doStop
    doBackup
    doUpdate
    doStart
elif [[ $1 == rollback ]];then
    DEPLOY_ACTION="rollback"
    doStop
    doRollback
elif [[ $1 == doModifyPermission ]];then
    doModifyPermission
else
    red_echo "please input parameter: start(启动并查看进程) | stop(关闭并查看进程) | install(安装) | update(升级) | rollback(回滚)"
fi
