package views;

import controller.LanguageController;
import logger.Logger;
import models.ProcessingElementList;
import models.minimizedStorageElement;
import views.defaultViews.QuixFileView;

import javax.swing.*;
import java.awt.*;

public class compareFileView extends QuixFileView {

    public JPanel rootPanel;
    private JTextPane fileContentPane;

    private minimizedStorageElement[] copyList;
    private minimizedStorageElement[] deleteList;

    public compareFileView(String title) {
        super(title);
    }

    public void createGUI() {

        super.createGUI(this.rootPanel, this.fileContentPane);

        for (minimizedStorageElement mse : this.copyList) {
            this.writeInPane("+ " + mse.toString(), Color.BLACK, QuixFileView.GREEN);
        }
        for (minimizedStorageElement mse : this.deleteList) {
            this.writeInPane("- " + mse.toString(), Color.BLACK, QuixFileView.RED);
        }

        int count = this.copyList.length + this.deleteList.length;
        if (count == 0) {
            this.writeInPane("Keine Änderungen.", Color.BLACK, Color.WHITE);
        }

        this.setTitle(LanguageController.getLang().ELEMENTS + ": " + count);

        Logger.print(LanguageController.getLang().ELEMENTS + ": " + count + " " + LanguageController.getLang().WERE_DIPSLAYED + ".");
        this.finish();
    }

    @Override
    public void setFile(String path) {
        ProcessingElementList pel = new ProcessingElementList().readJSON(path);
        this.copyList = pel.getCopyList();
        this.deleteList = pel.getDeleteList();
    }

    @Override
    public void writeInPane(String msg, Color cF, Color cB) {
        super.writeInPane(msg, cF, cB);
    }
}
