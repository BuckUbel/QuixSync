package views.viewInterfaces;

import controller.Controller;

import javax.swing.*;

public interface QuixDialogI extends QuixViewI {

    void setController(Controller c);

    void createGUI(JPanel rootPanel, JButton okButton, JLabel dialogText, String msg);

    void finish();
}
