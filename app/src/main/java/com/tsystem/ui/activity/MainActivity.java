package com.tsystem.ui.activity;

import android.databinding.DataBindingUtil;
import android.os.BadParcelableException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.tsystem.R;
import com.tsystem.data.pojo.ApiData;
import com.tsystem.databinding.ActivityMainBinding;
import com.tsystem.ui.base.BaseActivity;

import javax.inject.Inject;

public class MainActivity extends BaseActivity implements MainActivityContract, MainAdapter.AdapterListener {

    @Inject
    MainActivityPresenter presenter;
    ActivityMainBinding binding;
    public static int PAGE_NUMBER = 1;
    String keyword;

    MainAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        getActivityComponent().inject(this);
        presenter.setView(this);

        setSupportActionBar(binding.toolbar);
        if (getSupportActionBar() != null)
            getSupportActionBar().setTitle("");

        invalidateOptionsMenu();
    }

    @Override
    public void showLoader() {
        binding.progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoader() {
        binding.progressBar.setVisibility(View.GONE);
    }

    @Override
    public void dataFetched(Boolean isErrorFound, ApiData data) {
        if (data != null && !isErrorFound) {
            binding.recycler.setLayoutManager(new GridLayoutManager(this, 2));
            adapter = new MainAdapter(data, this, this);
            binding.recycler.setAdapter(adapter);
        }
        Log.v("data", String.valueOf(data));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {

            case R.id.search:
                if (!binding.searchEditText.getText().toString().trim().equalsIgnoreCase("")) {
                    keyword = binding.searchEditText.getText().toString().trim();
                    presenter.fetchData(String.valueOf(PAGE_NUMBER), keyword);
                } else
                    Toast.makeText(this, R.string.add_some_text, Toast.LENGTH_SHORT).show();
                break;

            case R.id.two:
                break;

            case R.id.three:
                break;

            case R.id.four:
                break;

            default:
                return super.onOptionsItemSelected(item);
        }

        return true;
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void loadMoreData() {
        PAGE_NUMBER++;
        presenter.fetchData(String.valueOf(PAGE_NUMBER), keyword);

    }
}
