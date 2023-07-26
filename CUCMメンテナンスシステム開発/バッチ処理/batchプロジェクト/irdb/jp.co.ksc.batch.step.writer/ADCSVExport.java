/*
 * Copyright (c) 2014, NET MARKS COMPANY, LIMITED All Rights Reserved.
 *
 * ADCSVExport.java
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
import jp.co.ksc.batch.util.BatchSettings;
import jp.co.ksc.batch.util.CSVUtil;
import jp.co.ksc.batch.util.LockFileManager;
import jp.co.netmarks.batch.persistence.CSVExpImpMapper;

/**
 * <pre>
 * ADCSVExportor
 *
 * &lt;MODIFICATION HISTORY&gt;
 * 1.0 2013/09/12 KSC Hiroaki Endo 新規作成
 * </pre>
 *
 * @author KSC Hiroaki Endo
 * @version 1.0 2013/09/12
 */
public class ADCSVExport implements ItemWriter<Object> {

	private static final Log log = LogFactory.getLog(ADCSVExport.class);

	@Autowired
    private Properties properties;
	@Autowired
	private CSVExpImpMapper csvMapper;


	/**
	 * ADをCSVへExportします
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
            Map<String,Object>[] expVL = csvMapper.getADExp();

            // 出力
            CSVUtil csv = new CSVUtil(expVL);
            csv.setHasHeader(true);
	        String[] header = bs.getADCsvHeader().split(",",0);

	        if (header != null) {
	            for (int i = 0; i < header.length; i++) {
	            	csv.addHeader(header[i]);
	            }
	        } else {
	            log.warn("CSVファイルのヘッダーが定義されてません。");
	            throw new CSVException();
	        }
            csv.setFileName(bs.getOutputDirAssociate() + csv.getExpTimeAddFileNM(bs.getExportAD()));

            File backupDir = new File(bs.getOutputDirAssociate());
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