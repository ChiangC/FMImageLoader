package com.fmtech.fmimageloader.loader;

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

public class NullLoader extends AbstractLoader {

    @Override
    protected Bitmap onLoad(BitmapRequest request) {
        return null;
    }

}
