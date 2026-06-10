package org.example.models;

public class BannerSlide {
    private int id;
    private String imageUrl;
    private String altText;
    private String linkUrl;
    private int orden;

    public BannerSlide(int id, String imageUrl, String altText, String linkUrl, int orden) {
        this.id = id;
        this.imageUrl = imageUrl;
        this.altText = altText;
        this.linkUrl = linkUrl;
        this.orden = orden;
    }

    public int getId() {
        return id;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getAltText() {
        return altText;
    }

    public String getLinkUrl() {
        return linkUrl;
    }

    public int getOrden() {
        return orden;
    }
}
