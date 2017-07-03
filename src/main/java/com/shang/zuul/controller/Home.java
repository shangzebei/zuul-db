package com.shang.zuul.controller;

import com.shang.zuul.ZuulService;
import com.shang.zuul.domain.URLEntry;
import com.shang.zuul.repository.HomeServie;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.zuul.filters.Route;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by shangzebei on 2017/7/1.
 */
@Controller
@Log4j
public class Home {
    @Autowired
    private ZuulService zuulService;
    @Autowired
    private HomeServie homeServie;
    @GetMapping("/")
    private String list(Model model) {
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
        model.addAttribute("kk", urlEntries);
        return "home";
    }

    @GetMapping("home/getAll")
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

    @PostMapping("home/add")
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

    @PostMapping("home/delete")
    public ResponseEntity delete() {
        return ResponseEntity.ok().build();
    }

    @GetMapping("zuul/{id}")
    public ResponseEntity get(@PathVariable int id) {
        Route route = zuulService.getRoutes().get(id);
        return ResponseEntity.ok(route);
    }

    @PostMapping("zuul/change")
    public ResponseEntity change(Long id, String path) {
        boolean change = homeServie.change(id, path);
        return ResponseEntity.ok(Collections.singletonMap("state", change));
    }
}
