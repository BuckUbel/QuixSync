package controller;

public class ThreadController {

    public static Thread createNewThread(Runnable r) {
        if (!ProgressController.isProcessRunning())
            return new Thread(r);
        return null;
    }
}
