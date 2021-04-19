package com.activeitzone.activeecommercecms.Network.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class CheckWishlistResponse implements Serializable {
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("is_in_wishlist")
    @Expose
    private Boolean isInWishlist;
    @SerializedName("product_id")
    @Expose
    private Integer productId;
    @SerializedName("wishlist_id")
    @Expose
    private Integer wishlistId;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Boolean getIsInWishlist() {
        return isInWishlist;
    }

    public void setIsInWishlist(Boolean isInWishlist) {
        this.isInWishlist = isInWishlist;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Integer getWishlistId() {
        return wishlistId;
    }

    public void setWishlistId(Integer wishlistId) {
        this.wishlistId = wishlistId;
    }
}
