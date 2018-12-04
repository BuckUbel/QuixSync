package views;

import controller.Controller;
import controller.SettingsController;
import logger.Logger;
import models.ProcessingElementList;
import models.minimizedStorageElement;

import javax.swing.*;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AnotherRunningProcessDialog extends JDialog implements ActionListener {


    public JPanel rootPanel;

    private int width;
    private int height;

    private String title;

    private Controller c;
    private JButton okButton;
    private JLabel dialogText;


    public AnotherRunningProcessDialog(String title) {
        this.width = 350;
        this.height = 200;
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
        this.okButton.addActionListener(this);

        this.dialogText.setText("<html>Ein anderer Prozess läuft derzeit. <br> Warten Sie bis dieser abgeschlossen ist. </html>");

        this.finish();
    }

    private void finish() {

        this.setSize(this.width, this.height);
        this.setResizable(false);
        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        this.setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        dispose();
    }

}
