package models;

import controller.LanguageController;

public class minimizedStorageElement {

    private String relativePath;
    private String dir;

    private int state;

    public minimizedStorageElement(StorageElement se) {
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

    @Override
    public String toString() {
        String returnString = "";
        boolean extraInfo = false;

        if (this.isNewFile()) {
            returnString += LanguageController.getLang().NEW_FILE;
            extraInfo = true;
        }
        if (this.isDifferentName() && !extraInfo) {
            returnString += LanguageController.getLang().DIFFERENT_NAME;
            extraInfo = true;
        }
        if (this.isDifferentSize() && !extraInfo) {
            returnString += LanguageController.getLang().DIFFERENT_SIZE;
            extraInfo = true;
        }
        if (this.isDifferentChildrenCount() && !extraInfo) {
            returnString += LanguageController.getLang().DIFFERENT_CHILDREN_COUNT;
            extraInfo = true;
        }
        if (this.isDifferentChanged() && !extraInfo) {
            returnString += LanguageController.getLang().DIFFERENT_CHANGEDATA;
            extraInfo = true;
        }

        returnString += " : \t\t";
        returnString += this.getRelativePath();

        return returnString;
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

}
