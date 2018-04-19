package com.tsystem.data;


import com.tsystem.data.pojo.ApiData;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiCalls {
    @GET("search/photos?client_id=c16c9a179271ec43576ff0219dbe0a65a579851817cf34651a9d14c55cbdc126&per_page=100")
    Observable<ApiData> fetchData(@Query("page") String page, @Query("query") String key);
}
