package com.tsystem.ui.activity;

public interface MainActivityPresenterContract {


    void setView(MainActivity view);
    void fetchData(String pageNo,String key);
}
