package com.activeitzone.activeecommercecms.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class ProductDetails implements Serializable {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("added_by")
    @Expose
    private String addedBy;
    @SerializedName("user")
    @Expose
    private ProductOwner productOwner;
    @SerializedName("photos")
    @Expose
    private List<String> photos = null;
    @SerializedName("thumbnail_image")
    @Expose
    private String thumbnailImage;
    @SerializedName("featured_image")
    @Expose
    private String featuredImage;
    @SerializedName("flash_deal_image")
    @Expose
    private String flashDealImage;
    @SerializedName("price_lower")
    @Expose
    private Double priceLower;
    @SerializedName("price_higher")
    @Expose
    private Double priceHigher;
    @SerializedName("choice_options")
    @Expose
    private List<ChoiceOption> choiceOptions = null;
    @SerializedName("colors")
    @Expose
    private List<String> colors = null;
    @SerializedName("todays_deal")
    @Expose
    private Integer todaysDeal;
    @SerializedName("featured")
    @Expose
    private Integer featured;
    @SerializedName("current_stock")
    @Expose
    private Integer currentStock;
    @SerializedName("unit")
    @Expose
    private String unit;
    @SerializedName("discount")
    @Expose
    private Double discount;
    @SerializedName("discount_type")
    @Expose
    private String discountType;
    @SerializedName("tax")
    @Expose
    private Double tax;
    @SerializedName("tax_type")
    @Expose
    private String taxType;
    @SerializedName("shipping_type")
    @Expose
    private Object shippingType;
    @SerializedName("shipping_cost")
    @Expose
    private Double shippingCost;
    @SerializedName("number_of_sales")
    @Expose
    private Integer numberOfSales;
    @SerializedName("rating")
    @Expose
    private Float rating;
    @SerializedName("rating_count")
    @Expose
    private Double ratingCount;
    @SerializedName("links")
    @Expose
    private ProductDetailsLinks links;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAddedBy() {
        return addedBy;
    }

    public void setAddedBy(String addedBy) {
        this.addedBy = addedBy;
    }

    public ProductOwner getUser() {
        return productOwner;
    }

    public void setUser(ProductOwner user) {
        this.productOwner = user;
    }

    public List<String> getPhotos() {
        return photos;
    }

    public void setPhotos(List<String> photos) {
        this.photos = photos;
    }

    public String getThumbnailImage() {
        return thumbnailImage;
    }

    public void setThumbnailImage(String thumbnailImage) {
        this.thumbnailImage = thumbnailImage;
    }

    public String getFeaturedImage() {
        return featuredImage;
    }

    public void setFeaturedImage(String featuredImage) {
        this.featuredImage = featuredImage;
    }

    public String getFlashDealImage() {
        return flashDealImage;
    }

    public void setFlashDealImage(String flashDealImage) {
        this.flashDealImage = flashDealImage;
    }

    public Double getPriceLower() {
        return priceLower;
    }

    public void setPriceLower(Double priceLower) {
        this.priceLower = priceLower;
    }

    public Double getPriceHigher() {
        return priceHigher;
    }

    public void setPriceHigher(Double priceHigher) {
        this.priceHigher = priceHigher;
    }

    public List<ChoiceOption> getChoiceOptions() {
        return choiceOptions;
    }

    public void setChoiceOptions(List<ChoiceOption> choiceOptions) {
        this.choiceOptions = choiceOptions;
    }

    public List<String> getColors() {
        return colors;
    }

    public void setColors(List<String> colors) {
        this.colors = colors;
    }

    public Integer getTodaysDeal() {
        return todaysDeal;
    }

    public void setTodaysDeal(Integer todaysDeal) {
        this.todaysDeal = todaysDeal;
    }

    public Integer getFeatured() {
        return featured;
    }

    public void setFeatured(Integer featured) {
        this.featured = featured;
    }

    public Integer getCurrentStock() {
        return currentStock;
    }

    public void setCurrentStock(Integer currentStock) {
        this.currentStock = currentStock;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public Double getDiscount() {
        return discount;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }

    public String getDiscountType() {
        return discountType;
    }

    public void setDiscountType(String discountType) {
        this.discountType = discountType;
    }

    public Double getTax() {
        return tax;
    }

    public void setTax(Double tax) {
        this.tax = tax;
    }

    public String getTaxType() {
        return taxType;
    }

    public void setTaxType(String taxType) {
        this.taxType = taxType;
    }

    public Object getShippingType() {
        return shippingType;
    }

    public void setShippingType(Object shippingType) {
        this.shippingType = shippingType;
    }

    public Double getShippingCost() {
        return shippingCost;
    }

    public void setShippingCost(Double shippingCost) {
        this.shippingCost = shippingCost;
    }

    public Integer getNumberOfSales() {
        return numberOfSales;
    }

    public void setNumberOfSales(Integer numberOfSales) {
        this.numberOfSales = numberOfSales;
    }

    public Float getRating() {
        return rating;
    }

    public void setRating(Float rating) {
        this.rating = rating;
    }

    public Double getRatingCount() {
        return ratingCount;
    }

    public void setRatingCount(Double ratingCount) {
        this.ratingCount = ratingCount;
    }

    public ProductDetailsLinks getLinks() {
        return links;
    }

    public void setLinks(ProductDetailsLinks links) {
        this.links = links;
    }
}
