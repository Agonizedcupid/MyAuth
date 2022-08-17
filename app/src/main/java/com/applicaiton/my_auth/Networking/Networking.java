package com.applicaiton.my_auth.Networking;

import com.applicaiton.my_auth.Interface.Apis;
import com.applicaiton.my_auth.Interface.LogInInterface;
import com.applicaiton.my_auth.Model.LogInModel;
import com.applicaiton.my_auth.Networking.Client.RetrofitClient;
import com.google.gson.JsonArray;

import org.json.JSONArray;
import org.json.JSONObject;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.schedulers.Schedulers;
import okhttp3.ResponseBody;

public class Networking {

    private CompositeDisposable logInDisposable = new CompositeDisposable();
    Apis apis;
    public Networking() {
        apis = RetrofitClient.getRetrofitClient().create(Apis.class);
    }

    public void checkLoggedIn(String userName, int pinCode, LogInInterface logInInterface) {
        logInDisposable.add(apis.attemptLogIn(userName,pinCode)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ResponseBody>() {
                    @Override
                    public void accept(ResponseBody responseBody) throws Throwable {
                        JSONArray root = new JSONArray(responseBody.string());
                        if (root.length() <=0) {
                            logInInterface.onError("Invalid Credential!");
                        } else {
                            JSONObject single = root.getJSONObject(0);
                            String userName = single.getString("UserName");
                            int userId = single.getInt("UserID");
                            int groupId = single.getInt("groupID");
                            int companyId = single.getInt("companyID");

                            LogInModel model = new LogInModel(userId, userName, groupId, companyId);
                            logInInterface.loggedIn("success", model);
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Throwable {
                        logInInterface.onError(""+throwable.getMessage());
                    }
                })
        );
    }

}
