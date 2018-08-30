package com.vakyam.spring.example.vo;

/**
 * Created by tito on 8/26/18.
 */

public class NestedObjectVO {
    private String field1;
    private String field2 = null;
    private int field3;
    private String[] field4;
    private int[] field5;
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

    public NestedObjectVO getField6() {
        return field6;
    }

    public void setField6(NestedObjectVO field6) {
        this.field6 = field6;
    }
}
