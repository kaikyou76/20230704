#!/bin/sh
#############################################################################
#
#  機能概要     : 一定期間経過ファイル削除 シェル
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
echo "一定期間経過ファイル削除 開始"
echo "30日更新されていないLogファイルを削除"
find /var/www/download/logs/batch -type f -mtime +30 -exec rm {} \;
find /var/www/download/logs/web -type f -mtime +30 -exec rm {} \;
echo "30日更新されていない夜間バッチ取込成功ファイルを削除"
find /home/batchuser/files/complatedfiles -type f -mtime +30 -exec rm {} \;
echo "30日更新されていない各出力ファイルを削除"
find /var/www/download/data/export/web -type f -mtime +30 -exec rm {} \;
find /var/www/download/data/export/batch/associate -type f -mtime +30 -exec rm {} \;
#find /var/www/download/data/export/batch/circuitlist -type f -mtime +30 -exec rm {} \;
find /var/www/download/data/export/batch/db -type f -mtime +30 -exec rm {} \;
echo "一定期間経過ファイル削除 終了"

exit 0