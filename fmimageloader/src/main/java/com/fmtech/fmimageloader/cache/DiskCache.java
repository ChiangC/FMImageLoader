package com.fmtech.fmimageloader.cache;

import android.graphics.Bitmap;

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

public class DiskCache implements IBitmapCache {

    @Override
    public void put(BitmapRequest bitmapRequest, Bitmap bitmap) {

    }

    @Override
    public Bitmap get(BitmapRequest bitmapRequest) {
        return null;
    }

    @Override
    public void remove(BitmapRequest bitmapRequest) {

    }

}
