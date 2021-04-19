package com.activeitzone.activeecommercecms.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ShopLink {
    @SerializedName("featured")
    @Expose
    private String featured;
    @SerializedName("top")
    @Expose
    private String top;
    @SerializedName("new")
    @Expose
    private String _new;
    @SerializedName("all")
    @Expose
    private String all;
    @SerializedName("brands")
    @Expose
    private String brands;

    public String getFeatured() {
        return featured;
    }

    public void setFeatured(String featured) {
        this.featured = featured;
    }

    public String getTop() {
        return top;
    }

    public void setTop(String top) {
        this.top = top;
    }

    public String getNew() {
        return _new;
    }

    public void setNew(String _new) {
        this._new = _new;
    }

    public String getAll() {
        return all;
    }

    public void setAll(String all) {
        this.all = all;
    }

    public String getBrands() {
        return brands;
    }

    public void setBrands(String brands) {
        this.brands = brands;
    }
}
