package zs.xmx.activity;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;

import zs.xmx.R;
import zs.xmx.utils.NestedScrollView_KeyboardUtils;
import zs.xmx.utils.StatusBar;

/*
 * @创建者     xmx
 * @博客       http://blog.csdn.net/u012792686
 * @创建时间   2017/7/3
 * @本类描述   使用重绘类xxx.assistActivity()
 *             处理部分"全屏模式"下,adjustResize失效问题
 * @内容说明   原理:监听软键盘弹出位置,让我们的布局滑动到合适的位置
 *
 *
 * @补充内容  注意:
 *           1.改动过状态栏的状态,记得在XML布局文件根布局设置android:fitsSystemWindows="true"
 *
 * ---------------------------------
 * @更新时间
 * @新增内容
 *
 */
public class ScrollViewByClassActivity extends AppCompatActivity {

    private LinearLayout     mParent;
    private EditText         account;
    private NestedScrollView scroll;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scroll_class);
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN); // 设置全屏
        StatusBar.setStatusBarColor(this, Color.BLUE);

        mParent = (LinearLayout) findViewById(R.id.ll_parent);

        account = (EditText) findViewById(R.id.account);

        scroll = (NestedScrollView) findViewById(R.id.scroll_view);
        setScroll();

    }


    //键盘不遮挡按钮
    private void setScroll() {
        NestedScrollView_KeyboardUtils.assistActivity(this, R.id.scroll_view);       //这个是别人给我的工具类，只用这个会有
        hideKeyboard();

        scroll.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {         //scroll为parent外面那层布局（）最好用NestedScrollView，ScrollView会有版本问题
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                v.smoothScrollTo(0, 450);     //这个是滑动距离，随便大一点就好
            }
        });
    }

    /**
     * EditText失去焦点,隐藏软键盘
     */
    private void hideKeyboard() {
        mParent.setOnTouchListener(new View.OnTouchListener() {                 //parent为Editext外面那层布局
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                mParent.setFocusable(true);
                mParent.setFocusableInTouchMode(true);
                mParent.requestFocus();
                InputMethodManager imm = (InputMethodManager) ScrollViewByClassActivity.this
                        .getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(account.getWindowToken(), 0);  //隐藏键盘，account为Editext，随便一个就好
                return false;
            }
        });
    }


}
