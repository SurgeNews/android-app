package com.surgenews.surge.Model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by anishhegde on 11/09/16.
 */
public class SignUpRequest {

    final String username;
    final String password;

    public SignUpRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
