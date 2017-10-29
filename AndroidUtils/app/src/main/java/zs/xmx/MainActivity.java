package zs.xmx;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import zs.xmx.utils.SPUtils;
import zs.xmx.utils.ToastUtils;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SPUtils.getInstance(this,"share");
        String str = "abc";
        SPUtils.putParam("token", str);
        ToastUtils.showToast(this, (String) SPUtils.getParam("token", "200"));


    }
}
