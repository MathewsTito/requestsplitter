package com.vakyam.spring.RequestSplitter;

import com.vakyam.spring.vo.ResponseFactory;
import com.vakyam.spring.vo.ResponseVO;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by tito on 8/26/18.
 */
@RestController
public class Controller {

    @RequestMapping("/update")
    public ResponseVO update(@RequestBody ResponseVO  request){

        System.out.println("Request Received from.."+request);

        return ResponseFactory.getResponseVO();
    }

    @RequestMapping("/read")
    public ResponseVO read(@RequestParam String  request){

        System.out.println("Request Received from.."+request);

        return ResponseFactory.getResponseVO();
    }
}
