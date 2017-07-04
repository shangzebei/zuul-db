package com.shang.zuul.mapper;

import org.mapstruct.Mapper;
import org.springframework.cloud.netflix.zuul.filters.Route;
import org.springframework.cloud.netflix.zuul.filters.ZuulProperties;

/**
 * Created by shangzebei on 2017/7/4.
 */
@Mapper(componentModel = "spring")
public interface RouteMapper {
    ZuulProperties.ZuulRoute toZuulRoute(Route route);
}
