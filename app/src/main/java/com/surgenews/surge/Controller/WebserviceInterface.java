package com.surgenews.surge.Controller;

import com.surgenews.surge.Model.SignUpRequest;
import com.surgenews.surge.Model.SignupResponse;
import com.surgenews.surge.Utils.WebserviceUtils;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;


public interface WebserviceInterface {

//    @GET(WebserviceUtils.ENDPOINT_GET_ALL_REPS_BY_STATE)
//    Call<String> getPolitList(@Query(WebserviceUtils.QUERY_STATE) String state
//            , @Query(WebserviceUtils.QUERY_OUTPUT) String outputType);

    @Headers( "Content-Type: application/json" )
    @POST(WebserviceUtils.ENDPOINT_SIGN_UP)
    Call<SignupResponse> signUp(@Body SignUpRequest body);

    @Headers( "Content-Type: application/json" )
    @POST(WebserviceUtils.ENDPOINT_LOGIN)
    Call<SignupResponse> login(@Body SignUpRequest body);

}
