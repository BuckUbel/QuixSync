package views;

import controller.Controller;
import logger.Logger;

import javax.swing.*;
import java.awt.*;

public class View extends JFrame {

    private int width = 0;
    private int height = 0;

    private String title = "";

    private JPanel topPanel = new JPanel();
//    public JPanel mainPanel = new JPanel();

    private Controller c;

    public int imageCounter = 0;
    public int currentImage = 0;


    public View(String title, int width, int height) {
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

        // TODO: create View components
        // @PhilippLudwig assigned


        // testButtons for Developing

        this.add(this.topPanel, BorderLayout.NORTH);
        this.topPanel.setLayout(new GridLayout(1, 10));

        JButton createI = new JButton(Controller.INDEXING);
        createI.setActionCommand(Controller.INDEXING);
        createI.addActionListener(c);
        topPanel.add(createI);

        JButton createGIF = new JButton(Controller.GET_INDEXING_FILES);
        createGIF.setActionCommand(Controller.GET_INDEXING_FILES);
        createGIF.addActionListener(c);
        topPanel.add(createGIF);

        JButton createC = new JButton(Controller.COMPARE);
        createC.setActionCommand(Controller.COMPARE);
        createC.addActionListener(c);
        topPanel.add(createC);

        JButton createGCF = new JButton(Controller.GET_COMPARE_FILES);
        createGCF.setActionCommand(Controller.GET_COMPARE_FILES);
        createGCF.addActionListener(c);
        topPanel.add(createGCF);

        JButton createS = new JButton(Controller.SYNC);
        createS.setActionCommand(Controller.SYNC);
        createS.addActionListener(c);
        topPanel.add(createS);

        JButton createWS = new JButton(Controller.WHOLE_SYNC);
        createWS.setActionCommand(Controller.WHOLE_SYNC);
        createWS.addActionListener(c);
        topPanel.add(createWS);

        JProgressBar progressBar = new JProgressBar(0, 0, 100);
        // progressBar.setValue(progress);

        progressBar.setStringPainted(true);
        progressBar.setString(" Das ist eine sehr sehr lange Progressbar ");
        progressBar.setForeground(Color.RED);
        topPanel.add(progressBar);

        // and at the end the function "finish" should be called.
        Logger.print("GUI is finished");
        this.finish();
    }

    // helper function
    public void refreshSlider(int value, int minimum, int maximum, JSlider slider) {
        slider.setMinimum(minimum);
        slider.setMaximum(maximum);
        slider.setMajorTickSpacing(5);
        slider.setMinorTickSpacing(1);
        slider.createStandardLabels(Math.max(1, maximum / 5));
        slider.setPaintTicks(true);
        slider.setPaintLabels(true);
        slider.setValue(value);

        this.setVisible(true);
    }

    private void finish() {

        this.setSize(this.width, this.height);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setVisible(true);
    }

}
