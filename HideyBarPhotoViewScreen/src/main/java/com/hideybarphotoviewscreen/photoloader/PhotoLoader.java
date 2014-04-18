package com.hideybarphotoviewscreen.photoloader;

/**
 *
 */
///////////////////////////////////////////////////
///////////////////////////////////////////////////
////// PHOTO LOADERS

import com.hideybarphotoviewscreen.utils.PictureUtils;

/**
 * Abstract super class photo loader.
 */
public class PhotoLoader{
    protected final static int TYPE_PICASSO_LOADER = 0;

    private int photoLoaderType;

    protected PhotoLoader(final int photoLoaderType){
        this.photoLoaderType = photoLoaderType;
    }

    public boolean isPicassoPhotoLoader(){
        return photoLoaderType == TYPE_PICASSO_LOADER;
    }
}
