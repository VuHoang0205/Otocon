package org.atmarkcafe.otocon.api.interactor;

import android.content.Context;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import org.atmarkcafe.otocon.BuildConfig;
import org.atmarkcafe.otocon.model.DBManager;
import org.atmarkcafe.otocon.utils.KeyExtensionUtils;
import org.atmarkcafe.otocon.utils.LogUtils;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * InteractorManager : is base interface for call api
 *
 * @author acv-truongvv
 * @version 1.0
 */
public class InteractorManager implements KeyExtensionUtils {

    private Retrofit retrofit = null;
    private OkHttpClient okHttpClient;
    private static int REQUEST_TIMEOUT = 60;


    public static ApiInterface getApiInterface(Context context) {
        Retrofit retrofit = null;
        OkHttpClient okHttpClient;
        OkHttpClient.Builder httpClient = new OkHttpClient().newBuilder()
                .connectTimeout(REQUEST_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(REQUEST_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(REQUEST_TIMEOUT, TimeUnit.SECONDS);

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(BuildConfig.DEBUG ? HttpLoggingInterceptor.Level.BODY : HttpLoggingInterceptor.Level.BASIC);

//        interceptor.setLevel(HttpLoggingInterceptor.Level.NONE);

        httpClient.addInterceptor(interceptor);

        // Set header in here
        httpClient.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request original = chain.request();
                Request.Builder requestBuilder = original.newBuilder()
                        .addHeader("Content-Type", "application/json")
                        .addHeader("X-Requested-With", "XMLHttpRequest");

                if(DBManager.isLogin(context)){
                    requestBuilder.addHeader("Authorization", "Bearer " + DBManager.getToken(context));
                    //LogUtils.d("token : " +  DBManager.getToken(context), null);
                }

                Request request = requestBuilder.build();
                return chain.proceed(request);
            }
        });

        okHttpClient = httpClient.build();

        retrofit = new Retrofit.Builder()
                .baseUrl(BuildConfig.SERVER_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(okHttpClient)
                .build();

        return retrofit.create(ApiInterface.class);

    }

}