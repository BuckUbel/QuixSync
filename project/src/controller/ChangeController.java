package controller;

import controller.Tasks.BackgroundTask;
import logger.Logger;
import models.*;
import views.mainView;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ChangeController implements ChangeListener {

    public static final String GET_INDEXING_FILES = "GET_INDEXING_FILES";
    public static final String GET_COMPARE_FILES = "GET_COMPARE_FILES";

    private mainView window;
    private BackgroundTask bt;
    private Controller c;

    ChangeController(mainView window, Controller c, BackgroundTask bt) {
        this.window = window;
        this.bt = bt;
        this.c = c;
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        TypeFile[] sel;

        if (window.tabbedPane1.getSelectedIndex() == SettingsController.TAB_NUMBER.COMPARE.getValue()) { // Comp
            Logger.print("GET_INDEXING_FILES");
            sel = FileController.getFilesWithSpecificString(SettingsController.getTempDir(), SettingsController.getIndexFileEnding());

            FileTableModel ftm = new IndexFileTableModel(sel);
            tblToFileTbl(window.tblSourceIndexFiles, window.tfQuellIndexdatei, ftm);

            FileTableModel ftm2 = new IndexFileTableModel(sel);
            tblToFileTbl(window.tbltargetIndexFiles, window.tfZielIndexdatei, ftm2);
        }
        if (window.tabbedPane1.getSelectedIndex() == SettingsController.TAB_NUMBER.SYNC.getValue()) { //Sync

            Logger.print("GET_COMPARE_FILES");
            sel = FileController.getFilesWithSpecificString(SettingsController.getTempDir(), SettingsController.getCompareFileEnding());

            FileTableModel ftm = new CompareFileTableModel(sel);
            tblToFileTbl(window.tblCompareFiles, window.tfVergleichsdatei, ftm);

            Controller controller = this.c;
            window.tblCompareFiles.addMouseListener(new MouseAdapter() {
                public void mousePressed(MouseEvent mouseEvent) {
                    JTable table =(JTable) mouseEvent.getSource();
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
