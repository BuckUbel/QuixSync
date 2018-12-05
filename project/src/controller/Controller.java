package controller;

import controller.Tasks.*;
import logger.Logger;
import models.TypeFile;
import views.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.nio.file.Files;

public class Controller implements ActionListener {

    private Thread thread;

    public static final String WHOLE_SYNC = "GESAMTSYNCHRONISIERUNG";
    public static final String INDEXING = "INDEXIERUNG";
    public static final String COMPARE = "VERGLEICHEN";
    public static final String SYNC = "SYNCHRONISIERUNG";
    public static final String STOP = "STOP";
    public static final String DISPLAY_COMPARE_FILE = "VERGLEICHSDATEI_ANZEIGEN";
    public static final String DISPLAY_LOG_FILE = "DISPLAY_LOG_FILE";
    private static final String OPEN_README = "README_OEFFNEN";
    public static final String NEXT_ACTION = "NAECHSTE_AKTION";
    public static final String ADD_FTP_CONNECTION = "FTP_VERBINDUNG_HINZUFUEGEN";
    public static final String CLEAR_CACHE = "CACHE_LEEREN";
    public static final String SAVE_SETTINGS = "SAVE_SETTINGS";

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
                Logger.print("Stopping Process: " + this.bt.status);
                this.window.activateLoading(false);
                this.window.progressAction.setText(SettingsController.getNoNextActionString());
                this.lastThread.stop();
                this.bt.pt.finish();
                this.bt.pt.reset();
                this.bt.pt.refresh();
                this.bt.status = 0;
                break;
            case Controller.DISPLAY_COMPARE_FILE:
                Logger.print("Vergleichsdatei anzeigen");
                displayCompareFile();
                break;
            case Controller.DISPLAY_LOG_FILE:
                Logger.print("Display Log File");
                displayLogFile();
                break;
            case Controller.OPEN_README:
                this.window.progressAction.setText(Controller.OPEN_README);
                this.openReadme();
                break;
            case Controller.CLEAR_CACHE:
                try {
                    this.window.progressAction.setText(Controller.CLEAR_CACHE);
                    this.clearCache();
                }
                catch(Exception error){
                    Logger.printErr(e.toString());
                }
                break;
            case Controller.SAVE_SETTINGS:
                this.window.progressAction.setText(Controller.SAVE_SETTINGS);
                this.applySettingChanges();
                SettingsController.saveSettings();
                break;
            case Controller.NEXT_ACTION:
                if (this.bt.isFree()) {
                    this.bt.setModus(this.nextActionModus);
                    lastThread = new Thread(this.bt);
                    lastThread.start();
                    this.window.nextActionButton.setEnabled(false);
                    this.window.activateLoading(true);
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
        this.window.activateLoading(true);

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
        this.window.activateLoading(true);

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
        this.window.activateLoading(true);

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

    private void displayAnotherRunningDialog() {
        AnotherRunningProcessDialog arpv = new AnotherRunningProcessDialog("ist in Betrieb...");
        arpv.setVisible(true);
        arpv.createGUI();
    }

    private void displayOtherDirDialog() {
        OtherDirDialog odd = new OtherDirDialog("Falscher Pfad");
        odd.setVisible(true);
        odd.createGUI();
    }


    void displayCompareFile() {
        String path = window.tfVergleichsdatei.getText();
        compareFileView cfv = new compareFileView("Vergleichen");
        cfv.setFile(path);
        cfv.setVisible(true);
        cfv.createGUI();
    }
    void displayLogFile(){
        try {
            String loggerFilepath = SettingsController.getLoggerFilePath();
            loggerFileView cfv = new loggerFileView("Log File");
            cfv.setFile(loggerFilepath);
            cfv.setVisible(true);
            cfv.createGUI();
        }
        catch (Exception e){
            Logger.printErr(e.toString());
        }
    }

    public void openReadme() {

        Logger.print("Readme öffnen");
        File openFile = new File("README.md");
        try {
            Desktop.getDesktop().browse(openFile.toURI());
        } catch (Exception error) {
            Logger.printErr(error.toString());
        }
    }

    public void clearCache() throws Exception {
        Logger.print("Cache leeren");

        TypeFile[] sel = FileController.getFilesWithSpecificString(SettingsController.getTempDir(), SettingsController.getIndexFileEnding());
        TypeFile[] sel2 = FileController.getFilesWithSpecificString(SettingsController.getTempDir(), SettingsController.getCompareFileEnding());
        for (TypeFile tf : sel) {
            File f = new File(tf.indexPath);
            Files.deleteIfExists(f.toPath());
        }
        for (TypeFile tf : sel2) {
            File f = new File(tf.indexPath);
            Files.deleteIfExists(f.toPath());
        }
    }

    public void applySettingChanges(){
//        SettingsController.setCompareFileEnding();
//        SettingsController.setFileEnding();
//        SettingsController.setHelpFileLogo();
//        SettingsController.setIndexFileEnding();
//        SettingsController.setLoadingFileLogo();
//        SettingsController.setLogDir();
//        SettingsController.setLoggerFileJson();
//        SettingsController.setLoggerFileNormal();
//        SettingsController.setLoggerMode();
//        SettingsController.setLogoFile();
//        SettingsController.setNoNextActionString();
//        SettingsController.setPrettyLogging();
        SettingsController.setStandardIsHardSync(this.window.rbHardSync.isSelected());
        SettingsController.setStandardIsSlowMode(this.window.slowModeBox2.isSelected());
        SettingsController.setIsDaemon(this.window.rbDaemonBetrieb.isSelected());
        SettingsController.setTempDir(this.window.tfTempVerzeichnis.getText());

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
            this.window.nextActionButton.setText(SettingsController.getNoNextActionString());
        } else if (this.compareProps.targetIndexPath.equals("")) {
            this.compareProps.targetIndexPath = pathToIndexFile;
            this.preCompare();
            this.postCompare();
            this.window.nextActionButton.setText("Vergleichen");
            this.window.nextActionButton.setEnabled(true);
        } else {
            this.compareProps.sourceIndexPath = this.compareProps.targetIndexPath;
            this.compareProps.targetIndexPath = pathToIndexFile;
            this.window.nextActionButton.setText("Compare");
            this.window.nextActionButton.setEnabled(true);
        }

    }

    public void setPathToCompareFileForNextAction(String pathToCompareFile) {
        this.syncProps.compareFilePath = pathToCompareFile;
        this.preSync();
        this.postSync();
        this.window.nextActionButton.setText("Synchronisieren");
        this.window.nextActionButton.setEnabled(true);
    }
}
