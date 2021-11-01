package com.skin.skinapp2.retrofit;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.skin.skinapp2.interfaces.SignUp;
import com.skin.skinapp2.models.HosptialsNearby;
import com.skin.skinapp2.models.Recommendations;
import com.skin.skinapp2.models.SignIn;
import com.skin.skinapp2.models.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiManager {
    private static SignUp signUp;
    private static ApiManager apiManager;

    public ApiManager() {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        Retrofit retrofit = new Retrofit.Builder().baseUrl("http://34.125.192.45:5055").addConverterFactory(GsonConverterFactory.create(gson)).build();
        signUp = retrofit.create(SignUp.class);
    }

    public static ApiManager getInstance(){
        if(apiManager == null){
            apiManager = new ApiManager();
        }
        return apiManager;
    }

    public void createUser(User user, Callback<User> callback) {
        Call<User> userCall = signUp.createUser(user);
        userCall.enqueue(callback);

    }

    public void signInUser(SignIn signIn, Callback<SignIn> callback){
        Call<SignIn> signInCall = signUp.signInUser(signIn);
        signInCall.enqueue(callback);
    }

    public void findHospitals(HosptialsNearby hosptialsNearby, Callback<List<HosptialsNearby>> callback){
        System.out.println("Callback " + callback);
        Call<List<HosptialsNearby>> hosptialsNearbyCall = signUp.findHospitals();
        System.out.println("Hospitals Nearby call: " + hosptialsNearbyCall);
        hosptialsNearbyCall.enqueue(callback);
    }

    public void recommendations(Recommendations recommendations, Callback<List<Recommendations>> callback){
        System.out.println("Callback " + callback);
        Call<List<Recommendations>> recommendationsCall = signUp.getRecommendations(recommendations);
        System.out.println("Recommendations  call: " + recommendationsCall);
        recommendationsCall.enqueue(callback);
    }


}
