package com.tsystem.ui.activity.fullscreen;

import javax.inject.Inject;

public class FullActivityPresenter implements FullActivityPresenterContract {


    FullActivity view;

    @Inject
    FullActivityPresenter() {

    }

    @Override
    public void setView(FullActivity view) {
        this.view = view;
    }
}
