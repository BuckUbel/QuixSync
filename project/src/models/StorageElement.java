package models;

import logger.Logger;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;

public class StorageElement {

    private Position position = new Position();

    private String relativePath;
    private transient String dir;
    private transient boolean shouldDelete;
    private long changedAt;
    private long createdAt;
    private double fileSize;

    private int state = 0;

    // use a Binary Map for the state
    private final static int COMPARED = 1;
    private final static int NAME = 2;
    private final static int CHANGED = 4;
    private final static int SIZE = 8;
    private final static int CHILDREN_COUNT = 16;
    private final static int NEW = 32;
    private final static int DIR = 64;


    public StorageElement() {
    }

    public StorageElement(File file, String dir) {
        this.dir = dir;
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
        this.setLastModfied();
        this.setFileSize();
        this.setIsDirectory(this.getAttributes().isDirectory());
    }

    minimizedStorageElement minify() {
        return new minimizedStorageElement(this);
    }

    int compareTo(StorageElement otherObject) {

        return Integer.compare(otherObject.getLft(), this.getLft());
    }

    String getDir() {
        return this.dir;
    }

    public int getState() {
        return this.state;
    }

    public int getChildrenCount() {
        return (position.rgt - position.lft - 1) / 2;
    }

    private Path getFileSystemPath() {

        Path path = null;
        try {
            path = Paths.get(this.getAbsolutePath());
        } catch (Exception err) {
            Logger.printErr(err.toString());
        }
        return path;
    }

    private BasicFileAttributes getAttributes() {

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

    public boolean isShouldDelete() {
        return shouldDelete;
    }

    public void setShouldDelete(boolean shouldDelete) {
        this.shouldDelete = shouldDelete;
    }

    public void setLft(int lft) {
        this.position.lft = lft;
    }

    public int getLft() {
        return this.position.lft;
    }

    public void setRgt(int rgt) {
        this.position.rgt = rgt;
    }

    public int getRgt() {
        return this.position.rgt;
    }

    public Position getPosition() {
        return this.position;
    }

    public long getCreatedAt() {
        return this.createdAt;
    }

    public long getChangedAt() {
        return this.changedAt;
    }

    public long getLastModified() {

        if (this.getCreatedAt() > this.getCreatedAt()) {
            return this.getCreatedAt();
        } else {
            return this.getChangedAt();
        }
    }

    private void setLastModfied() {
        this.setCreatedAtSpecific(this.getAttributes().creationTime().toMillis());
        this.setChangedAtSpecific(this.getAttributes().lastModifiedTime().toMillis());
    }

    public void setChangedAtSpecific(long changedAt) {
        this.changedAt = changedAt;
    }

    private void setCreatedAtSpecific(long createdAt) {
        this.createdAt = createdAt;
    }

    public double getFileSize() {
        return this.fileSize;
    }

    public String getFileSizeFormatted() {
        return FileSize.getFormattedString(this.fileSize);
    }

    private void setFileSize() {
        this.fileSize = this.getAttributes().size();
    }

    public boolean isDirectory() {
        this.setIsDirectory(this.getAttributes().isDirectory());
        return this.isDir();
    }

    public boolean isRegularFile() {
        this.setIsDirectory(this.getAttributes().isDirectory());
        return !this.isDir();
    }

    private void setIsDirectory(boolean isDirectory) {
        this.setIsDir(isDirectory);
    }


    // Compare functions

    private boolean isCompared() {
        return (this.state & StorageElement.COMPARED) != 0;
    }

    private boolean isDifferentName() {
        return (this.state & StorageElement.NAME) != 0;
    }

    private boolean isDifferentChanged() {
        return (this.state & StorageElement.CHANGED) != 0;
    }

    private boolean isDifferentSize() {
        return (this.state & StorageElement.SIZE) != 0;
    }

    private boolean isDifferentChildrenCount() {
        return (this.state & StorageElement.CHILDREN_COUNT) != 0;
    }

    private boolean isNewFile() {
        return (this.state & StorageElement.NEW) != 0;
    }

    private boolean isDir() {
        return (this.state & StorageElement.DIR) != 0;
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

    public void setDifferentChildrenCount(boolean isDifferentChildrenCount) {
        boolean currentState = this.isDifferentChildrenCount();
        if (isDifferentChildrenCount != currentState) {
            if (isDifferentChildrenCount) {
                this.state += StorageElement.CHILDREN_COUNT;
            } else {
                this.state -= StorageElement.CHILDREN_COUNT;
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

    private void setIsDir(boolean isDir) {
        boolean currentState = this.isDir();
        if (isDir != currentState) {
            if (isDir) {
                this.state += StorageElement.DIR;
            } else {
                this.state -= StorageElement.DIR;
            }
        }
    }
}
