/*
 * Copyright (c) 2014, NET MARKS COMPANY, LIMITED All Rights Reserved
 *
 * MasterReader.java
 *
 * @date 2013/09/12
 * @version 1.0
 * @author KSC Hiroaki Endo
 */
package jp.co.ksc.batch.step.reader;

import java.util.ArrayList;
import java.util.List;

import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;

/**
 * MasterReader
 *
 * <pre>
 * マスタパラメータ取得 JOBReader (処理なし)
 *
 * &lt;MODIFICATION HISTORY&gt;
 * 1.0 2013/09/12 KSC Hiroaki Endo
 * </pre>
 *
 * @author KSC Hiroaki Endo
 * @version 1.0 2013/09/12
 */
public class MasterReader implements ItemReader<Object> {
    // 実行フラグ
    private boolean flag = true;

    /**
     * (非 Javadoc)
     *
     * @see org.springframework.batch.item.ItemReader#read()
     */
    public Object read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
        List<Integer> tmpList = new ArrayList<Integer>();
        tmpList.add(0);
        if (flag) {
            flag = false;
            return tmpList;
        } else {
            return null;
        }
    }
}