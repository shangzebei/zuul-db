package com.shang.zuul.service;

import com.netflix.zuul.ZuulFilter;
import com.shang.zuul.Util;
import com.shang.zuul.mapper.RouteMapper;
import com.shang.zuul.repository.URLEntryRepository;
import com.shang.zuul.service.ZuulService;
import com.shang.zuul.domain.URLEntry;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.zuul.filters.Route;
import org.springframework.cloud.netflix.zuul.filters.ZuulProperties;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by shang-mac on 2017/7/2.
 */
@Service
@Log4j
public class HomeServie {
    @Autowired
    private ZuulService zuulService;
    @Autowired
    private URLEntryRepository urlEntryRepository;
    @Autowired
    private RouteMapper routeMapper;

    /**
     * add route
     *
     * @param urlEntry
     * @return
     */
    public boolean add(URLEntry urlEntry) {
        urlEntry = Util.check(urlEntry);
        zuulService.addRoute(new ZuulProperties.ZuulRoute(urlEntry.getPath(), urlEntry.getLocal()));
        URLEntry byUrl = urlEntryRepository.findByTitle(urlEntry.getTitle());
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
            zuulService.addChangeRoutes(one.getTitle(), local);
            one.setLocal(local);
            urlEntryRepository.save(one);
            return true;
        }
    }

    @Transactional
    public boolean deleteRoutes(String title) {
        boolean isdelete = false;
        String key = "";
        Map<String, ZuulProperties.ZuulRoute> propertiesRoutes = zuulService.getPropertiesRoutes();
        for (Map.Entry<String, ZuulProperties.ZuulRoute> stringZuulRouteEntry : propertiesRoutes.entrySet()) {
            if (stringZuulRouteEntry.getValue().getId().equals(title)) {
                key = stringZuulRouteEntry.getKey();
                isdelete = true;
            }
        }
        ZuulProperties.ZuulRoute remove = propertiesRoutes.remove(key);
        if (remove != null) {
             urlEntryRepository.deleteByTitle(title);
        }
        zuulService.reSetRoutes(propertiesRoutes);
        return isdelete;
    }

}
