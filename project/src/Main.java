import controller.FileController;
import fileWriter.JSONCreator;
import models.FileSize;
import models.StorageElement;
import models.StorageElementList;

import java.io.FileWriter;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) throws Exception {

        System.out.println("----------------------");
        String path = "D:\\Quentin\\Schule\\BA Leipzig\\5.Semester\\SP\\QuixSync\\project\\testdata\\B";
        System.out.println(path);
        StorageElement s = new StorageElement(path);

        StorageElementList elements = FileController.getAllElements(s.getPath());




        printElements(FileController.getAllElements(s.getPath()));
        writeElementsInFile(FileController.getAllElements(s.getPath()));
    }

    private static void printElements(StorageElementList se) {
        FileSize fs = new FileSize(0);
        List<Double> list = new ArrayList<Double>();
        for (int i = 0; i < se.elementList.size(); i++) {
//            System.out.println("[" + i + "]: " + se.get(i).getPath() + " [" + se.get(i).getFileSize() + "] ");
            list.add(fs.getBytes());
            fs = new FileSize((fs.getBytes() + se.elementList.get(i).getFileSizeObject().getBytes()));
        }
        System.out.println("----------------------");
        System.out.println(se.elementList.size() + " Dateien");
        System.out.println(fs.getFormattedString() + " Speichermenge");

        String path = "D:\\Quentin\\Schule\\BA Leipzig\\5.Semester\\SP";

        JSONCreator jsc = new JSONCreator(path);

        jsc.addObject("files", se);
        jsc.addObject("count", se.elementList.size());
        jsc.addObject("fileSize", fs.getFormattedString());
    }
    private static void writeElementsInFile(StorageElementList se) throws Exception{
        FileWriter writer = new FileWriter("output2.txt");

        FileSize fs = new FileSize(0);
        List<Double> list = new ArrayList<Double>();
        String str = "";
        for (int i = 0; i < se.elementList.size(); i++) {
            str = "[" + i + "]: " + se.elementList.get(i).getPath() + " [" + se.elementList.get(i).getFileSize() + "] \n";
            writer.write(str);
            list.add(fs.getBytes());
            fs = new FileSize((fs.getBytes() + se.elementList.get(i).getFileSizeObject().getBytes()));
        }

        writer.close();
    }

}
