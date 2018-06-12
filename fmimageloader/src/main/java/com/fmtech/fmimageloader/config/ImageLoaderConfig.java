package com.fmtech.fmimageloader.config;

import com.fmtech.fmimageloader.cache.IBitmapCache;
import com.fmtech.fmimageloader.policy.ILoaderPolicy;

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

public class ImageLoaderConfig {
    private IBitmapCache bitmapCache;
    private ILoaderPolicy loaderPolicy;

    private int threadCount = Runtime.getRuntime().availableProcessors();
    
}
