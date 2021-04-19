package com.activeitzone.activeecommercecms.Network.response;

import com.activeitzone.activeecommercecms.Models.ShippingAddress;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ShippingResponse {
    @SerializedName("data")
    @Expose
    private List<ShippingAddress> data = null;
    @SerializedName("success")
    @Expose
    private Boolean success;
    @SerializedName("status")
    @Expose
    private Integer status;

    public List<ShippingAddress> getData() {
        return data;
    }

    public void setData(List<ShippingAddress> data) {
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
