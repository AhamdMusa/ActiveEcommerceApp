package com.activeitzone.activeecommercecms.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class SubSubCategories implements Serializable {
    @SerializedName("data")
    @Expose
    private List<SubSubCategory> data = null;

    public List<SubSubCategory> getData() {
        return data;
    }

    public void setData(List<SubSubCategory> data) {
        this.data = data;
    }
}
