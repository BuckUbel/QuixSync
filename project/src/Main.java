import controller.BackgroundTasks;
import controller.Controller;
import logger.Logger;
import views.mainView;

public class Main {

    public static void main(String[] args) throws Exception {

        BackgroundTasks bt = new BackgroundTasks();

        mainView window = new mainView("QuixSync", 800, 400);
        Logger.setWindow(window);
        window.setVisible(true);

        Controller controller = new Controller(window, bt);

        window.setController(controller);
        window.createGUI();

    }
}
