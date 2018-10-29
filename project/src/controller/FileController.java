package controller;

import logger.Logger;
import models.FileSize;
import models.StorageElement;
import models.StorageElementList;

import java.io.File;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.attribute.BasicFileAttributes;
import static java.nio.file.StandardCopyOption.*;
import java.util.ArrayList;
import java.util.List;

class FileController {

    static StorageElementList getAllElements(String path) {

        List<StorageElement> returnArray = new ArrayList<>();

        StorageElement element = new StorageElement(path);

        BasicFileAttributes attr = element.getAttributes();

        if (attr.isDirectory()) {
            File f = new File(element.getPath());

            File[] containedElements = f.listFiles();
            try {

                int count = 0;
                if (containedElements != null) {
                    count = containedElements.length;
                }
                if (count > 0) {
                    StorageElement se;
                    for (int i = 0; i < count; i++) {
                        se = new StorageElement(containedElements[i].getAbsolutePath());
                        if (se.isRegularFile()) {
                            returnArray.add(se);
                        }
                        if (se.isDirectory()) {
                            returnArray.addAll(getAllElements(se.getPath()).getElements());
                        }
                    }

                }
            } catch (Exception e) {
                Logger.printErr("keine Elemente vorhanden: " + e);
            }
        }

        return new StorageElementList(returnArray);
    }

    static void printElements(StorageElementList se) {
        FileController.printElements(se, false);
    }

    static void printElements(StorageElementList se, boolean printingAll) {
        FileSize fs = new FileSize(0);
        List<Double> list = new ArrayList<Double>();
        StorageElement tmpSE;

        for (int i = 0; i < se.getElements().size(); i++) {
            tmpSE = se.getElements().get(i);
            if (printingAll) {
                Logger.print("[" + i + "]: " + tmpSE.getPath() + " [" + tmpSE.getFileSize() + "] ");
            }
            list.add(fs.getBytes());
            fs = new FileSize((fs.getBytes() + tmpSE.getFileSizeObject().getBytes()));
        }
        Logger.print("----------------------");
        Logger.print(se.getElements().size() + " Dateien");
        Logger.print(fs.getFormattedString() + " Speichermenge");


    }

    static void writeElementsInFile(StorageElementList se) {
        FileController.writeElementsInFile(se, "testData\\output2.txt");
    }

    static void writeElementsInFile(StorageElementList se, String path) {

        // TODO: use JSON instead
        // @ChrisSembritzki assigned

        try {

            FileWriter writer = new FileWriter(path);

            FileSize fs = new FileSize(0);
            List<Double> list = new ArrayList<Double>();
            String str = "";
            for (int i = 0; i < se.getElements().size(); i++) {
                str = "[" + i + "]: " + se.getElements().get(i).getPath() + " [" + se.getElements().get(i).getFileSize() + "] \n";
                writer.write(str);
                list.add(fs.getBytes());
                fs = new FileSize((fs.getBytes() + se.getElements().get(i).getFileSizeObject().getBytes()));
            }

            writer.close();

        } catch (Exception e) {
            Logger.printErr(e.getMessage());
        }
    }


    static String compareJSONFiles(String sourcePath, String targetPath) {

        String compareFilePath = "";

        //TODO: implement algorithm to compare JSONS
        //@QuentinWeber assigned


        return compareFilePath;
    }

    static boolean sync(String compareFilePath) {

        //TODO: implement algorithm to sync dirs
        //@QuentinWeber assigned

        try {

            // TODO: what makes COPY_ATTRIBUTES ? as attribute?
//            Files.copy(new File("testData\\output.txt").toPath(), new File("testData\\output3.txt").toPath(),REPLACE_EXISTING, COPY_ATTRIBUTES);
            Files.copy(new File("testData\\output.txt").toPath(), new File("testData\\output3.txt").toPath(),REPLACE_EXISTING);


            return true;

        } catch (Exception e) {

            Logger.printErr(e);
            return false;
        }
    }

}
