import controller.Controller;
import controller.SettingsController;
import controller.Tasks.BackgroundTask;
import logger.Logger;
import views.mainView;

public class Main {

    public static void main(String[] args) throws Exception {

        SettingsController.loadSettings();

        BackgroundTask bt = new BackgroundTask();
        mainView window = new mainView("QuixSync", 800, 400);
        Controller controller = new Controller(window, bt);


        bt.pt.setWindow(window);

        window.setController(controller);
        Logger.setWindow(window);

        window.setVisible(true);
        window.createGUI();

    }
}
