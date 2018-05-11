package com.graduation.one.graduation.fragment;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.graduation.one.graduation.R;
import com.graduation.one.graduation.model.MyClassACT;
import com.graduation.one.graduation.model.MyClassACT1;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by 666 on 2018/4/25.
 */

public class Fragment_3 extends android.support.v4.app.Fragment{
    private List<MyClassACT> myClassACTs = new ArrayList<>();
    private View view;
    private TextView tv_name;
    private TextView tv_mySex;
    private TextView tv_myOnClass;
    private TextView tv_mySit;
    private SwipeRefreshLayout mSwipeLayout;
    public Fragment_3() {
            // Required empty public constructor
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            // Inflate the layout for this fragment
            view = inflater.inflate(R.layout.activity_my_message, container, false);
            initView(view);
            return view;
        }

    private void initView(View view) {
        SharedPreferences share=this.getContext().getSharedPreferences("data2014",MODE_PRIVATE);
        tv_name= view.findViewById(R.id.myName);
        tv_mySex= view.findViewById(R.id.mySex);
        tv_myOnClass= view.findViewById(R.id.myOnClass);
        mSwipeLayout=view.findViewById(R.id.item_messages);
        initroot(share);
    }

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
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            boolean sucessed = jsonObject.getBoolean("flag");

                            if (sucessed){
                                JSONObject data=jsonObject.getJSONObject("data");
                                myClassACTs.clear();
                                if (data.length() == 0){
                                    Toast.makeText(getContext(),"您暂无信息",Toast.LENGTH_SHORT).show();
                                }else {
                                    String name=data.getString("examineeNumber");
                                    String sex=data.getString("sex");
                                    String class1=data.getString("graduatingClass");
                                    tv_myOnClass.setText(class1);
                                    tv_mySex.setText(sex);
                                    tv_name.setText(name);
                                    MyClassACT myClassACT = new MyClassACT();
                                    myClassACT.setStudentName(name);
                                    myClassACTs.add(myClassACT);

                                }
                                mSwipeLayout.setRefreshing(false);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }


                });
    }



    class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return myClassACTs.size();
        }

        @Override
        public MyClassACT getItem(int position) {
            return myClassACTs.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder1 viewHolder1;

            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_message, parent, false);
                viewHolder1 = new Fragment_3.ViewHolder1();
                viewHolder1.tv_name = convertView.findViewById(R.id.list_item_name);
                viewHolder1.tv_credit = convertView.findViewById(R.id.list_item_message);
                convertView.setTag(viewHolder1);
            } else {
                viewHolder1 = (Fragment_3.ViewHolder1) convertView.getTag();
            }
            viewHolder1.tv_name.setText(getItem(position).getStudentName());

            return convertView;
        }
    }

    static class ViewHolder1 {
        TextView tv_name;
        TextView tv_credit;
    }
}


