package com.tsystem.ui.activity.fullscreen;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.tsystem.R;
import com.tsystem.databinding.ActivityFullBinding;
import com.tsystem.ui.base.BaseActivity;

import javax.inject.Inject;

public class FullActivity extends BaseActivity implements FullActivityContract {

    @Inject
    FullActivityPresenter presenter;

    ActivityFullBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_full);
        getActivityComponent().inject(this);
        presenter.setView(this);
    }
}
