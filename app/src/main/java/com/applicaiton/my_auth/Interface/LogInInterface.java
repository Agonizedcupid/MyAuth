package com.applicaiton.my_auth.Interface;

import com.applicaiton.my_auth.Model.LogInModel;

public interface LogInInterface {

    void loggedIn(String successMessage, LogInModel model);
    void onError(String errorMessage);
}
