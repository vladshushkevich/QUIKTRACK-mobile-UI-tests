#!/usr/bin/env bash


shopt -s expand_aliases
set -o pipefail

KRED="\x1B[31m"
KNRM="\x1B[0m"
KGRN="\x1B[32m"
KYEL="\x1B[33m"
token='Phc0BNKlsiAAAAAAAAAAXDLpaNx39e5EwaIaCd_1INd0UPYlmR6W1OsGuo-vQCNz'
username='alerts'
upasswd='37def3954ce91dd33802cc47194926e7'
messageFile='send.html'

trap "rm -f ${messageFile}" SIGINT SIGKILL SIGQUIT SIGSEGV SIGPIPE SIGALRM SIGTERM EXIT

function ERROR() {

    printf $KRED"ERROR: "$KNRM 
    printf "%s " "$@" 
    printf "\n"
}

function OK() {

    printf $KGRN"OK: "$KNRM  
    printf "%s " "[ $@ ]" 
    printf "\n"
    echo "<br>$@<br>" >> ${messageFile}
}

function uploadData(){

    localFileName=report.zip
    uploadFileName="report-`date +%Y-%m-%d:%H:%M:%S`.zip"
    cp ${localFileName} ${uploadFileName}
    isUploaded=`curl -s -q -X POST https://content.dropboxapi.com/2/files/upload --header "Authorization: Bearer ${token}"  \
        --header "Dropbox-API-Arg: {\"path\": \"/Application/${uploadFileName}\",\"mode\": \"add\",\"autorename\": true,\"mute\": false}" \
        --header "Content-Type: application/octet-stream" --data-binary @${uploadFileName}`
    fileHash=`echo ${isUploaded} | jq '.content_hash' | tr -d '"'`
    uploadFileDownloadLink=`curl -q -s -XPOST "https://api.dropboxapi.com/1/shares/auto/Application/${uploadFileName}" --header "Authorization: Bearer ${token}"`
    fileDownloadLink=`echo ${uploadFileDownloadLink} | jq '.url' | tr -d '"'`

    if [ -z ${fileHash} ] ; then
        ERROR 'Upload file:    [ FAIL ]'
        ERROR 'Hash data is empty'
    else
        OK 'Upload file:    [ DONE ]'
        OK 'Upload file hash: ' ${fileHash}
        OK 'Upload file name: ' ${uploadFileName}
        OK 'Upload file download link:' ${fileDownloadLink}
    fi
    rm -rf ${uploadFileName}
}

function sendEmailToUser(){

    sendEmail -f ${username}@intellectsoft.net \
    -t $1 -u "Intellectsoft Minsk | Future Entertainment World API tests report" -o message-file="${messageFile}" \
    -o message-content-type=html -s smtp.gmail.com:587 -xu ${username}@intellectsoft.net -xp ${upasswd} 
}

uploadData
# sendEmailToUser "$1"





