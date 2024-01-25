package com.pmit.ems.api;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface APIRequests {
    @GET("courses")
    Call<ResponseBody> courses();
    @POST("get_lead")
    Call<ResponseBody> getLead(@Query("pagesize") String pagesize,@Query("search_key") String search_key,@Query("listtype") String listtype);

    @POST("followup_get_lead")
    Call<ResponseBody> followupGetLead(@Query("pagesize") String pagesize,@Query("search_key") String search_key);
}
