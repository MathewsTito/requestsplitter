package com.vakyam.spring.requestsplitter;

import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.client.RestTemplate;

/**
 * Created by tito on 8/26/18.
 */

public abstract class RemoteServiceProxy {

    RestTemplate restTemplate = null;

    @Async
    public void execute(RequestEntity r, Class reponseType){
        getRestTemplate().exchange(r,reponseType);
    }

    private RestTemplate getRestTemplate(){
        if (restTemplate != null)
            return restTemplate;

        return restTemplate = new RestTemplate();
    }

    public abstract String getRemoteHost();

}
