package models;

import java.io.File;

public class CompareFile extends TypeFile{

    public String path1;
    public String path2;
    public long createdAt;

    public CompareFile(ProcessingElementList pel){
        this.setPropsWithList(pel);
    }

    public CompareFile(File f){
        this.setPropsWithList(f);
    }

    public ProcessingElementList getListFromFile(File f){
        return new ProcessingElementList().readJSON(f.getAbsolutePath());
    }

    public void setPropsWithList(File f){
        this.indexPath = f.getAbsolutePath();
        this.setPropsWithList(this.getListFromFile(f));
    }

    public void setPropsWithList(ProcessingElementList pel){
        this.path1 = pel.sourceDirPath;
        this.path2 = pel.targetDirPath;
        this.createdAt = pel.listCreatedAt;
    }

}
