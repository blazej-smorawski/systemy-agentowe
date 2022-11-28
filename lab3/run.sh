#!/bin/bash -ex

CLASS_PATH="src/main/resources/jade.jar:target/lab3-1.0.jar"
CONTAINER_NAME="main"
GUI="-gui"
HOST="localhost"
PORT="9017"
LOCAL_HOST="localhost"
LOCAL_PORT="9017"
GUI='-gui'

ARGS=( "9018" "9019" "9020" )
ARGS2=( "Alicja" "Bogdan" "Cecylia" )

# mvn     package

#Test classes
java    "-cp"\
        "$CLASS_PATH"\
        "pl.gda.pg.eti.kask.sa.main"\

java    "-cp"\
        "$CLASS_PATH"\
        "jade.Boot"\
        "-container-name" $CONTAINER_NAME \
        "-host" $HOST\
        "-local-host"  $LOCAL_HOST\
        "-port" $PORT\
        "-local-port" $LOCAL_PORT\
        "$GUI"\
        &\

sleep   1s

for i in "${!ARGS[@]}"; do
        printf "CONTAINER: %s : %s\n" "${ARGS[i]}" "${ARGS[i]}"
        java        "-cp"\
                "$CLASS_PATH"\
                "jade.Boot"\
                "-container-name" ${ARGS2[i]} \
                "-host" $HOST\
                "-local-host"  $LOCAL_HOST\
                "-port" $PORT\
                "-local-port" ${ARGS[i]}\
                "-container"\
                &\
done