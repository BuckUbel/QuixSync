package controller.Tasks;

import controller.Controller;
import controller.FileControllerWithFeedback;
import controller.LanguageController;
import controller.SettingsController;
import controller.Threads.ProgressThread;
import logger.Logger;

public class BackgroundTask implements Runnable {

    public ProgressThread pt;

    private String modus = "";

    public int status = 0;

    private IndexTaskProps indexProps;
    private IndexTaskProps indexPropsTarget; // only for whole sync
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
                this.indexing(this.indexProps.pathToIndex, this.indexProps.indexFilePath);
                this.pt.window.c.setPathToIndexFilesForNextAction(this.indexProps.indexFilePath);

                break;
            case Controller.COMPARE:
                this.comparing(this.compareProps.sourceIndexPath, this.compareProps.targetIndexPath, this.compareProps.comparePath, this.compareProps.isHardSync, this.compareProps.fastMode);
                this.pt.window.c.setPathToCompareFileForNextAction(this.compareProps.comparePath);
                break;
            case Controller.SYNC:
                this.sync(this.syncProps.compareFilePath);
                this.pt.window.nextActionButton.setEnabled(false);
                this.pt.window.nextActionButton.setText(SettingsController.getNoNextActionString());
                break;
            case Controller.WHOLE_SYNC:
                try {
                    Logger.print("Start Whole Sync SRC[\"" + this.indexProps.pathToIndex + "\"]  TRG[\"" + this.indexPropsTarget.pathToIndex + "\" ] ");
                    this.indexing(this.indexProps.pathToIndex, this.indexProps.indexFilePath);
                    this.indexing(this.indexPropsTarget.pathToIndex, this.indexPropsTarget.indexFilePath);
                    this.pt.window.progressAction.setText(Controller.COMPARE);
                    this.comparing(this.compareProps.sourceIndexPath, this.compareProps.targetIndexPath, this.compareProps.comparePath, this.compareProps.isHardSync, this.compareProps.fastMode);
                    this.pt.window.progressAction.setText(Controller.SYNC);
                    this.sync(this.syncProps.compareFilePath);
                    Logger.print("End Whole Sync");
                } catch (Exception e) {
                    Logger.printErr(e);
                }
                break;
        }
        this.pt.window.activateLoading(false);
        this.status = 0;
        long endTime = System.currentTimeMillis();
        double seconds = (endTime - startTime);
        seconds = seconds / 1000;

        Logger.print(LanguageController.getLang().THE_PROCESS_LASTED + " " + String.format("%.2f", seconds) + " " + LanguageController.getLang().SECONDS + ".");
    }

    private void indexing(String pathToIndex, String indexFilePath) {
        Logger.print("Start Indexing: " + pathToIndex);
        FileControllerWithFeedback.getAllElements(pathToIndex, this.pt).saveAsJSON(indexFilePath);
        Logger.print("End Indexing: " + indexFilePath);
    }

    private void comparing(String sourceIndexPath, String targetIndexPath, String comparePath, boolean isHardSync, boolean fastMode) {
        Logger.print("Start Comparing SRC[\"" + sourceIndexPath + "\"]  TRG[\"" + targetIndexPath + "\" ] HardSync: " + isHardSync + " FastMode: " + fastMode);
        FileControllerWithFeedback.compareJSONFiles(sourceIndexPath, targetIndexPath, comparePath, isHardSync, fastMode, this.pt);
        Logger.print("End Comparing: " + comparePath);
    }

    private void sync(String compareFilePath) {
        try {
            Logger.print("Start Sync: " + compareFilePath);
            FileControllerWithFeedback.sync(compareFilePath, this.pt);
            Logger.print("End Sync");
        } catch (Exception e) {
            Logger.printErr(e);
        }
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

    public void setWholeProps(IndexTaskProps itp, IndexTaskProps itp2, CompareTaskProps ctp, SyncTaskProps stp) {
        this.status = 1;
        this.indexProps = itp;
        this.indexPropsTarget = itp2;
        this.compareProps = ctp;
        this.syncProps = stp;
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
