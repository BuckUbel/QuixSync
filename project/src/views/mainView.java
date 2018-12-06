package views;

import controller.Controller;
import controller.LanguageController;
import controller.SettingsController;
import javafx.scene.control.SelectionModel;
import models.Language;
import views.defaultViews.QuixView;
import views.viewInterfaces.QuixViewI;

import javax.swing.*;
import javax.swing.border.Border;
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
    public JCheckBox rbHardSync2;
    public JCheckBox fastModeBox2;
    public JCheckBox rbHardSync;
    public JCheckBox fastModeBox;
    public JCheckBox rbDaemonBetrieb;
    private JLabel loadIcon;
    private JButton displayLogFile;
    public JComboBox<String> langBox;

    public mainView(String title, int width, int height) {
        super(width, height, title);
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

        ImageIcon normalQuix = new ImageIcon(SettingsController.getHelpFileLogo());
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

        this.setLanguageStrings();

        this.refreshSettings();

        this.finish();
    }

    private void setLanguageStrings() {
        String[] languages = LanguageController.getAvailableLanguages();
        DefaultComboBoxModel<String> dcbm = new DefaultComboBoxModel<String>(languages);
        this.langBox.setModel(dcbm);
        this.langBox.setSelectedIndex(LanguageController.getLangCode(SettingsController.getLanguage()));

        this.langBox.setActionCommand(Controller.SET_LANGUAGE);
        this.langBox.addActionListener(c);
        this.setNewLanguageStrings();
    }
    public void setNewLanguageStrings(){

        LanguageController.loadLang((String) this.langBox.getSelectedItem());
        Language std = LanguageController.getLang();

        this.tabbedPane1.setTitleAt(SettingsController.TAB_NUMBER.WHOLE_SYNC.getValue(), std.WHOLE_SYNC);
        this.tabbedPane1.setTitleAt(SettingsController.TAB_NUMBER.INDEXING.getValue(), std.INDEXING);
        this.tabbedPane1.setTitleAt(SettingsController.TAB_NUMBER.COMPARE.getValue(), std.COMPARING);
        this.tabbedPane1.setTitleAt(SettingsController.TAB_NUMBER.SYNC.getValue(), std.SYNC);
        this.tabbedPane1.setTitleAt(SettingsController.TAB_NUMBER.SETTINGS.getValue(), std.WHOLE_SYNC);

        Border border = BorderFactory.createTitledBorder(std.PROGRESS_OF_CURRENT_ACTION);
        this.progressPanel.setBorder(border);

        //tfQuellIndexdatei
        //tfQuellverzeichnisG;
        //tfQuellverzeichnisI;
        //tfTempVerzeichnis;
        //tfVergleichsdatei
        //tfZielIndexdatei
        //tfZielverzeichnisG;
        this.anzeigenButton.setText(std.SHOW_FILE);
        this.btnAutoSyncG.setText(std.AS_AUTO_SYNC_SAVE);
        this.btnFTPVerbindung.setText(std.ADD_FTP_CONNECTION);
        this.btnIndexErstellen1.setText(std.CREATE_INDEX);
        this.btnQuellverzeichnisG.setText(std.SOURCE_DIR + " " + std.SELECT);
        this.btnQuellverzeichnisI.setText(std.DIRECTORY + " " + std.SELECT);
        this.btnSettingsSave.setText(std.SAVE);
        this.btnSyncG.setText(std.SYNC);
        this.btnSynchronisieren.setText(std.SYNC);
        this.btnVergleichen.setText(std.COMPARING);
        this.btnZielverzeichnisG.setText(std.SOURCE_DIR + " " + std.SELECT);
        this.deleteCacheBtn.setText(std.DELETE_CACHE);
        this.displayLogFile.setText(std.DISPLAY_LOGFILE);
        this.fastModeBox.setText(std.FAST_MODE_NO_RENAMING);
        this.fastModeBox2.setText(std.FAST_MODE_NO_RENAMING);
        this.helpIcon.setText("");
        this.lbQuellIndexdatei.setText(std.SOURCE_INDEX_FILE);
        this.lbQuellverzeichnisG.setText(std.SOURCE_DIR);
        this.lbQuellverzeichnisI.setText(std.DIRECTORY);
        this.lbTempVerzeichnis.setText(std.TEMP_DIR);
        this.lbVergleichsdatei.setText(std.COMPARE_FILE);
        this.lbZielIndexdatei.setText(std.TARGET_INDEX_FILE);
        this.lbZielverzeichnisG.setText(std.TARGET_DIR);
        this.nextActionButton.setText(std.NEXT_ACTION);
        this.progressAction.setText(std.NO_ACTION);
        this.progressInformation.setText("");
        this.progressPercentage.setText("");
        this.rbDaemonBetrieb.setText(std.ALLOW_DAEMON);
        this.rbHardSync.setText(std.HARD_SYNC);
        this.rbHardSync2.setText(std.HARD_SYNC);
        this.stopButton.setText(std.STOP);
    }

    private void openReadme() {
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

    public void activateLoading(boolean b) {
        this.loadIcon.setEnabled(b);
        this.stopButton.setEnabled(b);
    }

    public void refreshSettings() {

        this.fastModeBox.setSelected(SettingsController.getIsFastMode());
        this.fastModeBox2.setSelected(SettingsController.getIsFastMode());
        this.rbHardSync.setSelected(SettingsController.getIsHardSync());
        this.rbHardSync2.setSelected(SettingsController.getIsHardSync());
        this.fastModeBox.setSelected(SettingsController.getIsFastMode());
        this.rbDaemonBetrieb.setSelected(SettingsController.getIsDaemon());
        this.tfTempVerzeichnis.setText(SettingsController.getTempDir());

    }

}