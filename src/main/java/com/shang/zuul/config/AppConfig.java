package com.shang.zuul.config;

import com.shang.zuul.ZuulFilter;
import com.shang.zuul.ZuulService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.netflix.zuul.filters.ZuulProperties;
import org.springframework.cloud.netflix.zuul.filters.discovery.DiscoveryClientRouteLocator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by shangzebei on 2017/6/30.
 */
@Configuration
public class AppConfig {
    @Autowired
    ServerProperties server;
    @Autowired
    DiscoveryClient discovery;
    @Autowired
    ZuulProperties properties;
    @Autowired
    private ZuulService zuulService;

    @Bean(value = "discoveryRouteLocator")
    public DiscoveryClientRouteLocator discoveryClientRouteLocator() {
        return new ZuulFilter(server.getServletPath(), discovery, properties,zuulService);
    }
}
