package com.activeitzone.activeecommercecms.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class UserBid implements Serializable {
    @SerializedName("auction_product_id")
    @Expose
    private Integer auctionProductId;
    @SerializedName("product")
    @Expose
    private String product;
    @SerializedName("highest_bid")
    @Expose
    private double highestBid;
    @SerializedName("amount")
    @Expose
    private double amount;
    @SerializedName("biddable")
    @Expose
    private Boolean biddable;
    @SerializedName("purchasable")
    @Expose
    private Boolean purchasable;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("auction_product")
    @Expose
    private AuctionProductData auctionProductData;

    public Integer getAuctionProductId() {
        return auctionProductId;
    }

    public void setAuctionProductId(Integer auctionProductId) {
        this.auctionProductId = auctionProductId;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public double getHighestBid() {
        return highestBid;
    }

    public void setHighestBid(double highestBid) {
        this.highestBid = highestBid;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Boolean getBiddable() {
        return biddable;
    }

    public void setBiddable(Boolean biddable) {
        this.biddable = biddable;
    }

    public Boolean getPurchasable() {
        return purchasable;
    }

    public void setPurchasable(Boolean purchasable) {
        this.purchasable = purchasable;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public AuctionProductData getAuctionProduct() {
        return auctionProductData;
    }

    public void setAuctionProduct(AuctionProductData auctionProductData) {
        this.auctionProductData = auctionProductData;
    }
}
