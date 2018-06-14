package com.fmtech.fmimageloader.config;

import android.media.Image;

import com.fmtech.fmimageloader.cache.DoubleCache;
import com.fmtech.fmimageloader.cache.IBitmapCache;
import com.fmtech.fmimageloader.cache.MemoryCache;
import com.fmtech.fmimageloader.policy.ILoadPolicy;
import com.fmtech.fmimageloader.policy.ReverseLoadPolicy;

/**
 * ==================================================================
 * Copyright (C) 2018 FMTech All Rights Reserved.
 *
 * @author Drew.Chiang
 * @version v1.0.0
 * @email chiangchuna@gmail.com
 * @create_date 2018/6/12 23:01
 * <p>
 * ==================================================================
 */

public class ImageLoaderConfig {
    private IBitmapCache mBitmapCache = new MemoryCache();
    private ILoadPolicy mLoadPolicy = new ReverseLoadPolicy();
    private int mThreadCount = Runtime.getRuntime().availableProcessors();
    private DisplayConfig mDisplayConfig = new DisplayConfig();

    private ImageLoaderConfig(){

    }

    public static class Builder{
        private ImageLoaderConfig config;

        public Builder(){
            config = new ImageLoaderConfig();
        }

        public Builder setCachePolicy(IBitmapCache bitmapCache){
            config.mBitmapCache = bitmapCache;
            return this;
        }

        public Builder setLoadPolicy(ILoadPolicy loadPolicy){
            config.mLoadPolicy = loadPolicy;
            return this;
        }

        public Builder setThreadCount(int count){
            config.mThreadCount = count;
            return this;
        }

        public Builder setLoadingImage(int resId){
            config.mDisplayConfig.loadingImage = resId;
            return this;
        }

        public Builder setLoadFailedImage(int resId){
            config.mDisplayConfig.loadFailedImage = resId;
            return this;
        }

        public ImageLoaderConfig build(){
            return config;
        }

    }

    public IBitmapCache getBitmapCache() {
        return mBitmapCache;
    }

    public ILoadPolicy getLoadPolicy() {
        return mLoadPolicy;
    }

    public int getThreadCount() {
        return mThreadCount;
    }

    public DisplayConfig getDisplayConfig() {
        return mDisplayConfig;
    }


}
