/*******************************************************************************
 * Copyright 2014, Jonathan Fragoso Perez.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/


package com.hideybarphotoviewscreen.photoloader;

/**
 *
 */
///////////////////////////////////////////////////
///////////////////////////////////////////////////
////// PHOTO LOADERS

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Abstract super class photo loader.
 */
public abstract class PhotoLoader implements Parcelable {
    protected final static int TYPE_PICASSO_LOADER = 0;

    private int photoLoaderType;

    protected PhotoLoader(final int photoLoaderType){
        this.photoLoaderType = photoLoaderType;
    }

    public boolean isPicassoPhotoLoader(){
        return photoLoaderType == TYPE_PICASSO_LOADER;
    }


    /////////////////////////////////////////////
    ////// PARCEL SETUP
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.photoLoaderType);
    }

    protected PhotoLoader(Parcel in) {
        this.photoLoaderType = in.readInt();
    }

}
