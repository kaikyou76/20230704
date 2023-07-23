#!/bin/sh
#############################################################################
#
#  機能概要     : MasterParamter取得 JOB起動シェル
#
#  引数         : 無
#
#  戻り値       : ==0 正常終了
#               : !=0 異常終了
#
##############################################################################
#------------------#
# 環境変数読み込み #
#------------------#
. /home/batchuser/job/profile/.profile

#-----------#
# JOBの実行 #
#-----------#
java -cp ${CLASSPATH} org.springframework.batch.core.launch.support.CommandLineJobRunner classpath:/launch-context.xml masterUpd >/dev/null 2>&1

exit $?