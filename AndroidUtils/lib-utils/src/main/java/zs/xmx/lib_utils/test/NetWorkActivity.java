package zs.xmx.lib_utils.test;

import android.content.DialogInterface;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

import zs.xmx.Moblie.NetWorkUtils;
import zs.xmx.R;
import zs.xmx.receiver.NetWorkReceiver;

/*
 * @创建者     mqm
 * @博客       http://blog.csdn.net/u012792686
 * @创建时间   2017/5/16
 * @本类描述	  手机网络状态监听类
 * @内容说明   1.判断网络连接状态
 *            2.没网:提示用户当前网络不可用,打开网络设置页面
              3.有网:判断数据连通状态
              true: return;
              false: 提示用户当前网络不可用,打开网络设置页面
              如果当前使用的时GPRS数据,提示用户打开wifi

 *
 * @补充内容  1.wifi/gprs让手机自行处理,我们只关注App是否有网络数据
 *
 *           //TODO 以后直接封装成接口回调工具类,在BaseActivity init就能用
 *
 * ---------------------------------
 * @更新时间
 * @新增内容
 *
 */
public class NetWorkActivity extends AppCompatActivity implements NetWorkReceiver.onNetWorkStataChangedListener {
    private NetWorkReceiver     mMyReceiver;
    private AlertDialog.Builder mBuilder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_net_work);
        registerReceiver();
    }

    /**
     * 代码注册广播
     */
    private void registerReceiver() {
        IntentFilter intentFilter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        mMyReceiver = new NetWorkReceiver();
        mMyReceiver.setOnNetWorkStataChangedListener(this);//绑定广播
        this.registerReceiver(mMyReceiver, intentFilter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 注销广播
        unregisterReceiver(mMyReceiver);
    }

    @Override
    public void onNetWorkStataChanged(String mNetWorkTypeName) {
        //没网
        if (mNetWorkTypeName.equals("NETWORK_UNKNOWN") || mNetWorkTypeName.equals("NETWORK_NO")) {
            showDialog();


        }else if (mNetWorkTypeName.equals("NETWORK_WIFI")) { //wifi有网
                hasNetStata();
            } else { //GPRS有网
                try {
                    hasNetStata();
                    NetWorkUtils.openWifi(this,true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    private void hasNetStata() {
        //判断数据连通状态
        if (NetWorkUtils.isAvailableByPing()) {
            return;
        } else {
            showDialog();
        }
    }

    private void showDialog() {
        //显示Diag
        mBuilder = new AlertDialog.Builder(this);
        mBuilder.setTitle("网络设置提示").setMessage("网络连接不可用,是否进行设置?").setPositiveButton("设置", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                NetWorkUtils.openWirelessSettings(NetWorkActivity.this);
            }
        }).setNegativeButton("取消", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        }).show();
    }
}
