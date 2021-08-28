package com.example.gallery.utils;


import static androidx.core.view.ViewCompat.setTransitionName;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.gallery.R;

import java.util.ArrayList;

public class ImageDisplayAdapter extends RecyclerView.Adapter<PicHolder> {

    private ArrayList<PicFacer> imageList;
    private Context context;
    private ImageClickListener clickListener;

    public ImageDisplayAdapter(ArrayList<PicFacer> imageList, Context context, ImageClickListener clickListener) {
        this.imageList = imageList;
        this.context = context;
        this.clickListener = clickListener;
    }

    @Override
    public PicHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.pic_holder_item,parent,false);
        return new PicHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PicHolder holder, int position) {

        final PicFacer pic = imageList.get(position);

        Glide.with(context)
                .load(imageList.get(position).getImagePath())
                .apply(new RequestOptions().centerCrop())
                .into(holder.picture);

//        setTransitionName(holder.picture,String.valueOf(position) + "_image");

        holder.imageCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickListener.onPicClicked(holder,holder.getAdapterPosition(),imageList);
            }
        });
    }

    @Override
    public int getItemCount() {
        return imageList.size();
    }

}
