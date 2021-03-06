package com.shang.zuul;

import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.netflix.zuul.filters.ZuulProperties;
import org.springframework.cloud.netflix.zuul.filters.discovery.DiscoveryClientRouteLocator;

/**
 * Created by shangzebei on 2017/6/29.
 */
public class DefZuulFilter extends DiscoveryClientRouteLocator {
    private ZuulProvider zuulProvider;

    public DefZuulFilter(String servletPath, DiscoveryClient discovery, ZuulProperties properties, ZuulProvider zuulService) {
        super(servletPath, discovery, properties);
        this.zuulProvider = zuulService;
        if (zuulProvider != null) {
            zuulProvider.init(this, properties);
        }
    }

    @Override
    protected ZuulProperties.ZuulRoute getZuulRoute(String adjustedPath) {
        ZuulProperties.ZuulRoute zuulRoute = super.getZuulRoute(adjustedPath);
        if (zuulProvider == null) {
            return zuulRoute;
        }
        return zuulProvider.getRoute(zuulRoute);
    }
}
