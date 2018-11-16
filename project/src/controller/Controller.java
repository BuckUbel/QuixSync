package controller;

import logger.Logger;
import models.StorageElementList;
import views.mainView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Controller implements ActionListener {

    public static final String START = "START";

    public static final String WHOLE_SYNC = "WHOLE_SYNC";
    public static final String INDEXING = "INDEXING";
    public static final String COMPARE = "COMPARE";
    public static final String SYNC = "SYNC";
    public static final String ADD_FTP_CONNECTION = "ADD_FTP_CONNECTION ";
    public static final String OPEN_FILE_CHOOSER = "OPEN_FILE_CHOOSER ";

    public static final String STOP = "STOP";

    //private View_Old window;
    private mainView window;
    private BackgroundTasks bt;
    private Thread lastThread;

    //public Controller(View_Old window, BackgroundTasks bt) {
    public Controller(mainView window, BackgroundTasks bt) {
        this.window = window;
        this.bt = bt;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        String command = e.getActionCommand();

        String indexPath = "";
        String compareFilePath = "";
        boolean successfully = false;

        switch (command) {
            case Controller.START:

                Thread t = new Thread(this.bt);
                lastThread = t;
                t.start();

                break;


            // to cancel a algorithm
            case Controller.STOP:

                // lastThread.stop();
                // TODO: problem interrupt don't kill the Thread
                // @QuentinWeber assigned

                lastThread.interrupt();

                break;

            case Controller.WHOLE_SYNC:

                System.out.println("WHOLE SYNC");

                // TODO: get Directories
                // @QuentinWeber assigned
                // @PhilippLudwig assigned --> Möglichkeit zum Auslesen der Werte aus der mainView

                String source = "";
                String target = "";

                String[] indexPaths = this.doubleIndexing(source, target);
                compareFilePath = this.compare(indexPaths[0], indexPaths[1]);
                successfully = this.sync(compareFilePath);

                break;
            case Controller.INDEXING:

                Logger.print("INDEXING");

                // TODO: get Directories
                // @QuentinWeber assigned

                String dir = "";
                indexPath = this.indexing(dir);

                // TODO: set indexPath in mainView, for global storing

                break;
            case Controller.COMPARE:

                Logger.print("COMPARE");

                // TODO: get index-filePaths
                // @QuentinWeber assigned

                String sourceIndex = "";
                String targetIndex = "";
                compareFilePath = this.compare(sourceIndex, targetIndex);

                // TODO: set compareFilePath in mainView, for global storing

                break;
            case Controller.SYNC:

                Logger.print("SYNC");

                // TODO: get index-filePaths
                // @QuentinWeber assigned

                compareFilePath = "";
                successfully = this.sync(compareFilePath);

                // TODO: set successfully in mainView, for global storing

                break;
            case Controller.ADD_FTP_CONNECTION:

                // TODO: add FTP-Connection credentials in extra store and save it in json

                break;
            default:
                break;
        }

    }

    private String[] doubleIndexing(String dir1, String dir2) {

        String[] returnArray = new String[2];

        returnArray[0] = this.indexing(dir1);
        returnArray[1] = this.indexing(dir2);

        return returnArray;
    }

    private String indexing(String dir) {

        String indexPath = "";

        Logger.print("---------- INDEXING ------------");
//        String pathA = "testdata\\A";
        String pathA = "D:\\Quentin\\Schule\\BA Leipzig";
//        Logger.print(path);

        StorageElementList elementsA = FileController.getAllElements(pathA);
        // Logging
        // FileController.printElements(elements);
        FileController.writeElementsInFile(elementsA, "testData\\outputA.json");

//        String pathB = "testdata\\B";
        String pathB = "D:\\Quentin\\Schule\\BA Leipzig - Kopie";
        StorageElementList elementsB = FileController.getAllElements(pathB);
        FileController.writeElementsInFile(elementsB, "testData\\outputB.json");

        return indexPath;
    }

    private String compare(String file1, String file2) {

        String compareFilePath = "";

        Logger.print("---------- COMPARING ------------");
        String sourceIndexPath = "testdata\\outputA.json";
        String targetIndexPath = "testdata\\outputB.json";
        boolean isHardSync = true;
        boolean slowMode = true;

//        withRenaming mode:
//            not all Renamings in A, if no size/date changes
//            not all Renamings in B, if no size/date changes
//
//          new data in B will be deleted
//          --> but the parent folder from A will be copied??? --> is unneccessary --> folders with problems with children count shouldn't copy

        compareFilePath = FileController.compareJSONFiles(sourceIndexPath, targetIndexPath, isHardSync, slowMode);

        return compareFilePath;
    }

    private boolean sync(String compareFile) {
        try {
            String compareFilePath = "testData\\outputC.json";
            return FileController.sync(compareFilePath);

        } catch (Exception e) {
            Logger.printErr(e);
        }

        return false;
    }
}
