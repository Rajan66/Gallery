package com.example.gallery.utils;

import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gallery.R;

public class PicHolder extends RecyclerView.ViewHolder {

    public ImageView picture;
    public CardView imageCardView;

    public PicHolder(View itemView) {
        super(itemView);
        picture = itemView.findViewById(R.id.image);
        imageCardView= itemView.findViewById(R.id.image_card_view);
    }
}
