package logger;

import javax.swing.text.View;

public class Logger {

    View window;
    Boolean system = true;

    public Logger() {
    }

    public Logger(boolean system) {
        this.system = system;
    }

    public void setWindow(View window){
        this.window = window;
    }

    public void print(String s) {
        if (system) {
            System.out.println(s);
        } else {
            //this.window.write(s);
        }
    }


}

