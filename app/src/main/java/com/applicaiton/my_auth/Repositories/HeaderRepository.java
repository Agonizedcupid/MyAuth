package com.applicaiton.my_auth.Repositories;

import androidx.lifecycle.MutableLiveData;

import com.applicaiton.my_auth.Database.DatabaseAdapter;
import com.applicaiton.my_auth.Interface.HeaderInterface;
import com.applicaiton.my_auth.Model.HeaderModel;
import com.applicaiton.my_auth.Model.LineModel;
import com.applicaiton.my_auth.Networking.Networking;
import com.applicaiton.my_auth.Networking.ResponseHandler;

import java.util.List;

public class HeaderRepository {

    private static HeaderRepository headerRepository;

    public static HeaderRepository getInstance(DatabaseAdapter databaseAdapter) {
        synchronized (HeaderRepository.class) {
            if (headerRepository == null) {
                headerRepository = new HeaderRepository(databaseAdapter);
            }
        }
        return headerRepository;
    }

    private DatabaseAdapter databaseAdapter;

    public HeaderRepository(DatabaseAdapter databaseAdapter) {
        this.databaseAdapter = databaseAdapter;
    }

    private MutableLiveData<ResponseHandler<List<HeaderModel>>> listOfHeaders = new MutableLiveData<>();

    public MutableLiveData<ResponseHandler<List<HeaderModel>>> getListOfHeaders() {
        Networking networking = new Networking();
        networking.getPending(databaseAdapter, new HeaderInterface() {
            @Override
            public void gotHeadersNLines(List<HeaderModel> list, List<LineModel> listOfLines) {
                listOfHeaders.setValue(ResponseHandler.success(list));
            }

            @Override
            public void error(String errorMessage) {
                listOfHeaders.setValue(ResponseHandler.error(errorMessage, null));
            }
        });

        return listOfHeaders;
    }
}
