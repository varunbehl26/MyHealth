//package com.lifeapps.myhealth.network
//
//import android.content.Context
//
//import com.lifeapps.myhealth.model.MasterReponse
//import com.lifeapps.myhealth.model.User
//import com.readystatesoftware.chuck.ChuckInterceptor
//
//import java.util.concurrent.TimeUnit
//
//import okhttp3.OkHttpClient
//import okhttp3.logging.HttpLoggingInterceptor
//import retrofit2.Call
//import retrofit2.Retrofit
//import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
//import retrofit2.converter.gson.GsonConverterFactory
//import rx.Observable
//
///**
// * Created by varunbehl on 07/03/17.
// */
//class RetrofitManager private constructor(context: Context) {
//
//
//    val userInfo: Observable<MasterReponse>
//        get() = dataInterface!!.users
//
//    init {
//        val builder = OkHttpClient().newBuilder()
//        builder.readTimeout(10, TimeUnit.SECONDS)
//        builder.connectTimeout(5, TimeUnit.SECONDS)
//        builder.addInterceptor(ChuckInterceptor(context))
//
//
//        //            if (BuildConfig.DEBUG) {
//        val interceptor = HttpLoggingInterceptor()
//        interceptor.level = HttpLoggingInterceptor.Level.BODY
//        builder.addInterceptor(interceptor)
//        //            }
//
//        val client = builder.build()
//
//        val API_BASE_URL = "http://www.mocky.io/"
//        val retrofit = Retrofit.Builder()
//                .baseUrl(API_BASE_URL)
//                .client(client)
//                .addConverterFactory(GsonConverterFactory.create())
//                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
//                .build()
//
//        dataInterface = retrofit.create(DataInterface::class.java)
//
//    }
//
//
//    fun createUser(user: User): Call<User> {
//        return dataInterface!!.createUser(user)
//    }
//
//    companion object {
//
//        private val API_KEY = "29c90a4aee629499a2149041cc6a0ffd"
//        private var dataInterface: DataInterface? = null
//        private var retrofitManager: RetrofitManager? = null
//
//        fun getInstance(context: Context): RetrofitManager {
//            if (retrofitManager == null) {
//                retrofitManager = RetrofitManager(context)
//            }
//            return retrofitManager as RetrofitManager
//        }
//    }
//
//
//
//}
//
//
