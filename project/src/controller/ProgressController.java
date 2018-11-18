package controller;

public class ProgressController {

    public static int counter_max = 0;
    public static int counter_current = 0;
    public static int iterations_max = 0;
    public static int iterations_current = 0;

    public static void increaseMaxCounter(){
        ProgressController.counter_max++;
    }

    public static void setMaxCounter(int a) {
        ProgressController.counter_max = a;
        ProgressController.counter_current = 0;
    }

    public static void setMaxCounterAndIteration(int a) {
        ProgressController.increaseCurrentIterations();
        ProgressController.setMaxCounter(a);
    }

    public static void increaseCurrentCounter() {
        ProgressController.counter_current++;
        ProgressController.print();
    }

    public static void startWithCounting(){
        ProgressController.counter_max = 0;
        ProgressController.counter_current = 0;
        ProgressController.iterations_max = 0;
        ProgressController.iterations_current = 0;
    }
    public static void count(){
        ProgressController.increaseMaxCounter();
        ProgressController.increaseCurrentCounter();
    }

    public static void setMaxIterations(int a) {
        ProgressController.iterations_max = a;
        ProgressController.iterations_current = 0;
    }

    public static void increaseCurrentIterations() {
        ProgressController.iterations_current++;
        ProgressController.print();
    }

    public static void finish() {
        ProgressController.counter_current = ProgressController.counter_max;
        ProgressController.iterations_current = ProgressController.iterations_max;
    }


    public static int getStatus() {
        if (iterations_max != 0) {
            if (counter_max != 0) {
                if (counter_max == counter_current && iterations_max == iterations_current)
                    return 2; // a process is finished
                return 1; // a process is running
            } else {
                return -1; // no process is running
            }
        } else {
            if(counter_max == 0){
                return -1;
            }
            if (counter_max == counter_current) {
                return 0; // a process with only counting without iterations
            } else {
                return -1; // no process is running
            }
        }
    }

    public static boolean isOnlyOneCounter() {
        return ProgressController.getStatus() == 0;
    }
    public static boolean isProcessRunning() {
        int a = ProgressController.getStatus();
        return  a == 0 || a == 1;
    }
    public static boolean isFinished(){
        return  ProgressController.getStatus() == 2;
    }

    public static void print(){
//        System.out.println("Counter Max: " + ProgressController.counter_max);
//        System.out.println("Counter Current: " + ProgressController.counter_current);
//        System.out.println("Iteration Max: " + ProgressController.iterations_max);
//        System.out.println("Iteration Current: " + ProgressController.iterations_current);
    }
}
