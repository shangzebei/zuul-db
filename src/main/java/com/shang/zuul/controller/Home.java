package com.shang.zuul.controller;

import com.shang.zuul.domain.URLEntry;
import com.shang.zuul.repository.URLEntryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

/**
 * Created by shangzebei on 2017/7/1.
 */
@Controller
public class Home {
    @Autowired
    private URLEntryRepository urlEntryRepository;
    @GetMapping("/")
    private String list(Model model) {
        List<URLEntry> all = urlEntryRepository.findAll();
        model.addAttribute("kk", all);
        return "list";
    }
}
