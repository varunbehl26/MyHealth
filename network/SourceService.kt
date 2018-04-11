package com.lifeapps.myhealth.network

import android.arch.lifecycle.LiveData
import com.lifeapps.myhealth.model.User
import com.lifeapps.myhealth.utils.LiveDataCallAdapterFactory
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import rx.Observable

interface SourceService {

    @Headers("Content-type: application/json")
    @POST("user/")
    fun saveUser(@Body text: User): Observable<*>

    @POST("springrest/User/")
    fun createUser(@Body user: User): Call<User>

    //    @GET("springrest/User/")
    //    Observable<MasterReponse> getUsers();
    @GET("v2/5a7804ef2f00005100668dbb")
    fun getUsers(): LiveData<Resource<List<User>>>

    companion object Factory {
        const val BASE_URL = "http://www.mocky.io/"

//
//        var builder = OkHttpClient().newBuilder()
//        builder.readTimeout(10, TimeUnit.SECONDS)
//        builder.connectTimeout(5, TimeUnit.SECONDS)
////            builder.addInterceptor(ChuckInterceptor())
//
//        val interceptor = HttpLoggingInterceptor()
//        interceptor.level = HttpLoggingInterceptor.Level.BODY
//        builder.addInterceptor(interceptor)
//
//        val client = builder.build()


        // TODO: Move to DI.
        fun getSourceService(): SourceService {
            return Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(LiveDataCallAdapterFactory())
                    .build()
                    .create(SourceService::class.java)
        }
    }
}
