#!/usr/bin/env bash
source /etc/profile
source ~/.bash_profile


SPARK_HOME=/opt/app/spark-1.4.1
cd `dirname "$0"`/../
pwd
red='\e[0;41m' #red
configFile=conf/source.properties
jars=`echo lib/*.jar $SPARK_HOME/lib_managed/jars/*.jar | tr ' ' ','`
mainJar=atom-mllib_2.10-0.10.jar
echo -e "\033[31m jars:$jars \033[0m"
checkMetaTable(){
  read inputDb inputTable < <(cat $configFile |grep ^table_name|awk -F '=' '{print $2}'|awk -F '.' '{print $1,$2}')
  outdb=`cat $configFile |grep out_db|awk -F "=" '{print$2}'`
  outdbFlag=`hive -e "show databases like '$outdb'"|grep $outdb|wc -l`
  if [ $outdbFlag -lt 1 ];then
    echo -e "\033[31m Error:outdb $outdb does not exist! \033[0m"
    exit -2
  fi
  outTable="metadata_"$inputTable
  echo ${outdb}.${outTable}
  #check the input table
  inputFlag=`hive -e "use $inputDb;show tables like '$inputTable'" |grep $inputTable|wc -l`
  if [ $inputFlag -lt 1  ];then
    echo "Error:input table doesn't exist."
    exit -1
  else
    outFlag=`hive -e "use $outdb;show tables like '$outTable'"|grep $outTable |wc -l`
    if [ $outFlag -gt 0 ];then
      read -p $'\e[31m out table  exists,delete?[yes/no]\e[0m: ' del
      if [ $del =  "yes" ];then
        hive -e "use $outdb;drop table $outTable"
      else
        exit -1
      fi
     fi
  fi
}

submit(){
  $SPARK_HOME/bin/spark-submit --master yarn-client --jars $jars  --class com.ctrip.atom.ml.client.Client   --num-executors 20 --driver-memory 4g --executor-memory 5g --executor-cores 5 --driver-cores 2  $mainJar $@ configFile=conf/source.properties
}
case "$1" in
   'metadata')
   #checkMetaTable
    shift
    submit metadata $@
     ;;
    'eda')
    submit edacon
    ;;
    'dummy')
    submit dummy
    ;;
    *)
    echo "Usage : $0 {metadata|edacon|dummy}"
    exit 1
esac
exit 0
