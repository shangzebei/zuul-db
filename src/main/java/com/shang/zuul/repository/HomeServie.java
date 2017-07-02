package com.shang.zuul.repository;

import com.shang.zuul.Util;
import com.shang.zuul.ZuulService;
import com.shang.zuul.domain.URLEntry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.zuul.filters.Route;
import org.springframework.cloud.netflix.zuul.filters.ZuulProperties;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by shang-mac on 2017/7/2.
 */
@Service
public class HomeServie {
    @Autowired
    private ZuulService zuulService;
    @Autowired
    private URLEntryRepository urlEntryRepository;

    /**
     * add route
     *
     * @param urlEntry
     * @return
     */
    public boolean add(URLEntry urlEntry) {
        urlEntry = Util.check(urlEntry);
        zuulService.addRoute(new ZuulProperties.ZuulRoute(urlEntry.getUrl(), urlEntry.getLocal()));
        URLEntry byUrl = urlEntryRepository.findByUrl(urlEntry.getUrl());
        if (byUrl == null) {
            URLEntry save = urlEntryRepository.save(urlEntry);
            return save != null;
        }
        return false;
    }

    public boolean change(Long id, String local) {
        URLEntry one = urlEntryRepository.findOne(id);
        if (one == null) {
            List<Route> routes = zuulService.getRoutes();
            for (Route route : routes) {
                if (route.getId().equals(route.getId())) {
                    zuulService.addChangeRoutes(route.getId(), local);
                    return true;
                }
            }
            return false;
        } else {
            zuulService.addChangeRoutes(one.getUrl(), local);
            one.setLocal(local);
            urlEntryRepository.save(one);
            return true;
        }
    }

}
