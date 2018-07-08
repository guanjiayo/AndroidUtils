package zs.xmx;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import java.util.Timer;
import java.util.TimerTask;

import zs.xmx.utils.AlertDialog;
import zs.xmx.utils.SPUtils;
import zs.xmx.utils.ToastUtils;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SPUtils.getInstance(this, "share");
        String str = "abc";
        SPUtils.putParam("token", str);
        ToastUtils.showToast(this, (String) SPUtils.getParam("token", "200"));


    }

    public void showDilog(View view) {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        AlertDialog alertDialog2 = AlertDialog.getInstance(MainActivity.this);
                        alertDialog2
                                .setTitle("提示")
                                .setMsg("单例测试2")
                                .show();
                    }
                });

            }
        }, 0, 2000);


    }
}
