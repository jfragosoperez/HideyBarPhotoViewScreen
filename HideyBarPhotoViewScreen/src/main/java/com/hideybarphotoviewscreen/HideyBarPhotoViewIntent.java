package com.hideybarphotoviewscreen;

import android.content.Intent;

import com.hideybarphotoviewscreen.utils.HideyBarPhotoViewScreenExtras;

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
     * @return a new set up for the intent.
     */
    public static HideyBarPhotoViewScreenSetUp newConfiguration(){
        return getInstance().hideyBarPhotoViewScreenSetUp;
    }

    /**
     * Class which handles the setup of the intent to start {@link com.hideybarphotoviewscreen.HideyBarPhotoViewScreen}-
     */
    public class HideyBarPhotoViewScreenSetUp{

        private PhotoSourceSetup photoSourceSetup;
        private HideyBarModeTimeSetup hideyBarModeTimeSetup = new HideyBarModeTimeSetup();
        private String screenTitle = "";
        private Intent hideyBarPhotoViewIntent;

        /**
         * Sets the resource id of the drawable that will be displayed in the photo view of the screen.
         *
         * @param resourceId is the resource id of the drawable that will be displayed in the photo view screen.
         * @return the setup, so we can go on with the setup and finally create the intent using {@link #createIntent()}.
         */
        public HideyBarPhotoViewScreenSetUp setPhotoResourceId(final int resourceId) {
            hideyBarPhotoViewScreenSetUp.photoSourceSetup = new PhotoDrawableResourceSetup(resourceId);
            return hideyBarPhotoViewScreenSetUp;
        }

        /**
         * Sets the url of the photo that will be displayed in the {@link com.hideybarphotoviewscreen.HideyBarPhotoViewScreen}
         *
         * @param photoUrl is the url of the photo that we want to display in the {@link com.hideybarphotoviewscreen.HideyBarPhotoViewScreen}
         * @return the setup, so we can go on with the setup and finally create the intent using {@link #createIntent()}.
         */
        public HideyBarPhotoViewScreenSetUp setPhotoUrl(final String photoUrl) {
            hideyBarPhotoViewScreenSetUp.photoSourceSetup = new PhotoUrlSetup(photoUrl);
            return hideyBarPhotoViewScreenSetUp;
        }

        /**
         * Sets the time to start "HideyMode" of the ActionBar. This time is used both for hiding the ActionBar
         * when the user enters the screen (after our period of time) or then
         * to hide it once after the user has touched the picture of the screen and due to this fact the ActionBar was visible.
         *
         * @param timeToStartHideyMode is the time to start "HideyMode" of the ActionBar.
         * @return the setup, so we can go on with the setup and finally create the intent using {@link #createIntent()}.
         */
        public HideyBarPhotoViewScreenSetUp setTimeToStartHideyMode(float timeToStartHideyMode){
            hideyBarModeTimeSetup.setNewTime(timeToStartHideyMode);
            return this;
        }

        /**
         * Sets the title that will appear as the title in the ActionBar of the screen.
         *
         * @param title is the title that will appear as the title in the ActionBar of the screen.
         * @return the setup, so we can go on with the setup and finally create the intent using {@link #createIntent()}.
         */
        public HideyBarPhotoViewScreenSetUp setScreenTitle(final String title){
            this.screenTitle = title;
            return this;
        }

        /**
         * Creates an Intent (configured with the setup) ready to be started with {@link android.content.Context#startActivity(android.content.Intent)}
         * @return an Intent (configured with the setup) ready to be started with {@link android.content.Context#startActivity(android.content.Intent)}
         */
        public Intent createIntent() {
            hideyBarPhotoViewIntent = new Intent();
            addPhotoSourceSetUp();
            addHideyBarTimeSetUp();
            addTitleSetUp();
            return hideyBarPhotoViewIntent;
        }

        private void addPhotoSourceSetUp() {
            if(photoSourceSetup == null){
                throw new IllegalArgumentException("You must set the photo source, using methods setPhotoUrl() or setPhotoResourceId()");
            }
            if (photoSourceSetup.isDrawableResIdSetup()) {
                hideyBarPhotoViewIntent.putExtra(HideyBarPhotoViewScreenExtras.PICTURE_RES_ID,
                        ((PhotoDrawableResourceSetup) hideyBarPhotoViewScreenSetUp.photoSourceSetup)
                                .drawableResID);
            }
            //case we've an url
            else {
                final PhotoUrlSetup photoUrlSetup = ((PhotoUrlSetup) hideyBarPhotoViewScreenSetUp.photoSourceSetup);
                hideyBarPhotoViewIntent.putExtra(HideyBarPhotoViewScreenExtras.PICTURE_URL,
                        photoUrlSetup.url);
            }
        }

        private void addHideyBarTimeSetUp(){
            hideyBarPhotoViewIntent.putExtra(HideyBarPhotoViewScreenExtras.TIME_TO_START_HIDEY_BAR_MODE,
                    hideyBarModeTimeSetup.timeToStartHideyBar);
        }

        private void addTitleSetUp(){
            hideyBarPhotoViewIntent.putExtra(HideyBarPhotoViewScreenExtras.SCREEN_TITLE,
                    screenTitle);
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
    }

    private class PhotoUrlSetup extends PhotoSourceSetup {

        private final String url;

        protected PhotoUrlSetup(final String url) {
            super(PHOTO_SOURCE_TYPE_URL);
            this.url = url;
        }

    }

    private class PhotoDrawableResourceSetup extends PhotoSourceSetup {

        private final int drawableResID;

        protected PhotoDrawableResourceSetup(final int drawableResID) {
            super(PHOTO_SOURCE_TYPE_DRAWABLE_RESOURCE_ID);
            this.drawableResID = drawableResID;
        }

    }

    ///////////////////////////////////////////////////
    ///////////////////////////////////////////////////
    ////// HIDEY BAR
    private class HideyBarModeTimeSetup{
        private static final int START_HIDEY_MODE_DEFAULT_TIME = 2000;

        private float timeToStartHideyBar;

        private HideyBarModeTimeSetup(){
            this.timeToStartHideyBar = START_HIDEY_MODE_DEFAULT_TIME;
        }

        private void setNewTime(float newTimeToStartHideyBar){
            this.timeToStartHideyBar = newTimeToStartHideyBar;
        }
    }

}
