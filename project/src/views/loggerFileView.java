package views;

import controller.FormattingController;
import controller.LanguageController;
import fileWriter.JSONCreator;
import logger.LogInformation;
import logger.Logger;
import views.defaultViews.QuixFileView;

import javax.swing.*;
import java.awt.*;

public class loggerFileView extends QuixFileView {

    public JPanel rootPanel;
    private JTextPane fileContentPane;

    private LogInformation[] logList;

    public loggerFileView(String title) {
        super(title);
    }

    public void createGUI() {
        super.createGUI(this.rootPanel, this.fileContentPane);
        for (LogInformation li : this.logList) {
            if (li.level.equals("INFO")) {
                this.writeInPane(
                        li.level
                                + "\t"
                                + FormattingController.beautyMillis(li.instant.epochSecond*1000)
                                + "\t"
                                + li.message,
                        Color.BLACK,
                        compareFileView.GREEN
                );
            }
            if (li.level.equals("ERROR")) {
                this.writeInPane(
                        li.level
                                + "\t"
                                + FormattingController.beautyMillis(li.instant.epochSecond*1000)
                                + "\t"
                                + li.message,
                        Color.BLACK,
                        compareFileView.RED
                );
            }

        }
        if (this.logList.length == 0) {
            this.writeInPane(LanguageController.getLang().NO_LOGENTRIES, Color.BLACK, Color.WHITE);
        }
        this.setTitle("Logs: " + this.logList.length);
        Logger.print("Logs: " + this.logList.length + LanguageController.getLang().WERE_PRINTED);
        this.finish();
    }


    @Override
    public void setFile(String path) {
        this.logList = Logger.mergeMultipleLogArrays((LogInformation[][]) JSONCreator.read(path, LogInformation[][].class));
    }

    @Override
    public void writeInPane(String msg, Color cF, Color cB) {
        super.writeInPane(msg, cF, cB);
    }
}
