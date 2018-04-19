package com.tsystem.ui.activity.main;

public interface MainActivityPresenterContract {


    void setView(MainActivity view);
    void fetchData(String pageNo,String key);
}
