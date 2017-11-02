package com.lifeapps.myhealth.network;

import com.lifeapps.myhealth.model.User;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by varunbehl on 07/03/17.
 */

interface DataInterface {

    @Headers("Content-type: application/json")
    @POST("user/")
    public Observable saveUser(@Body User text);

    @POST("SpringRest/User/")
    Call<User> createUser(@Body User user);

    @GET("SpringRest/User/")
    Observable<List<User>> getUsers();
}
