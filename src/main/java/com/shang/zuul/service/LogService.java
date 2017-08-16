package com.shang.zuul.service;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.shang.zuul.domain.Message;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.stereotype.Component;
import org.springframework.util.FileSystemUtils;

import javax.servlet.http.HttpServletRequest;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.FileSystem;

import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.SEND_RESPONSE_FILTER_ORDER;

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
        return FilterConstants.POST_TYPE;
    }

    @Override
    public int filterOrder() {
        return SEND_RESPONSE_FILTER_ORDER-1;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() {
        RequestContext ctx = RequestContext.getCurrentContext();
        final HttpServletRequest request = ctx.getRequest();
        log.info(String.format("%s request to %s", request.getMethod(), request.getRequestURL().toString()));
        InputStream responseDataStream = ctx.getResponseDataStream();
        try {
            byte[] b=new byte[responseDataStream.available()];
            responseDataStream.read(b);
            log.info(new String(b,"UTF-8"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        messageWebsocket.sendMessage(new Message(1,String.format("%s request to %s", request.getMethod(), request.getRequestURL().toString())));

        return null;
    }
}
