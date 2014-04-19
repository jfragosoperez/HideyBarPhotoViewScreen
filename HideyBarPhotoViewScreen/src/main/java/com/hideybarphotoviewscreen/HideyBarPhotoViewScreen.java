package com.hideybarphotoviewscreen;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.hideybarphotoviewscreen.utils.HideyBarPhotoViewScreenExtras;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;

import uk.co.senab.photoview.PhotoViewAttacher;

import static uk.co.senab.photoview.PhotoViewAttacher.OnPhotoTapListener;

/**
 *
 */
public class HideyBarPhotoViewScreen extends Activity {

    ////////////////////////////////////////////
    ////////////////////////////////////////////
    ////// EXTRAS
    private long timeToStartHideyBarMode;
    private String pictureUrl, title;
    private int photoResId, photoPlaceHolderResId, photoErrorDrawableResId;
    private boolean showLoadingProgress;

    ////////////////////////////////////////////
    ////////////////////////////////////////////
    ////// VIEWS
    private View progressView;
    private ImageView picturePhotoView;

    private boolean loadPictureFromUrlMode, displayPlaceHolder, displayErrorDrawable;

    ////////////////////////////////////////////
    ////////////////////////////////////////////
    ////// PHOTOVIEW ATTACHER/SCREEN CONFIG

    private PhotoViewAttacher mPhotoViewAttacher;
    private boolean isNavigationHidden = false;
    private static final Handler hideActionBarHandler = new Handler();
    private volatile boolean userForcedToChangeOverlayMode = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hidey_bar_photo_view);
        setUpParamsFromExtras();
        setTitle();
        findViews();
        setUpPhotoView();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPhotoViewAttacher != null) {
            mPhotoViewAttacher.cleanup();
        }
    }

    private final void setUpParamsFromExtras(){
        Bundle extras = getIntent().getExtras();
        if(extras != null){
            title = extras.getString(HideyBarPhotoViewScreenExtras.SCREEN_TITLE);
            if(extras.containsKey(HideyBarPhotoViewScreenExtras.PICTURE_RES_ID)){
                photoResId = extras.getInt(HideyBarPhotoViewScreenExtras.PICTURE_RES_ID);
            }else{
                loadPictureFromUrlMode = true;
                pictureUrl = extras.getString(HideyBarPhotoViewScreenExtras.PICTURE_URL);
                showLoadingProgress = extras.getBoolean(HideyBarPhotoViewScreenExtras.SHOW_LOADING_PROGRESS);
                if(extras.containsKey(HideyBarPhotoViewScreenExtras.PicassoPhotoLoader.ERROR_DRAWABLE_RES_ID)){
                    photoErrorDrawableResId = extras.getInt(HideyBarPhotoViewScreenExtras.PicassoPhotoLoader.ERROR_DRAWABLE_RES_ID);
                    displayErrorDrawable = true;
                }
                if(extras.containsKey(HideyBarPhotoViewScreenExtras.PicassoPhotoLoader.PLACE_HOLDER_DRAWABLE_RES_ID)){
                    photoPlaceHolderResId = extras.getInt(HideyBarPhotoViewScreenExtras.PicassoPhotoLoader.PLACE_HOLDER_DRAWABLE_RES_ID);
                    displayPlaceHolder = true;
                }
            }
            timeToStartHideyBarMode = extras.getLong(HideyBarPhotoViewScreenExtras.TIME_TO_START_HIDEY_BAR_MODE);
        }
    }

    protected void setTitle() {
        if(!TextUtils.isEmpty(title)) {
            getActionBar().setTitle(title);
        }
    }

    private final void findViews(){
        progressView = findViewById(R.id.progressView);
        picturePhotoView = (ImageView) findViewById(R.id.picturePhotoView);
    }

    private final void setUpPhotoView(){
        mPhotoViewAttacher = new PhotoViewAttacher(picturePhotoView);
        mPhotoViewAttacher.setOnPhotoTapListener(new PhotoTapListener());
        if(isStaticPicture()){
            hideProgressView();
            setUpPhotoFromResource();
        }else{
            setUpPhotoFromUrl();
        }
    }

    private final boolean isStaticPicture(){
        return !loadPictureFromUrlMode;
    }

    private final void setUpPhotoFromResource(){
        picturePhotoView.setImageResource(photoResId);
        showPhotoView();
        runHideActionBarTimer();
    }

    private final void setUpPhotoFromUrl(){
        RequestCreator requestCreator = Picasso.with(this)
                .load(pictureUrl);

        if(!showLoadingProgress){
            hideProgressView();
        }
        if(displayErrorDrawableNeeded()){
            requestCreator.error(photoErrorDrawableResId);
        }
        if(displayPlaceHolderNeeded()){
            requestCreator.placeholder(photoPlaceHolderResId);
        }
        requestCreator
                .into(picturePhotoView, new Callback() {
                    @Override
                    public void onSuccess() {
                        hideProgressView();
                        showPhotoView();
                        runHideActionBarTimer();
                    }

                    @Override
                    public void onError() {
                        hideProgressView();
                    }
                });
    }

    private final void hideProgressView() {
        if (progressView != null) {
            progressView.setVisibility(View.GONE);
        }
    }

    private final boolean displayPlaceHolderNeeded(){
        return displayPlaceHolder;
    }

    private final boolean displayErrorDrawableNeeded(){
        return displayErrorDrawable;
    }

    private final void showPhotoView() {
        picturePhotoView.setVisibility(View.VISIBLE);
    }

    private final void runHideActionBarTimer() {
        hideActionBarHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (!userForcedToChangeOverlayMode && !isNavigationHidden) {
                    toggleShowOrHideHideyBarMode();
                }
            }
        }, timeToStartHideyBarMode);
    }

    private final class PhotoTapListener implements OnPhotoTapListener {

        @Override
        public void onPhotoTap(View view, float x, float y) {
            userForcedToChangeOverlayMode = true;
            toggleShowOrHideHideyBarMode();
        }
    }

    /**
     * Detects and toggles actionbarOverlay mode (also known as "hidey bar" mode).
     */
    public final void toggleShowOrHideHideyBarMode() {

        int uiOptions = getWindow().getDecorView().getSystemUiVisibility();
        int newUiOptions = uiOptions;

        if (!isNavigationHidden) {
            if(Build.VERSION.SDK_INT >= 14) {
                newUiOptions = View.SYSTEM_UI_FLAG_LOW_PROFILE;
            }
            getActionBar().hide();
            isNavigationHidden = true;
        } else {
            if(Build.VERSION.SDK_INT >= 14) {
                newUiOptions = View.SYSTEM_UI_FLAG_VISIBLE;
            }
            getActionBar().show();
            isNavigationHidden = false;
            userForcedToChangeOverlayMode = false;
            runHideActionBarTimer();
        }
        getWindow().getDecorView().setSystemUiVisibility(newUiOptions);
    }

}
