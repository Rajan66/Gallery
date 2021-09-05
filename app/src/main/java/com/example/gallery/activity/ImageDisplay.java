package com.example.gallery.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.transition.Fade;
import android.view.View;
import android.widget.Toast;

import com.example.gallery.utils.FullscreenFragment2;
import com.example.gallery.utils.CalculateColumn;
import com.example.gallery.utils.ImageClickListener;
import com.example.gallery.utils.ImageDisplayAdapter;
import com.example.gallery.utils.PicFacer;
import com.example.gallery.utils.PicHolder;
import com.example.gallery.R;

import java.util.ArrayList;

public class ImageDisplay extends AppCompatActivity implements ImageClickListener {

    private RecyclerView rv_imageList;
    private ArrayList<PicFacer> imageList;
    private boolean statusBarHidden = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_display);

        String folderPath = getIntent().getStringExtra("folderPath");
        String folderName = getIntent().getStringExtra("folderName");

        getSupportActionBar().setTitle(folderName);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

//        showStatusBar(); //TODO status bar disappears in the activity after you go to fragment

        rv_imageList = findViewById(R.id.rv_image_display_list);

        int noOfColumns = CalculateColumn.calculateNoOfColumns(this, 140);
        rv_imageList.setLayoutManager(new GridLayoutManager(this, noOfColumns));

        imageList = getImageList(folderPath);

        if (imageList != null) {
            rv_imageList.setAdapter(new ImageDisplayAdapter(imageList, ImageDisplay.this, this));
//            Toast.makeText(ImageDisplay.this, imageList.size(), Toast.LENGTH_SHORT).show();
        }


    }

    public void showStatusBar(){
        if(statusBarHidden){
            View decorView = getWindow().getDecorView(); // Hide the status bar.
            int uiOptions =  View.SYSTEM_UI_FLAG_VISIBLE;
            decorView.setSystemUiVisibility(uiOptions);
            getSupportActionBar().show();
            statusBarHidden = false;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    public ArrayList<PicFacer> getImageList(String path) {
        ArrayList<PicFacer> displayImageList = new ArrayList<>();
        Uri allImagesUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        String[] projection = {MediaStore.Images.ImageColumns.DATA, MediaStore.Images.Media.DISPLAY_NAME,
                MediaStore.Images.Media.SIZE};
        Cursor cursor = ImageDisplay.this.getContentResolver().query(allImagesUri, projection, MediaStore.Images.Media.DATA + " like ? ", new String[]{"%" + path + "%"}, null);
        try {
            cursor.moveToFirst();
            do {
                PicFacer pic = new PicFacer();

                pic.setImageName(cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DISPLAY_NAME)));
                pic.setImagePath(cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)));
                pic.setImageSize(cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.SIZE)));

                displayImageList.add(pic);
            } while (cursor.moveToNext());
            cursor.close();

            ArrayList<PicFacer> reSelection = new ArrayList<>();
            for (int i = displayImageList.size() - 1; i > -1; i--) {
                reSelection.add(displayImageList.get(i));
            }
            displayImageList = reSelection;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return displayImageList;
    }

    @Override
    public void onPicClicked(PicHolder holder, int position, ArrayList<PicFacer> pics) {
        Bundle bundle = new Bundle();
        bundle.putString("imagePath",pics.get(position).getImagePath());
//        bundle.putString("imageName",pics.get(position).getImageName());
//        bundle.putString("imageSize", pics.get(position).getImageSize());



        FullscreenFragment2 fullscreenFragment2 = new FullscreenFragment2();
        fullscreenFragment2.setArguments(bundle);

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction().addToBackStack(null);
        fragmentTransaction.add(R.id.display_container, fullscreenFragment2).commit();
        statusBarHidden = true;




//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
////            pictureBrowser.setEnterTransition(new Slide());
////            pictureBrowser.setExitTransition(new Slide());
////            uncomment this to use slide transition and comment the two lines below
//            pictureBrowser.setEnterTransition(new Fade());
//            pictureBrowser.setExitTransition(new Fade());
//        }
//
//        getSupportFragmentManager()
//                .beginTransaction()
//                .add(R.id.display_container,pictureBrowser)
//                .addToBackStack(null)
//                .commit();

    }

    @Override
    public void onPicClicked(String folderPath, String folderName) {

    }
}