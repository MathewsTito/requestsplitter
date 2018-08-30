package com.vakyam.spring.RequestSplitter;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.*;

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

    private byte[] body;

    public MyHttpServletRequestWrapper(HttpServletRequest request) throws IOException {

        super(request);

        InputStream is = request.getInputStream();

        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        int nRead;
        byte[] data = new byte[16384];

        while ((nRead = is.read(data, 0, data.length)) != -1) {
            buffer.write(data, 0, nRead);
        }

        buffer.flush();

        body = buffer.toByteArray();
    }


    @Override
    public ServletInputStream getInputStream() throws IOException {
        final ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(body);
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
