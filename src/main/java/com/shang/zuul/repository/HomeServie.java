package com.shang.zuul.repository;

import com.shang.zuul.ZuulService;
import com.shang.zuul.domain.URLEntry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.zuul.filters.ZuulProperties;
import org.springframework.stereotype.Service;

/**
 * Created by shang-mac on 2017/7/2.
 */
@Service
public class HomeServie {
    @Autowired
    private ZuulService zuulService;
    @Autowired
    private URLEntryRepository urlEntryRepository;

    public boolean add(URLEntry urlEntry) {
        zuulService.addRoute(new ZuulProperties.ZuulRoute(urlEntry.getUrl(), urlEntry.getLocal()));
        URLEntry byUrl = urlEntryRepository.findByUrl(urlEntry.getUrl());
        if (byUrl == null) {
            URLEntry save = urlEntryRepository.save(urlEntry);
            return save == null;
        }
        return false;
    }
}
