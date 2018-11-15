package controller.Tasks.Index;

import controller.FileControllerWithFeedback;
import controller.Tasks.BackgroundTask;
import controller.Threads.ProgressThread;
import javafx.concurrent.Task;
import logger.Logger;

public class IndexTask extends Task<Boolean> {

    public ProgressThread pt;

    private String pathToIndex;
    private String indexFilePath;

    public IndexTask(String dir, String indexFilePath) {
        this.pathToIndex = dir;
        this.indexFilePath = indexFilePath;
    }
    public void connectThread(Thread pt) {
        this.pt = new ProgressThread(pt);
    }

    @Override
    public void run() {

        FileControllerWithFeedback.getAllElements(pathToIndex, this.pt).saveAsJSON(indexFilePath);
    }

    @Override
    protected Boolean call() throws Exception {

        updateMessage("    Processing... ");
        FileControllerWithFeedback.getAllElements(pathToIndex, this.pt).saveAsJSON(indexFilePath);
        updateMessage("    Done.  ");

        return true;
    }
}
