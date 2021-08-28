package com.example.gallery.utils;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.gallery.R;

import java.util.ArrayList;

public class RVPagerImageIndicatorAdapter extends RecyclerView.Adapter<RVPagerImageIndicatorAdapter.IndicatorHolder>{

    private Context context;
    private ArrayList<PicFacer> imageList;
    private ImageIndicatorClickListener imageClickListener;

    public RVPagerImageIndicatorAdapter(Context context, ArrayList<PicFacer> imageList, ImageIndicatorClickListener imageClickListener) {
        this.context = context;
        this.imageList = imageList;
        this.imageClickListener = imageClickListener;
    }

    @NonNull
    @Override
    public IndicatorHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.indicator_holder, parent, false);
        return new IndicatorHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull IndicatorHolder holder, int position) {

        final PicFacer pic = imageList.get(position);

        holder.positionController.setBackgroundColor(pic.isSelected() ? Color.parseColor("#0000000") : Color.parseColor("#8c000000"));

        Glide.with(context)
                .load(pic.getImagePath())
                .apply(new RequestOptions().centerCrop())
                .into(holder.image);

        holder.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pic.setSelected(true);
                notifyDataSetChanged();
                imageClickListener.onImageIndicatorClicked(holder.getAdapterPosition());
            }
        });
    }

    @Override
    public int getItemCount() {
        return imageList.size();
    }

    public class IndicatorHolder extends RecyclerView.ViewHolder {

        private ImageView image;
        private CardView cardView;
        View positionController;

        public IndicatorHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.indicator_image);
            cardView = itemView.findViewById(R.id.indicator_card_view);
            positionController = itemView.findViewById(R.id.active_image);
        }
    }
}
