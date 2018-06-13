package com.fmtech.fmimageloader.request;

import com.fmtech.fmimageloader.loader.ILoader;
import com.fmtech.fmimageloader.loader.LoaderManager;

import java.util.concurrent.BlockingQueue;

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

public class RequestDispatcher extends Thread{
    private BlockingQueue<BitmapRequest> mRequestQueue;

    public RequestDispatcher(BlockingQueue<BitmapRequest> mRequestQueue) {
        this.mRequestQueue = mRequestQueue;
    }

    @Override
    public void run() {
        super.run();
        while (!isInterrupted()){
            try {
                //Blocking method, will block.
                BitmapRequest bitmapRequest = mRequestQueue.take();
                //TODO
                String schema = parseSchema(bitmapRequest.getImageUri());
                ILoader loader = LoaderManager.getInstance().getLoader(schema);
                loader.loadImage(bitmapRequest);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private String parseSchema(String uri){
        if(uri.contains("://")){
            return uri.split("://")[0];
        }else{
            //Not supported yet.
        }
        return null;
    }
}
