package models;

public class minimizedStorageElement {

    private String relativePath;
    private String dir;

    private int state;

    public minimizedStorageElement(StorageElement se){
        this.relativePath = se.getRelativePath();
        this.dir = se.getDir();
        this.state = se.getState();
    }

    public String getRelativePath() {
        return this.relativePath;
    }

    public String getAbsolutePath() {
        return this.dir + this.getRelativePath();
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }
}
