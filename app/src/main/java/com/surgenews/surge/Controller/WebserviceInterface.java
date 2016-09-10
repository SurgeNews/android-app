package com.surgenews.surge.Controller;

import com.surgenews.surge.Utils.WebserviceUtils;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;


public interface WebserviceInterface {

    @GET(WebserviceUtils.ENDPOINT_GET_ALL_REPS_BY_STATE)
    Call<String> getPolitList(@Query(WebserviceUtils.QUERY_STATE) String state
            , @Query(WebserviceUtils.QUERY_OUTPUT) String outputType);
}
