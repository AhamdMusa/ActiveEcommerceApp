package com.activeitzone.activeecommercecms.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class SearchProduct implements Serializable {
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("thumbnail_image")
    @Expose
    private String thumbnailImage;
    @SerializedName("base_price")
    @Expose
    private Double basePrice;
    @SerializedName("base_discounted_price")
    @Expose
    private Double baseDiscountedPrice;
    @SerializedName("rating")
    @Expose
    private Float rating;
    @SerializedName("links")
    @Expose
    private ProductLinks links;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getThumbnailImage() {
        return thumbnailImage;
    }

    public void setThumbnailImage(String thumbnailImage) {
        this.thumbnailImage = thumbnailImage;
    }

    public Double getBasePrice() {
        return basePrice;
    }

    public void setBasePrice(Double basePrice) {
        this.basePrice = basePrice;
    }

    public Double getBaseDiscountedPrice() {
        return baseDiscountedPrice;
    }

    public void setBaseDiscountedPrice(Double baseDiscountedPrice) {
        this.baseDiscountedPrice = baseDiscountedPrice;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(Float rating) {
        this.rating = rating;
    }

    public ProductLinks getLinks() {
        return links;
    }

    public void setLinks(ProductLinks links) {
        this.links = links;
    }
}
