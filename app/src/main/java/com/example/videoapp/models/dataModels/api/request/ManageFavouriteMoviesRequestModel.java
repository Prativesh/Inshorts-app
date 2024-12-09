package com.example.videoapp.models.dataModels.api.request;

import com.google.gson.annotations.SerializedName;

public class ManageFavouriteMoviesRequestModel {

    @SerializedName("media_type")
    private String mediaType;

    @SerializedName("media_id")
    private int mediaId;

    @SerializedName("favorite")
    private Boolean favorite;

    public ManageFavouriteMoviesRequestModel(String mediaType, int mediaId, boolean favorite) {
        this.mediaType = mediaType;
        this.mediaId = mediaId;
        this.favorite = favorite;
    }
}
