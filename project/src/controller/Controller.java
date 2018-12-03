package controller;

import controller.Tasks.*;
import logger.Logger;
import views.AnotherRunningProcessDialog;
import views.OtherDirDialog;
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

    private IndexTaskProps indexProps = new IndexTaskProps();
    private IndexTaskProps indexPropsTarget = new IndexTaskProps(); // only for whole sync
    private CompareTaskProps compareProps = new CompareTaskProps();
    private SyncTaskProps syncProps = new SyncTaskProps();

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

                if (sourceF.isDirectory()) {
                    if (targetF.isDirectory()) {

                        this.indexProps.pathToIndex = source;
                        this.indexPropsTarget.pathToIndex = target;
                        success = this.wholeIndexing();

                        if (success) {
                            lastThread.start();

                        } else {
                            this.displayAnotherRunningDialog();
                            // Feature: add to Process chain
                        }
                    } else {
                        // here trg
                        this.displayOtherDirDialog();
                    }
                } else {
                    // TODO: difference between source and target
                    // here src
                    this.displayOtherDirDialog();
                }

                break;
            case Controller.INDEXING:

                String dir = window.tfQuellverzeichnisI.getText();


                File f = new File(dir);
                if (f.isDirectory()) {
                    this.indexProps.pathToIndex = window.tfQuellverzeichnisI.getText();
                    success = this.indexing();

                    if (success) {
                        lastThread.start();
                    } else {
                        this.displayAnotherRunningDialog();
                        // Feature: add to Process chain
                    }

                } else {
                    this.displayOtherDirDialog();
                }

                break;
            case Controller.COMPARE:

                this.compareProps.sourceIndexPath = window.tfQuellIndexdatei.getText();
                this.compareProps.targetIndexPath = window.tfZielIndexdatei.getText();
                this.compareProps.isHardSync = true;
                this.compareProps.slowMode = true;
                success = this.compare();

                if (success) {
                    lastThread.start();
                } else {
                    this.displayAnotherRunningDialog();
                    // Feature: add to Process chain
                }
                break;
            case Controller.SYNC:

                this.syncProps.compareFilePath = window.tfVergleichsdatei.getText();
                success = this.sync();

                if (success) {
                    lastThread.start();
                } else {
                    this.displayAnotherRunningDialog();
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
                    this.window.nextActionButton.setEnabled(false);
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

            this.indexProps.indexFilePath = tempFile;
            this.indexPropsTarget.indexFilePath = tempFile2;
            this.compareProps.sourceIndexPath = tempFile;
            this.compareProps.targetIndexPath = tempFile2;
            this.compareProps.comparePath = tempFile3;
            this.syncProps.compareFilePath = tempFile3;

            this.bt.setWholeProps(this.indexProps, this.indexPropsTarget, this.compareProps, this.syncProps);
            this.nextActionModus = Controller.WHOLE_SYNC;
            this.bt.setModus(this.nextActionModus);
            lastThread = new Thread(this.bt);
            return true;
        }
        return false;
    }

    private void preIndexing() {
        this.window.progressAction.setText(Controller.INDEXING);
        this.nextActionModus = Controller.INDEXING;
        this.indexProps.indexFilePath = SettingsController.getTempDir() + System.currentTimeMillis() + SettingsController.getIndexFileEnding() + SettingsController.getFileEnding();
        this.bt.setIndexProps(this.indexProps);
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
        this.indexProps = new IndexTaskProps();
    }


    private void preCompare() {
        this.window.progressAction.setText(Controller.COMPARE);
        String tempFile = SettingsController.getTempDir() + System.currentTimeMillis() + SettingsController.getCompareFileEnding() + SettingsController.getFileEnding();
        this.nextActionModus = Controller.COMPARE;
        this.compareProps.comparePath = tempFile;
        this.bt.setCompareProps(this.compareProps);
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
        this.compareProps = new CompareTaskProps();
    }

    private void preSync() {

        this.window.progressAction.setText(Controller.SYNC);
        nextActionModus = Controller.SYNC;
        this.bt.setSyncProps(this.syncProps);
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
        this.syncProps = new SyncTaskProps();
    }

    private void displayAnotherRunningDialog(){
        AnotherRunningProcessDialog arpv = new AnotherRunningProcessDialog("is Running...");
        arpv.setVisible(true);
        arpv.createGUI();
    }

    private void displayOtherDirDialog(){
        OtherDirDialog odd = new OtherDirDialog("Falscher Pfad");
        odd.setVisible(true);
        odd.createGUI();
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
        this.indexProps.pathToIndex = pathToIndex;
        this.preIndexing();
        this.postIndexing();
        this.window.nextActionButton.setEnabled(true);
    }

    public void setPathToIndexFilesForNextAction(String pathToIndexFile) {
        if (this.compareProps.sourceIndexPath.equals("")) {
            this.compareProps.sourceIndexPath = pathToIndexFile;
            this.window.nextActionButton.setEnabled(false);
        } else if (this.compareProps.targetIndexPath.equals("")) {
            this.compareProps.targetIndexPath = pathToIndexFile;
            this.preCompare();
            this.postCompare();
            this.window.nextActionButton.setText("Compare");
            this.window.nextActionButton.setEnabled(true);
        } else {
            this.compareProps.sourceIndexPath= pathToIndexFile;
            this.compareProps.targetIndexPath = "";
            this.window.nextActionButton.setEnabled(false);
        }

    }

    public void setPathToCompareFileForNextAction(String pathToCompareFile) {
        this.syncProps.compareFilePath = pathToCompareFile;
        this.preSync();
        this.postSync();
        this.window.nextActionButton.setText("Sync");
        this.window.nextActionButton.setEnabled(true);
    }
}
