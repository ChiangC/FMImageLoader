package com.fmtech.fmimageloader.loader;

import java.util.HashMap;
import java.util.Map;

/**
 * ==================================================================
 * Copyright (C) 2018 FMTech All Rights Reserved.
 *
 * @author Drew.Chiang
 * @version v1.0.0
 * @email chiangchuna@gmail.com
 * @create_date 2018/6/13 21:03
 * <p>
 * ==================================================================
 */

public class LoaderManager {

    private Map<String, ILoader> mLoaderMap = new HashMap<>();
    private static LoaderManager sInstance = new LoaderManager();

    private LoaderManager(){
        register("http", new UrlLoader());
        register("https", new UrlLoader());
        register("file", new LocalLoader());
    }

    public static LoaderManager getInstance(){
        return sInstance;
    }

    private void register(String schema, ILoader loader){
        mLoaderMap.put(schema, loader);
    }

    public ILoader getLoader(String schema){
        if(mLoaderMap.containsKey(schema)) {
            return mLoaderMap.get(schema);
        }
        return new NullLoader();
    }

}
