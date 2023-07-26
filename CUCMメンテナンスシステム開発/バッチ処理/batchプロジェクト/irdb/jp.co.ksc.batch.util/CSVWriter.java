package jp.co.ksc.batch.util;

/**
 * <p>Title: CSVWriter.java</p>
  * <pre>
  * 'voicemail'固定値書き込みクラス
  * </pre>
* @Time:2023/07/14
* @author Yao KaiKyou
 */
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

public class CSVWriter {
    /**
     * CSVファイルにデータを書き込むメソッド
     * @param fileName ファイル名
     * @param header ヘッダー
     * @param data データ
     * @throws IOException
     */
    public static void writeCSV(String fileName, String header, Map<String,Object>[] data) throws IOException {
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
    private static String sanitizeValue(String value) {
        // "?"をエスケープ処理する
        if (value.contains("?")) {
            value = value.replace("?", "\\?");
        }
        return value;
    }
}
