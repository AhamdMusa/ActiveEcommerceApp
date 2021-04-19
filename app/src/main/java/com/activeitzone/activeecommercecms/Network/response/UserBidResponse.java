package com.activeitzone.activeecommercecms.Network.response;

import com.activeitzone.activeecommercecms.Models.UserBid;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class UserBidResponse {
    @SerializedName("data")
    @Expose
    private List<UserBid> data = null;
    @SerializedName("success")
    @Expose
    private Boolean success;
    @SerializedName("status")
    @Expose
    private Integer status;

    public List<UserBid> getData() {
        return data;
    }

    public void setData(List<UserBid> data) {
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
