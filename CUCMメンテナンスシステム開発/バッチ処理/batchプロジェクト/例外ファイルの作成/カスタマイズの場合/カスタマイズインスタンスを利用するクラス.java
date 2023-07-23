public class MyClass {
    public void someMethod(BatchSettings props) {
        String errorFilePath = props.getOutPutErrPath();
        String errorMessage = props.getErrMessage();
		String outputErrFile = props.getOutputErrFile();
        ErrorFileManager errorFileManager = new ErrorFileManager(errorFilePath, errorMessage, outputErrFile);

        try {
            errorFileManager.checkErrorFileExists();
            // エラーファイルが存在する場合の処理
        } catch (BatRuntimeException e) {
            // エラーファイルが存在しない場合の処理
            e.printStackTrace();
        }
    }
}
