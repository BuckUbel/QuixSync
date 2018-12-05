package views;

import controller.Controller;
import controller.SettingsController;
import views.defaultViews.QuixView;
import views.viewInterfaces.QuixViewI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

public class mainView extends QuixView implements QuixViewI {
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
    public JButton btnFTPVerbindung;
    public JButton btnSettingsSave;
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
    public JButton stopButton;
    public JTable tbltargetIndexFiles;
    public JTable tblCompareFiles;
    public JTable tblSourceIndexFiles;
    private JPanel progressPanel;
    private JButton anzeigenButton;
    public JLabel progressPercentage;
    public JLabel progressInformation;
    public JLabel progressAction;
    private JLabel helpIcon;
    public JButton nextActionButton;
    private JButton deleteCacheBtn;
    private JCheckBox harteSynchronisierungCheckBox;
    private JCheckBox slowModeBox1;
    public JCheckBox rbHardSync;
    public JCheckBox slowModeBox2;
    public JCheckBox rbDaemonBetrieb;
    private JLabel loadIcon;
    private JButton displayLogFile;

    public mainView(String title, int width, int height) {
        super(width,height,title);
    }

    public void createGUI() {

        super.createGUI(this.rootPanel);

        this.btnSyncG.setActionCommand(Controller.WHOLE_SYNC);
        this.btnSyncG.addActionListener(this.c);

        this.btnIndexErstellen1.setActionCommand(Controller.INDEXING);
        this.btnIndexErstellen1.addActionListener(this.c);

        this.btnVergleichen.setActionCommand(Controller.COMPARE);
        this.btnVergleichen.addActionListener(this.c);

        this.btnSynchronisieren.setActionCommand(Controller.SYNC);
        this.btnSynchronisieren.addActionListener(this.c);

        this.anzeigenButton.setActionCommand(Controller.DISPLAY_COMPARE_FILE);
        this.anzeigenButton.addActionListener(this.c);

        this.btnFTPVerbindung.setActionCommand(Controller.ADD_FTP_CONNECTION);
        this.btnFTPVerbindung.addActionListener(this.c);

        this.stopButton.setActionCommand(Controller.STOP);
        this.stopButton.addActionListener(this.c);

        this.btnQuellverzeichnisG.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                openFileChooser(tfQuellverzeichnisG);
            }
        });

        this.btnZielverzeichnisG.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                openFileChooser(tfZielverzeichnisG);
            }
        });

        this.btnQuellverzeichnisI.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                openFileChooser(tfQuellverzeichnisI);
            }
        });

        this.tabbedPane1.addChangeListener(this.c.cc);

        this.nextActionButton.setActionCommand(Controller.NEXT_ACTION);
        this.nextActionButton.addActionListener(this.c);

        this.deleteCacheBtn.setActionCommand(Controller.CLEAR_CACHE);
        this.deleteCacheBtn.addActionListener(this.c);

        this.displayLogFile.setActionCommand(Controller.DISPLAY_LOG_FILE);
        this.displayLogFile.addActionListener(this.c);

        this.btnSettingsSave.setActionCommand(Controller.SAVE_SETTINGS);
        this.btnSettingsSave.addActionListener(this.c);

        ImageIcon normalQuix  = new ImageIcon(SettingsController.getHelpFileLogo());
        ImageIcon animatedQuix = new ImageIcon(SettingsController.getLoadingFileLogo());

        Image newHelpImage = normalQuix.getImage().getScaledInstance(50, 35, Image.SCALE_DEFAULT);
        this.helpIcon.setIcon(new ImageIcon(newHelpImage));
        this.helpIcon.setText("");

        this.helpIcon.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                openReadme();
            }
        });

        Image newLoadImage = animatedQuix.getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT);
        Image newLoadDisabledImage = normalQuix.getImage().getScaledInstance(50, 35, Image.SCALE_DEFAULT);
        this.loadIcon.setIcon(new ImageIcon(newLoadImage));
        this.loadIcon.setDisabledIcon(new ImageIcon((newLoadDisabledImage)));
        this.loadIcon.setText("");

        this.slowModeBox1.setSelected(true);
        this.slowModeBox2.setSelected(true);

        this.refreshSettings();

        this.finish();
    }

    private void openReadme(){
        this.c.openReadme();
    }

    private void openFileChooser(JTextField TextField) {
        JFileChooser fc = new JFileChooser();
        fc.setCurrentDirectory(new File("C:/"));
        fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int returnVal = fc.showOpenDialog(null);
        File f;
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            f = fc.getSelectedFile();
            TextField.setText(f.getPath());
        }
    }

    public void activateLoading(boolean b){
        this.loadIcon.setEnabled(b);
        this.stopButton.setEnabled(b);
    }

    public void refreshSettings(){

        this.rbHardSync.setSelected(SettingsController.getIsHardSync());
        this.slowModeBox2.setSelected(SettingsController.getIsSlowMode());
        this.rbDaemonBetrieb.setSelected(SettingsController.getIsDaemon());
        this.tfTempVerzeichnis.setText(SettingsController.getTempDir());

    }

}