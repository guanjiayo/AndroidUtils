package zs.xmx.utils;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.util.List;

/*
 * @创建者     默小铭
 * @博客       http://blog.csdn.net/u012792686
 * @创建时间   2016/11/1 22:18
 * @本类描述	  Activity跳转界面工具类
 * @内容说明   1.Activity之间跳转
 *             2.Activity的转场动画
 *             3.获取当前运行的Activity
 *             4.判断Activity是否存在
 * @补充内容
 *
 * ---------------------------------     
 * @新增内容 {TODO} Activity之间的转场动画
 *
 */
public class ActivityUtils extends AppCompatActivity {
    /**
     * 通过Class跳转界面
     **/
    public void startActivity(Class<?> cls) {
        startActivity(cls,false);

    }
    /**
     * 通过Class跳转界面,跳转后是否finish原来的Activity
     **/
    public void startActivity(Class<?> cls,boolean isFinish) {
        startActivity(cls,null,isFinish);

    }

    /**
     * 含有Bundle通过Class跳转界面
     **/
    public void startActivity(Class<?> cls, Bundle bundle,boolean isFinish) {
        Intent intent = new Intent();
        intent.setClass(this, cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
        //  overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
        if(isFinish){
            finish();
        }
    }

    /**
     * 通过Action跳转界面
     **/
    public void startActivity(String action) {
        startActivity(action, false);
    }
    /**
     * 通过Action跳转界面,跳转后是否finish原来的Activity
     **/
    public void startActivity(String action,boolean isFinish) {
        startActivity(action, null,isFinish);
    }

    /**
     * 含有Bundle通过Action跳转界面
     **/
    public void startActivity(String action, Bundle bundle ,boolean isFinish) {
        Intent intent = new Intent();
        intent.setAction(action);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
        //   overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
        if(isFinish){
            finish();
        }
    }

    /**
     * 含有Bundle通过Class打开编辑界面
     **/
    public void startActivityForResult(Class<?> cls, Bundle bundle, int requestCode) {
        Intent intent = new Intent();
        intent.setClass(this, cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivityForResult(intent, requestCode);
        //    overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
    }

    /**
     * 带有右进右出动画的退出
     */
    @Override
    public void finish() {
        super.finish();
        //    overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
    }

    /**
     * 默认退出
     */
    public void defaultFinish() {
        super.finish();
    }

    /**
     * 判断是否存在Activity
     *
     * @param context     上下文
     * @param packageName 包名
     * @param className   activity全路径类名
     * @return {@code true}: 是<br>{@code false}: 否
     */
    public static boolean isActivityExists(Context context, String packageName, String className) {
        Intent intent = new Intent();
        intent.setClassName(packageName, className);
        return !(context.getPackageManager().resolveActivity(intent, 0) == null ||
                intent.resolveActivity(context.getPackageManager()) == null ||
                context.getPackageManager().queryIntentActivities(intent, 0).size() == 0);
    }

    /**
     * 获取launcher activity
     *
     * @param context     上下文
     * @param packageName 包名
     * @return launcher activity
     */
    public static String getLauncherActivity(Context context, String packageName) {
        Intent intent = new Intent(Intent.ACTION_MAIN, null);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        PackageManager pm = context.getPackageManager();
        List<ResolveInfo> infos = pm.queryIntentActivities(intent, 0);
        for (ResolveInfo info : infos) {
            if (info.activityInfo.packageName.equals(packageName)) {
                return info.activityInfo.name;
            }
        }
        return "no " + packageName;
    }
}
