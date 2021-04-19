package com.activeitzone.activeecommercecms.Network.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class VariantResponse {

    @SerializedName("product_id")
    @Expose
    private Integer productId;
    @SerializedName("variant")
    @Expose
    private String variant;
    @SerializedName("price")
    @Expose
    private Double price;
    @SerializedName("in_stock")
    @Expose
    private Boolean inStock;

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public String getVariant() {
        return variant;
    }

    public void setVariant(String variant) {
        this.variant = variant;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Boolean getInStock() {
        return inStock;
    }

    public void setInStock(Boolean inStock) {
        this.inStock = inStock;
    }

}