package com.applicaiton.my_auth.ViewModel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.applicaiton.my_auth.Database.DatabaseAdapter;
import com.applicaiton.my_auth.Model.HeaderModel;
import com.applicaiton.my_auth.Networking.ResponseHandler;
import com.applicaiton.my_auth.Repositories.HeaderRepository;

import java.util.List;

public class HeaderViewModel extends ViewModel {
    private MutableLiveData<ResponseHandler<List<HeaderModel>>> listOfHeaders;

    public void init(DatabaseAdapter databaseAdapter) {
        if (listOfHeaders != null) {
            return;
        }
        listOfHeaders = HeaderRepository.getInstance(databaseAdapter).getListOfHeaders();
    }

    public MutableLiveData<ResponseHandler<List<HeaderModel>>> getHeaders () {
        return listOfHeaders;
    }
}
