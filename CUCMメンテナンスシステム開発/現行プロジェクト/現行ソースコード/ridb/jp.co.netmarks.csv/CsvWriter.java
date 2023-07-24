/*
 * Copyright (c) 2010, KIRIN BEVERAGE COMPANY, LIMITED All Rights Reserved.
 *
 * CsvWriter.java
 *
 * @date 2013/09/17
 * @version 1.0
 * @author KSC Yuichiro Yoshida
 */
package jp.co.netmarks.csv;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletResponse;

import jp.co.netmarks.util.EscapeUtil;

/**
 * <pre>
 * CSVファイル出力用クラスです。
 *
 * &lt;MODIFICATION HISTORY&gt;
 * 1.0 2009/09/16 KSC Yuichiro Yoshida 新規作成
 * </pre>
 *
 * @author KSC Yuichiro Yoshida
 * @version 1.0 2013/09/17
 */
public class CsvWriter {

	/**
	 * PrintWriter
	 */
	private PrintWriter _out = null;

	/**
	 * ファイル名称
	 */
	private String _fileName = null;

	/**
	 * ヘッダー文字列
	 */
	private String[] _header = null;

	/**
	 * ファイル・ディレクトリ
	 */
	private String _filePath = "/";


	/**
	 * エンコードタイプ
	 */
	private String _encode = "Shift-JIS";

	/**
	 * <pre>
	 * コンストラクタ
	 * </pre>
	 *
	 * @param filePath ファイルパス
	 * @param fileName ファイル名
	 * @param header ヘッダー配列
	 */
	public CsvWriter(String filePath, String fileName, String[] header) {
		this._filePath = removeLastSeparator(filePath);
		this._fileName = fileName;
		this._header = header;
	}

	/**
	 * <pre>
	 * コンストラクタ
	 * </pre>
	 *
	 * @param fileName ファイル名
	 * @param header ヘッダー配列
	 */
	public CsvWriter(String fileName, String[] header) {
		this._fileName = fileName;
		this._header = header;
	}

	/**
	 * CSVファイル作成処理を行います。
	 * ※ファイル出力用です。
	 *
	 * １．ファイル名の設定
	 * ２．エンコードの設定
	 * ３．コンテンツタイプの設定
	 * ４．HTTPヘッダの設定
	 * ５．ストリームへのデータ書込み
	 *
	 * @param data 検索結果のリスト
	 * @param outputDt 出力日付
	 * @param res HTTP サーブレット用の出力情報
	 * @throws Exception 処理例外
	 */
	public void flashCsv(List<LinkedHashMap<String, String>> data, String outputDt, HttpServletResponse res) throws Exception {

		_fileName = outputDt + _fileName + ".csv";
		res.setCharacterEncoding(_encode);
		_fileName = EscapeUtil.decode(_fileName, _encode);
		res.setContentType("application/csv");
		res.setHeader("Content-Disposition","attachment;filename=" + _fileName);

		_out = res.getWriter();

		// ストリームへ書き込み
		this.writeLine(data);
	}

	/**
	 * CSVファイル作成処理を行います。
	 * ※特定のディレクトリへの作成用です。
	 *
	 * １．ファイル名の設定
	 * ２．エンコードの設定
	 * ３．ストリームへのデータ書込み
	 * ４．ファイルの作成
	 *
	 * @param data 検索結果のリスト
	 * @param outputDt 出力日付
	 * @throws Exception 例外処理
	 */
	public void createCSV(List<LinkedHashMap<String, String>> data, String outputDt, String loginId) throws Exception{

		_fileName = loginId + "_" + _fileName + "_" + outputDt + ".csv";
		_fileName = EscapeUtil.decode(_fileName, _encode);

		OutputStreamWriter ow = null;

		try {
			ow = new OutputStreamWriter(new FileOutputStream(
					_filePath + System.getProperty("file.separator") + this._fileName),_encode);
			_out = new PrintWriter(new BufferedWriter(ow));
			// ストリームへ書き込み
			this.writeLine(data);
		} catch (Exception e) {
			throw e;
		} finally {
			if(ow != null)ow.close();
		}
	}

	/**
	 * データ書込み処理
	 *
	 * @param data 検索結果のリスト
	 * @throws Exception 処理例外
	 */
	private void writeLine(List<LinkedHashMap<String, String>> data) throws Exception {
		try {
			StringBuffer sb = new StringBuffer();

			for (int i = 0; i < _header.length; i++) {

				// ヘッダー2行出力対策
				if (_header[i].equals("#nextLine#")) {
					// ヘッダー出力
					_out.println(sb.toString());
					// StringBuffer 初期化
					sb = new StringBuffer();
				} else {
					sb.append("" + EscapeUtil.escapeCsv(_header[i]));
					if (i < _header.length - 1) {
						sb.append(",");
					}
				}
			}

			// ヘッダー出力
			_out.println(sb.toString());

			for (Map<String, String> map : data) {

				// StringBuffer 初期化
				sb = new StringBuffer();

				for (Iterator<Entry<String, String>> iterator = map.entrySet().iterator(); iterator.hasNext();) {

					Entry<String, String> entry = iterator.next();
					String outputStr = EscapeUtil.escapeCsv(entry.getValue());

					sb.append(outputStr);

					if (iterator.hasNext()) {
						sb.append(",");
					} else {
						sb.append("");
					}
				}
				// 一行ずつ出力
				_out.println(sb.toString());
			}
		} catch (Exception e) {
			throw e;
		} finally {
			_out.close();
		}
	}

	/**
     * <pre>
     * パスの最後にセパレータがある場合のみそれを除去して返却します。
     * それ以外はそのまま返却します。
     * </pre>
     * @param path c:/hoge/
     * @return c:/hoge
     */
    private String removeLastSeparator(String path) {
    	String separator = System.getProperty("file.separator");
    	if (!path.endsWith(separator)) {
    		return path;
    	}
    	return path.substring(0, path.length() - 1);
    }
}