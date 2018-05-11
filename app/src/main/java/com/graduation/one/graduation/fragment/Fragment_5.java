package com.graduation.one.graduation.fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.graduation.one.graduation.R;
import com.graduation.one.graduation.acyivity.TeacherStudentEvery;
import com.graduation.one.graduation.model.MyClassACT;
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

public class Fragment_5 extends android.support.v4.app.Fragment {
    private View view;
    private List<MyClassACT> myClassACTs = new ArrayList<>();
    private ListView listView;
    private Fragment_5.MyAdapter adapter;
    private SwipeRefreshLayout mSwipeLayout;
    private SharedPreferences editor;

    public Fragment_5() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.activity_myclass, container, false);
        initView(view);
        initDate();

        return view;
    }

    private void initDate() {
        //mSwipeLayout.setRefreshing(true);
        OkHttpUtils.post()
                .url("http://140.143.26.74:8080/classSelect/login.do")
                .addParams("teachTD",editor.getString("account",""))
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
                            myClassACTs.clear();
                            if (sucessed) {
                                mSwipeLayout.setRefreshing(true);
                                JSONObject data = jsonObject.getJSONObject("data");
                                if (data.length() == 0) {
                                    Toast.makeText(getContext(), "您没有管理班级", Toast.LENGTH_SHORT).show();
                                } else {
                                    Log.d("TAG", "changdu:" + data.length());
                                    for (int j = 0; j < data.length(); j++) {
                                        JSONObject mycClass = data.getJSONObject(j + "");
                                        String lClass = mycClass.getString("className");
                                        String classID1 = mycClass.getString("classID");

                                        MyClassACT myClassACT = new MyClassACT();
                                        myClassACT.setClassID(classID1);
                                        myClassACT.setClassName(lClass);
                                        myClassACTs.add(myClassACT);
                                    }
                                }
                                mSwipeLayout.setRefreshing(false);
                                adapter.notifyDataSetChanged();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    private void initView(View view) {
        editor = getContext().getSharedPreferences("data2014", MODE_PRIVATE);
        mSwipeLayout =view.findViewById(R.id.item_class);
        listView = view.findViewById(R.id.my_class);
        adapter = new Fragment_5.MyAdapter();
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MyClassACT myClassACT = myClassACTs.get(position);
                SharedPreferences.Editor sharedPreferences = getContext().getSharedPreferences("data2014", MODE_PRIVATE).edit();
                sharedPreferences.putString("classN1", myClassACT.getClassID()).apply();
                Intent intent = new Intent(getContext(), TeacherStudentEvery.class);
                intent.putExtra("code1", "1");
                startActivity(intent);

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
            Fragment_5.ViewHolder viewHolder;
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_class, parent, false);
                viewHolder = new Fragment_5.ViewHolder();
                viewHolder.tv_name = convertView.findViewById(R.id.item_class10);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (Fragment_5.ViewHolder) convertView.getTag();
            }

            viewHolder.tv_name.setText(getItem(position).getClassName());


            return convertView;
        }
    }

    static class ViewHolder {
        TextView tv_name;
    }

}


