package com.activeitzone.activeecommercecms.Network.response;

import com.activeitzone.activeecommercecms.Models.AuctionProduct;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AuctionResponse {
    @SerializedName("data")
    @Expose
    private List<AuctionProduct> data = null;
    @SerializedName("success")
    @Expose
    private Boolean success;
    @SerializedName("status")
    @Expose
    private Integer status;

    public List<AuctionProduct> getData() {
        return data;
    }

    public void setData(List<AuctionProduct> data) {
        this.data = data;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
