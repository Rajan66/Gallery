package com.example.gallery.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;


import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.gallery.R;

import java.util.ArrayList;

public class ImageFolderAdapter extends RecyclerView.Adapter<ImageFolderAdapter.ViewHolder> {

    private Context context;
    private ArrayList<ImageFolder> imageFolder;
    private ImageClickListener clickListener;



    public ImageFolderAdapter(Context context,ArrayList<ImageFolder> imageFolder, ImageClickListener clickListener) {
        this.context = context;
        this.imageFolder = imageFolder;
        this.clickListener = clickListener;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.folder_items,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ImageFolderAdapter.ViewHolder holder,  int position) {
        final ImageFolder folder = imageFolder.get(position);

        holder.numOfPhotos.setText("(" + imageFolder.get(position).getNumOfPhotos() + ")");
        holder.folderName.setText(imageFolder.get(position).getFolderName());

        Glide.with(context)
                .load(imageFolder.get(position).getDisplayPicture())
                .apply(new RequestOptions().centerCrop())
                .into(holder.folderImage);
        //centercrop is essential, image will be centered and fit instead of showing its actual size.

        holder.folderCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               clickListener.onPicClicked(folder.getFolderPath(),folder.getFolderName());
            }
        });
    }

    @Override
    public int getItemCount() {
        return imageFolder.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{

         private ImageView folderImage;
         private TextView numOfPhotos, folderName;
         private CardView folderCardView;

        public ViewHolder(View itemView) {
            super(itemView);

            folderImage = itemView.findViewById(R.id.folder_image);
            numOfPhotos = itemView.findViewById(R.id.folder_num_of_photos);
            folderName = itemView.findViewById(R.id.folder_name);
            folderCardView = itemView.findViewById(R.id.folder_card_view);
        }
    }
}
