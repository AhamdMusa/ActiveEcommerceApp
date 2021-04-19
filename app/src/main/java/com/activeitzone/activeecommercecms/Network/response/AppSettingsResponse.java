package com.activeitzone.activeecommercecms.Network.response;

import com.activeitzone.activeecommercecms.Models.AppSettings;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class AppSettingsResponse implements Serializable {
    @SerializedName("data")
    @Expose
    private List<AppSettings> data = null;
    @SerializedName("success")
    @Expose
    private Boolean success;
    @SerializedName("status")
    @Expose
    private Integer status;

    public List<AppSettings> getData() {
        return data;
    }

    public void setData(List<AppSettings> data) {
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
