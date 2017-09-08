package zs.xmx;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import zs.xmx.activity.AdjustPan_KeyboardActivity;
import zs.xmx.activity.AdjustResize_KeyboardActivity;
import zs.xmx.activity.InputType_KeyboardActivity;
import zs.xmx.activity.KeyboardToggleActivity;
import zs.xmx.activity.ImeOptions_keyboardActivity;
import zs.xmx.activity.ScrollViewByClassActivity;
import zs.xmx.activity.ScrollViewByLayoutActivity;
import zs.xmx.activity.Scroll_keyboardActivity;
import zs.xmx.activity.StatusBarFail_KeyboardActivity;

/*
 * @创建者     xmx
 * @博客       http://blog.csdn.net/u012792686
 * @创建时间   2017/7/3
 * @本类描述
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
public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    private String[] itemName = {"ScrollView 嵌套布局(软键盘测试)", "adjustResize(软键盘测试)",
            "adjustPan(软键盘测试)", "处理AdjustResize失效(沉浸式状态栏)", "滚动自定义布局(工具类方法)",
            "滚动自动义布局(监听布局方式)", "动态开启/关闭软键盘", "EditText联动软键盘右下角按钮", "EditText联动软键盘界面"};
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(new ClassAdapter(this, R.layout.item_list, itemName));
        listView.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch (position) {
            case 0:
                startActivity(new Intent(this, Scroll_keyboardActivity.class));
                break;
            case 1:
                startActivity(new Intent(this, AdjustResize_KeyboardActivity.class));
                break;
            case 2:
                startActivity(new Intent(this, AdjustPan_KeyboardActivity.class));
                break;
            case 3:
                startActivity(new Intent(this, StatusBarFail_KeyboardActivity.class));
                break;
            case 4:
                startActivity(new Intent(this, ScrollViewByClassActivity.class));
                break;
            case 5:
                startActivity(new Intent(this, ScrollViewByLayoutActivity.class));
                break;
            case 6:
                startActivity(new Intent(this, KeyboardToggleActivity.class));
                break;
            case 7:
                startActivity(new Intent(this, ImeOptions_keyboardActivity.class));
                break;
            case 8:
                startActivity(new Intent(this, InputType_KeyboardActivity.class));
                break;

        }
    }

    class ClassAdapter extends ArrayAdapter<String> {

        private int mResourceId;

        public ClassAdapter(Context context, int textViewResourceId,
                            String[] className) {
            super(context, textViewResourceId, className);
            this.mResourceId = textViewResourceId;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            String item = getItem(position);
            LayoutInflater inflater = getLayoutInflater();
            View view = inflater.inflate(mResourceId, null);
            TextView text = (TextView) view.findViewById(R.id.tv_item);
            text.setText(item);
            return view;
        }
    }
}
