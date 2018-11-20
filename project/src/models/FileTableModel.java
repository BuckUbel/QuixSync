package models;

import javax.swing.table.DefaultTableModel;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public abstract class FileTableModel extends DefaultTableModel {

    public String[] header;
    public int[] headerWidth;
    public TypeFile[] data;

    public FileTableModel(String[] header, int rowCount){
        super(header, rowCount);
    }

    public FileTableModel(TypeFile data[], String[] header, int[] headerWidth) {
        super(header, 0);
        this.header = header;
        this.headerWidth = headerWidth;
        this.data = data;
        this.init();
    }

    public abstract void init();

    @Override
    public boolean isCellEditable(int row, int column) {
        //all cells false
        return false;
    }

    public static String millisToDate(long timestamp) {
        return new SimpleDateFormat("dd.MM.yyyy HH:mm").format(timestamp);
    }


}
