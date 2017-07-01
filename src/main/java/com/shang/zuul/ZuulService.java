package com.shang.zuul;

import com.shang.zuul.domain.URLEntry;
import com.shang.zuul.repository.URLEntryRepository;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.zuul.RoutesRefreshedEvent;
import org.springframework.cloud.netflix.zuul.filters.Route;
import org.springframework.cloud.netflix.zuul.filters.ZuulProperties;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

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
    volatile long count = 0;

    {
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                log.info("speed= " + count + "t/s");
                count = 0;
            }
        }, 0, 1000);
    }


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
        freshMapper();

    }

    /**
     * this mothod will reset
     *
     * @param map
     */
    public void addRoutes(Map<String, ZuulProperties.ZuulRoute> map) {
        properties.setRoutes(map);
        zuulFilter.refresh();
        freshMapper();
    }

    /**
     * get the routes
     *
     * @return
     */
    public List<Route> getRoutes() {
        return zuulFilter.getRoutes();
    }



    private void freshMapper() {
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
        List<URLEntry> all = urlEntryRepository.findAll();
        for (URLEntry urlEntry : all) {
            ZuulProperties.ZuulRoute route = new ZuulProperties.ZuulRoute(urlEntry.getUrl(), urlEntry.getLocal());
            zuulFilter.addRoute(route);
        }
    }
}
