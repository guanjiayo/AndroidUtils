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
        1.部分手机修改过安卓系统Rom,如小米4,如小米4,shouldShowRequestPermissionRationale会一直返回false
        2.targetSDKVersion>=23才有动态权限机制
        3.6.0以前的版本,shouldShowRequestPermissionRationale会一直返回false
        4.兼容性,使用V4包下的ActivityCompat(CotextCompat是它的父类)
        5.6.0以前的版本,ActivityCompat.checkSelfPermission()会始终等于PERMISSION_GRANTED
        6.6.0以前的版本,如果用户关闭了你申请的权限，ActivityCompat.checkSelfPermission(),会导致程序崩溃,加判断低于23就不申请权限

 * ---------------------------------     
 * @更新时间   
 * @新增内容
 *
 *
 *
 */

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import zs.xmx.utils.DialogUtils;
import zs.xmx.utils.Logger;


public class PermissionsUtils {

    //请求码(2017目前的动态权限,后续有更新继续补充)
    public static final  int CODE_WRITE_CONTACTS         = 0;
    public static final  int CODE_GET_ACCOUNTS           = 1;
    public static final  int CODE_READ_CONTACTS          = 2;
    public static final  int CODE_READ_CALL_LOG          = 3;
    public static final  int CODE_READ_PHONE_STATE       = 4;
    public static final  int CODE_CALL_PHONE             = 5;
    public static final  int CODE_WRITE_CALL_LOG         = 6;
    public static final  int CODE_USE_SIP                = 7;
    public static final  int CODE_PROCESS_OUTGOING_CALLS = 8;
    public static final  int CODE_READ_CALENDAR          = 9;
    public static final  int CODE_WRITE_CALENDAR         = 10;
    public static final  int CODE_CAMERA                 = 11;
    public static final  int CODE_BODY_SENSORS           = 12;
    public static final  int CODE_ACCESS_FINE_LOCATION   = 13;
    public static final  int CODE_ACCESS_COARSE_LOCATION = 14;
    public static final  int CODE_READ_EXTERNAL_STORAGE  = 15;
    public static final  int CODE_WRITE_EXTERNAL_STORAGE = 16;
    public static final  int CODE_RECORD_AUDIO           = 17;
    public static final  int CODE_READ_SMS               = 18;
    public static final  int CODE_RECEIVE_WAP_PUSH       = 19;
    public static final  int CODE_RECEIVE_MMS            = 20;
    public static final  int CODE_RECEIVE_SMS            = 21;
    public static final  int CODE_SEND_SMS               = 22;
    private static final int CODE_ALL_PERMISSION         = 100;
    private static final int CODE_Mutil_PERMISSION       = 200;

    //危险权限9组,25个权限(需要动态申请),每组只要有一个权限申请成功,默认整组权限都能用
    //联系人权限
    public static final String PERMISSION_WRITE_CONTACTS         = Manifest.permission.WRITE_CONTACTS;
    public static final String PERMISSION_GET_ACCOUNTS           = Manifest.permission.GET_ACCOUNTS;
    public static final String PERMISSION_READ_CONTACTS          = Manifest.permission.READ_CONTACTS;
    //电话权限(清单文件没找到PERMISSION_ADD_VOICEMAIL)
    public static final String PERMISSION_READ_CALL_LOG          = Manifest.permission.READ_CALL_LOG;
    public static final String PERMISSION_READ_PHONE_STATE       = Manifest.permission.READ_PHONE_STATE;
    public static final String PERMISSION_CALL_PHONE             = Manifest.permission.CALL_PHONE;
    public static final String PERMISSION_WRITE_CALL_LOG         = Manifest.permission.WRITE_CALL_LOG;
    public static final String PERMISSION_USE_SIP                = Manifest.permission.USE_SIP;
    public static final String PERMISSION_PROCESS_OUTGOING_CALLS = Manifest.permission.PROCESS_OUTGOING_CALLS;
    //public static final String PERMISSION_ADD_VOICEMAIL          = Manifest.permission.ADD_VOICEMAIL;
    //日历权限
    public static final String PERMISSION_READ_CALENDAR          = Manifest.permission.READ_CALENDAR;
    public static final String PERMISSION_WRITE_CALENDAR         = Manifest.permission.WRITE_CALENDAR;
    //相机权限
    public static final String PERMISSION_CAMERA                 = Manifest.permission.CAMERA;
    //传感器权限
    public static final String PERMISSION_BODY_SENSORS           = Manifest.permission.BODY_SENSORS;
    //定位权限
    public static final String PERMISSION_ACCESS_FINE_LOCATION   = Manifest.permission.ACCESS_FINE_LOCATION;
    public static final String PERMISSION_ACCESS_COARSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
    //存储权限
    public static final String PERMISSION_READ_EXTERNAL_STORAGE  = Manifest.permission.READ_EXTERNAL_STORAGE;
    public static final String PERMISSION_WRITE_EXTERNAL_STORAGE = Manifest.permission.WRITE_EXTERNAL_STORAGE;
    //麦克风权限
    public static final String PERMISSION_RECORD_AUDIO           = Manifest.permission.RECORD_AUDIO;
    //短信权限(没找到READ_CELL_BROADCASTS)
    public static final String PERMISSION_READ_SMS               = Manifest.permission.READ_SMS;
    public static final String PERMISSION_RECEIVE_WAP_PUSH       = Manifest.permission.RECEIVE_WAP_PUSH;
    public static final String PERMISSION_RECEIVE_MMS            = Manifest.permission.RECEIVE_MMS;
    public static final String PERMISSION_RECEIVE_SMS            = Manifest.permission.RECEIVE_SMS;
    public static final String PERMISSION_SEND_SMS               = Manifest.permission.SEND_SMS;

    public static final String[] requestPermissions = {
            PERMISSION_WRITE_CONTACTS,
            PERMISSION_GET_ACCOUNTS,
            PERMISSION_READ_CONTACTS,
            PERMISSION_READ_CALL_LOG,
            PERMISSION_READ_PHONE_STATE,
            PERMISSION_CALL_PHONE,
            PERMISSION_WRITE_CALL_LOG,
            PERMISSION_USE_SIP,
            PERMISSION_PROCESS_OUTGOING_CALLS,
            PERMISSION_READ_CALENDAR,
            PERMISSION_WRITE_CALENDAR,
            PERMISSION_CAMERA,
            PERMISSION_BODY_SENSORS,
            PERMISSION_ACCESS_FINE_LOCATION,
            PERMISSION_ACCESS_COARSE_LOCATION,
            PERMISSION_READ_EXTERNAL_STORAGE,
            PERMISSION_WRITE_EXTERNAL_STORAGE,
            PERMISSION_RECORD_AUDIO,
            PERMISSION_READ_SMS,
            PERMISSION_RECEIVE_WAP_PUSH,
            PERMISSION_RECEIVE_MMS,
            PERMISSION_RECEIVE_SMS,
            PERMISSION_SEND_SMS
    };


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
        //首次申请或彻底禁止,没有申请到的权限
        final List<String> permissionsList = getMutilPermission(activity, mutilPermissionList, false);
        //没有彻底禁止,但没有申请到的权限
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
            Toast.makeText(activity, "所有权限申请成功" + notGranted, Toast.LENGTH_SHORT).show();
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
    /**特殊权限**/
    //清单文件先声明权限,在Activty中实现下面代码:
    //
    //    /**
    //     * 悬浮窗权限
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
    //        Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION);
    //        intent.setData(Uri.parse("package:" + getPackageName()));
    //        startActivityForResult(intent, REQUEST_Floating_WINDOW);
    //    }
    //
    //    /**
    //     * 系统设置
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
    //        Intent intent = new Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS);
    //        intent.setData(Uri.parse("package:" + getPackageName()));
    //        startActivityForResult(intent, REQUEST_CODE_WRITE_SETTINGS);
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
    //            if (Settings.canDrawOverlays(this)) {
    //                Logger.i(TAG, "onActivityResult granted");
    //            }
    //        }
    //        if (requestCode == REQUEST_CODE_WRITE_SETTINGS) {
    //            if (Settings.System.canWrite(this)) {
    //                Logger.i(TAG, "onActivityResult write settings granted");
    //            }
    //        }
    //    }

}
