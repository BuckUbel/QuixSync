package views;

import controller.Controller;
import logger.Logger;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

public class View extends JFrame {
    private JPanel rootPanel;
    private JTabbedPane tabbedPane1;
    private JLabel lbQuellverzeichnisG;
    private JLabel lbZielverzeichnisG;
    private JButton btnSyncG;
    private JButton btnAutoSyncG;
    private JButton btnQuellverzeichnisG;
    private JButton btnZielverzeichnisG;
    private JButton btnIndexErstellen1;
    private JButton btnIndexErstellen2;
    private JButton btnBeideIndizes;
    private JTextField tfQuellIndexdatei;
    private JTextField tfZielIndexdatei;
    private JButton btnVergleichen;
    private JTextField tfVergleichsdatei;
    private JButton btnSynchronisieren;
    private JTextField tfTempVerzeichnis;
    private JRadioButton rbHardSync;
    private JRadioButton rbDaemonBetrieb;
    private JButton btnFTPVerbindung;
    private JButton btnSpeichern;
    private JTextField tfQuellverzeichnisG;
    private JTextField tfZielverzeichnisG;
    private JLabel lbQuellverzeichnisI;
    private JButton btnQuellverzeichnisI;
    private JTextField tfQuellverzeichnisI;
    private JLabel lbZielverzeichnisI;
    private JTextField tfZielverzeichnisI;
    private JButton btnZielverzeichnisI;
    private JLabel lbQuellIndexdatei;
    private JLabel lbZielIndexdatei;
    private JLabel lbVergleichsdatei;
    private JLabel lbTempVerzeichnis;
    private JPanel pnGesamtsync;
    private JPanel pnIndexierung;
    private JPanel pnVergleichen;
    private JPanel pnSync;
    private JPanel pnEinstellungen;

    private int width = 0;
    private int height = 0;

    private String title = "";

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

        btnSyncG.setActionCommand(Controller.WHOLE_SYNC);
        btnSyncG.addActionListener(c);

        btnIndexErstellen1.setActionCommand(Controller.INDEXING);
        btnIndexErstellen1.addActionListener(c);

        btnIndexErstellen2.setActionCommand(Controller.INDEXING);
        btnIndexErstellen2.addActionListener(c);

        btnVergleichen.setActionCommand(Controller.COMPARE);
        btnVergleichen.addActionListener(c);

        btnSynchronisieren.setActionCommand(Controller.SYNC);
        btnSynchronisieren.addActionListener(c);

        // kann man das kürzen?
        btnQuellverzeichnisG.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                openFileChooser(tfQuellverzeichnisG);
            }
        });

        btnZielverzeichnisG.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                openFileChooser(tfZielverzeichnisG);
            }
        });

        btnQuellverzeichnisI.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                openFileChooser(tfQuellverzeichnisI);
            }
        });

        btnZielverzeichnisI.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                openFileChooser(tfZielverzeichnisI);
            }
        });

        add(rootPanel);

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

    private void openFileChooser(JTextField TextField) {
        JFileChooser fc = new JFileChooser();
        fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int returnVal = fc.showOpenDialog(null);
        File f;
        if (returnVal == JFileChooser.APPROVE_OPTION)
        {
            f = fc.getSelectedFile();
            TextField.setText(f.getPath());
        }
    }

}