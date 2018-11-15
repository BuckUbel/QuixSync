package controller.Tasks.Sync;

import controller.FileController;
import controller.FileControllerWithFeedback;
import controller.Tasks.BackgroundTask;
import logger.Logger;

public class SyncTask extends BackgroundTask {
    private String compareFilePath;
    private String pathToIndex;

    private boolean returnHasSuccess;

    public SyncTask(String compareFilePath){
        this.compareFilePath = compareFilePath;
    }

    @Override
    public void run() {
        try{
//            FileController.sync(this.compareFilePath);
            FileControllerWithFeedback.sync(this.compareFilePath, this.pt);
        }
        catch(Exception e){
            this.returnHasSuccess = false;
            Logger.printErr(e);
        }
    }
}
