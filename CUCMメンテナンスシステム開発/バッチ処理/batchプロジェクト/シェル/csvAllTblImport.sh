#!/bin/sh
#############################################################################
#
#  �@�\�T�v     : �S�e�[�u���ꊇCSV���� JOB�N���V�F��
#
#  ����         : ��
#
#  �߂�l       : ==0 ����I��
#               : !=0 �ُ�I��
#
##############################################################################
#------------------#
# ���ϐ��ǂݍ��� #
#------------------#
. /home/batchuser/job/profile/.profile

#-----------#
# JOB�̎��s #
#-----------#
java -cp ${CLASSPATH} org.springframework.batch.core.launch.support.CommandLineJobRunner classpath:/launch-context.xml csvAppImport

exit $?