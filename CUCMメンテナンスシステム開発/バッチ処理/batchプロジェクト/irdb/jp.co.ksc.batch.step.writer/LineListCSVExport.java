/*
 * Copyright (c) 2014, NET MARKS COMPANY, LIMITED All Rights Reserved.
 *
 * LineListCSVExport.java
 *
 * @date 2013/09/12
 * @version 1.0
 * @author KSC Hiroaki Endo
 */
package jp.co.ksc.batch.step.writer;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;

import jp.co.ksc.batch.exception.BatRuntimeException;
import jp.co.ksc.batch.exception.CSVException;
import jp.co.ksc.batch.util.BaseConstants;
import jp.co.ksc.batch.util.BatchSettings;
import jp.co.ksc.batch.util.CSVUtil;
import jp.co.ksc.batch.util.LockFileManager;
import jp.co.netmarks.batch.persistence.CSVExpImpMapper;

/**
 * <pre>
 * LineListCSVExportor
 *
 * &lt;MODIFICATION HISTORY&gt;
 * 1.0 2013/09/12 KSC Hiroaki Endo 新規作成
 * </pre>
 *
 * @author KSC Hiroaki Endo
 * @version 1.0 2013/09/12
 */
public class LineListCSVExport implements ItemWriter<Object> {

	private static final Log log = LogFactory.getLog(LineListCSVExport.class);

	@Autowired
    private Properties properties;
	@Autowired
	private CSVExpImpMapper csvMapper;

	/**
	 * LineListをCSVへExportします
	 * @param paramList パラメータ
	 * @throws Exception
	 */
	@Override
	public void write(List<?> paramList) throws Exception {

		BatchSettings bs = new BatchSettings(properties);
        // ロックファイルを確認
        try {
            LockFileManager.lock(bs);
        } catch (IOException ex) {
            throw new BatRuntimeException(ex.getMessage(), ex);
        }
        try {

            // データ取得
        	BaseConstants constants = new BaseConstants();
            //Map<String,Object>[] expVL = csvMapper.getLineListExp(constants);
            Map<String,Object>[] expVL = csvMapper.getUserAndTelCsvList(constants);
         // ループ処理でexpVLの中身をログに出力する
            for (Map<String, Object> map : expVL) {
                for (Map.Entry<String, Object> entry : map.entrySet()) {
                    String key = entry.getKey();
                    Object value = entry.getValue();

                    // ログに出力
                    log.info(key + ": " + value);
                    // または、System.out.printで出力する場合
                    // System.out.print(key + ": " + value + " ");
                }
                // 改行を入れるためのログ出力
                log.info(""); // または System.out.println(""); を使う場合
            }


            // 出力
            CSVUtil csv = new CSVUtil(expVL);
            csv.setHasHeader(true);
	        String[] header = bs.getLineListCsvHeader().split(",",0);

	        if (header != null) {
	            for (int i = 0; i < header.length; i++) {
	            	csv.addHeader(header[i]);
	            }
	        } else {
	            log.warn("CSVファイルのヘッダーが定義されてません。");
	            throw new CSVException();
	        }
            csv.setFileName(bs.getOutputDirCircuitlist() + csv.getExpTimeAddFileNM(bs.getExportLineList()));

            File backupDir = new File(bs.getOutputDirCircuitlist());
            if (!backupDir.exists()) {
                backupDir.mkdirs();
            }
            csv.write();

		}catch (Exception e){
			throw e;
		} finally {
            // ロック解除
            LockFileManager.unlock(bs);
		}
	}
}