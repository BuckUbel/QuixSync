package models;

import java.io.File;

public class CompareFile extends TypeFile {

    String path1;
    String path2;
    public long createdAt;

    public CompareFile(File f) {
        this.setPropsWithList(f);
    }

    private ProcessingElementList getListFromFile(File f) {
        return new ProcessingElementList().readJSON(f.getAbsolutePath());
    }

    private void setPropsWithList(File f) {
        this.indexPath = f.getAbsolutePath();
        this.setPropsWithList(this.getListFromFile(f));
    }

    private void setPropsWithList(ProcessingElementList pel) {
        this.path1 = pel.sourceDirPath;
        this.path2 = pel.targetDirPath;
        this.createdAt = pel.listCreatedAt;
    }

}
