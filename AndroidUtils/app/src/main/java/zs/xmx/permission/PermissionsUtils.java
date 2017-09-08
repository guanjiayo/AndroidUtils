package zs.xmx.permission;
/*
 * @创建者     mqm
 * @博客       http://blog.csdn.net/u012792686
 * @创建时间   2017/9/04
 * @本类描述	  Android 6.0 动态申请权限工具类
 * @内容说明     1.申请对应权限
 *              2.申请所有权限
 *              3.申请多个权限
 *              4.特殊权限
 *
 *             使用:
 *             1.在清单文件声明权限
 *             2.在对应业务调用申请权限方法
 *                 PermissionsUtils.requestPermission()
 *                 PermissionsUtils.requestMultiPermission(this,new String[]{Manifest.permission.CALL_PHONE},mOnPermissionListener);
 *                 PermissionsUtils.requestALLPermission()
 *             3.在申请权限的页面(Activity:this,Fragment:getActivity或View:getContext),
 *             在onRequestPermissionsResult()调用PermissionsUtils.onRequestPermissionsResult()
 *
 *
 *
 * @补充内容
 *
 *       注意:
 *       1.部分手机修改过安卓系统Rom,如小米4,如小米4,shouldShowRequestPermissionRationale会一直返回false
 *       2.targetSDKVersion>=23才有动态权限机制
 *       3.6.0以前的版本,shouldShowRequestPermissionRationale会一直返回false
 *       4.兼容性,使用V4包下的ActivityCompat(CotextCompat是它的父类)
 *       5.6.0以前的版本,ActivityCompat.checkSelfPermission()会始终等于PERMISSION_GRANTED
 *       6.6.0以前的版本,如果用户关闭了你申请的权限，ActivityCompat.checkSelfPermission(),会导致程序崩溃,加判断低于23就不申请权限
 *       7.如果在清单文件中声明了CAMERA权限,我们使用Intent的方式启动系统相机,会产生SecurityException,针对这些现象,我们需要先判断该权限是否已经授权
 *       8.Android 6.0 增加了对附近设备的权限限制,如下三个API在调用前都需要ACCESS_FINE_LOCATION 或者 ACCESS_COARSE_LOCATION权限
 *          1. WifiManager.getScanResults()
 *          2. BluetoothDevice.ACTION_FOUND
 *          3. BluetoothLeScanner.startScan()
 *
 *    TODO 在调用申请蓝牙,wifi上述第8点时,写一个检测是否调用 ACCESS_FINE_LOCATION 或者 ACCESS_COARSE_LOCATION权限,否,让用户手动设置
 *    todo requestMutil 申请权限在genymotion 6.0 会申请3次
 *
 *
 *
 */

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import zs.xmx.utils.DialogUtils;
import zs.xmx.utils.Logger;

import static zs.xmx.constant.PermissionsContants.CODE_ALL_PERMISSION;
import static zs.xmx.constant.PermissionsContants.CODE_Mutil_PERMISSION;
import static zs.xmx.constant.PermissionsContants.requestPermissions;

public class PermissionsUtils {


    private static String TAG = "PermissionsUtils";


    private static OnPermissionListener mOnPermissionListener;

    public interface OnPermissionListener {//申请权限回调

        /**
         * 授权成功,把请求码返回(请求码可不处理)
         */
        void onPermissionGranted(int requestCode);

    }

    /**
     * 申请单个权限
     * <p>
     * 只申请权限,不对某个请求的权限返回码作处理
     *
     * @param activity
     * @param requestCode
     */
    public static void requestPermission(final Activity activity, final int requestCode) {
        requestPermission(activity, requestCode, null);

    }


    /**
     * 申请单个权限
     * <p>
     * 部分机型修改过Rom,如小米4,shouldShowRequestPermissionRationale会一直返回false
     *
     * @param activity    上下文
     * @param requestCode 请求码
     * @param listener    监听器
     */
    public static void requestPermission(final Activity activity, final int requestCode, OnPermissionListener listener) {
        mOnPermissionListener = listener;

        if (requestCode < 0 || requestCode >= requestPermissions.length) {
            Logger.i(TAG, "非法的请求码(不是需要动态申请的权限)");
            return;
        }

        final String requestPermission = requestPermissions[requestCode];
        if (ActivityCompat.checkSelfPermission(activity, requestPermission) != PackageManager.PERMISSION_GRANTED) {
            //判断权限是否被彻底禁止,首次调用或彻底禁止调用requestPermissions; 没有彻底禁止调用shouldShowRequestPermissionRationale
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity, requestPermission)) {
                Logger.i(TAG, "是否被彻底禁止: " + !ActivityCompat.shouldShowRequestPermissionRationale(activity, requestPermission));
                shouldShowRationale(activity, requestCode, requestPermission);
            } else {
                /**
                 * 首次申请权限或彻底禁止,系统调用这个方法
                 *
                 * 第一个参数是Context
                 * 第二个参数是需要申请的权限的字符串数组(由此看出支持一次性申请多个权限)
                 * 第三个参数为requestCode,回调的时候检测
                 *
                 */
                ActivityCompat.requestPermissions(activity, new String[]{requestPermission}, requestCode);

            }
        } else {
            Logger.i(TAG, "已授权");
            if (mOnPermissionListener != null)
                mOnPermissionListener.onPermissionGranted(requestCode);
        }

    }

    /**
     * 申请一组或多组权限
     * <p>
     * 只申请权限,不对某个请求的权限返回码作处理
     *
     * @param activity
     * @param PermissionGroup PermissionsContants.XX_GROUP
     */
    public static void requestPermissionGroup(final Activity activity, final String[]... PermissionGroup) {

        List<String> list = new ArrayList<>();

        for (int i = 0; i < PermissionGroup.length; i++) {
            for (int j = 0; j < PermissionGroup[i].length; j++) {
                list.add(PermissionGroup[i][j]);
            }
        }
        String[] permissions = list.toArray(new String[list.size()]);
        if (permissions.length == list.size()) {
            Log.i(TAG, "requestPermissionGroup: "+list.size());
            requestMultiPermission(activity, permissions, null);
        }
    }

    /**
     * 申请一组或多组权限
     * <p>
     * 只申请权限,不对某个请求的权限返回码作处理
     *
     * @param activity
     * @param PermissionGroup PermissionsContants.XX_GROUP
     */
    public static void requestPermissionGroup(final Activity activity, OnPermissionListener listener, final String[]... PermissionGroup) {

        List<String> list = new ArrayList<>();

        for (int i = 0; i < PermissionGroup.length; i++) {
            for (int j = 0; j < PermissionGroup[i].length; j++) {
                list.add(PermissionGroup[i][j]);
            }
        }

        requestMultiPermission(activity, list.toArray(new String[list.size()]), listener);
    }

    /**
     * 申请多个权限
     * <p>
     * 只申请权限,不对某个请求的权限返回码作处理
     *
     * @param activity
     * @param mutilPermissionList
     */
    public static void requestMultiPermission(final Activity activity, final String[] mutilPermissionList) {
        requestMultiPermission(activity, mutilPermissionList, null);

    }

    /**
     * 申请多个权限
     * <p>
     * 部分机型修改过Rom,如小米4,shouldShowRequestPermissionRationale会一直返回false
     *
     * @param activity
     * @param mutilPermissionList
     * @param listener
     */
    public static void requestMultiPermission(final Activity activity, final String[] mutilPermissionList, OnPermissionListener listener) {
        mOnPermissionListener = listener;
        /**首次申请或彻底禁止,没有申请到的权限**/
        final List<String> permissionsList = getMutilPermission(activity, mutilPermissionList, false);
        /**没有彻底禁止,但没有申请到的权限**/
        final List<String> shouldRationalePermissionsList = getMutilPermission(activity, mutilPermissionList, true);
        if (permissionsList == null || shouldRationalePermissionsList == null) {
            return;
        }
        Logger.i(TAG, "requestMultiPermissions permissionsList:" + permissionsList.size() + ",shouldRationalePermissionsList:" + shouldRationalePermissionsList.size());

        if (permissionsList.size() > 0) {
            for (int i = 0; i < requestPermissions.length; i++) {
                for (int j = 0; j < mutilPermissionList.length; j++) {
                    if (mutilPermissionList[j].equals(requestPermissions[i])) {
                        Logger.i(TAG, mutilPermissionList[j]);
                        ActivityCompat.requestPermissions(activity, permissionsList.toArray(new String[permissionsList.size()]),
                                CODE_Mutil_PERMISSION);
                    }
                }
            }

        } else if (shouldRationalePermissionsList.size() > 0) {
            showMessageOKCancel(activity, "这些权限需要授权",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions(activity, shouldRationalePermissionsList.toArray(new String[shouldRationalePermissionsList.size()]),
                                    CODE_Mutil_PERMISSION);
                            Logger.i(TAG, "showMessageOKCancel requestPermissions");
                        }
                    });
        } else {
            Logger.i(TAG, "已授权");
            if (mOnPermissionListener != null)
                mOnPermissionListener.onPermissionGranted(CODE_Mutil_PERMISSION);
        }

    }

    /**
     * 申请全部权限
     * <p>
     * 只申请权限,不对某个请求的权限返回码作处理
     *
     * @param activity
     */
    public static void requestAllPermissions(final Activity activity) {
        requestAllPermissions(activity, null);

    }


    /**
     * 申请全部权限
     * <p>
     * 部分机型修改过Rom,如小米4,shouldShowRequestPermissionRationale会一直返回false
     *
     * @param activity
     */
    public static void requestAllPermissions(final Activity activity, OnPermissionListener listener) {
        mOnPermissionListener = listener;
        //首次申请或彻底禁止,没有申请到的权限
        final List<String> permissionsList = getDeniedPermission(activity, false);
        //没有彻底禁止,但没有申请到的权限
        final List<String> shouldRationalePermissionsList = getDeniedPermission(activity, true);
        if (permissionsList == null || shouldRationalePermissionsList == null) {
            return;
        }
        Logger.i(TAG, "requestMultiPermissions permissionsList:" + permissionsList.size() + ",shouldRationalePermissionsList:" + shouldRationalePermissionsList.size());

        if (permissionsList.size() > 0) {
            ActivityCompat.requestPermissions(activity, permissionsList.toArray(new String[permissionsList.size()]),
                    CODE_ALL_PERMISSION);
        } else if (shouldRationalePermissionsList.size() > 0) {
            showMessageOKCancel(activity, "这些权限需要授权",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions(activity, shouldRationalePermissionsList.toArray(new String[shouldRationalePermissionsList.size()]),
                                    CODE_ALL_PERMISSION);
                            Logger.i(TAG, "showMessageOKCancel requestPermissions");
                        }
                    });
        } else {
            Logger.i(TAG, "已授权");
            if (mOnPermissionListener != null)
                mOnPermissionListener.onPermissionGranted(CODE_ALL_PERMISSION);
        }
    }


    private static void showMessageOKCancel(Activity context, String message, DialogInterface.OnClickListener okListener) {
        DialogUtils.showAlert(context, "温馨提示", message, "确定", okListener, "取消", null);
    }

    /**
     * 获取没有申请到的权限
     *
     * @param activity
     * @param isShouldRationale true: return Denied and shouldShowRequestPermissionRationale permissions, false:return Denied and !shouldShowRequestPermissionRationale
     * @return
     */
    private static ArrayList<String> getDeniedPermission(Activity activity, boolean isShouldRationale) {

        ArrayList<String> permissions = new ArrayList<>();

        for (int i = 0; i < requestPermissions.length; i++) {
            String requestPermission = requestPermissions[i];

            if (ActivityCompat.checkSelfPermission(activity, requestPermission) != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(activity, requestPermission)) {
                    if (isShouldRationale) {
                        permissions.add(requestPermission);
                    }

                } else {
                    if (!isShouldRationale) {
                        permissions.add(requestPermission);
                    }
                }

            }
        }

        return permissions;
    }

    /**
     * 获取要申请的多组权限
     *
     * @param activity
     * @param isShouldRationale true: return Denied and shouldShowRequestPermissionRationale permissions, false:return Denied and !shouldShowRequestPermissionRationale
     * @return
     */
    private static ArrayList<String> getMutilPermission(Activity activity, String[] mutilPermissionList, boolean isShouldRationale) {

        ArrayList<String> permissions = new ArrayList<>();

        for (int i = 0; i < mutilPermissionList.length; i++) {
            String mutilPermission = mutilPermissionList[i];

            if (ActivityCompat.checkSelfPermission(activity, mutilPermission) != PackageManager.PERMISSION_GRANTED) {
                try {
                    if (ActivityCompat.shouldShowRequestPermissionRationale(activity, mutilPermission)) {
                        if (isShouldRationale) {
                            permissions.add(mutilPermission);
                        }

                    } else {
                        if (!isShouldRationale) {
                            permissions.add(mutilPermission);
                        }
                    }
                } catch (Exception e) {
                    Logger.i(TAG, e.getMessage());
                }

            }
        }

        return permissions;
    }

    /**
     * 弹出Dialog向用户解释为何申请权限shouldShowRequestPermissionRationale。
     *
     * @param activity
     * @param requestCode
     * @param requestPermission
     */
    private static void shouldShowRationale(final Activity activity, final int requestCode, final String requestPermission) {
        //申请权限还是调用 ActivityCompat.requestPermissions(activity,new String[]{requestPermission},requestCode);
        showMessageOKCancel(activity, "系统部分功能需要: " + requestPermissions[requestCode] + "权限,才能正常运作,需要现在手动设置权限吗?", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ActivityCompat.requestPermissions(activity,
                        new String[]{requestPermission},
                        requestCode);
            }
        });
    }


    /**
     * 申请单个权限结果
     *
     * @param activity
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    public static void onRequestPermissionsResult(final Activity activity, final int requestCode, @NonNull String[] permissions,
                                                  @NonNull int[] grantResults) {
        if (requestCode == CODE_ALL_PERMISSION || requestCode == CODE_Mutil_PERMISSION) {
            onRequestMutilPermissionsResult(activity, permissions, grantResults);
            return;
        }
        //不是我们规定的权限
        if (requestCode < 0 || requestCode >= requestPermissions.length) {
            Logger.i(TAG, "非法的请求码:" + requestCode);
            return;
        }

        if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            if (mOnPermissionListener != null)
                mOnPermissionListener.onPermissionGranted(requestCode);

        } else {

            openPermissionsSetting(activity, "系统部分功能需要: " + requestPermissions[requestCode] + "权限,才能正常运作,需要现在手动设置权限吗?");
        }


    }

    /**
     * 申请所有(多个)权限结果
     * <p>
     * ps:(多个)所有权限参数基本一致,写到一起了
     *
     * @param activity
     * @param permissions
     * @param grantResults
     */
    private static void onRequestMutilPermissionsResult(Activity activity, String[] permissions, int[] grantResults) {

        Map<String, Integer> perms = new HashMap<>();

        ArrayList<String> notGranted = new ArrayList<>();
        for (int i = 0; i < permissions.length; i++) {
            perms.put(permissions[i], grantResults[i]);
            if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                notGranted.add(permissions[i]);
            }
        }

        if (notGranted.size() == 0) {
            Toast.makeText(activity, "权限申请成功" + notGranted, Toast.LENGTH_SHORT).show();
        } else {

            openPermissionsSetting(activity, "部分权限需要手动授权");

        }
    }


    /**
     * 打开系统权限设置页面
     *
     * @param activity
     * @param message
     */

    private static void openPermissionsSetting(final Activity activity, String message) {
        showMessageOKCancel(activity, message, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent();
                intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                Logger.i(TAG, "getPackageName(): " + activity.getPackageName());
                Uri uri = Uri.fromParts("package", activity.getPackageName(), null);
                intent.setData(uri);
                activity.startActivity(intent);
            }
        });
    }

    /**
     * 判断清单文件是否生命了某项权限的方法
     *
     * @param context
     * @param permissionName
     * @return
     */
    public boolean hasPermissionInManifest(Context context, String permissionName) {
        final String packageName = context.getPackageName();
        try {
            final PackageInfo packageInfo = context.getPackageManager()
                    .getPackageInfo(packageName, PackageManager.GET_PERMISSIONS);
            final String[] declaredPermisisons = packageInfo.requestedPermissions;
            if (declaredPermisisons != null && declaredPermisisons.length > 0) {
                for (String p : declaredPermisisons) {
                    if (p.equals(permissionName)) {
                        return true;
                    }
                }
            }
        } catch (PackageManager.NameNotFoundException e) {

        }
        return false;
    }

    /**特殊权限**/
    //    /**
    //     * 悬浮窗权限
    //     * <p>
    //     * 引导用用户去设置页面设置
    //     * <p>
    //     * <uses-permission android:name="android.permission.SYSTEM_ALERT_WINDOW"/>
    //     * <p>
    //     * 使用Action Settings.ACTION_MANAGE_OVERLAY_PERMISSION启动隐式Intent
    //     * <p>
    //     * 使用"package:" + getPackageName()携带App的包名信息
    //     * <p>
    //     * 使用Settings.canDrawOverlays方法判断授权结果
    //     *
    //     * @param view
    //     */
    //    public void Floating_window(View view) {
    //        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
    //            // 判断是否有SYSTEM_ALERT_WINDOW权限
    //            if (!Settings.canDrawOverlays(this)) {
    //                // 申请SYSTEM_ALERT_WINDOW权限
    //                Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION);
    //                intent.setData(Uri.parse("package:" + getPackageName()));
    //                startActivityForResult(intent, REQUEST_Floating_WINDOW);
    //            } else {
    //                //doSomething
    //            }
    //        }
    //    }
    //
    //    /**
    //     * 系统设置
    //     * <p>
    //     * 引导用用户去设置页面设置
    //     * <p>
    //     * <uses-permission android:name="android.permission.WRITE_SETTINGS"/>
    //     * <p>
    //     * 使用Action Settings.ACTION_MANAGE_WRITE_SETTINGS 启动隐式Intent
    //     * <p>
    //     * 使用"package:" + getPackageName()携带App的包名信息
    //     * <p>
    //     * 使用Settings.System.canWrite方法检测授权结果
    //     *
    //     * @param view
    //     */
    //    public void System_Setting(View view) {
    //        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
    //            //判断是否有WRITE_SETTINGS权限
    //            if (!Settings.System.canWrite(this)) {
    //                //申请WRITE_SETTINGS权限
    //                Intent intent = new Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS);
    //                intent.setData(Uri.parse("package:" + getPackageName()));
    //                startActivityForResult(intent, REQUEST_CODE_WRITE_SETTINGS);
    //            } else {
    //                //doSomething
    //            }
    //        }
    //
    //    }
    //
    //    private static final int REQUEST_Floating_WINDOW     = 1;
    //    private static final int REQUEST_CODE_WRITE_SETTINGS = 2;
    //
    //    @RequiresApi(api = Build.VERSION_CODES.M)
    //    @Override
    //    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    //        super.onActivityResult(requestCode, resultCode, data);
    //        if (requestCode == REQUEST_Floating_WINDOW) {
    //            if (canDrawOverlays(this)) {
    //                Logger.i(TAG, "onActivityResult Floating_WINDOW granted");
    //            }
    //        }
    //        if (requestCode == REQUEST_CODE_WRITE_SETTINGS) {
    //            if (Settings.System.canWrite(this)) {
    //                Logger.i(TAG, "onActivityResult write settings granted");
    //            }
    //        }
    //    }
}
