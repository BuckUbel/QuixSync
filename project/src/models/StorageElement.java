package models;

import logger.Logger;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;

public class StorageElement {

    // TODO: rebuild for nested sets, without these, there are no possibility for performance boost
    // @Quentin Weber

    private int lft;
    private int rgt;

    private String relativePath;
    private String dir;
    private long changed_at;
    private FileSize fileSize;
    private boolean isDirectory;

    private int state = 0;

    // use a Binary Map for the state
    private final static int COMPARED = 1;
    private final static int NAME = 2;
    private final static int CHANGED = 4;
    private final static int SIZE = 8;
    private final static int NEW = 16;

    public StorageElement(){}

    public StorageElement(File file){
        this.dir = file.getParent();
        this.standardInit(file.getAbsolutePath());
    }

    public StorageElement(String absolutePath, String dir) {
        this.dir = dir;
        this.standardInit(absolutePath);
    }

    public StorageElement(String path) {
        this.standardInit(path);
    }

    private void standardInit(String path) {
        this.setPath(path);
        this.setChanged_at();
        this.fileSize = new FileSize(this.getAttributes().size());
        this.setIsDirectory(this.getAttributes().isDirectory());
    }

    private Path getFileSystemPath() {

        Path path = null;
        try {
            path = Paths.get(this.getAbsolutePath());
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
            Logger.printErr(err);
        }
        return attr;
    }

    public String getRelativePath() {
        return this.relativePath;
    }

    public String getAbsolutePath() {

        return this.dir + this.getRelativePath();
    }

    public void setPath(String absolutePath) {

        if (dir != null) {
            int startNumberFromDirPosition = absolutePath.indexOf(dir);
            this.relativePath = absolutePath.substring(startNumberFromDirPosition + dir.length());

            if (this.relativePath.equals("")) {
                this.relativePath = "\\";
            }
        } else {
            this.relativePath = absolutePath;
        }

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

//    public long getCreated_at() {
//        Long created_at = this.created_at;
//
//        if (created_at == 0) {
//            this.setCreated_at();
//        }
//        return this.created_at;
//    }
//
//    public void setCreated_at() {
//        this.created_at = this.getAttributes().creationTime().toMillis();
//    }

    public String getFileSize() {
        double fileSize = this.fileSize.getBytes();

        if (fileSize == 0) {
            this.setFileSize();
        }
        return this.fileSize.getFormattedString();
    }

    public FileSize getFileSizeObject() {
        return this.fileSize;
    }

    public void setFileSize() {
        this.fileSize.set(this.getAttributes().size());
    }


    public boolean isDirectory() {
        this.setIsDirectory(this.getAttributes().isDirectory());
        return this.isDirectory;
    }

    public boolean isRegularFile() {
        this.setIsDirectory(this.getAttributes().isDirectory());
        return !this.isDirectory;
    }

    public void setIsDirectory(boolean isDirectory) {
        this.isDirectory = isDirectory;
    }


    // Compare functions

    public boolean isCompared() {
        return (this.state & StorageElement.COMPARED) != 0;
    }

    public boolean isDifferentName() {
        return (this.state & StorageElement.NAME) != 0;
    }

    public boolean isDifferentChanged() {
        return (this.state & StorageElement.CHANGED) != 0;
    }

    public boolean isDifferentSize() {
        return (this.state & StorageElement.SIZE) != 0;
    }
    public boolean isNewFile() {
        return (this.state & StorageElement.NEW) != 0;
    }

    public void setIsCompared(boolean isCompared) {
        boolean currentState = this.isCompared();
        if (isCompared != currentState) {
            if (isCompared) {
                this.state += StorageElement.COMPARED;
            } else {
                this.state -= StorageElement.COMPARED;
            }
        }
    }

    public void setDifferentName(boolean isDifferentName) {
        boolean currentState = this.isDifferentName();
        if (isDifferentName != currentState) {
            if (isDifferentName) {
                this.state += StorageElement.NAME;
            } else {
                this.state -= StorageElement.NAME;
            }
        }
    }

    public void setDifferentChanged(boolean isDifferentChanged) {
        boolean currentState = this.isDifferentChanged();
        if (isDifferentChanged != currentState) {
            if (isDifferentChanged) {
                this.state += StorageElement.CHANGED;
            } else {
                this.state -= StorageElement.CHANGED;
            }
        }
    }

    public void setDifferentSize(boolean isDifferentSize) {
        boolean currentState = this.isDifferentSize();
        if (isDifferentSize != currentState) {
            if (isDifferentSize) {
                this.state += StorageElement.SIZE;
            } else {
                this.state -= StorageElement.SIZE;
            }
        }
    }
    public void setIsNewFile(boolean isNew) {
        boolean currentState = this.isNewFile();
        if (isNew != currentState) {
            if (isNew) {
                this.state += StorageElement.NEW;
            } else {
                this.state -= StorageElement.NEW;
            }
        }
    }
}
