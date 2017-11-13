package com.minecraft.packer.GalleryWidget;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by user on 11/1/2017.
 */

public class ImageName {
    @SerializedName("name")
    @Expose
    private String imageName;

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }
}
