
import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.zhy.http.okhttp.OkHttpUtils;

import java.io.IOException;
import java.util.concurrent.TimeUnit;


import okhttp3.Authenticator;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.Route;
import com.graduation.one.graduation.utils.HttpUtil;

/**
 * Created by Administrator on 2017/6/8/008.
 */

public class MyApplication extends Application {
    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;

        // 配置OkHttpUtils
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(10000L, TimeUnit.MILLISECONDS)
                .readTimeout(10000L, TimeUnit.MILLISECONDS)
                .authenticator(new Authenticator() {
                    @Override
                    public Request authenticate(Route route, Response response) throws IOException {
                        return response.request().newBuilder()
                                .header("Authorization", "Bearer " + HttpUtil.getToken())
                                .build();
                    }
                })
                .build();

        OkHttpUtils.initClient(okHttpClient);

        // 捕获全局未捕获的异常
        Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(Thread thread, Throwable ex) {
                // 输出异常
                ex.printStackTrace();
                Log.e("捕获到的异常：", ex.toString());
                System.exit(0);
            }
        });
    }

    public static Context getContext () {
        return mContext;
    }
}
