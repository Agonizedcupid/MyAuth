package com.applicaiton.my_auth.Interface;

import com.applicaiton.my_auth.Model.LineModel;

import java.util.List;

public interface LineInterface {

    void gotLines (List<LineModel> list);
    void error(String errorMessage);
}
