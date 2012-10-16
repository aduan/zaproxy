#!/usr/bin/env bash

#Dereference from link to the real directory
SCRIPTNAME=$0

#While name of this script is symbolic link
while [ -L "$SCRIPTNAME" ] ; do 
        #Dereference the link to the name of the link target 
        SCRIPTNAME=$(ls -l "$SCRIPTNAME" | awk '{ print $NF; }')
done

#Base directory where ZAP is installed
BASEDIR=$(dirname "$SCRIPTNAME")

#Switch to the directory where ZAP is installed
cd "$BASEDIR"

#Get Operating System
OS=$(uname -s)

#Work out best memory options
if [ "$OS" = "Linux" ]; then
	MEM=$(expr $(sed -n 's/MemTotal:[ ]\{1,\}\([0-9]\{1,\}\) kB/\1/p' /proc/meminfo) / 1024)
elif [ "$OS" = "Darwin" ]; then
	MEM=$(system_profiler SPMemoryDataType | sed -n -e 's/.*Size: \([0-9]\{1,\}\) GB/\1/p' | awk '{s+=$0} END {print s*1024}')
else
    echo "Sorry, your plaform is not supported yet! You'll have to start ZAP from the commandline."
    exit 1;
fi

if [ -z $MEM ]
then
	echo "Failed to obtain current memory, using jmv default memory settings"
else
	echo "Available memory: " $MEM "MB"
	if [ $MEM -gt 1500 ]
	then
		JMEM="-Xmx512m"
	else
		if [ $MEM -gt 900 ]
		then
			JMEM="-Xmx256m"
		else
			if [ $MEM -gt 512 ]
			then
				JMEM="-Xmx128m"
			fi
		fi
	fi
fi

if [ -n "$JMEM" ]
then
	echo "Setting jvm heap size: $JMEM"
fi

#Start ZAP

java ${JMEM} -XX:PermSize=256M -jar "${BASEDIR}/zap.jar" org.zaproxy.zap.ZAP $*
