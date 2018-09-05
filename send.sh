#!/usr/bin/env bash


shopt -s expand_aliases
set -o pipefail

KRED="\x1B[31m"
KNRM="\x1B[0m"
KGRN="\x1B[32m"
KYEL="\x1B[33m"
username='alerts'
upasswd='37def3954ce91dd33802cc47194926e7'
messageFile='send.html'

trap "rm -f ${messageFile}" SIGINT SIGKILL SIGQUIT SIGSEGV SIGPIPE SIGALRM SIGTERM EXIT

function WARNING() {

    MESSAGES=("$@")
    count=1
    echo "WARNING" > ${messageFile}
    for (( i=1; i<=${#MESSAGES[@]}; i++ )) ; do
        echo "<br>${MESSAGES[i]}<br>" >> ${messageFile}
    done

}

function sendEmailToUser(){

    sendEmail -f ${username}@intellectsoft.net \
    -t $1 -u "Intellectsoft Minsk | Future Entertainment Exchange Platform API tests report" -o message-file="${messageFile}" \
    -o message-content-type=html -s smtp.gmail.com:587 -xu ${username}@intellectsoft.net -xp ${upasswd} 
}

WARNING "$@"
# sendEmailToUser "$1"





