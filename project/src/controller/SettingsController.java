package controller;

public class SettingsController {


    private static String tempDir = "testdata//";
    private static String indexFileEnding = ".index";
    private static String compareFileEnding = ".comp";
    private static String fileEnding = ".quix";

    public static String getCompareFileEnding(){
        return compareFileEnding;
    }
    public static String getIndexFileEnding(){
        return indexFileEnding;
    }
    public static String getFileEnding(){
        return fileEnding;
    }
    public static String getTempDir(){
        return tempDir;
    }
}
