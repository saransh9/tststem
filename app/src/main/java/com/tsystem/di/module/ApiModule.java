package com.tsystem.di.module;


import com.tsystem.data.ApiCalls;
import com.tsystem.di.scope.ApplicationScope;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by saransh on 17/04/18.
 */

@Module(includes = {Network.class})
public class ApiModule {

    String baseUrl;
    public ApiModule(String baseUrl)
    {
        this.baseUrl=baseUrl;
    }

    @Provides
    @ApplicationScope
    public ApiCalls apiCalls(Retrofit retrofit)
    {
        return retrofit.create(ApiCalls.class);
    }

    @Provides
    @ApplicationScope
    public Retrofit retrofitClient(OkHttpClient client)
    {
        return new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(client)
                .baseUrl(baseUrl)
                .build();
    }
}
