package com.shang.zuul;

import com.shang.zuul.domain.URLEntry;

/**
 * Created by shang-mac on 2017/7/2.
 */
public class Util {
    public static URLEntry check(URLEntry urlEntry) {
        if (urlEntry.getPath() == null) {
            urlEntry.setUrl("/" + urlEntry.getUrl() + "/**");
            urlEntry.setPath(urlEntry.getUrl());
        }
        return urlEntry;

    }

}
