#!/bin/sh
##############################################################################
#
#  機能概要     : OUTPUT CSV FTPS 転送テスト
#
#  戻り値       : ==0 正常終了
#               : !=0 異常終了
#
##############################################################################
echo "SFTP起動"

# 必須情報（要メンテナンス）#############################
SEND_SERVER_CNT=2                              #対象サーバー数
HOST_NAME1="172.16.23.103"                     #ホスト名またはIPアドレス1
HOST_NAME2="172.16.23.103"                     #ホスト名またはIPアドレス2
USER_NAME1="root"                              #ftpユーザー名1
USER_NAME2="root"                              #ftpユーザー名2
PASSWORD1="cucmadmin"                          #パスワード1
PASSWORD2="cucmadmin"                          #パスワード2
#ftp先の作業ディレクトリ
PUT_DIR="/home/batchuser/tmp"
##########################################################

#転送先のファイル名
TO_FILE_NAME="EXPORT_CHARGE.txt"
#ローカル側の作業ディレクトリ
LOCAL_DIR="/var/www/download/data/export/batch/associate"

#転送元ファイル名が渡されてない場合は、LOCALDIRの最新を対象
FILE_NAME=$1
if [ $1="" ]; then
FILE_NAME=`ls -t ${LOCAL_DIR}/*CHARGE* | head -1 | xargs basename`
fi
echo "転送するファイル名： " ${FILE_NAME}
echo "保存ファイル名（転送先）：" ${TO_FILE_NAME}

echo "1サーバー転送開始"
expect -c "
set timeout 10
spawn sftp ${USER_NAME1}@${HOST_NAME1}
expect \"Are you sure you want to continue connecting (yes/no)?\" {
  send \"yes\n\"
expect \"${USER_NAME1}@${HOST_NAME1}'s password:\"
  send \"${PASSWORD1}\n\" 
} \"${USER_NAME1}@${HOST_NAME1}'s password:\" {
  send \"${PASSWORD1}\n\" 
}
expect \"sftp>\"
send \"lcd ${LOCAL_DIR}\n\" 
send \"cd ${PUT_DIR}\n\" 
send \"put ${FILE_NAME} ${TO_FILE_NAME}\n\" 
send \"exit\n\" 
interact 
"
echo "1サーバー転送終了"

if [ ${SEND_SERVER_CNT} -eq 2 ]; then
echo "2サーバー転送開始"
expect -c "
set timeout 10
spawn sftp ${USER_NAME2}@${HOST_NAME2}
expect \"Are you sure you want to continue connecting (yes/no)?\" {
  send \"yes\n\"
expect \"${USER_NAME2}@${HOST_NAME2}'s password:\"
  send \"${PASSWORD2}\n\" 
} \"${USER_NAME2}@${HOST_NAME2}'s password:\" {
  send \"${PASSWORD2}\n\" 
}
expect \"sftp>\"
send \"lcd ${LOCAL_DIR}\n\" 
send \"cd ${PUT_DIR}\n\" 
send \"put ${FILE_NAME} ${TO_FILE_NAME}\n\" 
send \"exit\n\" 
interact 
"
echo "2サーバー転送終了"
fi

exit $?