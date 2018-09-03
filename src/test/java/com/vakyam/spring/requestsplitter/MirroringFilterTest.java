
package com.vakyam.spring.requestsplitter;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.http.HttpHeaders;

import javax.servlet.FilterChain;
import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;

/**
 * Created by tito on 9/2/18.
 */
public class MirroringFilterTest {

    @Test
    public void testFilter(){

        try {
            MirroringFilter filterToTest = new MirroringFilter();

            RemoteServiceProxy mockrsp = Mockito.mock(RemoteServiceProxy.class);

            HttpServletRequest mockitoReq = Mockito.mock(HttpServletRequest.class);
            HttpServletResponse mockitoRes = Mockito.mock(HttpServletResponse.class);
            FilterChain mockFilterChain = Mockito.mock(FilterChain.class);

            Mockito.when(mockrsp.getRemoteHost()).thenReturn("http://localhost:8080");
            Mockito.when(mockitoReq.getInputStream()).thenReturn(new ServletInputStream() {
                @Override
                public boolean isFinished() {
                    return true;
                }

                @Override
                public boolean isReady() {
                    return true;
                }

                @Override
                public void setReadListener(ReadListener readListener) {

                }

                @Override
                public int read() throws IOException {
                    return 0;
                }
            });

            Mockito.when(mockitoReq.getMethod()).thenReturn("POST");
            Mockito.when(mockitoReq.getRequestURI()).thenReturn("/read");
            Mockito.when(mockitoReq.getHeader("MIRRORED_REQUEST")).thenReturn(null);
            Mockito.when(mockitoReq.getQueryString()).thenReturn(null);
            Mockito.when(mockitoReq.getHeaderNames()).thenReturn(null);


            filterToTest.doFilter(mockitoReq, mockitoRes, mockFilterChain);


            Mockito.verify(mockrsp,Mockito.times(1)).execute(Mockito.any(), Mockito.any());

        } catch (Throwable t){
            Assert.fail(t.getMessage());
        }

    }

    @Test
    public void testFilterBypassOnNullHost(){

        MirroringFilter filterToTest = new MirroringFilter();

        RemoteServiceProxy rsp = Mockito.mock(RemoteServiceProxy.class);

        HttpServletRequest mockitoReq = Mockito.mock(HttpServletRequest.class);
        HttpServletResponse mockitoRes = Mockito.mock(HttpServletResponse.class);
        FilterChain mockFilterChain = Mockito.mock(FilterChain.class);

        //Mockito.when(mockitoReq.getHeader("MIRRORED_REQUEST")).thenReturn(null);


        try {
            filterToTest.doFilter(mockitoReq, mockitoRes, mockFilterChain);
        } catch (Throwable t){
            Assert.fail(t.getMessage());
        }

        Mockito.verify(rsp,Mockito.times(0)).execute(Mockito.any(), Mockito.any());

    }

    @Test
    public void testFilterBypassOnHeader(){

        MirroringFilter filterToTest = new MirroringFilter();

        RemoteServiceProxy rsp = Mockito.mock(RemoteServiceProxy.class);

        HttpServletRequest mockitoReq = Mockito.mock(HttpServletRequest.class);
        HttpServletResponse mockitoRes = Mockito.mock(HttpServletResponse.class);
        FilterChain mockFilterChain = Mockito.mock(FilterChain.class);

        Mockito.when(mockitoReq.getHeader("MIRRORED_REQUEST")).thenReturn("true");


        try {
            filterToTest.doFilter(mockitoReq, mockitoRes, mockFilterChain);
        } catch (Throwable t){
            Assert.fail(t.getMessage());
        }

        Mockito.verify(rsp,Mockito.times(0)).execute(Mockito.any(), Mockito.any());

    }
}
