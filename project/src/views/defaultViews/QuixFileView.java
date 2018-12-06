package views.defaultViews;

import logger.Logger;
import views.viewInterfaces.QuixFileViewI;

import javax.swing.*;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import java.awt.*;

public class QuixFileView extends QuixView implements QuixFileViewI {

    private JTextPane fileContentPane;

    protected QuixFileView(String title) {
        super(800, 600, title);
    }

    public void createGUI(JPanel rootPanel, JTextPane fileContentPane) {
        super.createGUI(rootPanel);
        this.fileContentPane = fileContentPane;
    }

    public void writeInPane(String msg, Color cF, Color cB) {

        StyledDocument doc = this.fileContentPane.getStyledDocument();
        SimpleAttributeSet attributeSet = new SimpleAttributeSet();
        StyleConstants.setForeground(attributeSet, cF);
        StyleConstants.setBackground(attributeSet, cB);
        StyleConstants.setBold(attributeSet, true);
        try {
            doc.insertString(doc.getLength(), msg + "\n", attributeSet);
        } catch (Exception e) {
            Logger.printErr(e.toString());
        }
    }

    public void setFile(String path) {
    }

    @Override
    public void finish() {
        super.finish();
        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    }
}
