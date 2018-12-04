package views.defaultViews;

import controller.Controller;
import controller.SettingsController;
import views.viewInterfaces.QuixViewI;

import javax.swing.*;
import java.awt.*;

public class QuixView extends JFrame implements QuixViewI {

    private int width;
    private int height;
    private String title;
    public Controller c;

    public QuixView(int width, int height, String title) {
        this.width = width;
        this.height = height;
        this.title = title;
        this.setLayout(new BorderLayout());
    }

    public void setController(Controller c) {
        this.c = c;
    }

    public void createGUI(JPanel rootPanel) {
        this.setTitle(this.title);
        ImageIcon img = new ImageIcon(SettingsController.getLogoFile());
        this.setIconImage(img.getImage());
        this.add(rootPanel);
    }

    public void finish() {
        this.setSize(this.width, this.height);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setVisible(true);
    }
}
