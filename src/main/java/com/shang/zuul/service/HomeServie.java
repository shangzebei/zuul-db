package com.shang.zuul.service;

import com.shang.zuul.Util;
import com.shang.zuul.domain.URLEntry;
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

    public boolean change(String title, String local) {
        URLEntry one = urlEntryRepository.findByTitle(title);
        String key = null;
        Map<String, ZuulProperties.ZuulRoute> routes = zuulService.getPropertiesRoutes();
        for (Map.Entry<String, ZuulProperties.ZuulRoute> stringZuulRouteEntry : routes.entrySet()) {
            if (stringZuulRouteEntry.getValue().getId().equals(title)) {
                key = stringZuulRouteEntry.getKey();
                break;
            }
        }
        if (key == null) return false;
        routes.get(key).setLocation(local);
        zuulService.reSetRoutes(routes);
        if (one != null) {
            one.setLocal(local);
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
