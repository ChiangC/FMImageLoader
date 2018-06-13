package com.fmtech.fmimageloader.loader;

import android.graphics.Bitmap;
import android.widget.ImageView;

import com.fmtech.fmimageloader.config.DisplayConfig;
import com.fmtech.fmimageloader.config.ImageLoaderConfig;
import com.fmtech.fmimageloader.request.BitmapRequest;
import com.fmtech.fmimageloader.request.RequestQueue;

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

public class SimpleImageLoader {
    private ImageLoaderConfig mImageLoadConfig;
    private RequestQueue mRequestQueue;
    private static volatile SimpleImageLoader sInstance;

    private SimpleImageLoader(){

    }

    private SimpleImageLoader(ImageLoaderConfig imageLoaderConfig){
        mImageLoadConfig = imageLoaderConfig;
        mRequestQueue = new RequestQueue(imageLoaderConfig.getThreadCount());
        mRequestQueue.start();
    }

    public static SimpleImageLoader getInstance(ImageLoaderConfig imageLoaderConfig){
        if(null == sInstance){
            synchronized (SimpleImageLoader.class){
                if(null == sInstance){
                    sInstance = new SimpleImageLoader(imageLoaderConfig);
                }
            }
        }
        return sInstance;
    }

    public static SimpleImageLoader getInstance(){
        if(null == sInstance){
            throw new UnsupportedOperationException("SimpleImageLoader not initialized.");
        }
        return sInstance;
    }

    public void displayImage(ImageView imageView, String uri){
        displayImage(imageView, uri, null, null);
    }

    public void displayImage(ImageView imageView, String uri, DisplayConfig displayConfig, ImageListener imageListener){
        BitmapRequest bitmapRequest = new BitmapRequest(imageView, uri, displayConfig, imageListener);
        mRequestQueue.addRequest(bitmapRequest);
    }

    public static interface ImageListener{
        void onComplete(ImageView imageView, Bitmap bitmap, String uri);
    }

    public ImageLoaderConfig getImageLoadConfig() {
        return mImageLoadConfig;
    }

}
