package com.fmtech.fmimageloader.loader;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;

import com.fmtech.fmimageloader.request.BitmapRequest;
import com.fmtech.fmimageloader.utils.BitmapDecoder;
import com.fmtech.fmimageloader.utils.ImageViewHelper;

import java.io.File;

/**
 * ==================================================================
 * Copyright (C) 2018 FMTech All Rights Reserved.
 *
 * @author Drew.Chiang
 * @version v1.0.0
 * @email chiangchuna@gmail.com
 * @create_date 2018/6/12 23:02
 * <p>
 * ==================================================================
 */

public class LocalLoader extends AbstractLoader {
    @Override
    protected Bitmap onLoad(BitmapRequest request) {
        final String path = request.getImageUri();
        if(!TextUtils.isEmpty(path)){
            File imageFile = new File(path);
            if(imageFile.exists()){
                BitmapDecoder bitmapDecoder = new BitmapDecoder() {
                    @Override
                    protected Bitmap decodeBitmapWithOption(BitmapFactory.Options options) {
                        return BitmapFactory.decodeFile(path, options);
                    }
                };

                return bitmapDecoder.decodeBitmap(ImageViewHelper.getImageViewWidth(request.getImageView()),
                        ImageViewHelper.getImageViewHeight(request.getImageView()));
            }
        }
        return null;
    }

}
