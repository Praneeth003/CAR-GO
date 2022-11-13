package com.cargobackend.pojo.request;

public class VariantImageInfo {
    String imageType;
    String imageUri;

    public String getImageType() {
        return imageType;
    }

    public void setImageType(String imageType) {
        this.imageType = imageType;
    }

    public String getImageUri() {
        return imageUri;
    }

    public void setImageUri(String imageUri) {
        this.imageUri = imageUri;
    }

    @Override
    public String toString() {
        return "VariantImageInfo{" +
                "imageType='" + imageType + '\'' +
                ", imageUri='" + imageUri + '\'' +
                '}';
    }
}
