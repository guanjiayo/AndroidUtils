package zs.xmx.constant;
/*
 * @创建者     默小铭
 * @博客       http://blog.csdn.net/u012792686
 * @创建时间   2017/9/10
 * @本类描述	  权限管理工具类_常量类
 * @使用   import static zs.xmx.constant.PermissionsContants.*; 直接使用
 *
 */

import android.Manifest;
import android.os.Build;

public class PermissionsContants {

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
    public static final int CODE_ALL_PERMISSION         = 100;
    public static final int CODE_Mutil_PERMISSION       = 200;

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

    public static final String[] CONTACTS_GROUP;//读写联系人
    public static final String[] PHONE_GROUP;//读电话状态、打电话、读写电话记录
    public static final String[] CALENDAR_GROUP;//读写日厉
    public static final String[] CAMERA_GROUP;//相机
    public static final String[] SENSORS_GROUP;//传感器
    public static final String[] LOCATION_GROUP;//读位置信息
    public static final String[] STORAGE_GROUP;//读写存储卡
    public static final String[] MICROPHONE_GROUP;//使用麦克风
    public static final String[] SMS_GROUP;//读写短信、收发短信

    /**
     * 在Android M以前使用某权限是不需要用户授权的，
     * 只要在Manifest中注册即可，在Android M之后需要注册并申请用户授权，
     * 所以我们根据系统版本在Android M以前用一个空数组作为权限组，在Android M以后用真实数组权限。
     */
    static {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            CONTACTS_GROUP = new String[]{};
            PHONE_GROUP = new String[]{};
            CALENDAR_GROUP = new String[]{};
            CAMERA_GROUP = new String[]{};
            SENSORS_GROUP = new String[]{};
            LOCATION_GROUP = new String[]{};
            STORAGE_GROUP = new String[]{};
            MICROPHONE_GROUP = new String[]{};
            SMS_GROUP = new String[]{};
        } else {
            CONTACTS_GROUP = new String[]{
                    PERMISSION_WRITE_CONTACTS,
                    PERMISSION_READ_CONTACTS,
                    PERMISSION_GET_ACCOUNTS
            };
            PHONE_GROUP = new String[]{
                    PERMISSION_READ_CALL_LOG,
                    PERMISSION_READ_PHONE_STATE,
                    PERMISSION_CALL_PHONE,
                    PERMISSION_WRITE_CALL_LOG,
                    PERMISSION_USE_SIP,
                    PERMISSION_PROCESS_OUTGOING_CALLS
            };
            CALENDAR_GROUP = new String[]{
                    PERMISSION_READ_CALENDAR,
                    PERMISSION_WRITE_CALENDAR
            };
            CAMERA_GROUP = new String[]{
                    PERMISSION_CAMERA
            };
            SENSORS_GROUP = new String[]{
                    PERMISSION_BODY_SENSORS
            };
            LOCATION_GROUP = new String[]{
                    PERMISSION_ACCESS_COARSE_LOCATION,
                    PERMISSION_ACCESS_FINE_LOCATION
            };
            STORAGE_GROUP = new String[]{
                    PERMISSION_READ_EXTERNAL_STORAGE,
                    PERMISSION_WRITE_EXTERNAL_STORAGE
            };
            MICROPHONE_GROUP = new String[]{
                    PERMISSION_RECORD_AUDIO
            };
            SMS_GROUP = new String[]{
                    PERMISSION_READ_SMS,
                    PERMISSION_RECEIVE_WAP_PUSH,
                    PERMISSION_RECEIVE_MMS,
                    PERMISSION_RECEIVE_SMS,
                    PERMISSION_SEND_SMS,
            };
        }

    }


}
