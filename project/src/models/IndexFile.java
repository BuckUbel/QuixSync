package models;

import java.io.File;

public class IndexFile extends TypeFile{

    public String path;
    public long createdAt;

    public IndexFile(StorageElementList sel){
        this.setPropsWithList(sel);
    }

    public IndexFile(File f){
        this.setPropsWithList(f);
    }

    public StorageElementList getListFromFile(File f){
        return new StorageElementList().readJSON(f.getAbsolutePath());
    }

    public void setPropsWithList(File f){
        this.indexPath = f.getAbsolutePath();
        this.setPropsWithList(this.getListFromFile(f));
    }
    public void setPropsWithList(StorageElementList sel){
        this.path = sel.getDirPath();
        this.createdAt = sel.getListCreatedAt();
    }
}
