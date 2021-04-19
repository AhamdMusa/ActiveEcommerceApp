package com.activeitzone.activeecommercecms.Models;

import com.activeitzone.activeecommercecms.Network.response.ProductResponse;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FlashDeal {
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("end_date")
    @Expose
    private Long endDate;
    @SerializedName("products")
    @Expose
    private ProductResponse productResponse;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getEndDate() {
        return endDate;
    }

    public void setEndDate(Long endDate) {
        this.endDate = endDate;
    }

    public ProductResponse getProducts() {
        return productResponse;
    }

    public void setProducts(ProductResponse products) {
        this.productResponse = products;
    }
}
