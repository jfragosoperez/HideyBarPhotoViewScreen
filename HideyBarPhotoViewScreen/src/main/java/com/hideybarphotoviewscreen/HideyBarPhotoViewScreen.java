package com.hideybarphotoviewscreen;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.ActionBarActivity;

import com.hideybarphotoviewscreen.utils.HideyBarPhotoViewScreenExtras;

/**
 *
 */
public class HideyBarPhotoViewScreen extends ActionBarActivity{

    private float timeToStartHideyBarMode;
    private String pictureUrl;
    private int pictureResId;
    private boolean showLoadingProgress;
    private String title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hidey_bar_photo_view);
        setUpParamsFromExtras();
        getSupportActionBar().setTitle(title);
    }

    private final void setUpParamsFromExtras(){
        Bundle extras = getIntent().getExtras();
        if(extras != null){
            title = extras.getString(HideyBarPhotoViewScreenExtras.SCREEN_TITLE);
            if(extras.containsKey(HideyBarPhotoViewScreenExtras.PICTURE_RES_ID)){
                pictureResId = extras.getInt(HideyBarPhotoViewScreenExtras.PICTURE_RES_ID);
            }else{
                pictureUrl = extras.getString(HideyBarPhotoViewScreenExtras.PICTURE_URL);
                showLoadingProgress = extras.getBoolean(HideyBarPhotoViewScreenExtras.SHOW_LOADING_PROGRESS);
            }
            timeToStartHideyBarMode = extras.getFloat(HideyBarPhotoViewScreenExtras.TIME_TO_START_HIDEY_BAR_MODE);
        }
    }
}
