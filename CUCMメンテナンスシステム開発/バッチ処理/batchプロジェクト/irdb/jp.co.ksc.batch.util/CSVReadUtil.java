/*
 * Copyright (c) 2014, NET MARKS COMPANY, LIMITED All Rights Reserved.
 *
 * CSVReadUtil.java
 *
 * @date 2013/09/12
 * @version 1.0
 * @author KSC Hiroaki Endo
 */
package jp.co.ksc.batch.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import jp.co.ksc.batch.exception.CSVException;

/**
 * <pre>
 * CSVファイル 読み込み用ユーティリティ
 *
 * &lt;MODIFICATION HISTORY&gt;
 * 1.0 2013/09/12 KSC Hiroaki Endo 新規作成
 * </pre>
 *
 * @author KSC Hiroaki Endo
 * @version 1.0 2013/09/12
 */
public class CSVReadUtil {
    /**
     * CSVファイルを解析し、読み込んだListを返す
     * @param fileName ファイル名
     * @param colCont カラム数
     * @return List<String> CSV内容
     * @throws FileNotFoundException
     * @throws IOException
     * @throws CSVException
     */
    @SuppressWarnings("resource")
	public List<String[]> read(String fileName, int colCont)
			throws FileNotFoundException, IOException, CSVException {
        List<String[]> ret = new ArrayList<String[]>();

        File file = new File(fileName);
        FileInputStream fis = new FileInputStream(file);
        InputStreamReader isr = new InputStreamReader(fis, "JISAutoDetect");

        BufferedReader d = new BufferedReader(isr);

        String s = null;
        while((s = d.readLine()) != null){
        	// 調整

            char[] tmpLine = s.toCharArray();
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < tmpLine.length; i++) {
                if (tmpLine[i] == ',') {
                    sb.append('>');
                    sb.append(',');
                    sb.append('<');
                } else {
                    sb.append(tmpLine[i]);
                }
            }
            String out = new String("<" + sb.toString() + ">");

            // 列数判定
            StringTokenizer st = new StringTokenizer(out, ",");
            if (colCont != st.countTokens()) {
                throw new CSVException("列数が異なります。");
            }

            // 値取得
            String[] line = new String[colCont];
            for (int i = 0; i < colCont; i++) {
                String colVal = st.nextToken();
                line[i] = new String(colVal.substring(1, colVal.length() - 1));
            }
            ret.add(line);
            s = null;
        }

        d.close();
        isr.close();
        fis.close();
        return ret;
    }

    /**
     * 指定されたファイルが存在するかチェックする
     *
     * @param fileName
     * @return boolean 存在有無
     */
    public boolean existFile(String fileName){
        File file = new File(fileName);
        return file.isFile();
    }
}