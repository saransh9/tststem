package com.tsystem.ui.activity.main;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Environment;
import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.GridLayoutManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.widget.Toast;

import com.tsystem.R;
import com.tsystem.data.pojo.ApiData;
import com.tsystem.data.pojo.Results;
import com.tsystem.databinding.ActivityMainBinding;
import com.tsystem.ui.activity.fullscreen.FullActivity;
import com.tsystem.ui.base.BaseActivity;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.inject.Inject;

public class MainActivity extends BaseActivity implements MainActivityContract, MainAdapter.AdapterListener {


    private static String TAG = MainActivity.class.getSimpleName();
    @Inject
    MainActivityPresenter presenter;
    ActivityMainBinding binding;
    public static int PAGE_NUMBER = 1;
    String keyword;
    ArrayList<Results> dataList = new ArrayList<>();
    ArrayList<File> cacheFileList = new ArrayList<>();
    MainAdapter adapter;
    ExecutorService executorService;

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
        binding.textview.setVisibility(View.VISIBLE);

        executorService = Executors.newFixedThreadPool(10);

        softKeyboardSearchAction();

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

            if (PAGE_NUMBER == 1) {
                dataList.addAll(Arrays.asList(data.getResults()));
                binding.recycler.setLayoutManager(new GridLayoutManager(this, 2));
                adapter = new MainAdapter(dataList, this, this, 2, true, null);
                binding.recycler.setAdapter(adapter);
            } else {
                dataList.addAll(Arrays.asList(data.getResults()));
                adapter.notifyDataSetChanged();
            }
        } else if(data == null && !isErrorFound){

            File directory = new File(getCacheDirectory());
            if (directory.exists()) {
                cacheFileList.addAll(Arrays.asList(directory.listFiles()));
                binding.recycler.setLayoutManager(new GridLayoutManager(this, 2));
                adapter = new MainAdapter(null, this, this, 2, false, cacheFileList);
                binding.recycler.setAdapter(adapter);
            } else {
                Toast.makeText(MainActivity.this, R.string.no_data, Toast.LENGTH_SHORT).show();
                binding.textview.setVisibility(View.VISIBLE);
            }

        }
        else
        {
            Toast.makeText(this,R.string.oops,Toast.LENGTH_SHORT).show();
        }
        Log.v("data", String.valueOf(data));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {

            case R.id.search:
                searchAction();
                closeSoftKeyBoard();
                break;

            case R.id.two:
                binding.recycler.setLayoutManager(new GridLayoutManager(this, 2));
                if (adapter != null) {
                    adapter.setNumber_columns(2);
                    adapter.notifyDataSetChanged();
                }
                break;

            case R.id.three:
                binding.recycler.setLayoutManager(new GridLayoutManager(this, 3));
                if (adapter != null) {
                    adapter.setNumber_columns(3);
                    adapter.notifyDataSetChanged();
                }
                break;

            case R.id.four:
                binding.recycler.setLayoutManager(new GridLayoutManager(this, 4));
                if (adapter != null) {
                    adapter.setNumber_columns(4);
                    adapter.notifyDataSetChanged();
                }
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

    @Override
    public File saveToCache(final Bitmap bitmap, final String filename) {
        final FileOutputStream[] out = new FileOutputStream[1];
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                out[0] = null;
                File myDir = new File(getCacheDirectory());
                if (!myDir.exists())
                    if (!myDir.mkdirs()) {
                        Log.e(TAG, "Directory not created");
                    } else {
                        Log.e(TAG, "Directory  created");
                    }
                else
                    Log.e(TAG, "Directory  exists");

                try {
                    File f = new File(getCacheDirectory(), filename);

                    if (!f.exists()) {
                        Log.e("Files", "File already created");
                        out[0] = new FileOutputStream(f);
                        bitmap.compress(Bitmap.CompressFormat.PNG, 90, out[0]);
                    }
                } catch (Exception e) {
                    Log.e(TAG, "Could not save bitmap", e);
                } finally {
                    try {
                        if (out[0] != null) {
                            out[0].close();
                        }
                    } catch (Throwable ignore) {
                    }
                }
            }
        });

        return null;
    }


    public String getCacheDirectory() {

        return Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState()) || !Environment.isExternalStorageRemovable() ?
                getExternalCacheDir().getPath() + File.separator + keyword : getCacheDir().getPath() + File.separator + keyword;
    }

    public void searchAction() {
        if (!binding.searchEditText.getText().toString().trim().equalsIgnoreCase("")) {
            if (cacheFileList != null)
                cacheFileList.clear();
            if (dataList != null)
                dataList.clear();
            if (adapter != null)
                adapter.notifyDataSetChanged();
            PAGE_NUMBER = 1;
            binding.textview.setVisibility(View.GONE);
            keyword = binding.searchEditText.getText().toString().trim();
            presenter.fetchData(String.valueOf(PAGE_NUMBER), keyword);

        } else
            Toast.makeText(this, R.string.add_some_text, Toast.LENGTH_SHORT).show();
    }

    public void softKeyboardSearchAction() {
        binding.searchEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                searchAction();
                closeSoftKeyBoard();
                return true;

            }
        });
    }

    public void closeSoftKeyBoard() {
        InputMethodManager in = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (in != null) {
            in.hideSoftInputFromWindow(binding.searchEditText.getWindowToken(), 0);
        }
    }
}
