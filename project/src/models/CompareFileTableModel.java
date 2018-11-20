package models;

public class CompareFileTableModel extends FileTableModel{

    public static String[] compareHeader = {"Verglichen am", "Quelle", "Ziel"};
    public static int[] compareHeaderWidth = {135, 350, 350};

    public CompareFileTableModel(TypeFile[] data) {
        super(CompareFileTableModel.compareHeader, 0);
        this.header = CompareFileTableModel.compareHeader;
        this.headerWidth = CompareFileTableModel.compareHeaderWidth;
        this.data = data;
        this.init();
    }

    public void init() {
        for (int i = 0; i < this.data.length; i++) {
            this.addRow(new Object[]{FileTableModel.millisToDate(((CompareFile)this.data[i]).createdAt), ((CompareFile)this.data[i]).path1, ((CompareFile)this.data[i]).path2});
        }
    }
}
