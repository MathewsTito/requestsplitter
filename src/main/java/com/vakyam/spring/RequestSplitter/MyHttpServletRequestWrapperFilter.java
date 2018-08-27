package com.vakyam.spring.RequestSplitter;

import com.netflix.zuul.filters.FilterRegistry;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by tito on 8/26/18.
 */
@Component
@Order(1)
public class MyHttpServletRequestWrapperFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        try {
            HttpServletRequest properRequest = ((HttpServletRequest) servletRequest);
            MyHttpServletRequestWrapper wrappedRequest = new MyHttpServletRequestWrapper(properRequest);
            servletRequest = wrappedRequest;

            filterChain.doFilter(servletRequest, servletResponse);
        } catch (Exception exception) {
            ((HttpServletResponse) servletResponse).sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "ERROR");
            System.out.println("MyHttpServletRequestWrapperFilter.doFilter() exception: "+exception.getMessage());
        }
    }

    @Override
    public void destroy() {

    }
}
