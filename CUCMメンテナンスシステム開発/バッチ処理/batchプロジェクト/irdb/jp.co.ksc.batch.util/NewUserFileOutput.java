package jp.co.ksc.batch.util;

import java.io.File;
import java.io.FileWriter;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.netmarks.batch.dao.StaffInfoDAO;

/**
 * <p>Title: NewUserFileOutput.java</p>
  * <pre>
  * 加入者リスト出力クラス
  * </pre>
* @Time:2023/07/12
* @author Yao KaiKyou
 */

public class NewUserFileOutput {
    private Properties properties;

    /* ログ出力クラス */
    private Log log = LogFactory.getLog(this.getClass());

    public NewUserFileOutput(Properties properties) {
        this.properties = properties;
    }

    public void newUserFileOut(StaffInfoDAO dao, List<String> employeeIds) throws Exception {
        BatchSettings bs = new BatchSettings(properties);
        File backupDir = new File(bs.getOutPutNewUsersDir());
        if (!backupDir.exists()) {
            backupDir.mkdirs();
        }

        try {
            // 出力先のファイルパス
            String filePath = bs.getOutPutNewUsersDir() + bs.getJoinedUserFileName();

            // 新入社員の情報を取得し、CSVファイルに書き込む
            try (FileWriter fileWriter = new FileWriter(filePath)) {
                for (String employeeId : employeeIds) {
                    Map<String, Object> record = dao.selAllNewUserList(employeeId);
                    String line = record.get("last_nm") + ","
                            + record.get("first_nm") + ","
                            + record.get("telephone_no") + ","
                            + record.get("full_name") + "\n";
                    fileWriter.write(line);
                }
            }
        } catch (Exception e) {
            log.error("CSVファイルの書き込みエラー: " + e.getMessage());
            throw new Exception("CSVファイルの書き込み中にエラーが発生しました", e);
        }
    }
}
