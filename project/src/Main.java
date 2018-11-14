import controller.BackgroundTasks;
import controller.Controller;
import logger.Logger;
//import views.View_Old;
import views.View;

public class Main {

    public static void main(String[] args) throws Exception {

        BackgroundTasks bt = new BackgroundTasks();

        /*View_Old window = new View_Old("QuixSync", 600, 800);
        Logger.setWindow(window);
        window.setVisible(true);

        Controller controller = new Controller(window, bt);

        window.setController(controller);
        window.createGUI();*/

        View window = new View("QuixSync", 800, 400);
        Logger.setWindow(window);
        window.setVisible(true);

        Controller controller = new Controller(window, bt);

        window.setController(controller);
        window.createGUI();

    }
}
