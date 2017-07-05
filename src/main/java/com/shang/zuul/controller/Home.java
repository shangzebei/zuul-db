package com.shang.zuul.controller;

import com.shang.zuul.service.ZuulService;
import com.shang.zuul.domain.RouteEntry;
import com.shang.zuul.service.HomeServie;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.zuul.filters.Route;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by shangzebei on 2017/7/1.
 */
@Controller
@Log4j
@RequestMapping("route")
public class Home {
    @Autowired
    private ZuulService zuulService;
    @Autowired
    private HomeServie homeServie;

    @GetMapping("getAll")
    @ResponseBody
    private ArrayList<RouteEntry> listAll() {
        List<Route> routes = zuulService.getRoutes();
        ArrayList<RouteEntry> urlEntries = new ArrayList<>();
        for (int i = 1; i <= routes.size(); i++) {
            RouteEntry routeEntry = new RouteEntry();
            Route route = routes.get(i - 1);
            routeEntry.setId(Long.valueOf(i));
            routeEntry.setTitle(route.getId());
            routeEntry.setPath(route.getFullPath());
            routeEntry.setUrl(route.getLocation());
            urlEntries.add(routeEntry);
        }
        return urlEntries;
    }

    @PostMapping("add")
    @ResponseBody
    public ResponseEntity add(@Valid RouteEntry routeEntry, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            log.info(bindingResult.toString());
            return ResponseEntity.ok(Collections.singletonMap("state", false));
        }

        boolean add = homeServie.add(routeEntry);
        return ResponseEntity.ok(Collections.singletonMap("state", add));
    }

    @GetMapping("{id}")
    public ResponseEntity get(@PathVariable int id) {
        Route route = zuulService.getRoutes().get(id);
        return ResponseEntity.ok(route);
    }

    @PostMapping("change")
    public ResponseEntity change(String title, String local,boolean stripPrefix) {
        boolean change = homeServie.change(title, local,stripPrefix);
        return ResponseEntity.ok(Collections.singletonMap("state", change));
    }

    @PostMapping("delete")
    public ResponseEntity zuulDelete(String title) {
        boolean change = homeServie.deleteRoutes(title);
        return ResponseEntity.ok(Collections.singletonMap("state", change));
    }



}
