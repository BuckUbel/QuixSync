package controller;

import controller.Tasks.*;
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

    private String pathToIndex = "";
    private String pathToIndex2 = "";   // only for whole sync
    private String pathToIndexFileSrc = "";
    private String pathToIndexFileTrg = "";
    private String pathToCompareFile = "";


    //private IndexTaskProps indexProps = new IndexTaskProps();
    //private IndexTaskProps indexProps2 = new IndexTaskProps(); // only for whole sync
    //private CompareTaskProps compareProps = new CompareTaskProps();
    //private SyncTaskProps syncProps = new SyncTaskProps();

    private String nextActionModus = "";


    public Controller(mainView window, BackgroundTask bt) {
        this.window = window;
        this.bt = bt;

        cc = new ChangeController(window, this, bt);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        String command = e.getActionCommand();
        boolean success;

        switch (command) {
            case Controller.WHOLE_SYNC:

                String source = window.tfQuellverzeichnisG.getText();
                String target = window.tfZielverzeichnisG.getText();

                File sourceF = new File(source);
                File targetF = new File(target);
                success = false;
                if (sourceF.isDirectory()) {
                    if (targetF.isDirectory()) {

                        this.pathToIndex = source;
                        this.pathToIndex2 = target;
                        success = this.wholeIndexing();

                        if (success) {
                            lastThread.start();

                        } else {
                            // TODO: say user, that another process is running
                            // Feature: add to Process chain
                        }
                    } else {
                        // TODO: ask the user after another target path, because, the specified string was no path to a folder
                    }
                } else {
                    // TODO: ask the user after another source path, because, the specified string was no path to a folder
                }


//                while (!this.bt.isFree()) ;
//                String indexPath1 = this.indexing(source);

//                while (!this.bt.isFree()) ;
//                String indexPath2 = this.indexing(target);

//                while (!this.bt.isFree()) ;
//                compareFilePath = this.compare(indexPath1, indexPath2);

//                while (!this.bt.isFree()) ;
//                this.sync(compareFilePath);

                break;
            case Controller.INDEXING:

                String dir = window.tfQuellverzeichnisI.getText();


                File f = new File(dir);
                if (f.isDirectory()) {
                    this.pathToIndex = window.tfQuellverzeichnisI.getText();
                    success = this.indexing();

                    if (success) {
                        lastThread.start();
                    } else {
                        // TODO: say user, that another process is running
                        // Feature: add to Process chain
                    }

                } else {
                    // TODO: ask the user after another path, because, the specified string was no path to a folder
                }

                break;
            case Controller.COMPARE:


                this.pathToIndexFileSrc = window.tfQuellIndexdatei.getText();
                this.pathToIndexFileTrg = window.tfZielIndexdatei.getText();

                success = this.compare();

                if (success) {
                    lastThread.start();
                } else {
                    // TODO: say user, that another process is running
                    // Feature: add to Process chain
                }
                break;
            case Controller.SYNC:

                this.pathToCompareFile = window.tfVergleichsdatei.getText();
                success = this.sync();

                if (success) {
                    lastThread.start();
                } else {
                    // TODO: say user, that another process is running
                    // Feature: add to Process chain
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

                if (this.bt.isFree()) {
                    this.bt.setModus(this.nextActionModus);
                    lastThread = new Thread(this.bt);
                    lastThread.start();
                }

                break;
            default:
                break;
        }

    }


    private boolean wholeIndexing() {
        if (this.bt.isFree()) {
            this.window.progressAction.setText(Controller.INDEXING);
            String tempFile = SettingsController.getTempDir() + System.currentTimeMillis() + SettingsController.getIndexFileEnding() + SettingsController.getFileEnding();
            String tempFile2 = SettingsController.getTempDir() + (System.currentTimeMillis() + 1) + SettingsController.getIndexFileEnding() + SettingsController.getFileEnding();
            String tempFile3 = SettingsController.getTempDir() + (System.currentTimeMillis() + 2) + SettingsController.getCompareFileEnding() + SettingsController.getFileEnding();

            this.bt.setWholeProps(
                    new IndexTaskProps(this.pathToIndex, tempFile),
                    new IndexTaskProps(this.pathToIndex2, tempFile2),
                    new CompareTaskProps(tempFile, tempFile2, tempFile3, true, true),
                    new SyncTaskProps(tempFile3)
            );

            this.nextActionModus = Controller.WHOLE_SYNC;
            this.bt.setModus(this.nextActionModus);
            lastThread = new Thread(this.bt);
            return true;
        }
        return false;
    }

    private void preIndexing() {
        this.window.progressAction.setText(Controller.INDEXING);
        String tempFile = SettingsController.getTempDir() + System.currentTimeMillis() + SettingsController.getIndexFileEnding() + SettingsController.getFileEnding();
        this.nextActionModus = Controller.INDEXING;
        this.bt.setIndexProps(new IndexTaskProps(this.pathToIndex, tempFile));
    }

    private boolean indexing() {
        if (this.bt.isFree()) {
            this.preIndexing();
            this.bt.setModus(this.nextActionModus);
            lastThread = new Thread(this.bt);
            this.postIndexing();
            return true;
        }
        return false;
    }

    private void postIndexing() {
        this.pathToIndex = "";
    }


    private void preCompare() {
        this.window.progressAction.setText(Controller.COMPARE);
        String tempFile = SettingsController.getTempDir() + System.currentTimeMillis() + SettingsController.getCompareFileEnding() + SettingsController.getFileEnding();
        this.nextActionModus = Controller.COMPARE;
        this.bt.setCompareProps(new CompareTaskProps(this.pathToIndexFileSrc, this.pathToIndexFileTrg, tempFile, true, true));
    }

    private boolean compare() {
        if (this.bt.isFree()) {
            this.preCompare();
            this.bt.setModus(this.nextActionModus);
            lastThread = new Thread(this.bt);
            this.postCompare();
            return true;
        }
        return false;
    }

    private void postCompare() {
        this.pathToIndexFileSrc = "";
        this.pathToIndexFileTrg = "";
    }

    private void preSync() {

        this.window.progressAction.setText(Controller.SYNC);
        nextActionModus = Controller.SYNC;
        this.bt.setSyncProps(new SyncTaskProps(this.pathToCompareFile));
    }

    private boolean sync() {
        if (this.bt.isFree()) {
            this.preSync();
            this.bt.setModus(this.nextActionModus);
            lastThread = new Thread(this.bt);
            this.postSync();
            return true;
        }
        return false;
    }

    private void postSync() {
        this.pathToCompareFile = "";
    }

    void displayCompareFile() {

        String path = window.tfVergleichsdatei.getText();
        compareFileView cfv = new compareFileView("Comparing", 400, 700);
        cfv.setFile(path);
        cfv.setVisible(true);
        cfv.createGUI();
    }

    public void openReadme() {

        Logger.print("Open Readme");

        File openFile = new File("README.md");
        try {
            Desktop.getDesktop().browse(openFile.toURI());
        } catch (Exception error) {
            Logger.printErr(error.toString());
        }
    }

    public void setPathToIndexForNextAction(String pathToIndex) {
        this.pathToIndex = pathToIndex;
        this.preIndexing();
    }

    public void setPathToIndexFilesForNextAction(String pathToIndexFile) {
        if (this.pathToIndexFileSrc.equals("")) {
            this.pathToIndexFileSrc = pathToIndexFile;
        } else if (this.pathToIndexFileTrg.equals("")) {
            this.pathToIndexFileTrg = pathToIndexFile;
            this.preCompare();
        } else {
            this.pathToIndexFileSrc = pathToIndexFile;
            this.pathToIndexFileTrg = "";
        }
    }

    public void setPathToCompareFileForNextAction(String pathToCompareFile) {
        this.pathToCompareFile = pathToCompareFile;
        this.preSync();
        this.postSync();
    }
}
