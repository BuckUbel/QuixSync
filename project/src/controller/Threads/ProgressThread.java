package controller.Threads;

public class ProgressThread {

    public Thread mainThread;

    public int counter_max = 0;
    public int counter_current = 0;
    public int iterations_max = 0;
    public int iterations_current = 0;

    public ProgressThread(Thread mainThread) {
        this.mainThread = mainThread;
    }

    public ProgressThread(){}

    public void increaseMaxCounter() {
        this.counter_max++;
    }

    public void setMaxCounter(int a) {
        this.counter_max = a;
        this.counter_current = 0;
    }

    public void setMaxCounterAndIteration(int a) {
        this.increaseCurrentIterations();
        this.setMaxCounter(a);
    }

    public void increaseCurrentCounter() {
        this.counter_current++;
        this.print();
    }

    public void startWithCounting() {
        this.counter_max = 0;
        this.counter_current = 0;
        this.iterations_max = 0;
        this.iterations_current = 0;
    }

    public void count() {
        this.increaseMaxCounter();
        this.increaseCurrentCounter();
    }

    public void setMaxIterations(int a) {
        this.iterations_max = a;
        this.iterations_current = 0;
    }

    public void increaseCurrentIterations() {
        this.iterations_current++;
        this.print();
    }

    public void finish() {
        this.counter_current = this.counter_max;
        this.iterations_current = this.iterations_max;
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
//        System.out.println("Counter Max: " + this.counter_max);
//        System.out.println("Counter Current: " + this.counter_current);
//        System.out.println("Iteration Max: " + this.iterations_max);
//        System.out.println("Iteration Current: " + this.iterations_current);
    }
}
