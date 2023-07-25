/*
 * Copyright (c) 2014, NET MARKS COMPANY, LIMITED All Rights Reserved.
 *
 * BaseGridListForm.java
 *
 * @date 2013/08/01
 * @version 1.0
 * @author KSC Tomomichi Iwasawa
 */
package jp.co.ksc.model.form.list;

/**
 * <pre>
 * GridのフォームBean基底クラス
 *
 * &lt;MODIFICATION HISTORY&gt;
 * 1.0 2013/08/01 KSC Tomomichi Iwasawa 新規作成
 * </pre>
 *
 * @author KSC Tomomichi Iwasawa
 * @version 1.0 2013/08/01
 */
public class BaseGridListForm {

	/* チェック有無 */
	private boolean checked = true;

	/* 値の変更有無 */
	private boolean changed;

/*********************************************************************
 * generated getter/setter
**********************************************************************/

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	public boolean isChanged() {
		return changed;
	}

	public void setChanged(boolean changed) {
		this.changed = changed;
	}

}