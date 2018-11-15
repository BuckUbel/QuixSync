package controller.Tasks;

import controller.Threads.ProgressThread;

public class BackgroundTask implements Runnable {

    public ProgressThread pt;

    public BackgroundTask() {
    }

    @Override
    public void run() {
    }

    public void connectThread(Thread pt) {
        this.pt = new ProgressThread(pt);
    }


}
