package com.activeitzone.activeecommercecms.Network.response;

import com.activeitzone.activeecommercecms.Models.FlashDeal;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FlashDealResponse {
    @SerializedName("data")
    @Expose
    private FlashDeal flashDeal;
    @SerializedName("success")
    @Expose
    private Boolean success;
    @SerializedName("status")
    @Expose
    private Integer status;

    public FlashDeal getData() {
        return flashDeal;
    }

    public void setData(FlashDeal data) {
        this.flashDeal = data;
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
