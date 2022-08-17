package com.applicaiton.my_auth.Networking;

import androidx.annotation.NonNull;

public class ResponseHandler<T> {

    public final Status status;
    public final T response;
    public final String errorMessage;

    public ResponseHandler(Status status, T response, String errorMessage) {
        this.status = status;
        this.response = response;
        this.errorMessage = errorMessage;
    }

    public static <T> ResponseHandler<T> success (@NonNull T response) {
        return new ResponseHandler<>(Status.SUCCESS, response, null);
    }

    public static <T> ResponseHandler<T> error(String message, @NonNull T response) {
        return new ResponseHandler<>(Status.ERROR, response,message);
    }

    public static <T> ResponseHandler<T> loading(@NonNull T response) {
        return new ResponseHandler<>(Status.LOADING,response, null);
    }

    public enum Status{SUCCESS,ERROR,LOADING}
}
