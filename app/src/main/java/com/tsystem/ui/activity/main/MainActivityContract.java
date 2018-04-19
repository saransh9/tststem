package com.tsystem.ui.activity.main;

import com.tsystem.data.pojo.ApiData;

public interface MainActivityContract {

    void showLoader();
    void hideLoader();

    void dataFetched(Boolean isErrorFound, ApiData data);
}
