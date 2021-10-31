package com.skin.skinapp2.interfaces;

import com.skin.skinapp2.models.HosptialsNearby;
import com.skin.skinapp2.models.SignIn;
import com.skin.skinapp2.models.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface SignUp {
    @POST("/registration")
    Call<User> createUser(@Body User user);

    @POST("/login")
    Call<SignIn> signInUser(@Body SignIn signIn);

    @GET("/hospitals")
    Call<List<HosptialsNearby>> findHospitals();

}
