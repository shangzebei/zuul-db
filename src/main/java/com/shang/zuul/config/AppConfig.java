package com.shang.zuul.config;

import com.shang.zuul.ZuulFilter;
import com.shang.zuul.ZuulProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.netflix.zuul.filters.ZuulProperties;
import org.springframework.cloud.netflix.zuul.filters.discovery.DiscoveryClientRouteLocator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Created by shangzebei on 2017/6/30.
 */
@Configuration
public class AppConfig {


    @Autowired(required = false)
    private ZuulProvider zuulProvider;

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurerAdapter() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**");
            }
        };
    }

    @Bean(value = "discoveryRouteLocator")
    public DiscoveryClientRouteLocator discoveryClientRouteLocator(ServerProperties server, DiscoveryClient discovery, ZuulProperties properties) {
        return new ZuulFilter(server.getServletPath(), discovery, properties, zuulProvider);
    }
}
