package jp.co.ksc.batch.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import jp.co.ksc.batch.exception.BatRuntimeException;

/**
 * <p>Title: ErrorFileManager.java</p>
  * <pre>
  * エラーファイル作成クラス
  * </pre>
* @Time:2023/07/03
* @author Yao KaiKyou
 */
public class ErrorFileManager {
    private String errorFilePath;
    private String errorMessage;
    private String outputErrFile;

    public ErrorFileManager(String errorFilePath, String errorMessage, String outputErrFile) {
        this.errorFilePath = errorFilePath;
        this.errorMessage = errorMessage;
        this.outputErrFile = outputErrFile;
    }

    public void checkErrorFileExists() {
        File errorFile = new File(errorFilePath);
        if (!errorFile.exists()) {
            createErrorFile(errorMessage);
            throw new BatRuntimeException(errorMessage);
        }
    }

    public void createErrorFile(String errorMessage) {
        FileWriter fileWriter = null;
        BufferedWriter bufferedWriter = null;

        try {
            fileWriter = new FileWriter(errorFilePath + outputErrFile, true);
            bufferedWriter = new BufferedWriter(fileWriter);

            bufferedWriter.write(errorMessage);
            bufferedWriter.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (bufferedWriter != null) {
                try {
                    bufferedWriter.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fileWriter != null) {
                try {
                    fileWriter.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
