package com.shang.zuul.service;

import com.shang.zuul.Util;
import com.shang.zuul.domain.RouteEntry;
import com.shang.zuul.repository.URLEntryRepository;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.zuul.filters.ZuulProperties;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Map;

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
    /**
     * add route
     *
     * @param routeEntry
     * @return
     */
    public boolean add(RouteEntry routeEntry) {
        zuulService.addRoute(Util.toZuulRoute(routeEntry));
        RouteEntry byUrl = urlEntryRepository.findByTitle(routeEntry.getTitle());
        if (byUrl == null) {
            RouteEntry save = urlEntryRepository.save(routeEntry);
            return save != null;
        }
        return false;
    }

    public boolean change(String title, String local, boolean stripPrefix) {
        RouteEntry one = urlEntryRepository.findByTitle(title);
        String key = null;
        Map<String, ZuulProperties.ZuulRoute> routes = zuulService.getPropertiesRoutes();
        for (Map.Entry<String, ZuulProperties.ZuulRoute> stringZuulRouteEntry : routes.entrySet()) {
            if (stringZuulRouteEntry.getValue().getId().equals(title)) {
                key = stringZuulRouteEntry.getKey();
                break;
            }
        }
        if (key == null) return false;
        ZuulProperties.ZuulRoute zuulRoute = routes.get(key);
        zuulRoute.setLocation(local);
        zuulRoute.setStripPrefix(stripPrefix);
        zuulService.reSetRoutes(routes);
        if (one != null) {
            one.setUrl(local);
            urlEntryRepository.save(one);
            return true;
        }
        return true;
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
