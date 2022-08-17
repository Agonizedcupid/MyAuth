package com.applicaiton.my_auth.Networking.Client;

import com.applicaiton.my_auth.Common.Constant;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {


    private static Retrofit retrofitClient;

    public static Retrofit getRetrofitClient() {
        if (retrofitClient == null) {
            retrofitClient =  new Retrofit.Builder()
                    .baseUrl(Constant.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                    .build();
        }
        return  retrofitClient;
    }
}
