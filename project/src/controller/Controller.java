package controller;

import controller.Tasks.BackgroundTask;
import controller.Tasks.Compare.CompareTask;
import controller.Tasks.Index.IndexTask;
import controller.Tasks.Sync.SyncTask;
import logger.Logger;
import models.IndexFile;
import views.View;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Controller implements ActionListener {

    private Thread thread;

    public static final String WHOLE_SYNC = "WHOLE_SYNC";

    public static final String INDEXING = "INDEXING";

    public static final String GET_INDEXING_FILES = "GET_INDEXING_FILES";

    public static final String COMPARE = "COMPARE";

    public static final String GET_COMPARE_FILES = "GET_COMPARE_FILES";

    public static final String SYNC = "SYNC";

    public static final String ADD_FTP_CONNECTION = "ADD_FTP_CONNECTION";


    private View window;
    private BackgroundTask bt;

    public Controller(View window) {
        this.window = window;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        String command = e.getActionCommand();

        String indexPath;
        String compareFilePath;
        boolean successfully = false;
        IndexFile[] sel;


        switch (command) {
            case Controller.WHOLE_SYNC:

                System.out.println("WHOLE SYNC");

                // TODO: get Directories
                // @QuentinWeber assigned
                // @PhilippLudwig assigned --> Möglichkeit zum Auslesen der Werte aus der View

                String source = "testData\\A";
                String target = "testData\\B";

                String indexPath1 = this.indexing(source);
                String indexPath2 = this.indexing(target);
                compareFilePath = this.compare(indexPath1, indexPath2);
//                this.sync(compareFilePath);

                break;
            case Controller.INDEXING:

                Logger.print("INDEXING");

                // TODO: get Directories
                // @QuentinWeber assigned


                String dir = "D:\\Quentin\\Schule\\BA Leipzig";
                String indexFilePath = this.indexing(dir);

//                String dir = "D:\\Quentin\\Schule\\BA Leipzig2";
//                String indexFilePath = this.indexing(dir);
//                System.out.println("Success! Index: " + indexFilePath);
//
//                dir = "D:\\Quentin\\Schule\\BA Leipzig3";
//                indexFilePath = this.indexing(dir);

                if (indexFilePath == null) {
                    System.out.println("Try later.");
                    // TODO: the user should try this action a little time later
                } else {
                    System.out.println("Success! Index: " + indexFilePath);
                    // TODO: set indexFilePath in View, for so it can be resumed immediately.
                }

                break;
            case Controller.GET_INDEXING_FILES:

                Logger.print("GET_INDEXING_FILES");
                sel = FileController.getFilesWithSpecificString(SettingsController.getTempDir(), SettingsController.getIndexFileEnding());

                // TODO: set in View to display the files to select two of them to compare

                break;
            case Controller.COMPARE:

                Logger.print("COMPARE");

                // TODO: get index-filePaths
                // @QuentinWeber assigned

                String sourceIndex = "testData\\1541948875871.index.quix";
                String targetIndex = "testData\\1541948875875.index.quix";
                compareFilePath = this.compare(sourceIndex, targetIndex);

                if (compareFilePath == null) {
                    System.out.println("Try later.");
                    // TODO: the user should try this action a little time later
                } else {
                    System.out.println("Success! Compare");
                    // TODO: set compareFilePath in View, for so it can be resumed immediately.
                }
                break;

            case Controller.GET_COMPARE_FILES:

                Logger.print("GET_COMPARE_FILES");
                sel = FileController.getFilesWithSpecificString(SettingsController.getTempDir(), SettingsController.getCompareFileEnding());
                // TODO: set in View to display the files to select one of them to sync
                break;
            case Controller.SYNC:

                Logger.print("SYNC");

                // TODO: get index-filePaths
                // @QuentinWeber assigned

                compareFilePath = "testData\\1541949027678.comp.quix";

                successfully = this.sync(compareFilePath);

                if (successfully) {
                    System.out.println("Success! Sync");
                    // TODO: set successfully in View, for global storing
                } else {
                    System.out.println("Try later.");
                }

                break;
            case Controller.ADD_FTP_CONNECTION:

                // TODO: add FTP-Connection credentials in extra store and save it in json

                break;
            default:
                break;
        }

    }

    private String indexing(String dir) {

        String tempFile = SettingsController.getTempDir() + System.currentTimeMillis() + SettingsController.getIndexFileEnding() + SettingsController.getFileEnding();
        IndexTask r = new IndexTask(dir, tempFile);
        thread = ThreadController.createNewThread(r);
        if (thread!= null) {
            r.connectThread(thread);
            thread.start();
            return tempFile;
        }
        return null;
    }

    private String compare(String file1, String file2) {

        String tempFile = SettingsController.getTempDir() + System.currentTimeMillis() + SettingsController.getCompareFileEnding() + SettingsController.getFileEnding();
        CompareTask r = new CompareTask(file1, file2, tempFile, true, true);
        thread = ThreadController.createNewThread(r);
        if (thread != null) {
            r.connectThread(thread);
            thread.start();
            return tempFile;
        }
        return null;
    }

    private boolean sync(String compareFilePath) {

        SyncTask r = new SyncTask(compareFilePath);
        thread = ThreadController.createNewThread(r);
        if (thread != null) {
            r.connectThread(thread);
            thread.start();
            return true;
        }
        return false;
    }
}
