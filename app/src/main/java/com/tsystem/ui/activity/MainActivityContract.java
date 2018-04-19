package com.tsystem.ui.activity;

import com.tsystem.data.pojo.ApiData;

public interface MainActivityContract {

    void showLoader();
    void hideLoader();

    void dataFetched(Boolean isErrorFound, ApiData data);
}
