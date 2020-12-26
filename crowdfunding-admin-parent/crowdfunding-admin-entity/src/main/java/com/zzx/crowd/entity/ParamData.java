package com.zzx.crowd.entity;

import java.util.List;

/**
 * @author zzx
 * @date 2020-12-26 14:45
 */
public class ParamData {

    private List<Integer> array;

    public ParamData() {
    }

    public ParamData(List<Integer> array) {
        this.array = array;
    }

    public List<Integer> getArray() {
        return array;
    }

    public void setArray(List<Integer> array) {
        this.array = array;
    }

    @Override
    public String toString() {
        return "ParamData{" +
                "array=" + array +
                '}';
    }
}
