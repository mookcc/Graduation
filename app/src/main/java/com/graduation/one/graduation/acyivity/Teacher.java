package com.graduation.one.graduation.acyivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.graduation.one.graduation.R;

/**
 * Created by 666 on 2018/3/12.
 */

public class Teacher extends AppCompatActivity{
    private Button myClass;
    private Button selectInsert;
    private Button insertClass;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher);
        initView();
    }

    private void initView() {
        myClass= (Button) findViewById(R.id.myClass);
        selectInsert= (Button) findViewById(R.id.selectInsert);
        insertClass= (Button) findViewById(R.id.insertClass);
        onClick();
    }


    public void onClick() {

            myClass.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(getApplicationContext(), MyClass.class));
                }
            });
            selectInsert.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(getApplicationContext(), SelectInsert.class));
                }
            });
            insertClass.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(getApplicationContext(), InsertClass.class));
                }
            });


        }

}
