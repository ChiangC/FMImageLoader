package com.fmtech.fmimageloader.loader;

import android.graphics.Bitmap;
import android.widget.ImageView;

import com.fmtech.fmimageloader.cache.IBitmapCache;
import com.fmtech.fmimageloader.config.DisplayConfig;
import com.fmtech.fmimageloader.request.BitmapRequest;

/**
 * ==================================================================
 * Copyright (C) 2018 FMTech All Rights Reserved.
 *
 * @author Drew.Chiang
 * @version v1.0.0
 * @email chiangchuna@gmail.com
 * @create_date 2018/6/12 23:01
 * <p>
 * ==================================================================
 */

public abstract class AbstractLoader implements ILoader{

    private IBitmapCache mBitmapCache = SimpleImageLoader.getInstance()
            .getImageLoadConfig().getBitmapCache();

    private DisplayConfig mDisplayConfig = SimpleImageLoader.getInstance()
            .getImageLoadConfig().getDisplayConfig();

    @Override
    public void loadImage(BitmapRequest bitmapRequest) {
        Bitmap bitmap = mBitmapCache.get(bitmapRequest);
        if(null == bitmap){
            //show placeholder image
            showLoadingImage(bitmapRequest);

            //load image
            bitmap = onLoad(bitmapRequest);

            cacheBitmap(bitmapRequest, bitmap);

        }

        deliveryToUIThread(bitmapRequest,bitmap);
    }

    private void showLoadingImage(BitmapRequest request){
        if(hasLoadingPlaceHolder()){
            final ImageView imageView = request.getImageView();
            if(null != imageView){
                imageView.post(new Runnable() {
                    @Override
                    public void run() {
                        imageView.setImageResource(mDisplayConfig.loadingImage);
                    }
                });
            }
        }
    }

    private boolean hasLoadingPlaceHolder(){
        return null != mDisplayConfig && mDisplayConfig.loadingImage>0;
    }

    private void cacheBitmap(BitmapRequest bitmapRequest, Bitmap bitmap){
        if(null != bitmapRequest && null != bitmap){
            synchronized (AbstractLoader.class){
                mBitmapCache.put(bitmapRequest, bitmap);
            }
        }
    }

    protected void deliveryToUIThread(final BitmapRequest request, final Bitmap bitmap){
        ImageView imageView = request.getImageView();
        if(null != imageView){
            imageView.post(new Runnable() {
                @Override
                public void run() {
                    updateImageView(request, bitmap);
                }
            });
        }
    }

    private void updateImageView(BitmapRequest request, Bitmap bitmap){
       ImageView imageView = request.getImageView();
        if(null != imageView && null != bitmap &&
                imageView.getTag().equals(request.getImageUri())){
            imageView.setImageBitmap(bitmap);
        }

        DisplayConfig displayConfig = request.getDisplayConfig();
        if(null == bitmap && null != displayConfig && displayConfig.loadFailedImage != -1){
            imageView.setImageResource(displayConfig.loadFailedImage);
        }

        if(null != request.mImageListener){
            request.mImageListener.onComplete(imageView, bitmap, request.getImageUri());
        }
    }

    protected abstract Bitmap onLoad(BitmapRequest request);


}
