package com.fmtech.fmimageloader.request;

import android.widget.ImageView;

import com.fmtech.fmimageloader.config.DisplayConfig;
import com.fmtech.fmimageloader.loader.SimpleImageLoader;
import com.fmtech.fmimageloader.policy.ILoadPolicy;

import java.lang.ref.SoftReference;
import java.util.Comparator;
import java.util.Objects;

/**
 * ==================================================================
 * Copyright (C) 2018 FMTech All Rights Reserved.
 *
 * @author Drew.Chiang
 * @version v1.0.0
 * @email chiangchuna@gmail.com
 * <p>
 * ==================================================================
 */

public class BitmapRequest implements Comparator<BitmapRequest>{
    private SoftReference<ImageView> mTarget;
    private String mImageUri;
    private String mImageUriMD5;
    private SimpleImageLoader.ImageListener mImageListener;
    private DisplayConfig mDisplayConfig;

    private ILoadPolicy mLoadPolicy = SimpleImageLoader.getInstance()
            .getImageLoadConfig().getLoadPolicy();

    private int mSerialNo;

    public BitmapRequest(ImageView imageView, String imageUri, DisplayConfig displayConfig, SimpleImageLoader.ImageListener imageListener) {
        mTarget = new SoftReference<>(imageView);
        imageView.setTag(imageUri);
        this.mImageUri = imageUri;
        this.mImageListener = imageListener;
        if(null != displayConfig){
            mDisplayConfig = displayConfig;
        }
    }

    @Override
    public int compare(BitmapRequest o1, BitmapRequest o2) {
        return mLoadPolicy.compareTo(o1, o2);
    }

    public int getSerialNo() {
        return mSerialNo;
    }

    public void setSerialNo(int serialNo) {
        mSerialNo = serialNo;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((mLoadPolicy == null) ? 0 : mLoadPolicy.hashCode());
        result = prime * result + mSerialNo;
        return result;
    }


    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        BitmapRequest other = (BitmapRequest) obj;
        if (mLoadPolicy == null) {
            if (other.mLoadPolicy != null)
                return false;
        } else if (!mLoadPolicy.equals(other.mLoadPolicy))
            return false;
        if (mSerialNo != other.mSerialNo)
            return false;
        return true;
    }

    public ImageView getImageView(){
        return mTarget.get();
    }

    public String getImageUri(){
        return mImageUri;
    }
}
