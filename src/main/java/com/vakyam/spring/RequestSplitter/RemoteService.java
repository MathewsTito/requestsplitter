package com.vakyam.spring.RequestSplitter;

import org.bouncycastle.crypto.RuntimeCryptoException;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by tito on 8/26/18.
 */
@Component("remoteService")
public class RemoteService {

    RestTemplate restTemplate = null;

    @Async
    public void execute(RequestEntity r, Class reponseType){
        try {
            Thread.sleep(10000);

        } catch (InterruptedException e) {
            throw new RuntimeException(e.getMessage());
        }
        ResponseEntity resp = getRestTemplate().exchange(r,reponseType);

    }

    private RestTemplate getRestTemplate(){
        if (restTemplate != null)
            return restTemplate;

        restTemplate = new RestTemplate();
        List<ClientHttpRequestInterceptor> interceptors = restTemplate.getInterceptors();
        if (interceptors == null || CollectionUtils.isEmpty(interceptors))
            interceptors = new ArrayList<>();

        interceptors.add(new RestTemplateHeaderModifierInterceptor());
        restTemplate.setInterceptors(interceptors);

        return restTemplate;
    }
}
