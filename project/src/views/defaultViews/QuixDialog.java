package views.defaultViews;

import controller.Controller;
import controller.SettingsController;
import views.viewInterfaces.QuixDialogI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class QuixDialog extends JDialog implements QuixDialogI, ActionListener {
    private int width;
    private int height;

    private String title;

    private Controller c;

    protected QuixDialog(String title) {
        this.width = 350;
        this.height = 200;
        this.title = title;
        this.setLayout(new BorderLayout());
    }

    public void setController(Controller c) {
        this.c = c;
    }

    @Override
    public void createGUI(JPanel rootPanel) {
        this.setTitle(this.title);
        ImageIcon img = new ImageIcon(SettingsController.getLogoFile());
        this.setIconImage(img.getImage());
        this.add(rootPanel);
    }

    public void createGUI(JPanel rootPanel, JButton okButton, JLabel dialogText, String msg) {
        this.createGUI(rootPanel);
        okButton.addActionListener(this);
        dialogText.setText(msg);
    }

    public void finish() {
        this.setSize(this.width, this.height);
        this.setResizable(false);
        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        dispose();
    }
}
