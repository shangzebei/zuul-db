package com.shang.zuul.service;

import com.shang.zuul.ZuulFilter;
import com.shang.zuul.ZuulProvider;
import com.shang.zuul.domain.RouteEntry;
import com.shang.zuul.repository.URLEntryRepository;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.zuul.RoutesRefreshedEvent;
import org.springframework.cloud.netflix.zuul.filters.Route;
import org.springframework.cloud.netflix.zuul.filters.ZuulProperties;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;

import java.io.IOException;
import java.util.*;

/**
 * Created by shangzebei on 2017/6/30.
 */
@Service
@Log4j
public class ZuulService implements ZuulProvider {


    public ZuulFilter zuulFilter;

    @Autowired
    private ApplicationEventPublisher publisher;

    @Autowired
    private URLEntryRepository urlEntryRepository;
    private ZuulProperties properties;

    @Autowired
    private SpeedWebsocket speedWebsocket;


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
        zuulFilter.addRoute(zuulRoute);
        freshMapperUrl();

    }

    /**
     * this mothod will reset
     *
     * @param map
     */
    public void reSetRoutes(Map<String, ZuulProperties.ZuulRoute> map) {
        properties.setRoutes(map);
        zuulFilter.refresh();
        freshMapperUrl();
    }

    /**
     * get the routes
     *
     * @return
     */
    public List<Route> getRoutes() {
        return zuulFilter.getRoutes();
    }

    public Map<String, ZuulProperties.ZuulRoute> getPropertiesRoutes() {
        return properties.getRoutes();
    }


    private void freshMapperUrl() {
        publisher.publishEvent(new RoutesRefreshedEvent(zuulFilter));
    }

    /**
     * init method
     *
     * @param zuulFilter
     * @param properties
     */
    @Override
    public void init(ZuulFilter zuulFilter, ZuulProperties properties) {
        this.zuulFilter = zuulFilter;
        this.properties = properties;
        List<RouteEntry> all = urlEntryRepository.findAll();
        for (RouteEntry routeEntry : all) {
            ZuulProperties.ZuulRoute route = new ZuulProperties
                    .ZuulRoute(routeEntry.getPath(), routeEntry.getUrl());
            zuulFilter.addRoute(route);
        }

        showSpeed();
    }

    private void showSpeed() {
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                if (speedWebsocket.get_session() != null) {
                    try {
                        speedWebsocket.get_session().sendMessage(new TextMessage(count+""));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                count = 0;
            }
        }, 0, 1000);
    }


}
