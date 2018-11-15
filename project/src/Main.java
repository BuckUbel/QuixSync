import controller.Tasks.BackgroundTask;
import controller.Controller;
import logger.Logger;
import views.View;

public class Main {

    public static void main(String[] args) throws Exception {

        View window = new View("QuixSync", 800, 600);
        Logger.setWindow(window);

        Controller controller = new Controller(window);

        window.setController(controller);
        window.createGUI();

    }
}
