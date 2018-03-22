package com.graduation.one.graduation.acyivity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.graduation.one.graduation.R;
import com.graduation.one.graduation.model.MyClassACT;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 666 on 2018/3/13.
 */

public class MyClass extends AppCompatActivity{
    private List<MyClassACT> myClassACTs = new ArrayList<>();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myclass);
        initView();
    }

    private void initView() {
        
    }
}
