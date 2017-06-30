package com.shang.zuul;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.zuul.RoutesRefreshedEvent;
import org.springframework.cloud.netflix.zuul.filters.Route;
import org.springframework.cloud.netflix.zuul.filters.ZuulProperties;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by shangzebei on 2017/6/30.
 */
@Service
public class ZuulService {

    public ZuulFilter zuulFilter;

    @Autowired
    private ApplicationEventPublisher publisher;

    public ZuulProperties.ZuulRoute getRoute(ZuulProperties.ZuulRoute zuulRoute) {
        System.out.println(zuulRoute);
        return zuulRoute;
    }

    /**
     * add route to zuul
     *
     * @param zuulRoute
     */
    public void addRoute(ZuulProperties.ZuulRoute zuulRoute) {
        publisher.publishEvent(new RoutesRefreshedEvent(zuulFilter));
        zuulFilter.addRoute(zuulRoute);
    }

    /**
     * get the routes
     *
     * @return
     */
    public List<Route> getRoutes() {
        return zuulFilter.getRoutes();
    }


}
