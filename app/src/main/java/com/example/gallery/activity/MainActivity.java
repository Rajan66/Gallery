package com.example.gallery.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.camera.core.Camera;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.ImageAnalysis;
import androidx.camera.core.ImageCapture;
import androidx.camera.core.ImageCaptureException;
import androidx.camera.core.Preview;
import androidx.camera.extensions.HdrImageCaptureExtender;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.camera.view.PreviewView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.LifecycleOwner;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gallery.utils.CalculateColumn;
import com.example.gallery.utils.ImageClickListener;
import com.example.gallery.utils.ImageFolder;
import com.example.gallery.utils.ImageFolderAdapter;
import com.example.gallery.utils.PicFacer;
import com.example.gallery.utils.PicHolder;
import com.example.gallery.R;
import com.google.common.util.concurrent.ListenableFuture;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity implements ImageClickListener {

    private static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 1;

    private ArrayList<ImageFolder> imageFolderList;
    private RecyclerView imageFolderRV;
    private TextView emptyView;
    private static final String TAG = "MainActivity";

    private Toolbar toolbar;
    private ImageView cameraButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.toolbar);
        cameraButton = findViewById(R.id.camera_icon);
        cameraButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CameraActivity.class);
                startActivity(intent);
            }
        });

        getPermit();


    }

    private void getPermit() {
        //Asking permission to read the storage

        if (ContextCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
            Toast.makeText(MainActivity.this, "Sorry... Please reopen the app", Toast.LENGTH_SHORT).show();

        }
        else
            setUpFolder();

    }

    private void setUpFolder() {
        emptyView = findViewById(R.id.empty);
        imageFolderRV = findViewById(R.id.rv_folder);
        imageFolderRV.setDrawingCacheEnabled(true);
        imageFolderRV.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);

        int numOfColumns = CalculateColumn.calculateNoOfColumns(MainActivity.this, 180);
        imageFolderRV.setLayoutManager(new GridLayoutManager(this, numOfColumns));

        imageFolderList = getImageFolderList();
        if (imageFolderList != null) {
            ImageFolderAdapter adapter = new ImageFolderAdapter(this, imageFolderList, this);
            imageFolderRV.setAdapter(adapter);
        } else {
            emptyView.setVisibility(View.VISIBLE); // if the folder list is empty display empty text.
        }
    }

    private ArrayList<ImageFolder> getImageFolderList() {
        ArrayList<ImageFolder> picFolders = new ArrayList<>();
        ArrayList<ImageFolder> tempFolders = new ArrayList<>();
        ArrayList<String> picPaths = new ArrayList<>();
        Uri allImagesUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;

       /* if you put internal_content_uri instead it will get the photos from the system like wallpapers...
        external_content_uri means the content from the internal storage and external storage
        basically system makes two partition in the internal storage, one to hold data that system
        requires and another for the user to use.*/

        String[] projection = {MediaStore.Images.ImageColumns.DATA, MediaStore.Images.Media.DISPLAY_NAME,
                MediaStore.Images.Media.BUCKET_DISPLAY_NAME, MediaStore.Images.Media.BUCKET_ID};
        Cursor cursor = this.getContentResolver().query(allImagesUri, projection, null, null, null);


        try {
            if (cursor != null) {
                cursor.moveToFirst();
            }
            do {
                ImageFolder folds = new ImageFolder();
                String name = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DISPLAY_NAME));
                String folder = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.BUCKET_DISPLAY_NAME));
                String dataPath = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA));

                String folderPaths = dataPath.substring(0, dataPath.lastIndexOf(folder + "/"));
                folderPaths = folderPaths + folder + "/";

                if (!picPaths.contains(folderPaths)) {
                    picPaths.add(folderPaths);
                    folds.setFolderPath(folderPaths);
                    folds.setFolderName(folder);
                    folds.setDisplayPicture(dataPath);//if the folder has only one picture this line helps to set it as first so as to avoid blank image in itemView
                    folds.addPics();
                    picFolders.add(folds);
                } else {
                    int j = 0;
                    for (int i = 0; i < picFolders.size(); i++) {
//                        if(picFolders.get(i) != picFolders.get(i+1)) {
//                            tempFolders.get(i) = picFolders.get(i);
//                        }
                        if (picFolders.get(i).getFolderPath().equals(folderPaths)) {
                            picFolders.get(i).setDisplayPicture(dataPath);
                            picFolders.get(i).addPics();
                        }
                    }
                }
            } while (cursor.moveToNext());
            cursor.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        for (int i = 0; i < picFolders.size(); i++) {
            Log.d("picture folders", picFolders.get(i).getFolderName() + " and path = " + picFolders.get(i).getFolderPath() + " " + picFolders.get(i).getNumOfPhotos());
        }

        return picFolders;
    }

    //when any image inside the folder is clicked, this works which is used in ImageDisplay class
    @Override
    public void onPicClicked(PicHolder holder, int position, ArrayList<PicFacer> pics) {

    }

    //when folder is clicked, this works
    @Override
    public void onPicClicked(String folderPath, String folderName) {
        Intent intent = new Intent(MainActivity.this, ImageDisplay.class);
        intent.putExtra("folderPath", folderPath);
        intent.putExtra("folderName", folderName);
//        Toast.makeText(this, folderPath + " " + folderName, Toast.LENGTH_SHORT).show();
        startActivity(intent);
    }


}