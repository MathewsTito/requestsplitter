package com.vakyam.spring.RequestSplitter;

import com.netflix.zuul.context.RequestContext;
import org.apache.commons.io.input.CloseShieldInputStream;
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
 * Created by tito on 8/26/18.
 */
@Component
@Order(2)
public class MirroringFilter implements Filter {

    @Autowired
    RemoteService remoteService;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        try {
            mirror((HttpServletRequest) servletRequest);
        } catch (Throwable t) {

        }

        filterChain.doFilter(servletRequest, servletResponse);

    }

    @Override
    public void destroy() {

    }

    private void mirror(HttpServletRequest request) throws URISyntaxException, IOException {

        System.out.println("Inside mirror()");

        if (request.getHeader("mirrored") != null)
            return;

        String method = request.getMethod();

        String uri = "http://localhost:9000/update";

        Map<String, String> headers = new HashMap<>();
        HttpHeaders HTTPheaders = new HttpHeaders();

        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String name = headerNames.nextElement();
            Enumeration<String> values = request.getHeaders(name);

            while (values.hasMoreElements()) {
                String value = values.nextElement();
                headers.put(name, value);
                HTTPheaders.add(name, value);

            }
        }

        headers.put("HTTP_AUTH_TOKEN", "dsjfsdjlfjsdlfjlsdj");

        InputStream inputStream = request.getInputStream();
        byte[] body = StreamUtils.copyToByteArray(inputStream);

        //Create the request Object
        RequestEntity<byte[]> reqEntity = RequestEntity.method(HttpMethod.resolve(method), new URI(uri))
                .header("HTTP_AUTH_TOKEN", "password")
                .header("mirrored","true")
                .body(body);

        //Add all the incoming headers to the request Object
        Iterator<String> i = headers.keySet().iterator();
        while (i.hasNext()) {
            String thisHeaderName = i.next();
            //reqEntity.HeadersBuilder.header(thisHeaderName, headers.get(thisHeaderName));
            try {
                reqEntity.getHeaders().set(thisHeaderName, headers.get(thisHeaderName));
            } catch (Throwable e) {
                System.out.println("name=" + thisHeaderName + "," + headers.get(thisHeaderName));
            }
        }

        byte[] mirrorResponse = new byte[]{};

        //RemoteService rs = (RemoteService) getContext().getBean("remoteService");

        remoteService.execute(reqEntity, mirrorResponse.getClass());

    }
}
