package zs.xmx.lib_utils.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;

import zs.xmx.lib_utils.moblie.NetWorkUtils;




/*
 * @创建者     mqm
 * @博客       http://blog.csdn.net/u012792686
 * @创建时间   2017/5/16
 * @本类描述	  监听手机网络状态广播
 * @内容说明  当手机状态发生改变,将数据用接口回调方式,传递出去
 * @补充内容
 *
 * ---------------------------------     
 * @更新时间   
 * @新增内容   
 *
 */

public class NetWorkReceiver extends BroadcastReceiver {

    private String mNetWorkTypeName;


    @Override
    public void onReceive(final Context context, Intent intent) {
        String action = intent.getAction();
        if (action.equals(ConnectivityManager.CONNECTIVITY_ACTION)) {
            //获取网络状态
            mNetWorkTypeName = NetWorkUtils.getNetWorkTypeName(context);
            if (mOnNetWorkStataChangedListener != null) {

                mOnNetWorkStataChangedListener.onNetWorkStataChanged(mNetWorkTypeName);
            }

        }
    }

    public interface onNetWorkStataChangedListener {
        void onNetWorkStataChanged(String mNetWorkTypeName);
    }

    private onNetWorkStataChangedListener mOnNetWorkStataChangedListener;

    public void setOnNetWorkStataChangedListener(onNetWorkStataChangedListener listener) {
        this.mOnNetWorkStataChangedListener = listener;
    }
}
