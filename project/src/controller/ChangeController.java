package controller;

import logger.Logger;
import models.CompareFileTableModel;
import models.FileTableModel;
import models.IndexFileTableModel;
import models.TypeFile;
import views.mainView;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ChangeController implements ChangeListener {

    private mainView window;
    private Controller c;

    ChangeController(mainView window, Controller c) {
        this.window = window;
        this.c = c;
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        TypeFile[] sel;

        if (window.tabbedPane1.getSelectedIndex() == SettingsController.TAB_NUMBER.COMPARE.getValue()) { // Comp
            Logger.print(LanguageController.getLang().GET_INDEXING_FILES);
            sel = FileController.getFilesWithSpecificString(SettingsController.getTempDir(), SettingsController.getIndexFileEnding());

            FileTableModel ftm = new IndexFileTableModel(sel);
            tblToFileTbl(window.tblSourceIndexFiles, window.tfQuellIndexdatei, ftm);

            FileTableModel ftm2 = new IndexFileTableModel(sel);
            tblToFileTbl(window.tbltargetIndexFiles, window.tfZielIndexdatei, ftm2);
        }
        if (window.tabbedPane1.getSelectedIndex() == SettingsController.TAB_NUMBER.SYNC.getValue()) { //Sync

            Logger.print(LanguageController.getLang().GET_COMPARE_FILES);
            sel = FileController.getFilesWithSpecificString(SettingsController.getTempDir(), SettingsController.getCompareFileEnding());

            FileTableModel ftm = new CompareFileTableModel(sel);
            tblToFileTbl(window.tblCompareFiles, window.tfVergleichsdatei, ftm);

            Controller controller = this.c;
            window.tblCompareFiles.addMouseListener(new MouseAdapter() {
                public void mousePressed(MouseEvent mouseEvent) {
                    JTable table = (JTable) mouseEvent.getSource();
                    if (mouseEvent.getClickCount() == 2 && table.getSelectedRow() != -1) {
                        controller.displayCompareFile();
                    }
                }
            });
        }
    }

    private void tblToFileTbl(JTable tbl, JTextField tf, FileTableModel ftm) {
        tbl.setModel(ftm);
        int width = 0;
        for (int i = 0; i < ftm.header.length; i++) {
            width += ftm.headerWidth[i];
            tbl.getColumn(ftm.header[i]).setPreferredWidth(ftm.headerWidth[i]);
        }
        // important for viewport of scrollpane
        tbl.setPreferredSize(new Dimension(width, ftm.data.length * SettingsController.getTableRowHeight()));

        tbl.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tbl.getSelectionModel().addListSelectionListener(e -> {
            int index = ((DefaultListSelectionModel) e.getSource()).getLeadSelectionIndex();
            if (index >= 0 && ftm.data.length > index) {
                tf.setText(ftm.data[index].indexPath);
            }
        });

    }
}
