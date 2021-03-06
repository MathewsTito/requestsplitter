package com.vakyam.spring.requestsplitter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Author: Tito Mathews
 * Created On: 08/29/18 6:45 P.M.
 *
 * This is a filter that will
 *
 * Change Log:
 *
 */
@Component
@Order(2)
public class MirroringFilter implements Filter {

    private RemoteServiceProxy remoteServiceProxy;

    private static final String MIRRORED_REQUEST = "MIRRORED_REQUEST";
    private static final String CONTENT_TYPE = "content-type";
    private static final String HTTP_AUTH_TOKEN = "HTTP_AUTH_TOKEN";

    private static final Log logger = LogFactory.getLog(MirroringFilter.class);


    @Autowired
    public MirroringFilter(RemoteServiceProxy rsp){
        remoteServiceProxy = rsp;
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        //Nothing to initialize
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        try {
            mirror((HttpServletRequest) servletRequest);
        } catch (Exception e) {
            logger.warn(e.toString());
        }

        filterChain.doFilter(servletRequest, servletResponse);

    }

    @Override
    public void destroy() {
        logger.debug("destroy() method called!");
    }

    private void mirror(HttpServletRequest request) throws URISyntaxException, IOException {

        logger.debug("Inside mirror()");


        //if this is a mirrored request, don't mirror it again.
        if (request.getHeader(MIRRORED_REQUEST) != null)
            return;

        String method = request.getMethod();

        //If remotehost is not provided.. abort mirroring and return without error
        String remoteHost = remoteServiceProxy.getRemoteHost();
        logger.debug("remotehost="+remoteHost);

        if (remoteHost == null || remoteHost.trim().length() == 0)
            return;

        String requestURI = request.getRequestURI();
        String requestQS = request.getQueryString();
        String uri = remoteHost
                        +requestURI
                        +(requestQS==null?"":"?"+requestQS);

        logger.debug("uri="+uri);
        if (!uri.matches("[a-zA-Z0-9/?&%$:=]++")){
            return;
        }

        Map<String, String> headers = new HashMap<>();
        HttpHeaders httpHeaders = new HttpHeaders();

        Enumeration<String> headerNames = request.getHeaderNames();

        if (headerNames != null) {
            while (headerNames.hasMoreElements()) {
                String name = headerNames.nextElement();
                Enumeration<String> values = request.getHeaders(name);

                while (values.hasMoreElements()) {
                    String value = values.nextElement();
                    headers.put(name, value);
                    httpHeaders.add(name, value);

                }
            }
        }

        InputStream inputStream = request.getInputStream();
        byte[] body = StreamUtils.copyToByteArray(inputStream);

        //Create the request Object
        RequestEntity<byte[]> reqEntity = RequestEntity.method(HttpMethod.resolve(method), new URI(uri))
                .header(HTTP_AUTH_TOKEN, headers.get(HTTP_AUTH_TOKEN))
                .header(MIRRORED_REQUEST,"true")
                .header(CONTENT_TYPE,headers.get(CONTENT_TYPE))
                .body(body);

        //Add all the incoming headers to the request Object
        Iterator<String> i = headers.keySet().iterator();
        while (i.hasNext()) {
            String thisHeaderName = i.next();
            try {
                reqEntity.getHeaders().set(thisHeaderName, headers.get(thisHeaderName));
            } catch (Exception e) {
                logger.debug("name=" + thisHeaderName + "," + headers.get(thisHeaderName));
            }
        }

        byte[] mirrorResponse = new byte[]{};


        logger.debug("Mirroring request to "+uri);
        remoteServiceProxy.execute(reqEntity, mirrorResponse.getClass());

    }
}
