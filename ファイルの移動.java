public class LoadStaffInfoWriter implements ItemWriter<Object> {
    private String importDir;
    private String exportDir;

    LoadStaffInfoWriter(String importDir, String exportDir) {
        this.importDir = importDir;
        this.exportDir = exportDir;
    }
	
    @Override
    public void write(List<? extends Object> plist) throws Exception {
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
        Path sourcePath = Path.of(sourceDir, fileName);
        Path targetPath = Path.of(targetDir, fileName);

        Files.copy(sourcePath, targetPath, StandardCopyOption.REPLACE_EXISTING);
        Files.delete(sourcePath);
    }
}

public class MyClass {
    public void someMethod() {
        String importDir = "D:\\text2023_03_22\\DDL\\test";
        String exportDir = "C:\\test";

        try {
            LoadStaffInfoWriter writer = new LoadStaffInfoWriter(importDir, exportDir);
            List<Object> plist = new ArrayList<>();
            writer.write(plist);
        } catch (IOException e) {
            System.out.println("エラーファイルの作成に失敗しました");
            e.printStackTrace();
        }
    }
}
