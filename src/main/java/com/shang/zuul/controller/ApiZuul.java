package com.shang.zuul.controller;

import com.shang.zuul.service.ZuulService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.zuul.filters.Route;
import org.springframework.cloud.netflix.zuul.filters.ZuulProperties;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by shangzebei on 2017/6/30.
 */
@RestController
public class ApiZuul {
    @Autowired
    private ZuulService zuulService;

    @PostMapping("addRoute")
    public void addRoute() {
        ZuulProperties.ZuulRoute route=new ZuulProperties.ZuulRoute("/chinamall/**","http://127.0.0.1:8888/getRoutes");
        zuulService.addRoute(route);
    }

    @GetMapping("getRoutes")
    public List<Route> getRoute() {
        List<Route> routes = zuulService.getRoutes();
        return routes;
    }

}
