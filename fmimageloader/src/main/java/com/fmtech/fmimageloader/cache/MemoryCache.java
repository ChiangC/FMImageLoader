package com.fmtech.fmimageloader.cache;

import android.graphics.Bitmap;
import android.util.LruCache;

import com.fmtech.fmimageloader.request.BitmapRequest;

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

public class MemoryCache implements IBitmapCache {
    private LruCache<String, Bitmap> mLruCache;

    public MemoryCache(){
        int maxSize = (int)(Runtime.getRuntime().freeMemory()/1024/8);
        mLruCache = new LruCache<String, Bitmap>(maxSize){
            @Override
            protected int sizeOf(String key, Bitmap value) {
                return value.getRowBytes()*value.getHeight();
            }
        };
    }

    @Override
    public void put(BitmapRequest bitmapRequest, Bitmap bitmap) {
        mLruCache.put(bitmapRequest.getImageUriMD5(), bitmap);
    }

    @Override
    public Bitmap get(BitmapRequest bitmapRequest) {
        return mLruCache.get(bitmapRequest.getImageUriMD5());
    }

    @Override
    public void remove(BitmapRequest bitmapRequest) {
        mLruCache.remove(bitmapRequest.getImageUriMD5());
    }

}
