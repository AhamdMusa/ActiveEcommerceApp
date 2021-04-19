package com.activeitzone.activeecommercecms.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AuctionProductData {
    @SerializedName("data")
    @Expose
    private List<AuctionProduct> data = null;

    public List<AuctionProduct> getData() {
        return data;
    }

    public void setData(List<AuctionProduct> data) {
        this.data = data;
    }
}
