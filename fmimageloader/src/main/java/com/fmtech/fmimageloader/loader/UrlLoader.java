package com.fmtech.fmimageloader.loader;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;

import com.fmtech.fmimageloader.request.BitmapRequest;
import com.fmtech.fmimageloader.utils.BitmapDecoder;
import com.fmtech.fmimageloader.utils.ImageViewHelper;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
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
    protected Bitmap onLoad(final BitmapRequest request) {

        downloadImgByUrl(request.getImageUri(),getCacheFile(request.getImageUriMD5()));

        BitmapDecoder bitmapDecoder = new BitmapDecoder() {
            @Override
            public Bitmap decodeBitmapWithOption(BitmapFactory.Options options) {
                Bitmap bitmap = BitmapFactory.decodeFile(getCacheFile(request.getImageUriMD5()).getAbsolutePath(), options);
                return bitmap;
            }
        };
        return bitmapDecoder.decodeBitmap(ImageViewHelper.getImageViewWidth(request.getImageView()),
                ImageViewHelper.getImageViewHeight(request.getImageView()));

    }

    private boolean downloadImgByUrl(String urlStr, File file){
        FileOutputStream fos = null;
        InputStream is = null;
        try {
            URL url = new URL(urlStr);
            HttpURLConnection connection = (HttpURLConnection)url.openConnection();
            is = connection.getInputStream();
            fos = new FileOutputStream(file);
            byte[] buf = new byte[512];
            int len = 0;
            while((len = is.read(buf)) != -1){
                fos.write(buf, 0, len);
            }
            fos.flush();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                if(null != fos){
                    fos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if(null != is){
                    is.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    private File getCacheFile(String unipue){
        File file = new File(Environment.getExternalStorageDirectory(), unipue);
        if(!file.exists()){
            file.mkdir();
        }
        return new File(file, unipue);
    }


}
