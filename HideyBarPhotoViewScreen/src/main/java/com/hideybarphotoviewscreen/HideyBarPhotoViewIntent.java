package com.hideybarphotoviewscreen;

import android.content.Intent;

import com.hideybarphotoviewscreen.utils.HideyBarPhotoViewScreenExtras;

/**
 *
 */
public class HideyBarPhotoViewIntent {

    private static HideyBarPhotoViewIntent sHideyBarPhotoViewIntent;
    private HideyBarPhotoViewScreenSetUp hideyBarPhotoViewScreenSetUp;

    private HideyBarPhotoViewIntent() {
        hideyBarPhotoViewScreenSetUp = new HideyBarPhotoViewScreenSetUp();
    }

    public static HideyBarPhotoViewIntent newConfiguration() {
        if (sHideyBarPhotoViewIntent == null) {
            sHideyBarPhotoViewIntent = new HideyBarPhotoViewIntent();
        }
        return sHideyBarPhotoViewIntent;
    }

    public HideyBarPhotoViewScreenSetUp setTimeToStartHideyMode(float timeToStartHideyMode){
        hideyBarPhotoViewScreenSetUp.timeToHideActionBar = timeToStartHideyMode;
        return hideyBarPhotoViewScreenSetUp;
    }

    public class HideyBarPhotoViewScreenSetUp{
        private static final int START_HIDEY_MODE_DEFAULT_TIME = 2000;

        private PhotoSourceSetup photoSourceSetup;
        private float timeToHideActionBar = START_HIDEY_MODE_DEFAULT_TIME;

        public PhotoDrawableResourceSetup setPhotoResourceId(final int resourceId) {
            hideyBarPhotoViewScreenSetUp.photoSourceSetup = new PhotoDrawableResourceSetup(resourceId);
            return (PhotoDrawableResourceSetup) hideyBarPhotoViewScreenSetUp.photoSourceSetup;
        }

        public PhotoUrlSetup setPhotoUrl(final String photoUrl) {
            hideyBarPhotoViewScreenSetUp.photoSourceSetup = new PhotoUrlSetup(photoUrl);
            return (PhotoUrlSetup) hideyBarPhotoViewScreenSetUp.photoSourceSetup;
        }
    }


    ///////////////////////////////////////////////////
    ///////////////////////////////////////////////////
    ////// PHOTO SOURCE
    private static final int PHOTO_SOURCE_TYPE_DRAWABLE_RESOURCE_ID = 0;
    private static final int PHOTO_SOURCE_TYPE_URL = 1;

    private abstract class PhotoSourceSetup {
        private final int sourceType;

        protected PhotoSourceSetup(int sourceType) {
            this.sourceType = sourceType;
        }

        private boolean isUrlSetup() {
            return sourceType == PHOTO_SOURCE_TYPE_URL;
        }

        private boolean isDrawableResIdSetup() {
            return sourceType == PHOTO_SOURCE_TYPE_DRAWABLE_RESOURCE_ID;
        }

        private Intent createIntent() {
            Intent hideyBarPhotoViewIntent = new Intent();
            if (isDrawableResIdSetup()) {
                hideyBarPhotoViewIntent.putExtra(HideyBarPhotoViewScreenExtras.PICTURE_RES_ID,
                        ((PhotoDrawableResourceSetup) hideyBarPhotoViewScreenSetUp.photoSourceSetup)
                                .drawableResID);
            }
            //case we've an url
            else {
                final PhotoUrlSetup photoUrlSetup = ((PhotoUrlSetup) hideyBarPhotoViewScreenSetUp.photoSourceSetup);
                hideyBarPhotoViewIntent.putExtra(HideyBarPhotoViewScreenExtras.PICTURE_URL,
                        photoUrlSetup.url);
                hideyBarPhotoViewIntent.putExtra(HideyBarPhotoViewScreenExtras.SHOW_LOADING_PROGRESS,
                        photoUrlSetup.willDisplayProgressView);
            }
            return hideyBarPhotoViewIntent;
        }
    }

    public class PhotoUrlSetup extends PhotoSourceSetup {

        private final String url;
        private boolean willDisplayProgressView = false;

        protected PhotoUrlSetup(final String url) {
            super(PHOTO_SOURCE_TYPE_URL);
            this.url = url;
        }

        public PhotoUrlSetup showProgress() {
            this.willDisplayProgressView = true;
            return this;
        }

    }

    public class PhotoDrawableResourceSetup extends PhotoSourceSetup {

        private final int drawableResID;

        protected PhotoDrawableResourceSetup(final int drawableResID) {
            super(PHOTO_SOURCE_TYPE_DRAWABLE_RESOURCE_ID);
            this.drawableResID = drawableResID;
        }

    }

}
