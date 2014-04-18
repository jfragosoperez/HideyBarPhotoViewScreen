package com.hideybarphotoviewscreen.utils;

/**
 *
 */
public final class PictureUtils {
    private static final int NO_DRAWABLE_RES_ID = -1;
    private PictureUtils(){}

    public static int unusedPictureResID(){
        return NO_DRAWABLE_RES_ID;
    }

    public static boolean isUnusedPictureResID(int pictureResID){
        return NO_DRAWABLE_RES_ID == NO_DRAWABLE_RES_ID;
    }
}
