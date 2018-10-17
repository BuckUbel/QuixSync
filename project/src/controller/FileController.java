package controller;

import models.StorageElement;
import models.StorageElementList;

import java.io.File;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;

public class FileController {

    public static StorageElementList getAllElements(String path) {

        List<StorageElement> returnArray = new ArrayList<StorageElement>();

        StorageElement element = new StorageElement(path);

        BasicFileAttributes attr = element.getAttributes();

        if (attr.isDirectory()) {
            File f = new File(element.getPath());

            File[] containedElements = f.listFiles();
            try {

                int count = containedElements.length;
                if (count > 0) {
                    StorageElement se;
                    for (int i = 0; i < count; i++) {
                        se = new StorageElement(containedElements[i].getAbsolutePath());
                        if (se.isRegularFile()) {
                            returnArray.add(se);
                        }
                        if (se.isDirectory()) {
                            returnArray.addAll(getAllElements(se.getPath()).elementList);
                        }
                    }

                }
            }
            catch (Exception e){
                System.err.println("keine Elemente vorhanden: " + e);
            }
        };

        return new StorageElementList(returnArray);
    }



}
