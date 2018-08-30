package com.vakyam.spring.example.vo;

import java.util.Random;

/**
 * Created by tito on 8/26/18.
 */
public class ResponseFactory {

    private static final String seed = "This is a string that seeds String values";

    public static String getString(int length){
        return seed.substring(0,0+length);
    }

    public static int getInt(){
        return new Random().nextInt();
    }

    public static String[] getStringArray(int length){
        String[] hold = new String[length];
        for (int i=0;i<length;i++){

            hold[i] = getString(20);
        }

        return hold;
    }


    public static int[] getIntArray(int length){
        int[] hold = new int[length];
        for (int i=0;i<length;i++){
            hold[i] = getInt();
        }

        return hold;
    }


    public static ResponseVO getResponseVO(){
        ResponseVO resp = new ResponseVO();
        resp.setField1(getString(20));
        resp.setField3(getInt());
        resp.setField4(getStringArray(10));
        resp.setField5(getIntArray(5));
        NestedObjectVO no = new NestedObjectVO();
        no.setField1(getString(20));
        no.setField3(getInt());
        no.setField4(getStringArray(10));
        no.setField5(getIntArray(5));
        resp.setField6(no);

        return resp;
    }
}
