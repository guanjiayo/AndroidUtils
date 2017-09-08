package zs.xmx.todo;

import android.app.Activity;
import android.os.CountDownTimer;
import android.util.DisplayMetrics;

import java.util.concurrent.TimeUnit;

import static android.util.TypedValue.COMPLEX_UNIT_DIP;
import static android.util.TypedValue.COMPLEX_UNIT_IN;
import static android.util.TypedValue.COMPLEX_UNIT_MM;
import static android.util.TypedValue.COMPLEX_UNIT_PT;
import static android.util.TypedValue.COMPLEX_UNIT_PX;
import static android.util.TypedValue.COMPLEX_UNIT_SP;

/*
 * @创建者     默小铭
 * @博客       http://blog.csdn.net/u012792686
 * @创建时间   2016/9/20 14:48
 * @本类描述	  补充内容
 * @内容说明   1.TimeUnit 时间单位转换
 *            2.CountDownTimer  倒计时类
 *            3.DisplayMetrics 屏幕度量
 *            4.TypedValue 单位转换(px,dp,sp,pt)
 *
 * 这个工具类最终要修改成Blankj的形式
 * https://github.com/Blankj/AndroidUtilCode/blob/master/update_log.md
 * 1.一个放在GitHub上的类库
 * 2.一个测试用的Sample
 *
 * @要百度的资料
 *            1.ActivityInfo
 *            2.测量子控件宽高,相对父布局位置
 *            3.BufferedOutputStream 带缓存的输出流
 *            4.Locale  本地化相关的(看Zxing来做一个)
 *            5.TelephonyManager 补充一下
 *            6.SmsManager
 *            7.ClipboardManager
 *            8.Base64
 *            9.PackageInfo  PackageManager
 *            10.Vibrator
 *            11.ThumbnailUtils
 *            12.DownloadManager
 *            13.BigDecimal
 *            14.Properties
 *            15.AlarmManager
 *            16.PendingIntent
 *            17.WifiManager  wifi管理器
 *            18.NetworkInfo
 *            19.ConnectivityManager
 *            20.百度地图权限问题
 *
 *      @{TODO} 把现有的工具类细分功能,拆分一下包
 */
public class Others {
    //TODO 返回结果有多种可能性的等Handler优化出来,补充

    /**
     * -----------------------TimeUnit---------------------------
     */

    /**
     * TimeUnit
     * Android 自带的时间单位转换类
     */
    public void useTimeUnit() { //例子: 秒 转 分钟
        long secondToMinutes = TimeUnit.SECONDS.toMinutes(60);

    }

    /**
     * -----------------------CountDownTimer---------------------------
     */

    /**
     * CountDownTimer
     * Android 自带的倒计时 类
     */
    public class TimeCount extends CountDownTimer {
        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
            // 参数依次为总时长,和计时的时间间隔
        }

        @Override
        public void onFinish() {   // 计时完毕时触发

        }

        @Override
        public void onTick(long millisUntilFinished) {   // 计时过程显示

        }
    }

    /**
     * -----------------------DisplayMetrics---------------------------
     */

    /**
     * 获取当前Activity屏幕信息
     *
     * @param context
     * @return 屏幕信息
     */
    public static String getDispalyMetrics(Activity context) {
        String str = "";
        DisplayMetrics metrics = new DisplayMetrics(); //Android 自带 屏幕度量
        context.getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int screenWidth = metrics.widthPixels;
        int screenHeight = metrics.heightPixels;
        float density = metrics.density;
        float xdpi = metrics.xdpi;
        float ydpi = metrics.ydpi;

        str += "The absolute width:" + String.valueOf(screenWidth) + "pixels\n";
        str += "The absolute heightin:" + String.valueOf(screenHeight) + "pixels\n";
        str += "The logical density of the display.:" + String.valueOf(density)
                + "\n";
        str += "X dimension :" + String.valueOf(xdpi) + "pixels per inch\n";
        str += "Y dimension :" + String.valueOf(ydpi) + "pixels per inch\n";

        return str;

    }

    /**
     * -----------------------TypedValue---------------------------
     */

    /**
     * 各种像素单位转换
     * <p>
     * 该方法在 TypedValue 抽取出来的
     *
     * @param unit    单位 px:0  dip:1  sp:2  pt:3  xdpi:4
     * @param value   值
     * @param metrics DisplayMetrics 实例化就好
     * @return 转换结果
     */
    public static float applyDimension(int unit, float value,
                                       DisplayMetrics metrics) {
        switch (unit) {
            case COMPLEX_UNIT_PX:
                return value;
            case COMPLEX_UNIT_DIP:
                return value * metrics.density;
            case COMPLEX_UNIT_SP:
                return value * metrics.scaledDensity;
            case COMPLEX_UNIT_PT:
                return value * metrics.xdpi * (1.0f / 72);
            case COMPLEX_UNIT_IN:
                return value * metrics.xdpi;
            case COMPLEX_UNIT_MM:
                return value * metrics.xdpi * (1.0f / 25.4f);
        }
        return 0;
    }

    /**
     * -----------------------百度地图权限问题---------------------------
     */

//    //部分早期Android6.0版存在位置开关关闭后无法定位的问题，目前测试最新的Android6.0版本原生系统，已不存在此问题。
    //    //针对6.0系统的位置开关问题。由于GPS设置的行为在各版本中一致，因此可以简单的使用系统API来判断用户是否打开了位置按钮，让检测到系统版本为6.0，并且用户未打开按钮时，进行提示。
    //    LocationManager locManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
    //
    //if(!locManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
    //
    //        // 未打开位置开关，可能导致定位失败或定位不准，提示用户或做相应处理
    //    }

}
