package com.fmtech.fmimageloader.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.InputStream;

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

public abstract class BitmapDecoder {
    public Bitmap decodeBitmap(int reqWidth, int reqHeight){
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        decodeBitmapWithOption(options);

        calculateSampleSizeWithOption(options, reqWidth, reqHeight);

        return decodeBitmapWithOption(options);
    }

    /**
     * 计算图片缩放的比例
     * @param options
     * @param reqWidth
     * @param reqHeight
     */
    private void calculateSampleSizeWithOption(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        //计算缩放的比例
        //图片的原始宽高
        int width = options.outWidth;
        int height = options.outHeight;

        int inSampleSize = 1;
        //  reqWidth   ImageView的  宽
        if(width > reqWidth || height > reqHeight){
            //宽高的缩放比例
            int heightRatio = Math.round((float)height / (float)reqHeight);
            int widthRatio = Math.round((float)width / (float)reqWidth);

            //有的图是长图、有的是宽图
            inSampleSize = Math.max(heightRatio, widthRatio);
        }

        //全景图
        //当inSampleSize为2，图片的宽与高变成原来的1/2
        //options.inSampleSize = 2
        options.inSampleSize = inSampleSize;

        //每个像素2个字节
        options.inPreferredConfig = Bitmap.Config.RGB_565;
        //Bitmap占用内存  true
        options.inJustDecodeBounds = false;
        //当系统内存不足时可以回收Bitmap
        options.inPurgeable = true;
        options.inInputShareable = true;


    }


    protected abstract Bitmap decodeBitmapWithOption(BitmapFactory.Options options);

}
