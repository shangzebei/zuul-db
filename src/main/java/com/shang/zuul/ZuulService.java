package com.shang.zuul;

import com.shang.zuul.domain.URLEntry;
import com.shang.zuul.repository.URLEntryRepository;
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
public class ZuulService implements ZuulProvider {


    public ZuulFilter zuulFilter;

    @Autowired
    private ApplicationEventPublisher publisher;

    @Autowired
    private URLEntryRepository urlEntryRepository;


    @Override
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


    /**
     * init method
     * @param zuulFilter
     */
    @Override
    public void init(ZuulFilter zuulFilter) {
        this.zuulFilter = zuulFilter;
        List<URLEntry> all = urlEntryRepository.findAll();
        for (URLEntry urlEntry : all) {
            ZuulProperties.ZuulRoute route=new ZuulProperties.ZuulRoute(urlEntry.getUrl(),urlEntry.getLocal());
            zuulFilter.addRoute(route);
        }
    }
}
