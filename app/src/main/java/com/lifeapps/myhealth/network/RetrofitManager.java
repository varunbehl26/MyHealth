package com.lifeapps.myhealth.network;

import android.content.Context;

import com.lifeapps.myhealth.model.MasterReponse;
import com.lifeapps.myhealth.model.User;
import com.readystatesoftware.chuck.ChuckInterceptor;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;

/**
 * Created by varunbehl on 07/03/17.
 */
public class RetrofitManager {

    private static final String API_KEY = "29c90a4aee629499a2149041cc6a0ffd";
    private static DataInterface dataInterface = null;
    private static RetrofitManager retrofitManager;

    private RetrofitManager(Context context) {
        OkHttpClient.Builder builder = new OkHttpClient().newBuilder();
        builder.readTimeout(10, TimeUnit.SECONDS);
        builder.connectTimeout(5, TimeUnit.SECONDS);
        builder.addInterceptor(new ChuckInterceptor(context));


//            if (BuildConfig.DEBUG) {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        builder.addInterceptor(interceptor);
//            }

        OkHttpClient client = builder.build();

        String API_BASE_URL = "http://13.89.238.127:8080/";
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API_BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();

        dataInterface = retrofit.create(DataInterface.class);

    }

    public static RetrofitManager getInstance(Context context) {
        if (retrofitManager == null) {
            retrofitManager = new RetrofitManager(context);
        }
        return retrofitManager;
    }


    public Observable<MasterReponse> getUserInfo() {
        return dataInterface.getUsers();
    }


    public Call<User> createUser(User user) {
        return dataInterface.createUser(user);
    }

//
//    public Observable<SearchResult> searchTvShows(String query) {
//        return dataInterface.searchTvShows(API_KEY, query);
//    }
//
//    public Observable<CastInfo> getCastInfo(String castId) {
//        return dataInterface.getCastInfo(castId, API_KEY, "en-US", "combined_credits");
//    }
//
//    public Observable<Picture_Detail> listMoviesInfo(String categories, int page) {
//        return dataInterface.listMoviesInfo(categories, page, API_KEY);
//    }
//
//    public Observable<CombinedMovieDetail> getMoviesDetail(int id) {
//        return dataInterface.getMovieDetail(id, API_KEY, "videos,images,credits,reviews,similar,recommendations");
//    }


}


