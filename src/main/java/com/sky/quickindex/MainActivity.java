package com.sky.quickindex;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private TextView tvIndex;
    private CustomView indexView;
    private List<ContactInfo> infoList;
    private ListView lv;

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();

    }

    private void initView () {
        lv = (ListView) findViewById(R.id.lv);
        // 创建集合存储名字信息
        infoList = new ArrayList<>();
        for (int i = 0; i < Cheeses.NAMES.length; i++) {
            infoList.add(new ContactInfo(Cheeses.NAMES[i]));
        }
        ContactAdapter adapter = new ContactAdapter(infoList);
        // 对集合进行排序
        Collections.sort(infoList);
        lv.setAdapter(adapter);

        tvIndex = (TextView) findViewById(R.id.tv);
        indexView = (CustomView) findViewById(R.id.index_view);
        indexView.setOnIndexChangedListener(listener);

    }
    // 对搜索栏进行监听
    private CustomView.OnIndexChangedListener listener = new CustomView.OnIndexChangedListener() {

        @Override
        public void indexVisible (String letter) {
            // 显示 tv 展示文本
            tvIndex.setVisibility(View.VISIBLE);
            tvIndex.setText(letter);
            for (int i = 0; i < infoList.size(); i++) {
                if (infoList.get(i).getFirstLetter().equals(letter)){
                    lv.setSelection(i);
                    break;
                }
            }
        }

        @Override
        public void indexGone () {
            // 文本隔一秒后消失
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run () {
                    tvIndex.setVisibility(View.GONE);
                }
            }, 1000);
        }
    };
}
