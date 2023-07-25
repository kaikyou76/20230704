/*
 * Copyright (c) 2014, NET MARKS COMPANY, LIMITED All Rights Reserved.
 *
 * EscapeUtil.java
 *
 * @date 2013/08/30
 * @version 1.0
 * @author KSC Yuichiro Yoshida
 */
package jp.co.netmarks.util;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.Date;

/**
 * <pre>
 * エスケープユーティリティクラス
 *
 * &lt;MODIFICATION HISTORY&gt;
 * 1.0 2009/09/18 KSC Tomohisa Kawana 新規作成
 * </pre>
 *
 * @author KSC Yuichiro Yoshida
 * @version 1.0 2013/09/18
 */
public class EscapeUtil {

	public static final String ISO_8859_1 = "ISO-8859-1";

	/**
	 * <pre>
	 * 引数が null のとき、空白を返します。
	 * </pre>
	 *
	 * @param str String
	 * @return String
	 */
	public static String escapeNull(String str) {
		return (str == null)? "": str;
	}

	/**
	 * <pre>
	 * 引数が null のとき、空白を返します。
	 * </pre>
	 *
	 * @param obj Object
	 * @return String
	 */
	public static String escapeNull(Object obj) {
		if (obj == null) return "";
		return obj.toString();
	}

	/**
	 * <pre>
	 * 引数が null のとき、空白を返します。
	 * </pre>
	 *
	 * @param decimal BigDecimal
	 * @return String
	 */
	public static String escapeNull(BigDecimal decimal) {
		String str = "";
		if(decimal != null) str = decimal.toString();
		return str;
	}

	/**
	 * <pre>
	 * 引数が null のとき、空白を返します。
	 * </pre>
	 *
	 * @param date Date
	 * @return String
	 */
	public static String escapeNull(Date date) {
		String str = "";
		if(date != null) str = date.toString();
		return str;
	}

	/**
	 * <pre>
	 * 文字列の長さを制限して出力します。
	 * </pre>
	 *
	 * @param value HTML表示文字列
	 * @param size  文字列の長さ
	 * @return 半角スペースに置き換えた文字列
	 */
	public static String resizeString(String value, int size) {
		if (value == null || value.trim().equals("")) {
			return value;
		}
		if (size < value.length()) {
			return value.substring(0,size);
		}
		return value;
	}

	/**
	 * <pre>
	 * 文字列に含まれている改行を 半角スペース " " に置き換えます。
	 * </pre>
	 *
	 * @param value HTML表示文字列
	 * @return 半角スペースに置き換えた文字列
	 */
	public static String replaceCRLFtoSpace(String value) {
		if (value == null
			|| value.trim().equals("")) {
			return value;
		}
		StringBuffer sb = new StringBuffer();
		char[] c = value.toCharArray();
		for (int i = 0; i < c.length; i++) {
			if (c[i] == '\r'
				&& c[i + 1] == '\n') {
				sb.append(" ");
				i++;
			} else {
				sb.append(String.valueOf(c[i]));
			}
		}
		return sb.toString();
	}

	/**
	 * <pre>
	 * 文字列に含まれている改行を EXCEL上での改行に置き換えます。
	 * </pre>
	 *
	 * @param value HTML表示文字列
	 * @return EXCEL上での改行に置き換えた文字列
	 */
	public static String replaceCRLFtoCRLF(String value) {
		if (value == null
			|| value.trim().equals("")) {
			return value;
		}
		StringBuffer sb = new StringBuffer();
		char[] c = value.toCharArray();
		for (int i = 0; i < c.length; i++) {
			if (c[i] == '\r'
				&& c[i + 1] == '\n') {
				sb.append("\n");
				i++;
			} else {
				sb.append(String.valueOf(c[i]));
			}
		}
		return sb.toString();
	}

	/**
	 * <pre>
	 * 文字列に改行もしくはダブルコーテーションが含まれている場合、
	 * EXCEL上のセル内でスペース、もしくはダブルコーテションを表示できるように置き換えます。
	 *
	 * 文字列に改行もしくはダブルコーテーションが含まれている場合、
	 * EXCEL上のセル内でスペース、もしくはダブルコーテションを表示できるように置き換えます。
	 *
	 * 文字列に改行もしくはダブルコーテーション、カンマが含まれていない場合、
	 * 入力した値をそのまま表示できるように置き換えます。
	 * </pre>
	 *
	 * @param value String
	 * @return EXCEL上で表示させるために置き換えた文字列
	 */
	public static String replaceEXCELSpace(String value) {
		if (value == null || value.trim().equals("")) {
			return value;
		}
		StringBuffer sb = new StringBuffer();
		char[] c = value.toCharArray();
		for (int i = 0; i < c.length; i++) {
			if (c[i] == '\r' && c[i + 1] == '\n') {
				sb.append(" ");
				i++;
			} else if (c[i] == '"') {
				sb.append("\"\"");
			} else {
				sb.append(String.valueOf(c[i]));
			}
		}
		return sb.toString();
	}

	/**
	 * <pre>
	 * 文字列に改行もしくはダブルコーテーションが含まれている場合、
	 * EXCEL上のセル内でスペース、もしくはダブルコーテションを表示できるように置き換えます。
	 *
	 * 文字列に改行もしくはダブルコーテーションが含まれていない場合、
	 * 入力した値をそのまま表示できるように置き換えます。
	 * </pre>
	 *
	 * @param obj Object
	 * @return EXCEL上で表示させるために置き換えた文字列
	 */
	public static String replaceEXCELSpace(Object obj) {
		if (obj == null || (obj.toString()).trim().equals("")) {
			return "";
		}
		String value = obj.toString();

		StringBuffer sb = new StringBuffer();
		char[] c = value.toCharArray();
		for (int i = 0; i < c.length; i++) {
			if (c[i] == '\r' && c[i + 1] == '\n') {
				sb.append(" ");
				i++;
			} else if (c[i] == '"') {
				sb.append("\"\"");
			} else {
				sb.append(String.valueOf(c[i]));
			}
		}
		return sb.toString();
	}

	/**
	 * <pre>
	 * 文字列に改行もしくはダブルコーテーションが含まれている場合、
	 * EXCEL上のセル内で改行、もしくはダブルコーテションを表示できるように置き換えます。
	 *
	 * 文字列に改行もしくはダブルコーテーションが含まれていない場合、
	 * 入力した値をそのまま表示できるように置き換えます。
	 * </pre>
	 *
	 * @param value String
	 * @return EXCEL上で表示させるために置き換えた文字列
	 */
	public static String replaceEXCELCRLF(String value) {
		if (value == null || value.trim().equals("")) {
			return value;
		}
		StringBuffer sb = new StringBuffer();
		char[] c = value.toCharArray();
		for (int i = 0; i < c.length; i++) {
			if (c[i] == '\r' && c[i + 1] == '\n') {
				sb.append("\n");
				i++;
			} else if (c[i] == '"') {
				sb.append("\"\"");
			} else {
				sb.append(String.valueOf(c[i]));
			}
		}
		return sb.toString();
	}

	/**
	 * <pre>
	 * 文字列に改行もしくはダブルコーテーションが含まれている場合、
	 * EXCEL上のセル内で改行、もしくはダブルコーテションを表示できるように置き換えます。
	 *
	 * 文字列に改行もしくはダブルコーテーションが含まれていない場合、
	 * 入力した値をそのまま表示できるように置き換えます。
	 * </pre>
	 *
	 * @param obj Object
	 * @return EXCEL上で表示させるために置き換えた文字列
	 */
	public static String replaceEXCELCRLF(Object obj) {
		if (obj == null || (obj.toString()).trim().equals("")) {
			return "";
		}
		String value = obj.toString();

		StringBuffer sb = new StringBuffer();
		char[] c = value.toCharArray();
		for (int i = 0; i < c.length; i++) {
			if (c[i] == '\r' && c[i + 1] == '\n') {
				sb.append("\n");
				i++;
			} else if (c[i] == '"') {
				sb.append("\"\"");
			} else {
				sb.append(String.valueOf(c[i]));
			}
		}
		return sb.toString();
	}

	/**
	 * <pre>
	 * 文字列に含まれている「"」を 表示上問題の無い「"」に置き換えます。
	 * </pre>
	 *
	 * @param value HTML表示文字列
	 * @return 表示上問題の無い「"」の文字列
	 */
	public static String replaceDoubleCautation(String value) {
		if (value == null || value.trim().equals("")) {
			return value;
		}
		StringBuffer sb = new StringBuffer();
		char[] c = value.toCharArray();
		for (int i = 0; i < c.length; i++) {
			if (c[i] == '"') {
				sb.append("\"\"");
			} else {
				sb.append(String.valueOf(c[i]));
			}
		}
		return sb.toString();
	}

	/**
	 * <pre>
	 * CSV用のエスケープ処理
	 *
	 * １．NULLはブランクに置き換えます。
	 * ２．改行はEXCEL上の改行に置き換えます。
	 * ３．タブは半角スペースに置き換えます。
	 * ４．ダブルコーテションはEXCEL上のダブルコーテションに置き換えます。
	 * ５．カンマは「，」に置き換えます。
	 * </pre>
	 *
	 * @param str 文字列
	 * @return 出力文字列
	 */
	public static String escapeCsv(String str) {
		if (str == null) {
			str = "";
		} else {
			str = str.replaceAll("\r\n", "\\\n");
//			str = str.replaceAll("\n", " ");
			str = str.replaceAll("\t", " ");
			str = str.replaceAll("\"", "\"\"");
			str = str.replaceAll(",", "，");
		}
		return str;
	}

	/**
	 * <pre>
	 * ファイル名に使う日本語の文字コードをISO-8859-1に置き換えます。
	 * </pre>
	 *
	 * @param str 文字列
	 * @param decoding 現在の文字コード
	 * @return 半角スペースに置き換えた文字列
	 */
	public static String decode(String str, String decoding)
		throws UnsupportedEncodingException {
		if (str != null) {
			return new String(str.getBytes(decoding), ISO_8859_1);
		}
		return "";
	}
}