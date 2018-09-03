
package com.vakyam.spring.requestsplitter;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.servlet.FilterChain;
import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

/**
 * Created by tito on 9/2/18.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class MirroringFilterTest {

    @Test
    public void testFilter(){

        try {

            RemoteServiceProxy mockrsp = Mockito.mock(RemoteServiceProxy.class);

            MirroringFilter filterToTest = new MirroringFilter(mockrsp);


            HttpServletRequest mockitoReq = Mockito.mock(HttpServletRequest.class);
            HttpServletResponse mockitoRes = Mockito.mock(HttpServletResponse.class);
            FilterChain mockFilterChain = Mockito.mock(FilterChain.class);

            Mockito.when(mockrsp.getRemoteHost()).thenReturn("http://localhost:8080");


            ServletInputStream servletInputStream =new ServletInputStream(){

                InputStream is = new ByteArrayInputStream(Charset.forName("UTF-16").encode("{\"field1\" : \"value1\"}").array());

                @Override
                public void setReadListener(ReadListener readListener){

                }

                @Override
                public boolean isFinished() {
                    try {
                        return (is.available() <= 0);
                    } catch (IOException e){
                        return true;
                    }
                }

                @Override
                public boolean isReady() {
                    return true;
                }

                @Override
                public int read() throws IOException {
                    return is.read();
                }
            };

            Mockito.when(mockitoReq.getInputStream()).thenReturn(servletInputStream);

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

        RemoteServiceProxy rsp = Mockito.mock(RemoteServiceProxy.class);

        MirroringFilter filterToTest = new MirroringFilter(rsp);

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

        RemoteServiceProxy rsp = Mockito.mock(RemoteServiceProxy.class);

        MirroringFilter filterToTest = new MirroringFilter(rsp);


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
