package models;

import controller.FormattingController;

public class IndexFileTableModel extends FileTableModel{

    private static String[] indexHeader = {"Indiziert am", "Indizierter Pfad"};
    private static int[] indexHeaderWidth = {135, 365};

    public IndexFileTableModel(TypeFile[] data) {
        super(IndexFileTableModel.indexHeader, 0);
        this.header = IndexFileTableModel.indexHeader;
        this.headerWidth = IndexFileTableModel.indexHeaderWidth;
        this.data = data;
        this.init();
    }

    @Override
    public void init() {
        for (TypeFile aData : this.data) {
            this.addRow(new Object[]{FormattingController.millisToDate(((IndexFile) aData).createdAt), ((IndexFile) aData).path});
        }
    }
}
