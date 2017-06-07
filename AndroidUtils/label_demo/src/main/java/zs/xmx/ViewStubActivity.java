package zs.xmx;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewStub;
import android.widget.Button;
import android.widget.Toast;

/*
 * @创建者     mqm
 * @博客       http://blog.csdn.net/u012792686
 * @创建时间   2017/6/7
 * @本类描述	  <ViewStub>标签使用
 * @内容说明
 * @补充内容
 *
 * ---------------------------------
 * @更新时间
 * @新增内容
 *
 */
public class ViewStubActivity extends AppCompatActivity implements View.OnClickListener, ViewStub.OnInflateListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_stub);
    }

    private View networkErrorView;

    private void showNetError() {
        // not repeated infalte
        if (networkErrorView != null) {
            //setVisibility()方式加载布局,加载次数不限
            networkErrorView.setVisibility(View.VISIBLE);
            return;
        }
        //inflate()方式加载布局,只能加载一次
        ViewStub stub = (ViewStub) findViewById(R.id.network_error_layout);
        stub.setOnInflateListener(this);
        networkErrorView = stub.inflate();
        Button networkSetting = (Button) networkErrorView.findViewById(R.id.network_miss);
        networkSetting.setOnClickListener(this);
        Button refresh = (Button) findViewById(R.id.network_refresh);
        refresh.setOnClickListener(this);

    }

    private void showNormal() {
        if (networkErrorView != null) {
            networkErrorView.setVisibility(View.GONE);
        }
    }

    public void show(View view) {
        showNetError();
    }

    public void refresh(View view) {
        showNormal();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.network_miss:
                Toast.makeText(this, "network_miss", Toast.LENGTH_SHORT).show();
                break;
            case R.id.network_refresh:
                Toast.makeText(this, "network_refresh", Toast.LENGTH_SHORT).show();
                break;
        }

    }

    /**
     *
     * @param stub 当前待inflate的ViewStub控件
     * @param inflated 当前被inflate的View视图
     */
    @Override
    public void onInflate(ViewStub stub, View inflated) {

    }
}
