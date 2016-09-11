package com.surgenews.surge.Model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by anishhegde on 11/09/16.
 */
public class UploadResponse {

    @SerializedName("Success")
    public boolean Success;

    public UploadResponse(boolean success) {
        Success = success;
    }

    public boolean isSuccess() {
        return Success;
    }

    public void setSuccess(boolean success) {
        Success = success;
    }
}
