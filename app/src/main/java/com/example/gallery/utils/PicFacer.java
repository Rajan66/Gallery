package com.example.gallery.utils;

public class PicFacer {

    private String imageName;
    private String imagePath;
    private String imageSize;
    private String imageUri;
    private boolean isSelected = false;

    public PicFacer(){

    }

    public PicFacer(String imageName, String imagePath, String imageSize, String imageUri) {
        this.imageName = imageName;
        this.imagePath = imagePath;
        this.imageSize = imageSize;
        this.imageUri = imageUri;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getImageSize() {
        return imageSize;
    }

    public void setImageSize(String imageSize) {
        this.imageSize = imageSize;
    }

    public String getImageUri() {
        return imageUri;
    }

    public void setImageUri(String imageUri) {
        this.imageUri = imageUri;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
