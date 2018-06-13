package com.fmtech.fmimageloader.loader;

import com.fmtech.fmimageloader.request.BitmapRequest;

/**
 * ==================================================================
 * Copyright (C) 2018 FMTech All Rights Reserved.
 *
 * @author Drew.Chiang
 * @version v1.0.0
 * @email chiangchuna@gmail.com
 * @create_date 2018/6/12 23:00
 * <p>
 * ==================================================================
 */

public interface ILoader {
    void loadImage(BitmapRequest bitmapRequest);
}
