#!/bin/bash
DIR="$(dirname "$0")"
cd $DIR

. $DIR/engine.properties

pid_file=$1
rm -R /var/run/scheduler-engine/running-engines/
mkdir -p /var/run/scheduler-engine/running-engines/
touch /var/run/scheduler-engine/use-sqs
touch /var/run/scheduler-engine/use-sqs-del

instanceid=`wget -q -O - http://169.254.169.254/latest/meta-data/instance-id`

rm /var/run/scheduler-engine.pid
java_option=""
while read PROC
do
  id=$((PROC+1))
  java -server ${java_options} -Xms1024m -Xmx1512m -XX:MaxPermSize=256m -Delasticsearch.enable=${elasticsearch_enable} -Delasticsearch.address=${elasticsearch_address} -Delasticsearch.port=${elasticsearch_port} -DEC2InstanceId=${instanceid} -DEngineId=${engine_prefix_id}:${instanceid}:${id} -DLogExecution=${log_execution} -DgraphiteHost=${graphite_host} -DgraphitePort=${graphite_port} "-Dhazelcast.address=${hazelcast_address}" -Dhazelcast.timeout=${hazelcast_timeout} -Dhazelcast.attemp.limit=${hazelcast_attemp_limit} -Dhazelcast.attemp.period=${hazelcast_attemp_period} -Dlog4j.configuration=log4j.properties -Dlogfile.name=${logfile_path}/${engine_prefix_id}_${instanceid}_${id}/optaplanner.log -cp lib/jsonevent-layout-1.7-SNAPSHOT.jar:lib/json-smart-1.1.1.jar:lib/cdi-api-1.1.jar:lib/commons-beanutils-1.8.3.jar:lib/commons-io-2.4.jar:lib/commons-lang-2.6.jar:lib/commons-lang3-3.1.jar:lib/commons-math3-3.2.jar:lib/disruptor-3.2.1.jar:lib/elasticsearch-1.1.1.jar:lib/engine-sqlserver-loader-1.0.jar:lib/gs-collections-5.1.0.jar:lib/gs-collections-api-5.1.0.jar:lib/hazelcast-3.5.jar:lib/hazelcast-all-3.5.jar:lib/jackson-annotations-2.3.0.jar:lib/jackson-core-2.3.0.jar:lib/jackson-databind-2.3.0.jar:lib/jackson-datatype-joda-2.3.0.jar:lib/jackson-module-afterburner-2.3.0.jar:lib/jboss-logging-3.1.3.GA.jar:lib/jboss-vfs-3.0.1.GA.jar:lib/joda-convert-1.7.jar:lib/joda-time-2.3.jar:lib/joda-time-hibernate-1.3.jar:lib/json-20131018.jar:lib/log4j-1.2.17.jar:lib/metrics-core-3.0.2.jar:lib/mysql-connector-java-5.1.6.jar:lib/optaplanner-engine-1.0.jar:lib/reactor-core-1.1.2.RELEASE.jar:lib/scheduler-common-1.0.jar:lib/scheduler-engine-1.0.jar:lib/shiro-core-1.2.2.jar:lib/slf4j-api-1.7.7.jar:lib/slf4j-simple-1.7.7.jar:lib/slf4j-log4j12-1.7.7.jar:lib/usertype.core-3.1.0.GA.jar:lib/kie-api-6.2.0.CR4.jar:lib/optaplanner-core-6.2.0.CR4.jar:lib/commons-collections4-4.0.jar:lib/engine-db-loader-api-1.0.jar:lib/jandex-1.2.1.Final.jar:lib/javassist-3.18.2-GA.jar:lib/jboss-logging-annotations-1.2.0.Final.jar:lib/xstream-1.4.7.jar:lib/xmlpull-1.1.3.1.jar:lib/xpp3_min-1.1.4c.jar:lib//drools-compiler-6.2.0.CR4.jar:lib/drools-core-6.2.0.CR4.jar:lib/guava-13.0.1.jar:lib/mvel2-2.2.1.Final.jar:lib/commons-collections-3.2.1.jar:lib/kie-internal-6.2.0.CR4.jar:lib/protobuf-java-2.5.0.jar:lib/ecj-3.7.2.jar:lib/antlr-runtime-3.5.jar:lib/aws-java-sdk-1.9.40.jar:lib/lucene-core-4.7.2.jar:lib/lucene-analyzers-common-4.7.2.jar:lib/aws-java-sdk-core-1.9.40.jar:lib/aws-java-sdk-sqs-1.9.40.jar:lib/aws-java-sdk-s3-1.9.40.jar:lib/aws-java-sdk-autoscaling-1.9.40.jar:lib/commons-logging-1.1.1.jar:lib/httpcore-4.3.2.jar:lib/httpclient-4.3.4.jar com.emlogis.schedule.engine.SchedulingEngine &

  echo $! >> /var/run/scheduler-engine.pid

done < <(cat /proc/cpuinfo | grep processor|awk '{print $3}')
