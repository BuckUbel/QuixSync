package views.viewInterfaces;

import javax.swing.*;
import java.awt.*;

public interface QuixFileViewI extends QuixViewI {

    Color RED = new Color(255, 128, 128);
    Color GREEN = new Color(128, 255, 128);
    Color BLUE = new Color(178, 178, 255);

    void setFile(String path);
    void createGUI(JPanel rootPanel, JTextPane fileContentPane);
}
