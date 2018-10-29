import controller.BackgroundTasks;
import controller.Controller;
import logger.Logger;
import views.View;

public class Main {

    public static void main(String[] args) throws Exception {

        BackgroundTasks bt = new BackgroundTasks();

        View window = new View("QuixSync", 800, 600);
        Logger.setWindow(window);

        Controller controller = new Controller(window, bt);

        window.setController(controller);
        window.createGUI();

    }
}
