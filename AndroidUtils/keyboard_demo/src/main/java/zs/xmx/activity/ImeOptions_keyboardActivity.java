package zs.xmx.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import zs.xmx.R;

/*
 * @创建者     xmx
 * @博客       http://blog.csdn.net/u012792686
 * @创建时间   2017/7/3
 * @本类描述   EditText与软键盘右下角按钮联动
 * @内容说明   相关参数 android:ImeOptions
 *
 *
 * @补充内容 注意:
 *          1. 如果使用setOnEditorActionListener,事件的处理都将交由这里实现
 *
 * ---------------------------------
 * @更新时间
 * @新增内容
 *
 */
public class ImeOptions_keyboardActivity extends AppCompatActivity implements TextView.OnEditorActionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_imeoptions);
        EditText search = (EditText) findViewById(R.id.et_search);
        EditText next = (EditText) findViewById(R.id.et_next);
        EditText done = (EditText) findViewById(R.id.et_done);
        search.setOnEditorActionListener(this);
        next.setOnEditorActionListener(this);
        done.setOnEditorActionListener(this);

    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        switch (actionId) {
            case EditorInfo.IME_ACTION_SEARCH:
                Toast.makeText(this, "点击了搜索键", Toast.LENGTH_SHORT).show();
                break;
            case EditorInfo.IME_ACTION_NEXT:
                //代码实现,XML设置的属性会被这里抢占,事件全部交由这里处理
                //一般"next"也不需要操作,这里只是为了展示效果
                Toast.makeText(this, "下一个", Toast.LENGTH_SHORT).show();
                break;
            case EditorInfo.IME_ACTION_DONE:
                Toast.makeText(this, "完成", Toast.LENGTH_SHORT).show();
                break;
        }
        return true;
    }
}
