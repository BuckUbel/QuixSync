package views;

import controller.Controller;
import controller.SettingsController;
import logger.Logger;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

public class mainView extends JFrame {
    public JPanel rootPanel;
    public JTabbedPane tabbedPane1;
    public JLabel lbQuellverzeichnisG;
    public JLabel lbZielverzeichnisG;
    public JButton btnSyncG;
    public JButton btnAutoSyncG;
    public JButton btnQuellverzeichnisG;
    public JButton btnZielverzeichnisG;
    public JButton btnIndexErstellen1;
    public JButton btnIndexErstellen2;
    public JTextField tfQuellIndexdatei;
    public JTextField tfZielIndexdatei;
    public JButton btnVergleichen;
    public JTextField tfVergleichsdatei;
    public JButton btnSynchronisieren;
    public JTextField tfTempVerzeichnis;
    public JRadioButton rbHardSync;
    public JRadioButton rbDaemonBetrieb;
    public JButton btnFTPVerbindung;
    public JButton btnSpeichern;
    public JTextField tfQuellverzeichnisG;
    public JTextField tfZielverzeichnisG;
    public JLabel lbQuellverzeichnisI;
    public JButton btnQuellverzeichnisI;
    public JTextField tfQuellverzeichnisI;
    public JTextField tfZielverzeichnisI;
    public JButton btnZielverzeichnisI;
    public JLabel lbQuellIndexdatei;
    public JLabel lbZielIndexdatei;
    public JLabel lbVergleichsdatei;
    public JLabel lbTempVerzeichnis;
    public JPanel pnGesamtsync;
    public JPanel pnIndexierung;
    public JPanel pnVergleichen;
    public JPanel pnSync;
    public JPanel pnEinstellungen;
    public JProgressBar progressBar1;
    private JButton stopButton;
    public JTable tbltargetIndexFiles;
    public JTable tblCompareFiles;
    public JTable tblSourceIndexFiles;
    private JPanel progressPanel;
    private JButton anzeigenButton;
    public JLabel progressPercentage;
    public JLabel progressInformation;
    public JLabel progressAction;
    private JLabel helpIcon;
    private JButton nextActionButton;

    private int width;
    private int height;

    private String title;

    private Controller c;

    public int imageCounter = 0;
    public int currentImage = 0;


    public mainView(String title, int width, int height) {
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

        btnSyncG.setActionCommand(Controller.WHOLE_SYNC);
        btnSyncG.addActionListener(c);

        btnIndexErstellen1.setActionCommand(Controller.INDEXING);
        btnIndexErstellen1.addActionListener(c);

        btnVergleichen.setActionCommand(Controller.COMPARE);
        btnVergleichen.addActionListener(c);

        btnSynchronisieren.setActionCommand(Controller.SYNC);
        btnSynchronisieren.addActionListener(c);

        anzeigenButton.setActionCommand(Controller.DISPLAY_COMPARE_FILE);
        anzeigenButton.addActionListener(c);

        btnFTPVerbindung.setActionCommand(Controller.ADD_FTP_CONNECTION);
        btnFTPVerbindung.addActionListener(c);

        stopButton.setActionCommand(Controller.STOP);
        stopButton.addActionListener(c);

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

        tabbedPane1.addChangeListener(c.cc);

        nextActionButton.setActionCommand(Controller.NEXT_ACTION);
        nextActionButton.addActionListener(c);


        ImageIcon helpImage = new ImageIcon(SettingsController.getHelpFileLogo());
        Image newImage = helpImage.getImage().getScaledInstance(50, 35, Image.SCALE_DEFAULT);
        helpIcon.setIcon(new ImageIcon(newImage));
        helpIcon.setText("");

        helpIcon.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                openReadme();
            }
        });


        // TODO: edit new Action button to start the nex Action

        add(rootPanel);
        this.finish();
    }

    private void openReadme(){
        this.c.openReadme();
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
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            f = fc.getSelectedFile();
            TextField.setText(f.getPath());
        }
    }

}