package views;

import controller.Controller;
import controller.SettingsController;
import fileWriter.JSONCreator;
import logger.Logger;
import models.ProcessingElementList;
import models.minimizedStorageElement;

import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.io.File;

public class compareFileView extends JFrame {

    public JPanel rootPanel;
    private JTextPane fileContentPane;

    private int width;
    private int height;

    private String title;

    private Controller c;

    public int imageCounter = 0;
    public int currentImage = 0;

    private minimizedStorageElement[] copyList;
    private minimizedStorageElement[] deleteList;

    private static final Color RED = new Color(255, 128, 128);
    private static final Color GREEN = new Color(128,255,128);
    private static final Color BLUE = new Color(178,178,255);


    public compareFileView(String title, int width, int height) {
        this.width = width;
        this.height = height;
        this.title = title;
        this.setLayout(new BorderLayout());

    }

    public void setController(Controller c) {
        this.c = c;
    }

    public void createGUI() {

        this.setTitle(this.title);
        ImageIcon img = new ImageIcon(SettingsController.getLogoFile());
        this.setIconImage(img.getImage());

        this.add(rootPanel);

        for (minimizedStorageElement mse : this.copyList) {
            this.writeInPane("+ " + mse.toString() + mse.getState(), Color.BLACK, compareFileView.GREEN);
        }
        for (minimizedStorageElement mse : this.deleteList) {
            this.writeInPane("- " + mse.toString(), Color.BLACK, compareFileView.RED);
        }

        int count = this.copyList.length + this.deleteList.length;
        if(count == 0){
            this.writeInPane("Keine Änderungen.", Color.BLACK, Color.WHITE);
        }

        this.setTitle("Elemente: " + count);

        Logger.print("Elemente: " + count + " wurden dargestellt.");
        this.finish();
    }

    private void finish() {

        this.setSize(this.width, this.height);
        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        this.setVisible(true);
    }

    public void writeInPane(String msg, Color cF, Color cB) {

        StyledDocument doc = fileContentPane.getStyledDocument();


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

        ProcessingElementList pel = new ProcessingElementList().readJSON(path);
        this.copyList = pel.getCopyList();
        this.deleteList = pel.getDeleteList();
    }

}
