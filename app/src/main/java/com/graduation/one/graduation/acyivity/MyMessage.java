package com.graduation.one.graduation.acyivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.graduation.one.graduation.R;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;

/**
 * Created by 666 on 2018/3/13.
 */

public class MyMessage extends AppCompatActivity {
    TextView tv_name;
    TextView tv_mySex;
    TextView tv_myOnClass;
    TextView tv_mySit;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_message);
        initView();

    }

    private void initView() {
        SharedPreferences share=getSharedPreferences("data2014",MODE_PRIVATE);
        tv_name= (TextView) findViewById(R.id.myName);
        tv_mySex= (TextView) findViewById(R.id.mySex);
        tv_myOnClass= (TextView) findViewById(R.id.myOnClass);
        tv_mySit= (TextView) findViewById(R.id.mySit);
        initroot(share);
    }
//提交网络请求
    private void initroot(SharedPreferences share) {
        OkHttpUtils
                .post()
                .url("http://140.143.26.74:8080/information/login.do")
                .addParams("number",share.getString("account",""))
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {

                    }
                });
    }
}
