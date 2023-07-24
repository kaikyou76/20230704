/*
 * Copyright (c) 2014, NET MARKS COMPANY, LIMITED All Rights Reserved.
 *
 * VoiceLoggerAssociationMapper.java
 *
 * @date 2013/08/27
 * @version 1.0
 * @author KSC Yuichiro Yoshida
 */
package jp.co.netmarks.persistence;

import jp.co.netmarks.model.entity.VoiceLoggerAssociationEntity;

/**
 * <pre>
 * VOICE_LOGGER_ASSOCIATIONテーブル用データマッパーインタフェース
 *
 * &lt;MODIFICATION HISTORY&gt;
 * 1.0 2013/08/27 KSC Yuichiro Yoshida 新規作成
 * </pre>
 *
 * @author KSC Yuichiro Yoshida
 * @version 1.0 2013/08/27
 */
public interface VoiceLoggerAssociationMapper {

	/**
	 * 登録処理
	 *
	 * @param params 登録パラメータ
	 * @return 登録件数
	 */
	int inertVoiceLoggerAssociation(VoiceLoggerAssociationEntity params);

	/**
	 * 更新処理
	 *
	 * @param params 更新パラメータ
	 * @return 更新件数
	 */
	int updateVoiceLoggerAssociation(VoiceLoggerAssociationEntity params);

	/**
	 * 更新処理（削除フラグ更新）
	 *
	 * @param params 更新パラメータ
	 * @return 更新件数
	 */
	int updateVoiceLoggerAssociationDelete(VoiceLoggerAssociationEntity params);
}