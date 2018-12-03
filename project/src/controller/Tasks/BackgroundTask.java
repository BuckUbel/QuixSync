package controller.Tasks;

import controller.Controller;
import controller.FileControllerWithFeedback;
import controller.Threads.ProgressThread;
import logger.Logger;

public class BackgroundTask implements Runnable {

    public ProgressThread pt;

    private String modus = "";

    public int status = 0;

    private IndexTaskProps indexProps;
    private CompareTaskProps compareProps;
    private SyncTaskProps syncProps;

    public BackgroundTask() {
        this.pt = new ProgressThread();
    }

    @Override
    public void run() {
        long startTime = System.currentTimeMillis();

        switch (this.modus) {
            case Controller.INDEXING:
                Logger.print("Start Indexing");
                FileControllerWithFeedback.getAllElements(indexProps.pathToIndex, this.pt).saveAsJSON(indexProps.indexFilePath);
                Logger.print("Start Indexing");
                break;
            case Controller.COMPARE:
                Logger.print("Start Comparing");
                FileControllerWithFeedback.compareJSONFiles(compareProps.sourceIndexPath, compareProps.targetIndexPath, compareProps.comparePath, compareProps.isHardSync, compareProps.slowMode, this.pt);
                Logger.print("Start Comparing");
                break;
            case Controller.SYNC:
                try {
                    Logger.print("Start Sync");
                    FileControllerWithFeedback.sync(syncProps.compareFilePath, this.pt);
                    Logger.print("Start Sync");
                } catch (Exception e) {
                    Logger.printErr(e);
                }
                break;
        }
        this.status = 0;
        long endTime = System.currentTimeMillis();
        double seconds = (endTime-startTime);
        seconds = seconds/1000;

        Logger.print("Der Vorgang dauerte " + String.format("%.2f", seconds) + " Sekunden.");
    }

    public boolean isFree() {
        switch (this.status) {
            case 1:
                return false;
            case 0:
                return true;
            case -1:
                return false;
        }
        return false;
    }

    public void setModus(String s) {
        this.modus = s;
    }
    public String getModus() {
        return this.modus;
    }

    public void setIndexProps(IndexTaskProps itp) {
        this.status = 1;
        this.indexProps = itp;
        this.compareProps = null;
        this.syncProps = null;
    }

    public void setCompareProps(CompareTaskProps ctp) {
        this.status = 1;
        this.indexProps = null;
        this.compareProps = ctp;
        this.syncProps = null;
    }

    public void setSyncProps(SyncTaskProps stp) {
        this.status = 1;
        this.indexProps = null;
        this.compareProps = null;
        this.syncProps = stp;
    }
}
