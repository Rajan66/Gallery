package com.example.gallery.utils;

import java.util.ArrayList;

public interface ImageClickListener {
    void onPicClicked(PicHolder holder, int position, ArrayList<PicFacer> pics);
    void onPicClicked(String folderPath, String folderName);
}
