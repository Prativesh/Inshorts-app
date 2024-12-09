package com.example.videoapp.models.dataModels.api.response;

import com.google.gson.annotations.SerializedName;

public class ManageFavouriteMoviesResponseModel {

    @SerializedName("success")
    private Boolean success;

    @SerializedName("status_code")
    private int statusCode;

    @SerializedName("status_message")
    private String statusMessage;

    @SerializedName("total_pages")
    private int totalPages;

    public String getStatusMessage() {
        return statusMessage;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public Boolean getSuccess() {
        return success;
    }

    public int getTotalPages() {
        return totalPages;
    }
}
