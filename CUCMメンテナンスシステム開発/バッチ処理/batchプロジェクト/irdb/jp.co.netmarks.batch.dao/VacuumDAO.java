package jp.co.netmarks.batch.dao;

/*
 * Copyright (c) 2014, NET MARKS COMPANY, LIMITED All Rights Reserved.
 *
 * VacuumDAO.java
 *
 * @date 2013/09/12
 * @version 1.0
 * @author KSC Hiroaki Endo
 */

import java.sql.SQLException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import jp.co.netmarks.batch.persistence.LoadPersonnelMapper;
import jp.co.netmarks.batch.persistence.LoadStaffMapper;
import jp.co.netmarks.batch.persistence.MasterMapper;

/**
 * <pre>
 * Vacuum操作DAOクラス
 *
 * &lt;MODIFICATION HISTORY&gt;
 * 1.0 2013/09/12 KSC Hiroaki Endo 新規作成
 * </pre>
 *
 * @author KSC Hiroaki Endo
 * @version 1.0 2013/09/12
 */
@Component
public class VacuumDAO {

    /** ログ出力クラス*/
    private Log log = LogFactory.getLog(this.getClass());

    @Autowired
    private LoadPersonnelMapper loadMapper;
    @Autowired
    private LoadStaffMapper staffMapper;
    @Autowired
    private MasterMapper masMapper;
	@Autowired
	private PlatformTransactionManager txManager;

	private DefaultTransactionDefinition dtd = null;

	/**
	 * 初期化
	 */
	private void vacuum_init() {
		dtd = new DefaultTransactionDefinition();
		dtd.setPropagationBehavior(TransactionDefinition.PROPAGATION_NOT_SUPPORTED);
		dtd.setIsolationLevel(TransactionDefinition.ISOLATION_READ_UNCOMMITTED);
	}

    /**
     * FullVacuumを実行
     * @throws SQLException
     */
    public void fullVacuum() throws SQLException {
        try {
        	vacuum_init();
			TransactionStatus ts = txManager.getTransaction(dtd);
        	masMapper.fullvacuum();
        	txManager.commit(ts);

        } catch (Exception e) {
            log.warn("Vacuum エラー: FULL Vacuum");
            throw e;
        }
    }

    /**
     * 全テーブルのVacuumとAnalyzeを実行
     * @throws SQLException
     */
    public void dayVacuum() throws SQLException {
        try {
        	vacuum_init();
			TransactionStatus ts = txManager.getTransaction(dtd);
        	masMapper.dayvacuum();
        	txManager.commit(ts);

        } catch (Exception e) {
            log.warn("Vacuum エラー: FULL Vacuum");
            throw e;
        }
    }

    /**
     * 人事情報取込系テーブルのVacuum実行
     * @throws SQLException
     */
    public void personnelVacuum() throws SQLException {
        try {
        	vacuum_init();
			TransactionStatus ts = txManager.getTransaction(dtd);
        	loadMapper.vacuum();
        	txManager.commit(ts);

        } catch (Exception e) {
            log.warn("Vacuum エラー: LoadPersonnel");
            throw e;
        }
    }

    /**
     * 人事情報取込系テーブルのVacuum実行
     * @throws SQLException
     */
    public void staffVacuum() throws SQLException {
        try {
        	vacuum_init();
			TransactionStatus ts = txManager.getTransaction(dtd);
        	staffMapper.vacuum();
        	txManager.commit(ts);

        } catch (Exception e) {
            log.warn("Vacuum エラー: LoadStaff");
            throw e;
        }
    }

    /**
     * マスターパラメータ取得系テーブルのVacuum実行
     * @throws SQLException
     */
    public void masterVacuum() throws SQLException {
        try {
        	vacuum_init();
        	TransactionStatus ts = txManager.getTransaction(dtd);
            masMapper.vacuum();
            txManager.commit(ts);

        } catch (Exception e) {
            log.warn("Vacuum エラー: MasterParameter");
            throw e;
        }
    }

}
