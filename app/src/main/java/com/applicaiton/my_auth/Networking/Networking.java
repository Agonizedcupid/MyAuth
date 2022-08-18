package com.applicaiton.my_auth.Networking;

import android.util.Log;

import com.applicaiton.my_auth.Database.DatabaseAdapter;
import com.applicaiton.my_auth.Interface.Apis;
import com.applicaiton.my_auth.Interface.HeaderInterface;
import com.applicaiton.my_auth.Interface.LineInterface;
import com.applicaiton.my_auth.Interface.LogInInterface;
import com.applicaiton.my_auth.Model.HeaderModel;
import com.applicaiton.my_auth.Model.LineModel;
import com.applicaiton.my_auth.Model.LogInModel;
import com.applicaiton.my_auth.Model.QueueModel;
import com.applicaiton.my_auth.Networking.Client.RetrofitClient;
import com.google.gson.JsonArray;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.schedulers.Schedulers;
import okhttp3.ResponseBody;

public class Networking {

    private CompositeDisposable logInDisposable = new CompositeDisposable();
    private CompositeDisposable headerDisposable = new CompositeDisposable();
    private CompositeDisposable lineDisposable = new CompositeDisposable();
    Apis apis;

    public Networking() {
        apis = RetrofitClient.getRetrofitClient().create(Apis.class);
    }

    public void checkLoggedIn(String userName, int pinCode, LogInInterface logInInterface) {
        logInDisposable.add(apis.attemptLogIn(userName, pinCode)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ResponseBody>() {
                    @Override
                    public void accept(ResponseBody responseBody) throws Throwable {
                        JSONArray root = new JSONArray(responseBody.string());
                        if (root.length() <= 0) {
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
                        if (!logInDisposable.isDisposed()) {
                            logInDisposable.dispose();
                            logInDisposable.clear();
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Throwable {
                        logInInterface.onError("" + throwable.getMessage());
                    }
                })
        );


    }

    private void insertPendingInLocal(HeaderInterface headerInterface) {
        List<HeaderModel> listOfHeaders = new ArrayList<>();
        List<LineModel> listOfLines = new ArrayList<>();
        headerDisposable.add(apis.getPendingAuth()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ResponseBody>() {
                    @Override
                    public void accept(ResponseBody responseBody) throws Throwable {
                        try {
                            JSONObject root = new JSONObject(responseBody.string());
                            JSONArray headers = root.getJSONArray("MessageHeaders");
                            if (headers.length() <= 0) {
                                Log.d("HEADER_API", "accept: No headers found");
                                headerInterface.error("No Header data found");
                            } else {
                                listOfHeaders.clear();
                                for (int i = 0; i < headers.length(); i++) {
                                    JSONObject single = headers.getJSONObject(i);
                                    String intHeaderID = single.getString("intHeaderID");
                                    String strMessage = single.getString("strMessage");
                                    JSONObject dateObj = single.getJSONObject("dteTimeCreated");
                                    String date = dateObj.getString("date");
                                    String strUserName = single.getString("strUserName");
                                    String strCell = single.getString("strCell");
                                    String strEmail = single.getString("strEmail");
                                    int companyID = single.getInt("companyID");
                                    int groupID = single.getInt("groupID");
                                    HeaderModel model = new HeaderModel(
                                            intHeaderID,
                                            strMessage,
                                            date,
                                            strUserName,
                                            strCell,
                                            strEmail,
                                            companyID,
                                            groupID
                                    );

                                    listOfHeaders.add(model);
                                }
                                //headerInterface.gotHeadersNLines(listOfHeaders, null);
                            }

                            JSONArray lines = root.getJSONArray("MessageLines");
                            if (lines.length() <= 0) {

                            } else {
                                listOfLines.clear();
                                for (int i = 0; i < lines.length(); i++) {
                                    JSONObject single = lines.getJSONObject(i);
                                    String intAuthLineId = single.getString("intAuthLineId");
                                    String strLineMessage1 = single.getString("strLineMessage1");
                                    String strLineMessage2 = single.getString("strLineMessage2");
                                    String strLineMessage3 = single.getString("strLineMessage3");
                                    String blnIsApproved = single.getString("blnIsApproved");
                                    String intHeaderID = single.getString("intHeaderID");

                                    LineModel model = new LineModel(intAuthLineId, strLineMessage1, strLineMessage2, strLineMessage3, blnIsApproved, intHeaderID);
                                    listOfLines.add(model);
                                }
                            }
                            headerInterface.gotHeadersNLines(listOfHeaders, listOfLines);
                        } catch (Exception e) {
                            headerInterface.error("Exception: " + e.getMessage());
                            Log.d("HEADER_API", "accept: " + e.getMessage());
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Throwable {
                        Log.d("HEADER_API", "accept: " + throwable.getMessage());
                        headerInterface.error("Throwable: " + throwable.getMessage());
                    }
                }));
    }


    public void getPending(DatabaseAdapter databaseAdapter, HeaderInterface headerInterface) {
        if (databaseAdapter.getHeaders().size() <= 0) {
            insertPendingInLocal(new HeaderInterface() {
                @Override
                public void gotHeadersNLines(List<HeaderModel> list, List<LineModel> listOfLines) {
                    insertHeaderIntoLocal(list, databaseAdapter);
                    insertLinesIntoLocal(listOfLines, databaseAdapter);
                    headerInterface.gotHeadersNLines(list, listOfLines);
                }

                @Override
                public void error(String errorMessage) {
                    headerInterface.error(errorMessage);
                }
            });
            //getPending(databaseAdapter, headerInterface);
        } else {
            headerInterface.gotHeadersNLines(databaseAdapter.getHeaders(), databaseAdapter.getLines());
        }
    }

    private void insertLinesIntoLocal(List<LineModel> listOfLines, DatabaseAdapter databaseAdapter) {

        Observable<LineModel> observable = Observable.fromIterable(listOfLines);

        Observer observer = new Observer() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onNext(Object o) {
                LineModel model = (LineModel) o;
                databaseAdapter.insertLines(model);
            }

            @Override
            public void onError(@NonNull Throwable e) {
                Log.d("INSERT_HEADER", "onComplete: " + e.getMessage());
            }

            @Override
            public void onComplete() {
                Log.d("INSERT_HEADER", "onComplete: ");
            }
        };

        observable.subscribe(observer);
    }

    private void insertHeaderIntoLocal(List<HeaderModel> list, DatabaseAdapter databaseAdapter) {
        Observable<HeaderModel> observable = Observable.fromIterable(list);

        Observer observer = new Observer() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onNext(Object o) {
                HeaderModel model = (HeaderModel) o;
                databaseAdapter.insertHeaders(model);
            }

            @Override
            public void onError(@NonNull Throwable e) {
                Log.d("INSERT_HEADER", "onComplete: " + e.getMessage());
            }

            @Override
            public void onComplete() {
                Log.d("INSERT_HEADER", "onComplete: ");
            }
        };

        observable.subscribe(observer);
    }

    public void getLines(DatabaseAdapter databaseAdapter, LineInterface lineInterface, String headerId) {
        List<LineModel> list = new ArrayList<>();
        if (databaseAdapter.getHeaders().size() <= 0) {
            lineInterface.error("No Lines found!");
        } else {
            list.clear();
            list = databaseAdapter.getLinesByHeaderId(headerId);
            lineInterface.gotLines(list);
        }
    }

    public String insertQueue(DatabaseAdapter databaseAdapter, QueueModel model) {
        long id = databaseAdapter.insertQueue(model);
        if (id > 0) {
            return model.getIntAuthLineId();
        } else {
            return "-777";
        }
    }

    private CompositeDisposable postDisposable = new CompositeDisposable();

    public void postDirectToServer(List<QueueModel> list, DatabaseAdapter databaseAdapter) {

        postDisposable.add(apis.postQueue(list)
                .subscribeOn(Schedulers.io())
                .subscribe(new Consumer<ResponseBody>() {
                    @Override
                    public void accept(ResponseBody responseBody) throws Throwable {
                        Log.d("POST_RESPONSE", "accept: " + responseBody.string());
                        for (QueueModel model : list) {
                            long id = databaseAdapter.deleteQueues(model.getIntAuthLineId());
                            //long id = databaseAdapter.deleteLines(model.getIntAuthLineId());
                            if (id > 0) {
                                Log.d("POST_RESPONSE", "accept: Posted successfully! " + model.getIntAuthLineId());
                            } else {
                                Log.d("POST_RESPONSE", "accept: Unable to post! "+ model.getIntAuthLineId());
                            }
                        }

                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Throwable {
                        Log.d("POST_RESPONSE", "Exception: " + throwable.getMessage());
                    }
                }));


    }

}
