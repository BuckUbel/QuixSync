package models;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;

public class StorageElement {

    private String path;
    private long created_at;
    private long changed_at;
    private FileSize fileSize;
    private boolean isCompared = false;
    private boolean isDirectory;
    private boolean isRegularFile;

    public StorageElement(String path){
        this.path = path;
        this.setCreated_at();
        this.setChanged_at();
        this.fileSize = new FileSize(this.getAttributes().size());
        this.setIsCompared(false);
        this.setIsDirectory(this.getAttributes().isDirectory());
    }

    private Path getFileSystemPath() {

        Path path = null;
        try {
            path = Paths.get(this.getPath());
        } catch (Exception err) {
            System.err.println(err);
        }
        return path;
    }

    public BasicFileAttributes getAttributes() {

        Path systemPath = this.getFileSystemPath();
        BasicFileAttributes attr = null;
        try {
            attr = Files.readAttributes(systemPath, BasicFileAttributes.class);
        } catch (Exception err) {
            System.err.println(err);
        }
        return attr;
    }

    public String getPath() {
        return this.path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public long getChanged_at() {

        Long changed_at = this.changed_at;

        if (changed_at == 0) {
            this.setChanged_at();
        }
        return this.changed_at;
    }

    public void setChanged_at() {
        this.changed_at = this.getAttributes().lastModifiedTime().toMillis();
    }

    public long getCreated_at() {
        Long created_at = this.created_at;

        if (created_at == 0) {
            this.setCreated_at();
        }
        return this.created_at;
    }

    public void setCreated_at() {
        this.created_at = this.getAttributes().creationTime().toMillis();
    }

    public String getFileSize() {
        double fileSize = this.fileSize.getBytes();

        if (fileSize == 0) {
            this.setFileSize();
        }
        return this.fileSize.getFormattedString();
    }

    public FileSize getFileSizeObject(){
        return this.fileSize;
    }

    public void setFileSize() {
        this.fileSize.set(this.getAttributes().size());
    }

    public boolean isCompared() {
        return isCompared;
    }
    public void setIsCompared(boolean isCompared){
        this.isCompared = isCompared;
    }

    public boolean isDirectory() {
        if (!(this.isDirectory && this.isRegularFile)) {
            this.setIsDirectory(this.getAttributes().isDirectory());
        }
        return this.isDirectory;
    }

    public boolean isRegularFile() {
        if (!(this.isDirectory && this.isRegularFile)) {
            this.setIsDirectory(this.getAttributes().isDirectory());
        }
        return this.isRegularFile;
    }

    public void setIsDirectory(boolean isDirectory) {
        this.isDirectory = isDirectory;
        this.isRegularFile = !isDirectory;

    }
}
