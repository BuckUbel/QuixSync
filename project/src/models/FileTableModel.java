package models;

import javax.swing.table.DefaultTableModel;
import java.text.SimpleDateFormat;

public abstract class FileTableModel extends DefaultTableModel {

    public String[] header;
    public int[] headerWidth;
    public TypeFile[] data;

    FileTableModel(String[] header, int rowCount) {
        super(header, rowCount);
    }

    public abstract void init();

    @Override
    public boolean isCellEditable(int row, int column) {
        return false;
    }
}
