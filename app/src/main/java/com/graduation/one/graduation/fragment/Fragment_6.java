package com.graduation.one.graduation.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.graduation.one.graduation.R;

/**
 * Created by 666 on 2018/4/25.
 */

public class Fragment_6 extends android.support.v4.app.Fragment {
    private View view;


    public Fragment_6() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_3, container, false);

        return view;
    }


}


