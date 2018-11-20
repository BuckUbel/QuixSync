package controller;

import controller.Tasks.BackgroundTask;
import controller.Tasks.Compare.CompareTask;
import controller.Tasks.Index.IndexTask;
import controller.Tasks.Sync.SyncTask;
import logger.Logger;
import views.mainView;
import models.IndexFile;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class Controller implements ActionListener {

    private Thread thread;

    public static final String WHOLE_SYNC = "WHOLE_SYNC";
    public static final String INDEXING = "INDEXING";
    public static final String COMPARE = "COMPARE";
    public static final String SYNC = "SYNC";
    public static final String ADD_FTP_CONNECTION = "ADD_FTP_CONNECTION";


    private mainView window;
    private BackgroundTask bt;
    private Thread lastThread;
    public ChangeController cc;

    public Controller(mainView window, BackgroundTask bt) {
        this.window = window;
        this.bt = bt;

        cc = new ChangeController(window,bt);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        String command = e.getActionCommand();

        String indexPath;
        String compareFilePath;
        boolean successfully = false;


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

                String dir = window.tfQuellverzeichnisI.getText();
                File f = new File(dir);
                if (f.isDirectory()) {

                    String indexFilePath = this.indexing(dir);

                    if (indexFilePath == null) {
                        System.out.println("Try later.");
                        // TODO: the user should try this action a little time later
                    } else {
                        System.out.println("Success! Index: " + indexFilePath);
                        // TODO: set indexFilePath in View, for so it can be resumed immediately.
                    }
                }
                else{
                    // TODO: ask the user after another path, because, the specified string was no path to a folder
                }

                break;
            case Controller.COMPARE:

                Logger.print("COMPARE");

                // TODO: get index-filePaths
                // @QuentinWeber assigned

                String sourceIndex = window.tfQuellIndexdatei.getText();
                String targetIndex = window.tfZielIndexdatei.getText();

                compareFilePath = this.compare(sourceIndex, targetIndex);

                if (compareFilePath == null) {
                    System.out.println("Try later.");
                    // TODO: the user should try this action a little time later
                } else {
                    System.out.println("Success! Compare");
                    // TODO: set compareFilePath in View, for so it can be resumed immediately.
                }
                break;

            case Controller.SYNC:

                Logger.print("SYNC");

                // TODO: get index-filePaths
                // @QuentinWeber assigned

                compareFilePath = window.tfVergleichsdatei.getText();
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
        if (thread != null) {
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
