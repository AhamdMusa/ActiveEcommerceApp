package com.activeitzone.activeecommercecms.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ProductLinks implements Serializable {
    @SerializedName("details")
    @Expose
    private String details;
    @SerializedName("reviews")
    @Expose
    private String reviews;
    @SerializedName("related")
    @Expose
    private String related;
    @SerializedName("top_from_seller")
    @Expose
    private String topFromSeller;

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getReviews() {
        return reviews;
    }

    public void setReviews(String reviews) {
        this.reviews = reviews;
    }

    public String getRelated() {
        return related;
    }

    public void setRelated(String related) {
        this.related = related;
    }

    public String getTopFromSeller() {
        return topFromSeller;
    }

    public void setTopFromSeller(String topFromSeller) {
        this.topFromSeller = topFromSeller;
    }
}
