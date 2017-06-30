package com.shang.zuul;

import org.springframework.cloud.netflix.zuul.filters.ZuulProperties;

/**
 * Created by shangzebei on 2017/6/30.
 */
public interface ZuulProvider {
    /**
     * this method can be change zuulRoute
     * @param zuulRoute
     * @return
     */
    ZuulProperties.ZuulRoute getRoute(ZuulProperties.ZuulRoute zuulRoute);

    /**
     * init with zuulFilter
     * @param zuulFilter
     */
    void init(ZuulFilter zuulFilter);
}
