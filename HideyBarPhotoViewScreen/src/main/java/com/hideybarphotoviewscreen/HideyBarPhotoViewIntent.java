package com.hideybarphotoviewscreen;

import android.content.Context;
import android.content.Intent;

import com.hideybarphotoviewscreen.photoloader.PhotoLoader;
import com.hideybarphotoviewscreen.photoloader.PicassoPhotoLoader;
import com.hideybarphotoviewscreen.utils.HideyBarPhotoViewScreenExtras;
import com.hideybarphotoviewscreen.utils.PictureUtils;

/**
 * This class enables to create an Intent with basic setup to start {@link com.hideybarphotoviewscreen.HideyBarPhotoViewScreen} activity.
 * This is not a must, but will be really useful for most of the cases when we want a simple base setup.
 */
public class HideyBarPhotoViewIntent {

    private static HideyBarPhotoViewIntent sHideyBarPhotoViewIntent;
    private HideyBarPhotoViewScreenSetUp hideyBarPhotoViewScreenSetUp;

    private HideyBarPhotoViewIntent() {
        hideyBarPhotoViewScreenSetUp = new HideyBarPhotoViewScreenSetUp();
    }

    private static HideyBarPhotoViewIntent getInstance() {
        if (sHideyBarPhotoViewIntent == null) {
            sHideyBarPhotoViewIntent = new HideyBarPhotoViewIntent();
        }
        return sHideyBarPhotoViewIntent;
    }

    /**
     * Creates a new setup so we can configure the intent with our requirements.
     *
     * @return a new set up for the intent.
     */
    public static HideyBarPhotoViewScreenSetUp newConfiguration() {
        return getInstance().hideyBarPhotoViewScreenSetUp;
    }

    /**
     * Class which handles the setup of the intent to start {@link com.hideybarphotoviewscreen.HideyBarPhotoViewScreen}.
     * This class is based on a builder, so make your setup calling the methods you want and finally call {@link #create(android.content.Context, Class)}
     * in order to obtain the intent created.
     */
    public class HideyBarPhotoViewScreenSetUp {

        private PhotoSourceSetup photoSourceSetup;
        private HideyBarModeTimeSetup hideyBarModeTimeSetup = new HideyBarModeTimeSetup();
        private String screenTitle = "";
        private Intent hideyBarPhotoViewIntent;

        /**
         * Sets the resource id of the drawable that will be displayed in the photo view of the screen.
         *
         * @param resourceId is the resource id of the drawable that will be displayed in the photo view screen.
         * @return the setup, so we can go on with the setup and finally create the intent using {@link #create(android.content.Context, Class)}.
         */
        public HideyBarPhotoViewScreenSetUp setPhotoResourceId(final int resourceId) {
            hideyBarPhotoViewScreenSetUp.photoSourceSetup = new PhotoDrawableResourceSetup(resourceId);
            return this;
        }

        /**
         * Sets the url of the photo that will be displayed in the {@link com.hideybarphotoviewscreen.HideyBarPhotoViewScreen}
         *
         * @param photoUrl    is the url of the photo that we want to display in the {@link com.hideybarphotoviewscreen.HideyBarPhotoViewScreen}
         * @param photoLoader is the type of loader that will be used to load the photo.
         * @return the photo url setup so we can set the preferred photo loader.
         */
        public HideyBarPhotoViewScreenSetUp setPhotoUrl(final String photoUrl, final PhotoLoader photoLoader) {
            PhotoUrlSetup photoUrlSetup = new PhotoUrlSetup(photoUrl);
            photoUrlSetup.setPhotoLoader(photoLoader);
            hideyBarPhotoViewScreenSetUp.photoSourceSetup = photoUrlSetup;
            return this;
        }

        /**
         * Sets the url of the photo that will be displayed in the {@link com.hideybarphotoviewscreen.HideyBarPhotoViewScreen}.
         * This uses by default Picasso library (by Square) to load the photo view.
         *
         * @param photoUrl is the url of the photo that we want to display in the {@link com.hideybarphotoviewscreen.HideyBarPhotoViewScreen}
         * @return the photo url setup so we can set the preferred photo loader.
         */
        public HideyBarPhotoViewScreenSetUp setPhotoUrl(final String photoUrl) {
            PhotoUrlSetup photoUrlSetup = new PhotoUrlSetup(photoUrl);
            photoUrlSetup.setPhotoLoader(new PicassoPhotoLoader().baseSetup());
            hideyBarPhotoViewScreenSetUp.photoSourceSetup = photoUrlSetup;
            return this;
        }

        /**
         * Sets the time to start "HideyMode" of the ActionBar. This time is used both for hiding the ActionBar
         * when the user enters the screen (after our period of time) or then
         * to hide it once after the user has touched the picture of the screen and due to this fact the ActionBar was visible.
         *
         * @param timeToStartHideyMode is the time to start "HideyMode" of the ActionBar.
         * @return the setup, so we can go on with the setup and finally create the intent using {@link #create(android.content.Context, Class)}.
         */
        public HideyBarPhotoViewScreenSetUp timeToStartHideyMode(long timeToStartHideyMode) {
            hideyBarModeTimeSetup.setNewTime(timeToStartHideyMode);
            return this;
        }

        /**
         * Sets the title that will appear as the title in the ActionBar of the screen.
         *
         * @param title is the title that will appear as the title in the ActionBar of the screen.
         * @return the setup, so we can go on with the setup and finally create the intent using {@link #create(android.content.Context, Class)}.
         */
        public HideyBarPhotoViewScreenSetUp screenTitle(final String title) {
            this.screenTitle = title;
            return this;
        }

        /**
         * Creates an Intent (configured with the setup) ready to be started with {@link android.content.Context#startActivity(android.content.Intent)}
         *
         * @param context is the context from which we will start this intent.
         * @param hideyBarPhotoViewScreenClass is the name of the class that inherits from the {@link com.hideybarphotoviewscreen.HideyBarPhotoViewScreen} activity.
         *
         * @return an Intent (configured with the setup) ready to be started with {@link android.content.Context#startActivity(android.content.Intent)}
         */
        public Intent create(final Context context, Class<? extends HideyBarPhotoViewScreen> hideyBarPhotoViewScreenClass) {
            if (photoSourceSetup == null) {
                throw new IllegalArgumentException("The is no photo to load. You must set the photo source, using methods setPhotoUrl() or setPhotoResourceId()");
            }
            hideyBarPhotoViewIntent = new Intent(context, hideyBarPhotoViewScreenClass);
            addPhotoSourceSetUp();
            addHideyBarTimeSetUp();
            addTitleSetUp();
            return hideyBarPhotoViewIntent;
        }

        private void addPhotoSourceSetUp() {
            if (photoSourceSetup.isDrawableResIdSetup()) {
                final PhotoDrawableResourceSetup photoDrawableResourceSetup = (PhotoDrawableResourceSetup) hideyBarPhotoViewScreenSetUp.photoSourceSetup;
                hideyBarPhotoViewIntent.putExtra(HideyBarPhotoViewScreenExtras.PICTURE_RES_ID,
                        photoDrawableResourceSetup.drawableResID);
            }
            //case we've an url
            else {
                final PhotoUrlSetup photoUrlSetup = (PhotoUrlSetup) hideyBarPhotoViewScreenSetUp.photoSourceSetup;
                hideyBarPhotoViewIntent.putExtra(HideyBarPhotoViewScreenExtras.PICTURE_URL,
                        photoUrlSetup.url);
                if (photoUrlSetup.hasPicassoLoader()) {
                    PicassoPhotoLoader picassoPhotoLoader = (PicassoPhotoLoader) photoUrlSetup.photoLoader;
                    hideyBarPhotoViewIntent.putExtra(HideyBarPhotoViewScreenExtras.SHOW_LOADING_PROGRESS,
                            picassoPhotoLoader.needsToDisplayProgressView());
                    if (!PictureUtils.isUnusedPictureResID(picassoPhotoLoader.getErrorDrawableResId())) {
                        hideyBarPhotoViewIntent.putExtra(HideyBarPhotoViewScreenExtras.PicassoPhotoLoader.ERROR_DRAWABLE_RES_ID,
                                picassoPhotoLoader.getErrorDrawableResId());
                    }
                    if (!PictureUtils.isUnusedPictureResID(picassoPhotoLoader.getPlaceHolderResId())) {
                        hideyBarPhotoViewIntent.putExtra(HideyBarPhotoViewScreenExtras.PicassoPhotoLoader.PLACE_HOLDER_DRAWABLE_RES_ID,
                                picassoPhotoLoader.getPlaceHolderResId());
                    }
                }
            }
        }

        private void addHideyBarTimeSetUp() {
            hideyBarPhotoViewIntent.putExtra(HideyBarPhotoViewScreenExtras.TIME_TO_START_HIDEY_BAR_MODE,
                    hideyBarModeTimeSetup.timeToStartHideyBar);
        }

        private void addTitleSetUp() {
            hideyBarPhotoViewIntent.putExtra(HideyBarPhotoViewScreenExtras.SCREEN_TITLE,
                    screenTitle);
        }

    }


    ///////////////////////////////////////////////////
    ///////////////////////////////////////////////////
    ////// PHOTO SOURCE
    private abstract class PhotoSourceSetup {
        protected static final int PHOTO_SOURCE_TYPE_DRAWABLE_RESOURCE_ID = 0;
        protected static final int PHOTO_SOURCE_TYPE_URL = 1;

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
    }

    /**
     * Setup class for loading pictures from url.
     */
    public final class PhotoUrlSetup extends PhotoSourceSetup {
        private final String url;
        private PhotoLoader photoLoader;

        protected PhotoUrlSetup(final String url) {
            super(PHOTO_SOURCE_TYPE_URL);
            this.url = url;
        }

        private boolean hasPicassoLoader() {
            return photoLoader.isPicassoPhotoLoader();
        }

        /**
         * Creates a new Picasso Loader so we are able to set up it.
         *
         * @return a new instance of {@link com.hideybarphotoviewscreen.photoloader.PicassoPhotoLoader}
         */
        public PicassoPhotoLoader newPicassoLoader() {
            PicassoPhotoLoader picassoPhotoLoader = new PicassoPhotoLoader();
            setPhotoLoader(picassoPhotoLoader);
            return (PicassoPhotoLoader) photoLoader;
        }

        private void setPhotoLoader(PhotoLoader photoLoader) {
            this.photoLoader = photoLoader;
        }

    }

    private final class PhotoDrawableResourceSetup extends PhotoSourceSetup {
        private final int drawableResID;

        protected PhotoDrawableResourceSetup(final int drawableResID) {
            super(PHOTO_SOURCE_TYPE_DRAWABLE_RESOURCE_ID);
            this.drawableResID = drawableResID;
        }

    }

    ///////////////////////////////////////////////////
    ///////////////////////////////////////////////////
    ////// HIDEY BAR
    private final class HideyBarModeTimeSetup {
        private static final int START_HIDEY_MODE_DEFAULT_TIME = 2000;
        private long timeToStartHideyBar;

        private HideyBarModeTimeSetup() {
            this.timeToStartHideyBar = START_HIDEY_MODE_DEFAULT_TIME;
        }

        private void setNewTime(long newTimeToStartHideyBar) {
            this.timeToStartHideyBar = newTimeToStartHideyBar;
        }
    }

}
