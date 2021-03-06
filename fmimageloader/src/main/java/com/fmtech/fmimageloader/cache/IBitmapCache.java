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

public interface IBitmapCache {
    void put(BitmapRequest bitmapRequest, Bitmap bitmap);

    Bitmap get(BitmapRequest bitmapRequest);

    void remove(BitmapRequest bitmapRequest);

}
