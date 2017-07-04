package com.shang.zuul.controller;

import com.shang.zuul.service.ZuulService;
import com.shang.zuul.domain.URLEntry;
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
    private ArrayList<URLEntry> listAll() {
        List<Route> routes = zuulService.getRoutes();
        ArrayList<URLEntry> urlEntries = new ArrayList<>();
        for (int i = 1; i <= routes.size(); i++) {
            URLEntry urlEntry = new URLEntry();
            Route route = routes.get(i - 1);
            urlEntry.setId(Long.valueOf(i));
            urlEntry.setTitle(route.getId());
            urlEntry.setPath(route.getFullPath());
            urlEntry.setLocal(route.getLocation());
            urlEntries.add(urlEntry);
        }
        return urlEntries;
    }

    @PostMapping("add")
    @ResponseBody
    public ResponseEntity add(@Valid URLEntry urlEntry, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            log.info(bindingResult.toString());
            return ResponseEntity.badRequest()
                    .body(Collections.singletonMap("state", false));
        }

        boolean add = homeServie.add(urlEntry);
        return ResponseEntity.ok(Collections.singletonMap("state", add));
    }

    @GetMapping("{id}")
    public ResponseEntity get(@PathVariable int id) {
        Route route = zuulService.getRoutes().get(id);
        return ResponseEntity.ok(route);
    }

    @PostMapping("change")
    public ResponseEntity change(String title, String local) {
        boolean change = homeServie.change(title, local);
        return ResponseEntity.ok(Collections.singletonMap("state", change));
    }

    @PostMapping("delete")
    public ResponseEntity zuulDelete(String title) {
        boolean change = homeServie.deleteRoutes(title);
        return ResponseEntity.ok(Collections.singletonMap("state", change));
    }



}
