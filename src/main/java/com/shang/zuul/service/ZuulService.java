package com.shang.zuul.service;

import com.shang.zuul.DefZuulFilter;
import com.shang.zuul.Util;
import com.shang.zuul.ZuulProvider;
import com.shang.zuul.domain.Message;
import com.shang.zuul.domain.RouteEntry;
import com.shang.zuul.repository.URLEntryRepository;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.zuul.RoutesRefreshedEvent;
import org.springframework.cloud.netflix.zuul.filters.Route;
import org.springframework.cloud.netflix.zuul.filters.ZuulProperties;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by shangzebei on 2017/6/30.
 */
@Service
@Log4j
public class ZuulService implements ZuulProvider {


    public DefZuulFilter defZuulFilter;

    @Autowired
    private ApplicationEventPublisher publisher;

    @Autowired
    private URLEntryRepository urlEntryRepository;
    private ZuulProperties properties;

    @Autowired
    private MessageWebsocket messageWebsocket;


    volatile long count = 0;

    @Override
    public ZuulProperties.ZuulRoute getRoute(ZuulProperties.ZuulRoute zuulRoute) {
        count++;
        return zuulRoute;
    }

    /**
     * add route to zuul
     *
     * @param zuulRoute
     */
    public void addRoute(ZuulProperties.ZuulRoute zuulRoute) {
        defZuulFilter.addRoute(zuulRoute);
        freshMapperUrl();

    }

    /**
     * this mothod will reset
     *
     * @param map
     */
    public void reSetRoutes(Map<String, ZuulProperties.ZuulRoute> map) {
        properties.setRoutes(map);
        defZuulFilter.refresh();
        freshMapperUrl();
    }

    /**
     * get the routes
     *
     * @return
     */
    public List<Route> getRoutes() {
        return defZuulFilter.getRoutes();
    }

    public Map<String, ZuulProperties.ZuulRoute> getPropertiesRoutes() {
        return properties.getRoutes();
    }


    private void freshMapperUrl() {
        publisher.publishEvent(new RoutesRefreshedEvent(defZuulFilter));
    }

    /**
     * init method
     *
     * @param defZuulFilter
     * @param properties
     */
    @Override
    public void init(DefZuulFilter defZuulFilter, ZuulProperties properties) {
        this.defZuulFilter = defZuulFilter;
        this.properties = properties;
        List<RouteEntry> all = urlEntryRepository.findAll();
        for (RouteEntry routeEntry : all) {
            ZuulProperties.ZuulRoute route = Util.toZuulRoute(routeEntry);
            defZuulFilter.addRoute(route);
        }

        showSpeed();
    }

    private void showSpeed() {
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {

                messageWebsocket.sendMessage(new Message(0,count + ""));

                count = 0;
            }
        }, 0, 1000);
    }


}
