package com.vakyam.spring.example;

import com.vakyam.spring.requestsplitter.RemoteServiceProxy;
import org.springframework.stereotype.Component;

/**
 * Author: Tito Mathews
 * Created on: 8/29/18 10:38 PM
 *
 * Change Log:
 **/

@Component("remoteServiceProxy")
public class RemoteServiceProxyImpl extends RemoteServiceProxy {

    @Override
    public String getRemoteHost() {
        return  "http://localhost:9000";
    }
}
