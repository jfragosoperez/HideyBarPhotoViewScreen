package com.hideybarphotoviewscreen.sample;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.hideybarphotoviewscreen.HideyBarPhotoViewIntent;
import com.hideybarphotoviewscreen.HideyBarPhotoViewScreen;
import com.hideybarphotoviewscreen.photoloader.PhotoLoader;
import com.hideybarphotoviewscreen.photoloader.PicassoPhotoLoader;

/**
 *
 */
public final class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViews();
    }

    private void findViews(){
        findViewById(R.id.startPhotoFromResourcesBtt).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startHideyBarWithStaticDrawable();
            }
        });

        findViewById(R.id.startPhotoFromUrlBtt).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startHideyBarWithPhotoFromUrl();
            }
        });

        findViewById(R.id.startPhotoFromUrlWithPlaceHolder).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startHideyBarWithPhotoFromUrlWithPlaceHolder();
            }
        });
    }

    private void startHideyBarWithStaticDrawable(){
        Intent hideyBarPhotoViewIntent = HideyBarPhotoViewIntent.newConfiguration()
                .setPhotoResourceId(R.drawable.grand_sunset)
                .timeToStartHideyMode(3000)
                .screenTitle("Grand Sunset")
                .create(this, HideyBarPhotoViewScreen.class);
        startActivity(hideyBarPhotoViewIntent);
    }

    private void startHideyBarWithPhotoFromUrl(){
        Intent hideyBarPhotoViewIntent = HideyBarPhotoViewIntent.newConfiguration()
                .setPhotoUrl("https://lh6.googleusercontent.com/-Zku6vhdOOMU/UMop6mAjrCI/AAAAAAAAMRw/FLEcVVoZ-BY/w1648-h1098-no/Delicate+Arch.jpg")
                .timeToStartHideyMode(2000)
                .screenTitle("Delicate Arch")
                .create(this, HideyBarPhotoViewScreen.class);
        startActivity(hideyBarPhotoViewIntent);
    }

    private void startHideyBarWithPhotoFromUrlWithPlaceHolder(){
        Intent hideyBarPhotoViewIntent = HideyBarPhotoViewIntent.newConfiguration()
                .setPhotoUrl("https://lh6.googleusercontent.com/-dJgpQyZK89k/UQOBedpoASI/AAAAAAAALuk/kWhI3-xIX1w/w1902-h1070-no/reunion.jpg",
                        new PicassoPhotoLoader().baseSetup()
                                .setPlaceHolderResId(R.drawable.ic_hidey_bar_photo_view_screen_placeholder)
                                .showProgressView(false))
                .timeToStartHideyMode(4000)
                .screenTitle("Reunion")
                .create(this, HideyBarPhotoViewScreen.class);
        startActivity(hideyBarPhotoViewIntent);
    }

}
