#!/bin/sh
#############################################################################
#
#  機能概要     : 夜間バッチ前半 JOB起動シェル
#
#  引数         : 無
#
#  戻り値       : ==0 正常終了
#               : !=0 異常終了
#
##############################################################################
export TMP_DIR=/home/batchuser/tmp
export SCRIPT_DIR=/home/batchuser/job
export MSG_DATE=`date '+%Y-%m-%d'`
export LOG_DATE=`date +"%Y/%m/%d %p %I:%M:%S"`
export LOG=/var/www/download/logs/batch/before_batch_executed_result_$MSG_DATE.log

#------監視システム用------#
export EXECUTIVE_START=START
export EXECUTIVE_OK=OK
export EXECUTIVE_NG=NG
#システム停止・メンテナンス画面に変更
export EXECUTIVE_LOG_MAINTENANCE=ChangeMaintenance
#Tomcatリスタート
export EXECUTIVE_LOG_TOMCAT=RestartTomcat
#バッチ処理前のビジネスフォンクラスタ反映
export EXECUTIVE_LOG_CUCMREFLECT=FirstCUCMReflect
#課金情報CSVエクスポート/送信
export EXECUTIVE_LOG_CHARGE=ChargeExport
#CUC情報CSVエクスポート
export EXECUTIVE_LOG_CUC=CUCExport
#電子電話帳情報CSVエクスポート
export EXECUTIVE_LOG_TELDIR=TelDirExport
#通録サーバ情報CSVエクスポート
export EXECUTIVE_LOG_VOICE=VoiceLoggerExport
#AD情報のCSVエクスポート/送信
export EXECUTIVE_LOG_AD=ADExport
#回線一覧のCSVエクスポート
export EXECUTIVE_LOG_LINE=LineListExport
#DB内全データCSVエクスポート
export EXECUTIVE_LOG_DBCSV=DBCSVExport
#ローテート処理
export EXECUTIVE_LOG_ROTATE=LogRotate
#バックアップ
export EXECUTIVE_LOG_BACKUP=DBBackUp

echo $LOG_DATE" : ※バッチ処理前半開始。人事情報取り込み処理までを実行します。" >> $LOG

#-----------#
# JOBの実行 #
#-----------#
if [ -e $TMP_DIR/run_turough_1_END ]; then
  echo $LOG_DATE" : ※夜間バッチ処理 前半正常終了。人事情報取り込み処理時間待ち" >> $LOG
  exit 0;
fi

#---------------------------------------------------------
# メンテナンス画面設定処理
#---------------------------------------------------------
#監視ファイル作成
/usr/local/shell/sendstatus.sh $EXECUTIVE_LOG_MAINTENANCE $EXECUTIVE_START
$SCRIPT_DIR/set_maintenance.sh
export SHRESULT=$?
#---------------------------------------------------------
# tomcatリスタート
#---------------------------------------------------------
if [ $SHRESULT -eq 0 ]; then
    #監視正常終了ファイル作成
    /usr/local/shell/sendstatus.sh $EXECUTIVE_LOG_MAINTENANCE $EXECUTIVE_OK
    #監視ファイル作成
    /usr/local/shell/sendstatus.sh $EXECUTIVE_LOG_TOMCAT $EXECUTIVE_START
    /etc/init.d/tomcat restart
else
    echo $LOG_DATE" : バッチ処理前半 [メンテナンス画面設定処理]にてエラー" >> $LOG
    echo $LOG_DATE" : [set_maintenance.sh]" >> $LOG
    $SCRIPT_DIR/set_error.sh
    #----------------------------------#
    # 監視システムへエラーファイル転送 #
    #----------------------------------#
    /usr/local/shell/sendstatus.sh $EXECUTIVE_LOG_MAINTENANCE $EXECUTIVE_NG
    exit 9
fi
#---------------------------------------------------------
# CUCM反映処理
#---------------------------------------------------------
if lsof -i:8080; then
    #監視正常終了ファイル作成
    /usr/local/shell/sendstatus.sh $EXECUTIVE_LOG_TOMCAT $EXECUTIVE_OK
    #監視ファイル作成
    /usr/local/shell/sendstatus.sh $EXECUTIVE_LOG_CUCMREFLECT $EXECUTIVE_START
    $SCRIPT_DIR/DailyCUCMReflection.sh
    SHRESULT=$?
else
    echo $LOG_DATE" : バッチ処理前半 [tomcatリスタート処理]にてエラー" >> $LOG
    echo $LOG_DATE" : [set_maintenance.sh]" >> $LOG
    $SCRIPT_DIR/set_error.sh
    #----------------------------------#
    # 監視システムへエラーファイル転送 #
    #----------------------------------#
    /usr/local/shell/sendstatus.sh $EXECUTIVE_LOG_TOMCAT $EXECUTIVE_NG
    exit 9
fi
#---------------------------------------------------------
# 課金情報出力処理
#---------------------------------------------------------
if [ $SHRESULT -eq 0 ]; then
    #監視正常終了ファイル作成
    /usr/local/shell/sendstatus.sh $EXECUTIVE_LOG_CUCMREFLECT $EXECUTIVE_OK
    #監視ファイル作成
    /usr/local/shell/sendstatus.sh $EXECUTIVE_LOG_CHARGE $EXECUTIVE_START
    $SCRIPT_DIR/csvChargeExport.sh
    SHRESULT=$?
else
    echo $LOG_DATE" : バッチ処理前半 [CUCM反映処理]にてエラー" >> $LOG
    echo $LOG_DATE" : [DailyCUCMReflection.sh]" >> $LOG
    $SCRIPT_DIR/set_error.sh
    #----------------------------------#
    # 監視システムへエラーファイル転送 #
    #----------------------------------#
    /usr/local/shell/sendstatus.sh $EXECUTIVE_LOG_CUCMREFLECT $EXECUTIVE_NG
    exit 9
fi
#---------------------------------------------------------
# ユニティ情報出力処理
#---------------------------------------------------------
if [ $SHRESULT -eq 0 ]; then
    #監視正常終了ファイル作成
    /usr/local/shell/sendstatus.sh $EXECUTIVE_LOG_CHARGE $EXECUTIVE_OK
    #監視ファイル作成
    /usr/local/shell/sendstatus.sh $EXECUTIVE_LOG_CUC $EXECUTIVE_START
    $SCRIPT_DIR/csvCUCExport.sh
    SHRESULT=$?
else
    echo $LOG_DATE" : バッチ処理前半 [課金情報出力処理]にてエラー" >> $LOG
    echo $LOG_DATE" : [csvChargeExport.sh]" >> $LOG
    $SCRIPT_DIR/set_error.sh
    #----------------------------------#
    # 監視システムへエラーファイル転送 #
    #----------------------------------#
    /usr/local/shell/sendstatus.sh $EXECUTIVE_LOG_CHARGE $EXECUTIVE_NG
    exit 9
fi
#---------------------------------------------------------
# 電子電話帳出力処理
#---------------------------------------------------------
if [ $SHRESULT -eq 0 ]; then
    #監視正常終了ファイル作成
    /usr/local/shell/sendstatus.sh $EXECUTIVE_LOG_CUC $EXECUTIVE_OK
    #監視ファイル作成
    /usr/local/shell/sendstatus.sh $EXECUTIVE_LOG_TELDIR $EXECUTIVE_START
    $SCRIPT_DIR/csvTelDirExport.sh
    SHRESULT=$?
else
    echo $LOG_DATE" : バッチ処理前半 [ユニティ情報出力処理]にてエラー" >> $LOG
    echo $LOG_DATE" : [csvCUCExport.sh]" >> $LOG
    $SCRIPT_DIR/set_error.sh
    #----------------------------------#
    # 監視システムへエラーファイル転送 #
    #----------------------------------#
    /usr/local/shell/sendstatus.sh $EXECUTIVE_LOG_CUC $EXECUTIVE_NG
    exit 9
fi
#---------------------------------------------------------
# 通録情報出力処理
#---------------------------------------------------------
if [ $SHRESULT -eq 0 ]; then
    #監視正常終了ファイル作成
    /usr/local/shell/sendstatus.sh $EXECUTIVE_LOG_TELDIR $EXECUTIVE_OK
    #監視ファイル作成
    /usr/local/shell/sendstatus.sh $EXECUTIVE_LOG_VOICE $EXECUTIVE_START
    $SCRIPT_DIR/csvVoiceLoggerExport.sh
    SHRESULT=$?
else
    echo $LOG_DATE" : バッチ処理前半 [電子電話帳出力処理]にてエラー" >> $LOG
    echo $LOG_DATE" : [csvTelDirExport.sh]" >> $LOG
    $SCRIPT_DIR/set_error.sh
    #----------------------------------#
    # 監視システムへエラーファイル転送 #
    #----------------------------------#
    /usr/local/shell/sendstatus.sh $EXECUTIVE_LOG_TELDIR $EXECUTIVE_NG
    exit 9
fi
#---------------------------------------------------------
# AD情報出力処理
#---------------------------------------------------------
if [ $SHRESULT -eq 0 ]; then
    #監視正常終了ファイル作成
    /usr/local/shell/sendstatus.sh $EXECUTIVE_LOG_VOICE $EXECUTIVE_OK
    #監視ファイル作成
    /usr/local/shell/sendstatus.sh $EXECUTIVE_LOG_AD $EXECUTIVE_START
    $SCRIPT_DIR/csvADExport.sh
    SHRESULT=$?
else
    echo $LOG_DATE" : バッチ処理前半 [通録情報出力処理]にてエラー" >> $LOG
    echo $LOG_DATE" : [csvVoiceLoggerExport.sh]" >> $LOG
    $SCRIPT_DIR/set_error.sh
    #----------------------------------#
    # 監視システムへエラーファイル転送 #
    #----------------------------------#
    /usr/local/shell/sendstatus.sh $EXECUTIVE_LOG_VOICE $EXECUTIVE_NG
    exit 9
fi
#---------------------------------------------------------
# 回線一覧情報出力処理
#---------------------------------------------------------
if [ $SHRESULT -eq 0 ]; then
    #監視正常終了ファイル作成
    /usr/local/shell/sendstatus.sh $EXECUTIVE_LOG_AD $EXECUTIVE_OK
    #監視ファイル作成
    /usr/local/shell/sendstatus.sh $EXECUTIVE_LOG_LINE $EXECUTIVE_START
    $SCRIPT_DIR/csvLineListExport.sh
    SHRESULT=$?
else
    echo $LOG_DATE" : バッチ処理前半 [AD情報出力処理]にてエラー" >> $LOG
    echo $LOG_DATE" : [csvADExport.sh]" >> $LOG
    $SCRIPT_DIR/set_error.sh
    #----------------------------------#
    # 監視システムへエラーファイル転送 #
    #----------------------------------#
    /usr/local/shell/sendstatus.sh $EXECUTIVE_LOG_AD $EXECUTIVE_NG
    exit 9
fi
#---------------------------------------------------------
# 全テーブルCSV一括出力処理
#---------------------------------------------------------
if [ $SHRESULT -eq 0 ]; then
    #監視正常終了ファイル作成
    /usr/local/shell/sendstatus.sh $EXECUTIVE_LOG_LINE $EXECUTIVE_OK
    #監視ファイル作成
    /usr/local/shell/sendstatus.sh $EXECUTIVE_LOG_DBCSV $EXECUTIVE_START
    $SCRIPT_DIR/csvAllTblExport.sh
    SHRESULT=$?
else
    echo $LOG_DATE" : バッチ処理前半 [回線一覧情報出力処理]にてエラー" >> $LOG
    echo $LOG_DATE" : [csvLineListExport.sh]" >> $LOG
    $SCRIPT_DIR/set_error.sh
    #----------------------------------#
    # 監視システムへエラーファイル転送 #
    #----------------------------------#
    /usr/local/shell/sendstatus.sh $EXECUTIVE_LOG_LINE $EXECUTIVE_NG
    exit 9
fi
#---------------------------------------------------------
# 日時DBバックアップ起動シェル
#---------------------------------------------------------
if [ $SHRESULT -eq 0 ]; then
    #監視正常終了ファイル作成
    /usr/local/shell/sendstatus.sh $EXECUTIVE_LOG_DBCSV $EXECUTIVE_OK
    #監視ファイル作成
    /usr/local/shell/sendstatus.sh $EXECUTIVE_LOG_BACKUP $EXECUTIVE_START
    $SCRIPT_DIR/dump.sh
    SHRESULT=$?
else
    echo $LOG_DATE" : バッチ処理前半 [全テーブルCSV一括出力処理]にてエラー" >> $LOG
    echo $LOG_DATE" : [csvAllTblExport.sh]" >> $LOG
    $SCRIPT_DIR/set_error.sh
    #----------------------------------#
    # 監視システムへエラーファイル転送 #
    #----------------------------------#
    /usr/local/shell/sendstatus.sh $EXECUTIVE_LOG_DBCSV $EXECUTIVE_NG
    exit 9
fi
#---------------------------------------------------------
# 出力ファイル一定期間後削除処理
#---------------------------------------------------------
if [ $SHRESULT -eq 0 ]; then
    #監視正常終了ファイル作成
    /usr/local/shell/sendstatus.sh $EXECUTIVE_LOG_BACKUP $EXECUTIVE_OK
    #監視ファイル作成
    /usr/local/shell/sendstatus.sh $EXECUTIVE_LOG_ROTATE $EXECUTIVE_START
    $SCRIPT_DIR/del_files.sh
    SHRESULT=$?
else
    echo $LOG_DATE" : バッチ処理前半 [日時DBバックアップ起動シェル]にてエラー" >> $LOG
    echo $LOG_DATE" : [dump.sh]" >> $LOG
    $SCRIPT_DIR/set_error.sh
    #----------------------------------#
    # 監視システムへエラーファイル転送 #
    #----------------------------------#
    /usr/local/shell/sendstatus.sh $EXECUTIVE_LOG_BACKUP $EXECUTIVE_NG
    exit 9
fi
#---------------------------------------------------------
# 前半終了
#---------------------------------------------------------
if [ $SHRESULT -eq 0 ]; then
    #監視正常終了ファイル作成
    /usr/local/shell/sendstatus.sh $EXECUTIVE_LOG_ROTATE $EXECUTIVE_OK
    echo $LOG_DATE" : ※バッチ処理前半完了。人事情報取り込み処理を待ちます。" >> $LOG
    touch $TMP_DIR/run_turough_1_END
    if [ -e $TMP_DIR/run_turough_2_END ]; then
        rm -f $TMP_DIR/run_turough_2_END
    fi
else
    echo $LOG_DATE" : バッチ処理前半 [出力ファイル一定期間後削除処理]にてエラー" >> $LOG
    echo $LOG_DATE" : [del_files.sh]" >> $LOG
    $SCRIPT_DIR/set_error.sh
    #----------------------------------#
    # 監視システムへエラーファイル転送 #
    #----------------------------------#
    /usr/local/shell/sendstatus.sh $EXECUTIVE_LOG_ROTATE $EXECUTIVE_NG
    exit 9
fi
#---------------------------------------------------------

exit 0