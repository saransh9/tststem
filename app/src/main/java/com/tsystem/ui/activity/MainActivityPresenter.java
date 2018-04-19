package com.tsystem.ui.activity;

import com.tsystem.data.ApiCalls;
import com.tsystem.data.pojo.ApiData;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

import static com.tsystem.ui.activity.MainActivity.PAGE_NUMBER;

public class MainActivityPresenter implements MainActivityPresenterContract {

    private ApiCalls apiCalls;

    private MainActivity view;

    private CompositeDisposable compositeDisposable;

    @Inject
    MainActivityPresenter(ApiCalls apiCalls) {
        this.apiCalls = apiCalls;
        compositeDisposable = new CompositeDisposable();
    }

    @Override
    public void setView(MainActivity view) {
        this.view = view;

    }

    @Override
    public void fetchData(String pageNo, String key) {
        if (PAGE_NUMBER == 1)
            view.showLoader();
        compositeDisposable.add(apiCalls.fetchData(pageNo, key)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<ApiData>() {
                    @Override
                    public void onNext(ApiData apiData) {
                        if (view != null) {
                            view.hideLoader();
                            view.dataFetched(false, apiData);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (view != null) {
                            view.hideLoader();
                            // view.showError();
                            view.dataFetched(true, null);
                        }
                    }

                    @Override
                    public void onComplete() {

                    }
                })
        );

    }
}
