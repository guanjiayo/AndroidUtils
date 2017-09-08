package zs.xmx.activity;

import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import zs.xmx.R;
import zs.xmx.dialog.KeyboardDialog;
import zs.xmx.utils.KeyboardToggleUtils;
import zs.xmx.utils.Utils;


/*
 * @创建者     xmx
 * @博客       http://blog.csdn.net/u012792686
 * @创建时间   2017/7/3
 * @本类描述   动态开启/关闭软键盘
 * @内容说明
 *
 *
 * @补充内容
 *
 * ---------------------------------
 * @更新时间
 * @新增内容
 *
 */
public class KeyboardToggleActivity extends AppCompatActivity implements View.OnClickListener {

    TextView tvAboutKeyboard;
    EditText etInput;
    private AlertDialog dialog;



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_keyboardtoggle);
        Utils.init(this);
        initView();
    }


    private void initView() {
        etInput = (EditText) findViewById(R.id.et_input);
        findViewById(R.id.btn_hide_soft_input).setOnClickListener(this);
        findViewById(R.id.btn_show_soft_input).setOnClickListener(this);
        findViewById(R.id.btn_toggle_soft_input).setOnClickListener(this);
        findViewById(R.id.btn_keyboard_in_fragment).setOnClickListener(this);
        tvAboutKeyboard = (TextView) findViewById(R.id.tv_about_keyboard);
    }





    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (isShouldHideKeyboard(v, ev)) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    // 根据EditText所在坐标和用户点击的坐标相对比，来判断是否隐藏键盘
    private boolean isShouldHideKeyboard(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] l = {0, 0};
            v.getLocationInWindow(l);
            int left = l[0],
                    top = l[1],
                    bottom = top + v.getHeight(),
                    right = left + v.getWidth();
            return !(event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom);
        }
        return false;
    }

    private boolean isKeyboardShown(View rootView) {
        final int softKeyboardHeight = 100;
        Rect frame = new Rect();
        rootView.getWindowVisibleDisplayFrame(frame);
        DisplayMetrics dm = rootView.getResources().getDisplayMetrics();
        int heightDiff = rootView.getBottom() - frame.bottom;
        //LogUtils.d("" + rootView.getBottom() + ", " + frame.bottom + ", " + heightDiff);
        return heightDiff > softKeyboardHeight * dm.density;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_hide_soft_input:
                KeyboardToggleUtils.hideSoftInput(this);
                break;
            case R.id.btn_show_soft_input:
                KeyboardToggleUtils.showSoftInput(this);
                break;
            case R.id.btn_toggle_soft_input:
                KeyboardToggleUtils.toggleSoftInput();
                break;
            case R.id.btn_keyboard_in_fragment:
                KeyboardToggleUtils.hideSoftInput(this);
                new KeyboardDialog(this).show();
                break;
        }
    }
}
