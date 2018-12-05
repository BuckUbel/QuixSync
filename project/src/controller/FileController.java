package controller;

import models.*;
import java.io.File;
import java.util.Arrays;

abstract class FileController {

    static TypeFile[] getFilesWithSpecificString(String path, String matchString) {

        boolean forCompareFiles = matchString.equals(SettingsController.getCompareFileEnding());
        CompareFile[] cF = new CompareFile[0];
        IndexFile[] iF =  new IndexFile[0];

        File[] indexFiles;

        File[] files = new File(path).listFiles();

        if (files != null) {

            indexFiles = new File[files.length];

            for (int i = 0; i < files.length; i++) {
                if (files[i].getName().contains(matchString)) {
                    indexFiles[i] = files[i];
                }
            }
            indexFiles = Arrays.stream(indexFiles)
                    .filter(s -> (s != null && s.length() > 0))
                    .toArray(File[]::new);

            if (forCompareFiles) {
                cF = new CompareFile[indexFiles.length];

                for (int i = 0; i < indexFiles.length; i++) {
                    cF[i] = new CompareFile(indexFiles[i]);
                }
            } else {

                iF = new IndexFile[indexFiles.length];

                for (int i = 0; i < indexFiles.length; i++) {
                    iF[i] = new IndexFile(indexFiles[i]);
                }
            }
        }
        if (forCompareFiles) {
            return cF;
        }
            return iF;
    }
}
