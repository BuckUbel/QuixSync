package views.viewInterfaces;

import controller.Controller;

import javax.swing.*;

public interface QuixViewI {

    void setController(Controller c);

    void createGUI(JPanel rootPanel);

    void finish();
}
