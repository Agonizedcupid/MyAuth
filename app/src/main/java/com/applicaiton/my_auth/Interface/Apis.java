package com.applicaiton.my_auth.Interface;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import okhttp3.ResponseBody;
import retrofit2.Response;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface Apis {

    @FormUrlEncoded
    @POST("users.php")
    Observable<ResponseBody> attemptLogIn(@Field("username") String userName, @Field("pincode") int pinCode);

    @GET("AuthHeadersAndLines.php")
    Observable<ResponseBody> getPendingAuth ();
}
