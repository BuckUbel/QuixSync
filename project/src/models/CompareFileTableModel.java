package models;

import controller.FormattingController;

public class CompareFileTableModel extends FileTableModel{

    private static String[] compareHeader = {"Verglichen am", "Quelle", "Ziel"};
    private static int[] compareHeaderWidth = {135, 350, 350};

    public CompareFileTableModel(TypeFile[] data) {
        super(CompareFileTableModel.compareHeader, 0);
        this.header = CompareFileTableModel.compareHeader;
        this.headerWidth = CompareFileTableModel.compareHeaderWidth;
        this.data = data;
        this.init();
    }

    public void init() {
        for (TypeFile aData : this.data) {
            this.addRow(new Object[]{FormattingController.millisToDate(((CompareFile) aData).createdAt), ((CompareFile) aData).path1, ((CompareFile) aData).path2});
        }
    }
}
