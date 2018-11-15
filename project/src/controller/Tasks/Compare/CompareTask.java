package controller.Tasks.Compare;

import controller.FileController;
import controller.FileControllerWithFeedback;
import controller.Tasks.BackgroundTask;

public class CompareTask extends BackgroundTask {

    private String sourceIndexPath;
    private String targetIndexPath;
    private String comparePath;
    private boolean isHardSync;
    private boolean slowMode;

    public CompareTask(String sourceIndexPath, String targetIndexPath, String comparePath, boolean isHardSync, boolean slowMode){
        this.sourceIndexPath = sourceIndexPath;
        this.targetIndexPath = targetIndexPath;
        this.comparePath = comparePath;
        this.isHardSync = isHardSync;
        this.slowMode = slowMode;
    }

    @Override
    public void run() {
//        FileController.compareJSONFiles(this.sourceIndexPath, this.targetIndexPath, this.comparePath, this.isHardSync, this.slowMode);
        FileControllerWithFeedback.compareJSONFiles(this.sourceIndexPath, this.targetIndexPath, this.comparePath, this.isHardSync, this.slowMode, this.pt);
    }
}
