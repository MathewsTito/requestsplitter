package com.vakyam.spring.example        ;

import com.vakyam.spring.example.vo.ResponseFactory;
import com.vakyam.spring.example.vo.ResponseVO;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by tito on 8/26/18.
 */
@RestController
public class Controller {

    private static final Log logger = LogFactory.getLog(Controller.class);

    @RequestMapping("/update")
    public ResponseVO update(@RequestBody ResponseVO  request){

        logger.debug("Request Received from.."+request);

        return ResponseFactory.getResponseVO();
    }

    @RequestMapping("/read")
    public ResponseVO read(@RequestParam String  request){

        logger.debug("Request Received from.."+request);

        return ResponseFactory.getResponseVO();
    }
}
