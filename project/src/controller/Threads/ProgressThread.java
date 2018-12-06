package controller.Threads;

import controller.LanguageController;
import views.mainView;

public class ProgressThread {

    public mainView window;

    private int counter_max = 0;
    private int counter_current = 0;
    private int iterations_max = 0;
    private int iterations_current = 0;

    private boolean onlyCounting;
    private boolean isFinish;

    public ProgressThread() {
    }

    public void setWindow(mainView window) {
        this.window = window;
    }

    private static String beautify(int i) {
        String integerString = Integer.toString(i);
        String returnString = "";
        int length = integerString.length();

        if (i > 1000000) {
            returnString += integerString.substring(0, length - 6);
            returnString += ".";
            returnString += integerString.substring(length - 6, length - 3);
            returnString += ".";
            returnString += integerString.substring(length - 3, length);
        } else if (i > 1000) {
            returnString += integerString.substring(0, length - 3);
            returnString += ".";
            returnString += integerString.substring(length - 3, length);
        } else {
            returnString += integerString;
        }
        return returnString;
    }

    public void refresh() {

        int max, min, current;
        double percentage;

        max = (counter_max + 1) * (iterations_max + 1);
        min = 0;
        current = (counter_current + 1) * (iterations_current + 1);
        percentage = ((float) current / (float) max) * 100;
        //this.print();
        if (!onlyCounting) {
            this.window.progressBar1.setMaximum(max);
            this.window.progressBar1.setMinimum(min);
            this.window.progressBar1.setValue(current);
            this.window.progressInformation.setText(ProgressThread.beautify(current) + " " + LanguageController.getLang().OF + " " + ProgressThread.beautify(max) + " " + LanguageController.getLang().ELEMENTS);
            this.window.progressPercentage.setText(String.format("%.2f", percentage) + " %");
        } else {
            if (isFinish) {
                this.window.progressBar1.setMaximum(100);
                this.window.progressBar1.setMinimum(0);
                this.window.progressBar1.setValue(100);
                this.window.progressInformation.setText(ProgressThread.beautify(max) + " " + LanguageController.getLang().ELEMENTS);
                this.window.progressPercentage.setText(LanguageController.getLang().FOUND_AND_INDEXED);
            } else {
                if (counter_current != 0) {
                    this.window.progressBar1.setMaximum(100);
                    this.window.progressBar1.setMinimum(0);
                    this.window.progressBar1.setValue(0);
                    this.window.progressInformation.setText(ProgressThread.beautify(current) + " " + LanguageController.getLang().ELEMENTS);
                    this.window.progressPercentage.setText(LanguageController.getLang().FOUND_SO_FAR);

                } else {
                    this.window.progressBar1.setMaximum(0);
                    this.window.progressBar1.setMinimum(0);
                    this.window.progressBar1.setValue(0);
                    this.window.progressInformation.setText("");
                    this.window.progressPercentage.setText("");
                }
            }
        }
    }

    private void increaseMaxCounter() {
        this.counter_max++;
        this.refresh();
    }

    public void setMaxCounter(int a) {
        this.counter_max = a;
        this.counter_current = 0;
        this.refresh();
    }

    public void setMaxCounterAndIteration(int a) {
        this.increaseCurrentIterations();
        this.setMaxCounter(a);
        this.refresh();
    }

    public void increaseCurrentCounter(int a) {
        this.counter_current += a;
        this.refresh();
    }

    public void increaseCurrentCounter() {
        this.counter_current++;
        this.refresh();
    }

    public void startWithCounting(boolean onlyCounting) {
        this.reset();
        this.onlyCounting = onlyCounting;
        this.refresh();
    }

    public void count() {
        this.increaseMaxCounter();
        this.increaseCurrentCounter();
        this.refresh();
    }

    public void setMaxIterations(int a) {
        this.iterations_max = a;
        this.iterations_current = 0;
        this.refresh();
    }

    public void increaseCurrentIterations() {
        this.iterations_current++;
        this.counter_current = 0;
        this.refresh();
    }

    public void finish() {
        this.isFinish = true;
        this.counter_current = this.counter_max;
        this.iterations_current = this.iterations_max;
        this.refresh();
    }

    public void reset() {
        this.isFinish = false;
        this.onlyCounting = true;
        this.counter_max = 0;
        this.counter_current = 0;
        this.iterations_max = 0;
        this.iterations_current = 0;
        this.refresh();
    }

    private int getStatus() {
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
