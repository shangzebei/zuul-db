package com.shang.zuul.service;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.shang.zuul.domain.Message;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by shangzebei on 2017/8/16.
 */
@Log4j
@Component
public class LogService extends ZuulFilter {

    @Autowired
    private MessageWebsocket messageWebsocket;

    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 1;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() {
        RequestContext ctx = RequestContext.getCurrentContext();
        final HttpServletRequest request = ctx.getRequest();
        log.debug(String.format("%s request to %s", request.getMethod(), request.getRequestURL().toString()));
         messageWebsocket.sendMessage(new Message(1,String.format("%s request to %s", request.getMethod(), request.getRequestURL().toString())));
        return null;
    }
}
