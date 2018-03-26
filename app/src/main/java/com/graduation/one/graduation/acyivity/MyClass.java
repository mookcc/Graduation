package com.graduation.one.graduation.acyivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.graduation.one.graduation.R;
import com.graduation.one.graduation.model.MyClassACT;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;

import static com.graduation.one.graduation.MyApplication.getContext;

/**
 * Created by 666 on 2018/3/13.
 */

public class MyClass extends AppCompatActivity{
    private List<MyClassACT> myClassACTs = new ArrayList<>();
    private ListView listView;
    private MyAdapter adapter;
    private SwipeRefreshLayout mSwipeLayout;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myclass);
        initView();
    }

    private void initView() {
        mSwipeLayout=(SwipeRefreshLayout)findViewById(R.id.item_class);
        listView= (ListView) findViewById(R.id.my_class);
        listView.setAdapter(adapter);
        SharedPreferences editor=getSharedPreferences("data2014",MODE_PRIVATE);

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
                            if (sucessed){
                                JSONObject data=jsonObject.getJSONObject("data");

                                if (data.length() == 0){
                                    Toast.makeText(getApplicationContext(),"您没有管理班级",Toast.LENGTH_SHORT).show();
                                }else {
                                    for (int j = 0; j < data.length(); j++){
                                        JSONObject mycClass=data.getJSONObject(j+"");
                                        String lClass=mycClass.getString("className");
                                        MyClassACT myClassACT=new MyClassACT();
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
            ViewHolder viewHolder;
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_message, parent, false);
                viewHolder = new MyClass.ViewHolder();
                viewHolder.tv_name = (TextView) convertView.findViewById(R.id.item_class);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (MyClass.ViewHolder) convertView.getTag();
            }

            viewHolder.tv_name.setText(getItem(position).getClassName());


            return convertView;
        }
    }

    static class ViewHolder {
        TextView tv_name;
    }
}
