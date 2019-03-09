package com.example.hj.quickindexbar;

import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private QuickIndexBar quickIndexBar;
    private RecyclerView recyclerView;
    private List<Friend> friends;
    private TextView showTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        quickIndexBar = findViewById(R.id.quickIndexBar);
        recyclerView = findViewById(R.id.recyclerView);
        showTv = findViewById(R.id.show_tv);

        friends = new ArrayList<>();
        //设置数据
        fillList();
        //根据拼音排序
        Collections.sort(friends);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(new MyAdapter(friends, this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, OrientationHelper.HORIZONTAL));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        quickIndexBar.setOnTouchLetterListener(new QuickIndexBar.OnTouchLetterListener() {
            @Override
            public void onTouchLetter(String letter) {
                //判断item中的首字母是否与触摸的字母一致
                for (int i = 0; i < friends.size(); i++) {
                    String itemWord = String.valueOf(friends.get(i).getPinyin().charAt(0));
                    if (itemWord.equals(letter)) {
                        //将item滚动到屏幕顶部
                        layoutManager.scrollToPositionWithOffset(i, 0);
                        //只判断第一组汉字
                        break;
                    }
                }
                //显示当前触摸的字母
                showCurrentWord(letter);
            }
        });
    }

    private Handler handler = new Handler();
    private boolean isScale;

    private void showCurrentWord(String letter) {
        showTv.setVisibility(View.VISIBLE);
        if (!isScale) { //避免重复执行缩放动画
            isScale = true;
            ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(showTv, "scaleX", 0f, 1f);
            objectAnimator.setDuration(450);
            objectAnimator.start();
            objectAnimator = ObjectAnimator.ofFloat(showTv, "scaleY", 0f, 1f);
            objectAnimator.setDuration(450);
            objectAnimator.start();
        }
        showTv.setText(letter);

        //先移除之前的任务，避免showTv闪烁
        handler.removeCallbacksAndMessages(null);
        //延时隐藏
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
//                showTv.setVisibility(View.GONE);
                ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(showTv, "scaleX", 1f, 0f);
                objectAnimator.setDuration(450);
                objectAnimator.start();
                objectAnimator = ObjectAnimator.ofFloat(showTv, "scaleY", 1f, 0f);
                objectAnimator.setDuration(450);
                objectAnimator.start();
                isScale = false;
            }
        }, 1500);
    }


    private void fillList() {
        // 虚拟数据
        friends.add(new Friend("李伟"));
        friends.add(new Friend("张三"));
        friends.add(new Friend("阿三"));
        friends.add(new Friend("阿四"));
        friends.add(new Friend("段誉"));
        friends.add(new Friend("段正淳"));
        friends.add(new Friend("张三丰"));
        friends.add(new Friend("重庆"));
        friends.add(new Friend("陈坤"));
        friends.add(new Friend("林俊杰1"));
        friends.add(new Friend("陈坤2"));
        friends.add(new Friend("王二a"));
        friends.add(new Friend("林俊杰a"));
        friends.add(new Friend("张四"));
        friends.add(new Friend("林俊杰"));
        friends.add(new Friend("王二"));
        friends.add(new Friend("王二b"));
        friends.add(new Friend("赵四"));
        friends.add(new Friend("杨坤"));
        friends.add(new Friend("赵子龙"));
        friends.add(new Friend("杨坤1"));
        friends.add(new Friend("李伟1"));
        friends.add(new Friend("宋江"));
        friends.add(new Friend("宋江1"));
        friends.add(new Friend("李伟3"));
        friends.add(new Friend("重要"));
    }
}
