import controller.Controller;
import controller.LanguageController;
import controller.SettingsController;
import controller.Tasks.BackgroundTask;
import logger.Logger;
import views.mainView;

public class Main {

    public static void main(String[] args) throws Exception {

        SettingsController.loadSettings();
        LanguageController.loadLang(SettingsController.getLanguage());

        BackgroundTask bt = new BackgroundTask();
        mainView window = new mainView("QuixSync", 800, 400);
        Controller controller = new Controller(window, bt);

        bt.pt.setWindow(window);

        window.setController(controller);
        window.setVisible(true);
        window.createGUI();

        Logger.print(LanguageController.getLang().PROGRAM_STARTED_SUCCESSFULLY);
    }
}
