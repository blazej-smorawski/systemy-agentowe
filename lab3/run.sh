#!/bin/bash -ex

CLASS_PATH="src/main/resources/jade.jar:target/lab3-1.0.jar"
CONTAINER_NAME="main"
GUI="-gui"
HOST="localhost"
PORT="9000"
LOCAL_HOST="localhost"
LOCAL_PORT="9000"
GUI='-gui'

mvn     package

# Test classes
java    "-cp"\
        "$CLASS_PATH"\
        "main.java.pl.gda.pg.eti.kask.sa.main"\

java    "-cp"\
        "$CLASS_PATH"\
        "jade.Boot"\
        "-container-name" $CONTAINER_NAME \
        "-host" $HOST\
        "-local-host"  $LOCAL_HOST\
        "-port" $PORT\
        "-local-port" $LOCAL_PORT\
        "$GUI"\
