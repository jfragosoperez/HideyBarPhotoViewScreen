package com.hideybarphotoviewscreen.sample;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;

import com.hideybarphotoviewscreen.HideyBarPhotoViewIntent;
import com.hideybarphotoviewscreen.HideyBarPhotoViewScreen;

/**
 *
 */
public final class MainActivity extends ActionBarActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViews();
    }

    private void findViews(){
        findViewById(R.id.startDefaultBtt).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startHideyBarWithStaticDrawable();
            }
        });
    }

    private void startHideyBarWithStaticDrawable(){
        Intent hideyBarPhotoViewIntent = HideyBarPhotoViewIntent.newConfiguration()
                .setPhotoResourceId(R.drawable.grand_sunset)
                .setTimeToStartHideyMode(3000)
                .setScreenTitle("Hello world")
                .create(this, HideyBarPhotoViewScreen.class);
        startActivity(hideyBarPhotoViewIntent);
    }

}
