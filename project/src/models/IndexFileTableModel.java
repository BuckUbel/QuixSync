package models;

public class IndexFileTableModel extends FileTableModel{

    public static String[] indexHeader = {"Indiziert am", "Indizierter Pfad"};
    public static int[] indexHeaderWidth = {135, 365};

    public IndexFileTableModel(TypeFile[] data) {
        super(IndexFileTableModel.indexHeader, 0);
        this.header = IndexFileTableModel.indexHeader;
        this.headerWidth = IndexFileTableModel.indexHeaderWidth;
        this.data = data;
        this.init();
    }

    @Override
    public void init() {
        for (int i = 0; i < this.data.length; i++) {
            this.addRow(new Object[]{FileTableModel.millisToDate(((IndexFile)this.data[i]).createdAt), ((IndexFile)this.data[i]).path});
        }
    }
}
