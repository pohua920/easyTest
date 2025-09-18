package com.tlg.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.dispatcher.ng.filter.StrutsPrepareFilter;

public class XssFilter extends StrutsPrepareFilter {

    /**
     * 改寫 Filter 功能，使用新的 request 物件傳遞
     */
    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {
        // 直接呼叫原有的方式，僅將 request 替換成 處理 xss 之 Request
        //System.out.println("XssFilter doFilter");
        super.doFilter(new XSSRequestWrapper((HttpServletRequest) req), res, chain);
    }
}
