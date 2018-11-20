package controller.Tasks;

import controller.Controller;
import controller.FileController;
import controller.FileControllerWithFeedback;
import controller.Threads.ProgressThread;
import logger.Logger;

public class BackgroundTask implements Runnable {

    public ProgressThread pt;

    private String modus = "";

    private int status = 0;

    private IndexTaskProps indexProps;
    private CompareTaskProps compareProps;
    private SyncTaskProps syncProps;

    public BackgroundTask() {
        this.pt = new ProgressThread();
    }

    @Override
    public void run() {
        switch (this.modus) {
            case Controller.INDEXING:

//                FileController.getAllElements(indexProps.pathToIndex).saveAsJSON(indexProps.indexFilePath);
                FileControllerWithFeedback.getAllElements(indexProps.pathToIndex, this.pt).saveAsJSON(indexProps.indexFilePath);

                break;
            case Controller.COMPARE:

                FileControllerWithFeedback.compareJSONFiles(compareProps.sourceIndexPath, compareProps.targetIndexPath, compareProps.comparePath, compareProps.isHardSync, compareProps.slowMode, this.pt);
//                FileController.compareJSONFiles(compareProps.sourceIndexPath, compareProps.targetIndexPath, compareProps.comparePath, compareProps.isHardSync, compareProps.slowMode);

                break;
            case Controller.SYNC:

                try {
                    FileControllerWithFeedback.sync(syncProps.compareFilePath,this.pt);
//                    FileControllerWithFeedback.sync(syncProps.compareFilePath, this.pt);

                } catch (Exception e) {
                    Logger.printErr(e);

                }
                break;
        }
        this.status = 0;
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
