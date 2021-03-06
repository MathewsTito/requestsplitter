package com.vakyam.spring.requestsplitter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Author: Tito Mathews
 * Created On: 08/29/18 6:38 P.M.
 *
 * This is a ServletFilter that will wrap the ServletRequest in MyHttpServletRequestWrapper
 * on its way in.
 *
 * Change Log:
 *
 */
@Component
@Order(1)
public class MyHttpServletRequestWrapperFilter implements Filter {

    private static final Log logger = LogFactory.getLog(MirroringFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        //Nothing to configure
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
            logger.error("MyHttpServletRequestWrapperFilter.doFilter() exception: "+exception.getMessage());
        }
    }

    @Override
    public void destroy() {
        //Nothing to cleanup
    }
}
