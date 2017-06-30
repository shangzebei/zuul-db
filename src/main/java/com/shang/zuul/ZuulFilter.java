package com.shang.zuul;

import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.netflix.zuul.filters.ZuulProperties;
import org.springframework.cloud.netflix.zuul.filters.discovery.DiscoveryClientRouteLocator;

/**
 * Created by shangzebei on 2017/6/29.
 */
public class ZuulFilter extends DiscoveryClientRouteLocator {
    private ZuulService zuulService;

    public ZuulFilter(String servletPath, DiscoveryClient discovery, ZuulProperties properties, ZuulService zuulService) {
        super(servletPath, discovery, properties);
        this.zuulService = zuulService;
        this.zuulService.zuulFilter=this;
    }

    @Override
    protected ZuulProperties.ZuulRoute getZuulRoute(String adjustedPath) {
        ZuulProperties.ZuulRoute zuulRoute = super.getZuulRoute(adjustedPath);
        return zuulService.getRoute(zuulRoute);
    }
}
