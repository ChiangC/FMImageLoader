package com.fmtech.fmimageloader.cache;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;

import com.fmtech.fmimageloader.disk.DiskLruCache;
import com.fmtech.fmimageloader.disk.IOUtil;
import com.fmtech.fmimageloader.request.BitmapRequest;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

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

    private static DiskCache mDiskCache;
    private String mCacheDir = "fm_image_loader";
    private static final int MB = 1024*1024;
    private DiskLruCache mDiskLruCache;

    private DiskCache(Context context){
        initDiskCache(context);
    }

    public static DiskCache getInstance(Context context) {
        if(null == mDiskCache){
            synchronized (DiskCache.class){
                if(null == mDiskCache){
                    mDiskCache = new DiskCache(context);
                }
            }
        }
        return mDiskCache;
    }

    private void initDiskCache(Context context){
        //android/data/data/packagename/image
        File directory = getDiskCacheDir(mCacheDir, context);
        if(!directory.exists()){
            directory.mkdir();
        }
        try {
            mDiskLruCache = DiskLruCache.open(directory, 1, 1, 50*MB);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private File getDiskCacheDir(String cacheDir, Context context){
//        return new File(context.getCacheDir(), cacheDir);
        return new File(Environment.getExternalStorageDirectory(), cacheDir);
    }

    @Override
    public void put(BitmapRequest bitmapRequest, Bitmap bitmap) {
        DiskLruCache.Editor editor = null;
        OutputStream outputStream = null;
        try {
            editor = mDiskLruCache.edit(bitmapRequest.getImageUriMD5());
            outputStream = editor.newOutputStream(0);
            if(persistBitmap2Disk(bitmap, outputStream)){
                editor.commit();
            }else{
                editor.abort();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Bitmap get(BitmapRequest bitmapRequest) {
        try {
            DiskLruCache.Snapshot snapshot = mDiskLruCache.get(bitmapRequest.getImageUriMD5());
            //Get the first file InputStream
            if(null != snapshot) {
                InputStream inputStream = snapshot.getInputStream(0);
                return BitmapFactory.decodeStream(inputStream);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void remove(BitmapRequest bitmapRequest) {
        try {
            mDiskLruCache.remove(bitmapRequest.getImageUriMD5());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean persistBitmap2Disk(Bitmap bitmap, OutputStream outputStream){
        BufferedOutputStream bos = new BufferedOutputStream(outputStream);
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
        try {
            bos.flush();
        } catch (IOException e) {
            e.printStackTrace();
            IOUtil.closeQuietly(bos);
            return false;
        }
        return true;
    }


}
