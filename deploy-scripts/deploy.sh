#!/bin/bash

# constant
SERVICE_DIR="serviceFiles"
COMMONCONFIG="common"
LINUX_USER=""

# deploy servie names
DEPLOY_NAMES=("spideroperateservice")

# deploy servie(for cluster)
spideroperateserviceArray=()

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

DEPLOY_ACTION=""

if [[ "${1}" == "install" ]];then
    DEPLOY_ACTION="install"
elif [[  "${1}" == "update" ]];then
    DEPLOY_ACTION="update"
elif [[  "${1}" == "rollback" ]];then
    DEPLOY_ACTION="rollback"
else
    red_echo "please input cmd: install | update | rollback"
    exit 0
fi

#===================================================================
# Name      : startDeploy
# Descript  : send file, and do $4 command
# Parameters: 
#      $1 dir to be send
#      $2 target ip
#      $3 target path
#      $4 command: 
#            install : install service and startup
#            update : update service and startup
#-----------------------------------------------------------------
function startDeploy()
{
    # send file
    if [[ "${4}" == "install" || "${4}" == "update" ]];then
        scp -r ${1}/* ${LINUX_USER}@${2}:${3}
    fi
    
    # remote command
    ssh ${LINUX_USER}@${2} "cd ${3}; sh executeCMD.sh ${4}"
    
    # hold for log info 
    red_echo "please press ENTER to continue next step, or input 'quit' then press ENTER to exit"
    read cmd
    if [[ "$cmd" == "" ]];then
        blue_echo "you choose to continue..."
        sleep 2
    elif [[ "$cmd" == "quit" ]];then
        blue_echo "you choose to exit, will exit in 2 seconds"
        blue_echo " 2"
        sleep 1
        blue_echo " 1"
        sleep 1
        blue_echo "bye~"
        exit 0
    fi
}

# read basic deploy information from 'input.txt'
while read LINE
do
    if [[  "${LINE}" =~ ^"#"  || "${LINE}" == "" ]];then
        continue
    fi
    
    if [[ "${LINE%=*}" == "linuxUser" ]];then
        LINUX_USER="${LINE#*=}"
        if [[ "${LINUX_USER}x" == "x" ]];then
            red_echo "no linux user in 'input.txt'!"
            exit 0
        fi
        blue_echo "linux user: ${LINUX_USER}"
        continue
    fi
	
    # store info into each array
    for i in ${DEPLOY_NAMES[@]}
    do
		blue_echo "${i}"
        if [[ "${LINE%=*}" == "${i}Deploy" ]];then
            blue_echo "---> ${i} target info is: ${LINE#*=}"
            arrayName=${i}Array
            eval 'len=${#'"${i}Array"'[@]}'
            eval "${i}Array[${len}]=\"${LINE#*=}\""
            eval 'echo'" array length is:"'${#'"${i}Array"'[@]}'", content is:"'${'"${i}Array"'[@]}'
            break
        fi
    done
done < input.txt

function coverCommonConfig()
{
    # $1 service name
    serviceName=${1}
	cnt1=0
	tempArray=()
	eval 'for i in ''"${'"${1}Array[@]}\""'
	do
		tempArray[$cnt1]="${i}"
		let "cnt1++"
	done'
	
	cnt2=0
	for i in "${tempArray[@]}"
	do
		tempDir="${serviceName}"
		mkdir ${tempDir}
		targetIp=`echo ${i} | awk '{printf $1}'`
		accessPort=`echo ${i} | awk '{printf $2}'`
		shutdownPort=`echo ${i} | awk '{printf $3}'`
		targetDir=`echo ${i} | awk '{printf $4}'`
		
		 cp ${COMMONCONFIG}/server.xml ${tempDir}
		 cp ${COMMONCONFIG}/apache-tomcat-8.5.20-security.zip ${tempDir}
		 
		 sed -i "22c \<Server port=\"${shutdownPort}\" shutdown=\"7UJcaN*ikmasd\"\>" ${tempDir}/server.xml
		 sed -i "69c \<Connector port=\"${accessPort}\" protocol=\"HTTP\/1.1\"" ${tempDir}/server.xml
		 sed -i "152c \<Context path=\"\/${serviceName}\" reloadable=\"false\" docBase=\"${serviceName}\" workDir=\"webapps\/${serviceName}\/WEB-INF\/work\"\/>" ${tempDir}/server.xml
		
		# service script
		cp ${SERVICE_DIR}/${serviceName}-executeCMD.sh ${tempDir}/executeCMD.sh
		sed -i "s%^WAR_FILE=.*%WAR_FILE=${serviceName}%" ${tempDir}/executeCMD.sh
		
		cp ${SERVICE_DIR}/${serviceName}.war ${tempDir}

		startDeploy ${tempDir} ${targetIp} ${targetDir} ${DEPLOY_ACTION}
	
		let "cnt2++"
		rm -R ${tempDir}
	done
}

# config
for i in ${DEPLOY_NAMES[@]}
do
    eval 'len=${#'"${i}Array"'[@]}'
    if [[ $len -ge 1 ]];then
        if [[ $len -eq 1 ]];then
            blue_echo "only one ${i}, use single config"
        else
            blue_echo "$len ${i}, use cluster config"
        fi
        coverCommonConfig ${i}
    fi
done