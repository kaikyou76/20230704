/**
 * Copyright (c) 2014, NET MARKS COMPANY, LIMITED All Rights Reserved.
 * ErrorMailMapper.java
 *
 * @date 2013/09/12
 * @version 1.0
 * @author KSC Hiroaki Endo
 */

package jp.co.netmarks.batch.persistence;

import java.util.List;
import java.util.Map;

/**
 * <pre>
 * Error Mail マッパークラス
 *
 * &lt; MODIFICATION HISTORY&gt;
 * 1.0 2013/09/12 KSC Hiroaki Endo 新規作成
 * </pre>
 *
 * @author KSC Hiroaki Endo
 * @version 1.0 2013/09/12
 */
public interface ErrorMailMapper {
    /**
     * 管理者のMAIL_ADDRESS取得
     * @return MAIL_ADDRESS
     */
    List<Map<String, Object>> selectMailAll();

    /**
     * 実行者のMAIL_ADDRESS取得
     * @param parameterValues
     * @return 実行者のMAIL_ADDRESS
     */
    Map<String, Object> selectHimself(Map<String, Object> parameterValues);

    /**
     * メール宛先に実行者を含めるか否か
     * @param parameterValues
     * @return メール宛先に実行者を含めるか否か
     */
    Map<String, Object> selectisHimself(Map<String, Object> parameterValues);
}
