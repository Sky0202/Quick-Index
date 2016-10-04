package com.sky.quickindex;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.github.ksoichiro.android.observablescrollview.ObservableListView;
import com.github.ksoichiro.android.observablescrollview.ObservableScrollViewCallbacks;
import com.github.ksoichiro.android.observablescrollview.ScrollState;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity implements ObservableScrollViewCallbacks {

    private TextView tvIndex;
    private CustomView indexView;
    private List<ContactInfo> infoList;
    private ObservableListView lv;
    private ActionBar ab;

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // 获取 actionbar
        ab = getSupportActionBar();

        //Drawable drawable = this.getResources().getDrawable(R.drawable.head);
        // 给actionBar设置背景
        //ab.setBackgroundDrawable(drawable);

        initView();

    }

    private void initView () {
        lv = (ObservableListView) findViewById(R.id.lv);

        lv.setScrollViewCallbacks(this);

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
                if (infoList.get(i).getFirstLetter().equals(letter)) {
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

    @Override
    public void onScrollChanged (int scrollY, boolean firstScroll, boolean dragging) {

    }

    @Override
    public void onDownMotionEvent () {

    }

    @Override
    public void onUpOrCancelMotionEvent (ScrollState scrollState) {

        if (scrollState == ScrollState.UP) {
            if (ab.isShowing()) {
                ab.hide();
            }
        } else if (scrollState == ScrollState.DOWN) {
            if (!ab.isShowing()) {
                ab.show();
            }
        }
    }
}
