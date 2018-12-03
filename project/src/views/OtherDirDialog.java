package views;

import controller.Controller;
import controller.SettingsController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class OtherDirDialog extends JDialog implements ActionListener {


    public JPanel rootPanel;

    private int width;
    private int height;

    private String title;

    private Controller c;
    private JButton okButton;
    private JLabel dialogText;


    public OtherDirDialog(String title) {
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

        this.dialogText.setText("<html>Der eingegebene Pfad war kein existierendes Verzeichnis <br> Bitte wählen Sie ein anderes Verzeichnis.</html>");

        this.finish();
    }

    private void finish() {

        this.setSize(this.width, this.height);
        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        this.setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        dispose();
    }
}
