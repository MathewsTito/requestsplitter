package com.vakyam.spring.RequestSplitter;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Author: Tito Mathews
 * Created On: 08/29/18 6:19 P.M.
 *
 * This class wraps the HTTPRequest to allow reading of the HTTP body multiple times.
 *
 * Change Log:
 *
 */

public class MyHttpServletRequestWrapper extends HttpServletRequestWrapper {

    private String _body;

    public MyHttpServletRequestWrapper(HttpServletRequest request) throws IOException {
        super(request);
        _body = "";
        BufferedReader bufferedReader = request.getReader();
        String line;
        while ((line = bufferedReader.readLine()) != null){
            _body += line;
        }
    }

    @Override
    public ServletInputStream getInputStream() throws IOException {
        final ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(_body.getBytes());
        return new ServletInputStream() {

            private ReadListener readListener;

            @Override
            public boolean isFinished() {
                return (byteArrayInputStream.available() <= 0);
            }

            @Override
            public boolean isReady() {
                return true;
            }

            @Override
            public void setReadListener(ReadListener readListener) {
                this.readListener = readListener;
            }

            public int read() throws IOException {
                return byteArrayInputStream.read();
            }
        };
    }

    @Override
    public BufferedReader getReader() throws IOException {
        return new BufferedReader(new InputStreamReader(this.getInputStream()));
    }
}
