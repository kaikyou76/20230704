package jp.co.ksc.batch.util;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

import org.springframework.batch.item.ItemWriter;

/**
 * <p>Title: LoadStaffInfoWriter.java</p>
  * <pre>
  * 連携ファイルの移動用クラス
  * </pre>
* @Time:2023/07/04
* @author Yao KaiKyou
 */
public class LoadStaffInfoWriter implements ItemWriter<Object> {
    private String importDir;
    private String exportDir;

    public LoadStaffInfoWriter(String importDir, String exportDir) {
        this.importDir = importDir;
        this.exportDir = exportDir;
    }

    public void write(List<?> plist) throws Exception {
        List<String> fileList = listFilesInDirectory(importDir);
        for (String fileName : fileList) {
            copyAndDeleteFile(importDir, exportDir, fileName);
        }
    }

    private List<String> listFilesInDirectory(String directory) {
        List<String> fileList = new ArrayList<>();
        File folder = new File(directory);
        if (folder.exists() && folder.isDirectory()) {
            File[] files = folder.listFiles();
            if (files != null) {
                for (File file : files) {
                    if (file.isFile()) {
                        fileList.add(file.getName());
                    }
                }
            }
        }
        return fileList;
    }

    private void copyAndDeleteFile(String sourceDir, String targetDir, String fileName) throws IOException {
        Path sourcePath = new File(sourceDir, fileName).toPath();
        Path targetPath = new File(targetDir, fileName).toPath();

        Files.copy(sourcePath, targetPath, StandardCopyOption.REPLACE_EXISTING);
        Files.delete(sourcePath);
    }
}
