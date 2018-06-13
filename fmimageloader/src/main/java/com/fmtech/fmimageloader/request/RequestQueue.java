package com.fmtech.fmimageloader.request;

import java.util.concurrent.BlockingDeque;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

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

public class RequestQueue {

    /**
     *
     */
    private BlockingQueue<BitmapRequest> mRequestQueue = new PriorityBlockingQueue<>();

    private int mThreadCount;

    private RequestDispatcher[] mDispatchers;

    private AtomicInteger mQueueIndex = new AtomicInteger(0);

    public RequestQueue(int threadCount){
        mThreadCount = threadCount;
    }

    public void addRequest(BitmapRequest bitmapRequest){
        if(null == bitmapRequest){
            return;
        }

        if(!mRequestQueue.contains(bitmapRequest)){
            bitmapRequest.setSerialNo(mQueueIndex.incrementAndGet());
            mRequestQueue.add(bitmapRequest);
        }else{

        }
    }

    public void start(){
        stop();
        startDispatchers();
    }

    public void stop(){

    }

    private void startDispatchers(){
        mDispatchers = new RequestDispatcher[mThreadCount];
        for(int i=0; i< mThreadCount;i++){
            RequestDispatcher requestDispatcher = new RequestDispatcher(mRequestQueue);
            mDispatchers[i] = requestDispatcher;
            mDispatchers[i].start();
        }
    }

}
