package com.vakyam.spring.vo;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by tito on 8/26/18.
 */
public class ResponseVO {

    @JsonProperty("field1")
    private String field1;
    @JsonProperty("field2")
    private String field2 = null;
    @JsonProperty("field3")
    private int field3;
    @JsonProperty("field4")
    private String[] field4;
    @JsonProperty("field5")
    private int[] field5;
    @JsonProperty("field6")
    private NestedObjectVO field6;

    public String getField1() {
        return field1;
    }

    public void setField1(String field1) {
        this.field1 = field1;
    }

    public String getField2() {
        return field2;
    }

    public void setField2(String field2) {
        this.field2 = field2;
    }

    public int getField3() {
        return field3;
    }

    public void setField3(int field3) {
        this.field3 = field3;
    }

    public String[] getField4() {
        return field4;
    }

    public void setField4(String[] field4) {
        this.field4 = field4;
    }

    public int[] getField5() {
        return field5;
    }

    public void setField5(int[] field5) {
        this.field5 = field5;
    }

    public com.vakyam.spring.vo.NestedObjectVO getField6() {
        return field6;
    }

    public void setField6(com.vakyam.spring.vo.NestedObjectVO field6) {
        this.field6 = field6;
    }

    public String toString(){
        return field1+":"+field2+":"+field3+":"+field4+":"+field5+":"+field6;
    }

}
