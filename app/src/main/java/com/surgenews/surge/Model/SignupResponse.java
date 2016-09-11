package com.surgenews.surge.Model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by anishhegde on 11/09/16.
 */
public class SignupResponse {


    @SerializedName("Success")
    public boolean Success;
    @SerializedName("Token")
    public String Token;

    public SignupResponse(boolean success, String token) {
        Success = success;
        Token = token;
    }

    public boolean isSuccess() {
        return Success;
    }

    public void setSuccess(boolean success) {
        Success = success;
    }

    public String getToken() {
        return Token;
    }

    public void setToken(String token) {
        Token = token;
    }
}
