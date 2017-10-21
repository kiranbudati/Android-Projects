package com.journaldev.loginphpmysql;

import retrofit.Callback;
import retrofit.client.Response;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;

public interface RegisterAPI {
    @FormUrlEncoded
    @POST("/people.php")
    public void insertUser(
            @Field("name") String name,
            @Field("age") String age,
            @Field("vial_id") String vial_id,
            Callback<Response> callback);
}