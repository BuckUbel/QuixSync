package models;

import fileWriter.JSONCreator;

public class ElementList {

    long listCreatedAt;

    public void setListCreatedAt(long listCreatedAt) {
        this.listCreatedAt = listCreatedAt;
    }

    public void saveAsJSON(String path) {
        this.setListCreatedAt(System.currentTimeMillis());
        JSONCreator.save(path, this);
    }

    public ElementList readJSON(String path) {
        return (ElementList) JSONCreator.read(path, ElementList.class);
    }
}
