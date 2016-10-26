package com.example.cc.recycleviewdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.cc.recycleviewdemo.adapter.NetEaseAdapter;
import com.example.cc.recycleviewdemo.biz.Xhttp;
import com.example.cc.recycleviewdemo.entity.NetEase;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
    String  uri="http://c.m.163.com/nc/article/list/T1348647909107/0-20.html";
    NetEaseAdapter ada;

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        Xhttp.getNewsList(uri, "T1348647909107", new Xhttp.OnSuccessListener() {
            @Override
            public void setNewsList(List<NetEase> neteaseNews) {
                ada=new NetEaseAdapter(neteaseNews);
                recyclerView.setAdapter(ada);
                recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));

            }
        });


    }
}
