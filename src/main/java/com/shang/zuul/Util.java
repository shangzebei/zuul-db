package com.shang.zuul;

import com.shang.zuul.domain.RouteEntry;
import org.springframework.cloud.netflix.zuul.filters.ZuulProperties;

/**
 * Created by shang-mac on 2017/7/2.
 */
public class Util {
    public static ZuulProperties.ZuulRoute toZuulRoute(RouteEntry routeEntry){
        ZuulProperties.ZuulRoute zuulRoute = new ZuulProperties.ZuulRoute();
        zuulRoute.setId(routeEntry.getTitle());
        if (routeEntry.getPath() == null || routeEntry.getPath().equals("")) {
            zuulRoute.setPath("/" + routeEntry.getTitle() + "/**");
        } else {
            zuulRoute.setPath(routeEntry.getPath());
        }
        zuulRoute.setLocation(routeEntry.getUrl());
        zuulRoute.setStripPrefix(routeEntry.isStripPrefix());
        zuulRoute.setCustomSensitiveHeaders(true);
        routeEntry.setPath(zuulRoute.getPath());
        routeEntry.setServiceId(zuulRoute.getServiceId());

        return zuulRoute;
    }



}
