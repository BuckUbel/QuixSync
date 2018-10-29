package controller;

import models.StorageElement;

public class BackgroundTasks implements Runnable {


    // TODO: this class will be used to run tasks in the background of the application (index, compare, sync)

    private StorageElement storageElement;

    public BackgroundTasks() {
    }

    public BackgroundTasks(StorageElement storageElement) {
        this.storageElement = storageElement;
    }

    @Override
    public void run() {

    }

}
