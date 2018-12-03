package controller;

import controller.Tasks.BackgroundTask;
import controller.Tasks.CompareTaskProps;
import controller.Tasks.IndexTaskProps;
import controller.Tasks.SyncTaskProps;
import logger.Logger;
import views.compareFileView;
import views.mainView;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class Controller implements ActionListener {

    private Thread thread;

    public static final String WHOLE_SYNC = "WHOLE_SYNC";
    public static final String INDEXING = "INDEXING";
    public static final String COMPARE = "COMPARE";
    public static final String SYNC = "SYNC";
    public static final String STOP = "STOP";
    public static final String DISPLAY_COMPARE_FILE = "DISPLAY_COMPARE_FILE";
    public static final String OPEN_README = "OPEN_README";
    public static final String NEXT_ACTION = "NEXT_ACTION";
    public static final String ADD_FTP_CONNECTION = "ADD_FTP_CONNECTION";

    private mainView window;
    private BackgroundTask bt;
    private Thread lastThread;
    public ChangeController cc;

    public Controller(mainView window, BackgroundTask bt) {
        this.window = window;
        this.bt = bt;

        cc = new ChangeController(window,this, bt);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        String command = e.getActionCommand();
        String indexPath;
        String compareFilePath;
        boolean successfully;

        switch (command) {
            case Controller.WHOLE_SYNC:

                String source = window.tfQuellverzeichnisG.getText();
                String target = window.tfZielverzeichnisG.getText();

                Logger.print("WHOLE SYNC:  SRC[\"" + source + "\"]  TRG[\"" + target + "\" ]");

                Logger.print("Indexing1: " + this.bt.isFree());
                while (!this.bt.isFree()) ;
                String indexPath1 = this.indexing(source);

                Logger.print("Indexing2: " + this.bt.isFree());
                while (!this.bt.isFree()) ;
                String indexPath2 = this.indexing(target);

                Logger.print("Compare: " + this.bt.isFree());
                while (!this.bt.isFree()) ;
                compareFilePath = this.compare(indexPath1, indexPath2);

                Logger.print("Sync1: " + this.bt.isFree());
                while (!this.bt.isFree()) ;
                this.sync(compareFilePath);

                break;
            case Controller.INDEXING:

                String dir = window.tfQuellverzeichnisI.getText();

                Logger.print("INDEXING: " + dir);

                File f = new File(dir);
                if (f.isDirectory()) {

                    String indexFilePath = this.indexing(dir);

                    if (indexFilePath != null) {
                        // set path to compare this
                    } else {
                        // Feature: add to Process chain
                        // now: say user, that another process is running
                    }

                } else {
                    // TODO: ask the user after another path, because, the specified string was no path to a folder
                }


                break;
            case Controller.COMPARE:


                String sourceIndex = window.tfQuellIndexdatei.getText();
                String targetIndex = window.tfZielIndexdatei.getText();

                Logger.print("COMPARE:  SRC[\"" + sourceIndex + "\"]  TRG[\"" + targetIndex + "\" ]");

                compareFilePath = this.compare(sourceIndex, targetIndex);

                if (compareFilePath != null) {
                    // set path to sync this
                } else {
                    // Feature: add to Process chain
                    // now: say user, that another process is running
                }
                break;
            case Controller.SYNC:

                compareFilePath = window.tfVergleichsdatei.getText();
                Logger.print("SYNC: " + compareFilePath);

                successfully = this.sync(compareFilePath);

                if (successfully) {
                    // is Running
                } else {
                    // Feature: add to Process chain
                    // now: say user, that another process is running
                }
                break;
            case Controller.ADD_FTP_CONNECTION:

                // TODO: add FTP-Connection credentials in extra store and save it in json

                break;
            case Controller.STOP:

                Logger.print("SYNC: " + this.bt.status);

                this.lastThread.stop();
                this.bt.pt.finish();
                this.bt.pt.reset();
                this.bt.pt.refresh();
                this.bt.status = 0;

                break;
            case Controller.DISPLAY_COMPARE_FILE:

                Logger.print("Display Compare File");
                displayCompareFile();
                break;
            case Controller.OPEN_README:

                this.openReadme();


                break;

            case Controller.NEXT_ACTION:

                int modus = 1;

                switch(modus){
                    case(1):
                        // Indexing
                        break;
                    case(2):
                        // Comparing
                        break;
                    case(3):
                        // Syncing
                        break;
                }
                break;
            default:
                break;
        }

    }

    private String indexing(String dir) {
        if (this.bt.isFree()) {
            this.window.progressAction.setText(Controller.INDEXING);
            String tempFile = SettingsController.getTempDir() + System.currentTimeMillis() + SettingsController.getIndexFileEnding() + SettingsController.getFileEnding();
            this.bt.setModus(Controller.INDEXING);
            this.bt.setIndexProps(new IndexTaskProps(dir, tempFile));
            Thread t = new Thread(this.bt);
            lastThread = t;
            t.start();
            return tempFile;
        }
        return null;
    }

    private String compare(String file1, String file2) {
        if (this.bt.isFree()) {
            this.window.progressAction.setText(Controller.COMPARE);
            String tempFile = SettingsController.getTempDir() + System.currentTimeMillis() + SettingsController.getCompareFileEnding() + SettingsController.getFileEnding();
            this.bt.setModus(Controller.COMPARE);
            this.bt.setCompareProps(new CompareTaskProps(file1, file2, tempFile, true, true));
            Thread t = new Thread(this.bt);
            lastThread = t;
            t.start();
            return tempFile;
        }
        return null;
    }

    private boolean sync(String compareFilePath) {
        if (this.bt.isFree()) {
            this.window.progressAction.setText(Controller.SYNC);
            this.bt.setModus(Controller.SYNC);
            this.bt.setSyncProps(new SyncTaskProps(compareFilePath));
            Thread t = new Thread(this.bt);
            lastThread = t;
            t.start();
            return true;
        }
        return false;
    }

    void displayCompareFile(){

        String path = window.tfVergleichsdatei.getText();
        compareFileView cfv = new compareFileView("Comparing", 400, 700);
        cfv.setFile(path);
        cfv.setVisible(true);
        cfv.createGUI();
    }

    public void openReadme(){

        Logger.print("Open Readme");

        File openFile = new File("README.md");
        try {
            Desktop.getDesktop().browse(openFile.toURI());
        } catch (Exception error) {
            Logger.printErr(error.toString());
        }
    }
}
