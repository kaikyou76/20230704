/**
 * MasterProcessor.javal
 * @date 2013/09/12
 * @version 1.0
 * @author KSC Hiroaki Endo
 */
package jp.co.ksc.batch.step.processor;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.item.ItemProcessor;

/**
 * マスタパラメータ取得Processor (処理なし)
 * author ksc
 **/
public class MasterProcessor implements ItemProcessor<Object, Object> {
    @SuppressWarnings("unused")
    private static final Log log = LogFactory.getLog(MasterProcessor.class);

    /**
     * (非 Javadoc)
     * @see org.springframework.batch.item.ItemProcessor#process(Object)
     */
    @Override
    public Object process(Object param) throws Exception {
        return param;
    }
}
