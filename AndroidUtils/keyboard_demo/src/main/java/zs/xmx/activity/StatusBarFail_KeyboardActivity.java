package zs.xmx.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;

import zs.xmx.R;
import zs.xmx.utils.StatusBar;

/*
 * @创建者     xmx
 * @博客       http://blog.csdn.net/u012792686
 * @创建时间   2017/7/3
 * @本类描述   AdjustResize失效处理(沉浸式状态栏)
 * @内容说明   解决: 1. 使用5497类(StatusBar_KeyboardUtils),动态监听软键盘弹出位置
 *                  2. 在设置了沉浸式状态栏的Actvity对应的XML布局文件加上android:fitsSystemWindows="true"属性(建议)
 *
 *
 *
 * @补充内容  注意:
 *           使用第一种方式,要自定义ToolBar,原生的会覆盖一部分内容
 *
 * ---------------------------------
 * @更新时间
 * @新增内容
 *
 */
public class StatusBarFail_KeyboardActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statusbarfail);
        //StatusBar_keyboardUtils.assistActivity(this);

        //代码方式设置adjustResize
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        //工具类设置状态栏颜色
        StatusBar.setStatusBarColor(this, Color.RED);
    }
}
