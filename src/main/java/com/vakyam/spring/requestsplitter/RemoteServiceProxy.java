package com.vakyam.spring.requestsplitter;

import org.springframework.http.RequestEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.client.RestTemplate;

/**
 * Created by tito on 8/26/18.
 */

public abstract class RemoteServiceProxy {

    RestTemplate restTemplate = null;

    @Async
    public void execute(RequestEntity r, Class reponseType){
        RestTemplate rt = getRestTemplate();
        rt.exchange(r,reponseType);
    }

    private RestTemplate getRestTemplate(){
        if (restTemplate != null)
            return restTemplate;

        restTemplate = new RestTemplate();

        return restTemplate;
    }

    public abstract String getRemoteHost();

}
