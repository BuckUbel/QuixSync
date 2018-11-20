package controller;

import controller.Tasks.BackgroundTask;
import logger.Logger;
import models.*;
import views.mainView;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.Calendar;

public class ChangeController implements ChangeListener {

    public static final String GET_INDEXING_FILES = "GET_INDEXING_FILES";
    public static final String GET_COMPARE_FILES = "GET_COMPARE_FILES";

    private mainView window;
    private BackgroundTask bt;

    public ChangeController(mainView window, BackgroundTask bt) {
        this.window = window;
        this.bt = bt;
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        TypeFile[] sel;

        System.out.println("CC");
        if (window.tabbedPane1.getSelectedIndex() == 2) { // Comp
            Logger.print("GET_INDEXING_FILES");
            sel = FileController.getFilesWithSpecificString(SettingsController.getTempDir(), SettingsController.getIndexFileEnding());

            FileTableModel ftm = new IndexFileTableModel(sel);
            tblToFileTbl(window.tblSourceIndexFiles, window.tfQuellIndexdatei, ftm);

            FileTableModel ftm2 = new IndexFileTableModel(sel);
            tblToFileTbl(window.tbltargetIndexFiles, window.tfZielIndexdatei, ftm2);

        }

        if (window.tabbedPane1.getSelectedIndex() == 3) { //Sync

            Logger.print("GET_COMPARE_FILES");
            sel = (CompareFile[]) FileController.getFilesWithSpecificString(SettingsController.getTempDir(), SettingsController.getCompareFileEnding());

            FileTableModel ftm = new CompareFileTableModel(sel);
            tblToFileTbl(window.tblCompareFiles, window.tfVergleichsdatei, ftm);
        }
    }


    void tblToFileTbl(JTable tbl, JTextField tf, FileTableModel ftm){
        tbl.setModel(ftm);
        int width = 0;
        for(int i=0; i<ftm.header.length; i++){
            width += ftm.headerWidth[i];
            tbl.getColumn(ftm.header[i]).setPreferredWidth(ftm.headerWidth[i]);
        }
        tbl.setPreferredSize(new Dimension(width, 500));
        tbl.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tbl.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                int index = ((DefaultListSelectionModel) e.getSource()).getLeadSelectionIndex();
                if(index >= 0 && ftm.data.length > index){
                    tf.setText(ftm.data[index].indexPath);
                }
            }
        });
    }
}
