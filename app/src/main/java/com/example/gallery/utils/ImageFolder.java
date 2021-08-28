package com.example.gallery.utils;

public class ImageFolder {

    private String folderPath;
    private int numOfPhotos = 0;
    private String folderName;
    private String displayPicture;
    private int folderId;

    public ImageFolder(){

    }

    public ImageFolder(String folderPath, int numOfPhotos, String folderName, String displayPicture) {
        this.folderPath = folderPath;
        this.numOfPhotos = numOfPhotos;
        this.folderName = folderName;
        this.displayPicture = displayPicture;
    }

    public String getFolderPath() {
        return folderPath;
    }

    public void setFolderPath(String folderPath) {
        this.folderPath = folderPath;
    }

    public int getNumOfPhotos() {
        return numOfPhotos;
    }

    public void setNumOfPhotos(int numOfPhotos) {
        this.numOfPhotos = numOfPhotos;
    }

    public String getFolderName() {
        return folderName;
    }

    public void setFolderName(String folderName) {
        this.folderName = folderName;
    }

    public String getDisplayPicture() {
        return displayPicture;
    }

    public void setDisplayPicture(String displayPicture) {
        this.displayPicture = displayPicture;
    }

    public int getFolderId() {
        return folderId;
    }

    public void setFolderId(int folderId) {
        this.folderId = folderId;
    }

    public void addPics(){
        this.numOfPhotos++;
    }
}
