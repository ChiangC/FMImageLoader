package com.fmtech.fmimageloader.cache;

import android.content.Context;
import android.graphics.Bitmap;

import com.fmtech.fmimageloader.request.BitmapRequest;

/**
 * ==================================================================
 * Copyright (C) 2018 FMTech All Rights Reserved.
 *
 * @author Drew.Chiang
 * @version v1.0.0
 * @email chiangchuna@gmail.com
 * @create_date 2018/6/14 08:37
 * <p>
 * ==================================================================
 */

public class DoubleCache implements IBitmapCache {
    private MemoryCache mMemoryCache = new MemoryCache();
    private DiskCache mDiskCache;

    public DoubleCache(Context context){
        mDiskCache = DiskCache.getInstance(context);
    }

    @Override
    public void put(BitmapRequest bitmapRequest, Bitmap bitmap) {
        mMemoryCache.put(bitmapRequest, bitmap);
        mDiskCache.put(bitmapRequest, bitmap);
    }

    @Override
    public Bitmap get(BitmapRequest bitmapRequest) {
        Bitmap bitmap = mMemoryCache.get(bitmapRequest);
        if(null == bitmap){
            bitmap = mDiskCache.get(bitmapRequest);
            if(null != bitmap){
                mMemoryCache.put(bitmapRequest, bitmap);
            }
        }
        return bitmap;
    }

    @Override
    public void remove(BitmapRequest bitmapRequest) {
        mMemoryCache.remove(bitmapRequest);
        mDiskCache.remove(bitmapRequest);
    }

}
