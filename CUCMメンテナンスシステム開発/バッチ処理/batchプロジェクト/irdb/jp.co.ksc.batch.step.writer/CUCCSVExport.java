/*
 * Copyright (c) 2014, NET MARKS COMPANY, LIMITED All Rights Reserved.
 *
 * CUCCSVExport.java
 *
 * @date 2013/09/12
 * @version 1.0
 * @author KSC Hiroaki Endo
 */
package jp.co.ksc.batch.step.writer;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
import jp.co.ksc.batch.util.CSVWriter;
import jp.co.ksc.batch.util.LockFileManager;
import jp.co.netmarks.batch.persistence.CSVExpImpMapper;

/**
 * <p>Title: CUCCSVExport.java</p>
  * <pre>
  * 'csv固定値書き込み対応
  * </pre>
* @Time:2023/07/14
* @author Yao KaiKyou
 */
public class CUCCSVExport implements ItemWriter<Object> {

    private static final Log log = LogFactory.getLog(CUCCSVExport.class);

    @Autowired
    private Properties properties;
    @Autowired
    private CSVExpImpMapper csvMapper;

    /**
     * UnityをCSVへExportします
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
            Map<String,Object>[] expVL = csvMapper.getCUCExp();
            log.info("expVLの要素数: " + expVL.length);

            String[] header = bs.getCUCCsvHeader().split(",", 0);
            if (header != null) {
                String outputFile = bs.getOutputDirAssociate() + getExpTimeAddFileNM(bs.getExportCUC());
                CSVWriter.writeCSV(outputFile, String.join(",", header), expVL);
            } else {
                String logMessage = bs.getProperty("BT_000_E003");
                String replacedMessage = MessageFormat.format(logMessage, "CUCCsvHeader");
                log.warn(replacedMessage);
                throw new CSVException();
            }

            //String logMessage = bs.getProperty("BT_000_E003");
            String logMessage = "{0}ファイルの処理は正常終了しました。処理件数：{1}";
            String arg1 = getExpTimeAddFileNM(bs.getExportCUC());
            int arg2 = expVL.length;
            logMessage = MessageFormat.format(logMessage, arg1, arg2);
            log.info(logMessage);
        } catch (Exception e) {
            throw e;
        } finally {
            // ロック解除
            LockFileManager.unlock(bs);
        }
    }

    /**
     * ファイル名の拡張子の前に現在の時間を追加するメソッド
     * @param fileName ファイル名
     * @return 拡張子の前に現在の時間を追加したファイル名
     */
    private String getExpTimeAddFileNM(String fileName) {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
        int dotIndex = fileName.lastIndexOf(".");
        if (dotIndex != -1) {
            // 拡張子が存在する場合
            String name = fileName.substring(0, dotIndex);
            String extension = fileName.substring(dotIndex);
            return name + "_" + timestamp + extension;
        } else {
            // 拡張子が存在しない場合
            return fileName + "_" + timestamp;
        }
    }

    /**
     * CSVファイルにデータを書き込むメソッド
     * @param fileName ファイル名
     * @param header ヘッダー
     * @param data データ
     * @throws IOException
     */
    private void writeCSV(String fileName, String header, Map<String,Object>[] data) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            // ヘッダーを書き込む
            writer.write(header);
            writer.newLine();

            // データを書き込む
            for (Map<String,Object> row : data) {
                StringBuilder sb = new StringBuilder();
                for (Object value : row.values()) {
                    String sanitizedValue = sanitizeValue(value != null ? value.toString() : "");
                    sb.append(sanitizedValue).append(",");
                }
                sb.deleteCharAt(sb.lastIndexOf(","));
                writer.write(sb.toString());
                writer.newLine();
            }
        } catch (IOException e) {
            throw new IOException("CSVファイルの書き込み中にエラーが発生しました: " + e.getMessage(), e);
        }
    }

    /**
     * 値をエスケープするメソッド
     * @param value エスケープする値
     * @return エスケープされた値
     */
    private String sanitizeValue(String value) {
        // "?"をエスケープ処理する
        if (value.contains("?")) {
            value = value.replace("?", "\\?");
        }
        return value;
    }
}
