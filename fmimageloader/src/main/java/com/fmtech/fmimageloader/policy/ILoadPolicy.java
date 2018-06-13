package com.fmtech.fmimageloader.policy;

import com.fmtech.fmimageloader.request.BitmapRequest;

/**
 * ==================================================================
 * Copyright (C) 2018 FMTech All Rights Reserved.
 *
 * @author Drew.Chiang
 * @version v1.0.0
 * @email chiangchuna@gmail.com
 * @create_date 2018/6/12 23:03
 * <p>
 * ==================================================================
 */

public interface ILoadPolicy {
    int compareTo(BitmapRequest request1, BitmapRequest request2);
}
