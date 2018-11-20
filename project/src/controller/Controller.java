package controller;

import controller.Tasks.BackgroundTask;
import controller.Tasks.CompareTaskProps;
import controller.Tasks.IndexTaskProps;
import controller.Tasks.SyncTaskProps;
import logger.Logger;
import views.mainView;

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
    public static final String ADD_FTP_CONNECTION = "ADD_FTP_CONNECTION";


    private mainView window;
    private BackgroundTask bt;
    private Thread lastThread;
    public ChangeController cc;

    public Controller(mainView window, BackgroundTask bt) {
        this.window = window;
        this.bt = bt;

        cc = new ChangeController(window, bt);
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

                String source = window.tfQuellverzeichnisG.getText();
                String target = window.tfZielverzeichnisG.getText();
                System.out.println("Indexing1: "+ this.bt.isFree());
                while(!this.bt.isFree());
                String indexPath1 = this.indexing(source);
                System.out.println("Indexing2: "+ this.bt.isFree());
                while(!this.bt.isFree());
                String indexPath2 = this.indexing(target);
                System.out.println("Compare: "+ this.bt.isFree());
                while(!this.bt.isFree());
                compareFilePath = this.compare(indexPath1, indexPath2);
                System.out.println("Sync1: "+ this.bt.isFree());
                while(!this.bt.isFree());
                this.sync(compareFilePath);

                break;
            case Controller.INDEXING:

                Logger.print("INDEXING");

                String dir = window.tfQuellverzeichnisI.getText();
                File f = new File(dir);
                if (f.isDirectory()) {

                    String indexFilePath = this.indexing(dir);

                    if(indexFilePath != null){
                        // set path to compare this
                    }
                    else{
                        // Feature: add to Process chain
                        // now: say user, that another process is running
                    }

                } else {
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

                if(compareFilePath != null){
                    // set path to sync this
                }
                else{
                    // Feature: add to Process chain
                    // now: say user, that another process is running
                }
                break;

            case Controller.SYNC:

                Logger.print("SYNC");

                // TODO: get index-filePaths
                // @QuentinWeber assigned

                compareFilePath = window.tfVergleichsdatei.getText();
                successfully = this.sync(compareFilePath);

                if(successfully){
                    // is Running
                }
                else{
                    // Feature: add to Process chain
                    // now: say user, that another process is running
                }

                break;
            case Controller.ADD_FTP_CONNECTION:

                // TODO: add FTP-Connection credentials in extra store and save it in json

                break;
            case Controller.STOP:

                this.lastThread.stop();
                // TODO: add FTP-Connection credentials in extra store and save it in json

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
            System.out.println("Before Indexing");
            t.start();
            System.out.println("After Indexing");

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
}
