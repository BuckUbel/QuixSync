package controller.Threads;

import views.mainView;

public class ProgressThread {

    private mainView window;

    private int counter_max = 0;
    private int counter_current = 0;
    private int iterations_max = 0;
    private int iterations_current = 0;

    public ProgressThread() {
    }

    public void setWindow(mainView window) {
        this.window = window;
    }

    public void refresh() {

        int max, min, current;
        double percentage;

        max = (counter_max + 1) * (iterations_max + 1);
        min = 0;
        current = (counter_current + 1) * (iterations_current + 1);
        percentage = ((float) current / (float) max) * 100;

        if (max != current) {
            this.window.progressBar1.setMaximum(max);
            this.window.progressBar1.setMinimum(min);
            this.window.progressBar1.setValue(current);
            this.window.progressInformation.setText(current + " von " + max + " Elementen");
            this.window.progressPercentage.setText(String.format("%.2f", percentage) + " %");
        } else {
            this.window.progressBar1.setMaximum(100);
            this.window.progressBar1.setMinimum(0);
            this.window.progressBar1.setValue(100);
            this.window.progressInformation.setText(max + " Elemente");
            this.window.progressPercentage.setText(" ... ");
        }
    }

    private void increaseMaxCounter() {
        this.counter_max++;
        refresh();
    }

    public void setMaxCounter(int a) {
        this.counter_max = a;
        this.counter_current = 0;
        refresh();
    }

    public void setMaxCounterAndIteration(int a) {
        this.increaseCurrentIterations();
        this.setMaxCounter(a);
        refresh();
    }

    public void increaseCurrentCounter() {
        this.counter_current++;
        refresh();
    }

    public void startWithCounting() {
        this.counter_max = 0;
        this.counter_current = 0;
        this.iterations_max = 0;
        this.iterations_current = 0;
        refresh();
    }

    public void count() {
        this.increaseMaxCounter();
        this.increaseCurrentCounter();
        refresh();
    }

    public void setMaxIterations(int a) {
        this.iterations_max = a;
        this.iterations_current = 0;
        refresh();
    }

    public void increaseCurrentIterations() {
        this.iterations_current++;
        this.counter_current = 0;
        refresh();
    }

    public void finish() {
        this.counter_current = this.counter_max;
        this.iterations_current = this.iterations_max;
        refresh();
    }

    public int getStatus() {
        if (iterations_max != 0) {
            if (counter_max != 0) {
                if (counter_max == counter_current && iterations_max == iterations_current)
                    return 2; // a process is finished
                return 1; // a process is running
            } else {
                return -1; // no process is running
            }
        } else {
            if (counter_max == 0) {
                return -1;
            }
            if (counter_max == counter_current) {
                return 0; // a process with only counting without iterations
            } else {
                return -1; // no process is running
            }
        }
    }

    public boolean isOnlyOneCounter() {
        return this.getStatus() == 0;
    }

    public boolean isProcessRunning() {
        int a = this.getStatus();
        return a == 0 || a == 1;
    }

    public boolean isFinished() {
        return this.getStatus() == 2;
    }

    public void print() {
        System.out.println("Counter Max: " + this.counter_max);
        System.out.println("Counter Current: " + this.counter_current);
        System.out.println("Iteration Max: " + this.iterations_max);
        System.out.println("Iteration Current: " + this.iterations_current);
    }
}
