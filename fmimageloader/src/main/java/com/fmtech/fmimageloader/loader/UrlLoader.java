package com.fmtech.fmimageloader.loader;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.fmtech.fmimageloader.request.BitmapRequest;
import com.fmtech.fmimageloader.utils.BitmapDecoder;
import com.fmtech.fmimageloader.utils.ImageViewHelper;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

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

public class UrlLoader extends AbstractLoader {

    @Override
    protected Bitmap onLoad(BitmapRequest request) {
        HttpURLConnection connection = null;
        InputStream inputStream = null;
        BitmapDecoder bitmapDecoder=null;
        try {
            connection = (HttpURLConnection)(new URL(request.getImageUri())).openConnection();
            inputStream = new BufferedInputStream(connection.getInputStream());

//            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
            final InputStream finalInputStream = inputStream;
            bitmapDecoder=new BitmapDecoder() {
                @Override
                public Bitmap decodeBitmapWithOption(BitmapFactory.Options options) {
                    //options 是入参出参对象
                    Bitmap bitmap=BitmapFactory.decodeStream(finalInputStream,null,options);
                    //流这里是第一次读   在BitmapDecoder里面 设置inJustDecodeBounds=true
                    if(options.inJustDecodeBounds)
                    {
                        try {
                            //第一次读图片宽高信息，读完之后必须为第二次读整个图片进行准备，将流重置
                            finalInputStream.reset();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }else
                    {
                        //第二次读 设置inJustDecodeBounds=false
                        try {
                            finalInputStream.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    return bitmap;
                }
            };
            return bitmapDecoder.decodeBitmap(ImageViewHelper.getImageViewWidth(request.getImageView()),
                    ImageViewHelper.getImageViewHeight(request.getImageView()));

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
        }
        return null;
    }

}
