import controller.Controller;
import controller.Tasks.BackgroundTask;
import logger.Logger;
import views.mainView;

public class Main {

    public static void main(String[] args) throws Exception {

        BackgroundTask bt = new BackgroundTask();

        mainView window = new mainView("QuixSync", 800, 400);
        Logger.setWindow(window);
        window.setVisible(true);

        Controller controller = new Controller(window, bt);

        window.setController(controller);
        window.createGUI();

    }
}
