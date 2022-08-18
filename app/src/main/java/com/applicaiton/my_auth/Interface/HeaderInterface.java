package com.applicaiton.my_auth.Interface;

import com.applicaiton.my_auth.Model.HeaderModel;
import com.applicaiton.my_auth.Model.LineModel;

import java.util.List;

public interface HeaderInterface {

    void gotHeadersNLines(List<HeaderModel> list, List<LineModel> listOfLines);
    void error(String errorMessage);
}
