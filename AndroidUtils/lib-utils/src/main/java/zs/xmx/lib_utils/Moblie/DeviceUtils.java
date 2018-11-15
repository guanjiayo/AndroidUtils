package zs.xmx.lib_utils.moblie;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.provider.Settings;
import android.telephony.TelephonyManager;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.NetworkInterface;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.regex.Pattern;

import zs.xmx.lib_utils.utils.Logger;
import zs.xmx.lib_utils.utils.ShellUtils;


/*
 * @创建者     默小铭
 * @博客       http://blog.csdn.net/u012792686
 * @创建时间   2016/9/5 16:19
 * @本类描述	  获取设备相关信息
 * @内容说明   1.获取Mac地址
 *            2.调用TelePhoneManger的如果没有通话功能的设备可能返回null
 *            3.使用按需求,如唯一识别码(MD5加密(MAC+IMEI))
 *            4.获取手机状态建议用 getPhoneStatue
 *            5.得到CPU核心数
 */
public class DeviceUtils {
    protected static final String TAG = DeviceUtils.class.getSimpleName();

    // 移动
    private static final int CHINA_MOBILE       = 1;
    // 联通
    private static final int UNICOM             = 2;
    // 电信
    private static final int TELECOMMUNICATIONS = 3;
    // 失败
    private static final int ERROR              = 0;

    /**
     * 设备唯一标识(UUID+设备序列号)
     *
     * @param context
     * @return 唯一识别码(不可变)
     */
    public static String getUUID(Context context) {
        final TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        final String tmDevice, tmSerial, androidId;
        tmDevice = "" + tm.getDeviceId();
        tmSerial = "" + tm.getSimSerialNumber();
        androidId = "" + Settings.Secure.getString(context.getContentResolver(),
                Settings.Secure.ANDROID_ID);
        UUID deviceUuid = new UUID(androidId.hashCode(), ((long) tmDevice.hashCode() << 32) | tmSerial.hashCode());
        String uniqueId = deviceUuid.toString();
        return uniqueId;
    }

    /**
     * 获取设备AndroidID
     *
     * @param context 上下文
     * @return AndroidID
     */
    @SuppressLint("HardwareIds")
    public static String getAndroidID(Context context) {
        return Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
    }


    /**
     * 获取设备MAC地址
     * <p>需添加权限 {@code <uses-permission android:name="android.permission.ACCESS_WIFI_STATE"/>}</p>
     * <p>需添加权限 {@code <uses-permission android:name="android.permission.INTERNET"/>}</p>
     *
     * @param context 上下文
     * @return MAC地址
     */
    public static String getMacAddress(Context context) {
        String macAddress = getMacAddressByWifiInfo(context);
        if (!"02:00:00:00:00:00".equals(macAddress)) {
            return macAddress;
        }
        macAddress = getMacAddressByNetworkInterface();
        if (!"02:00:00:00:00:00".equals(macAddress)) {
            return macAddress;
        }
        macAddress = getMacAddressByFile();
        if (!"02:00:00:00:00:00".equals(macAddress)) {
            return macAddress;
        }
        return "please open wifi";
    }

    /**
     * 获取设备MAC地址
     * <p>需添加权限 {@code <uses-permission android:name="android.permission.ACCESS_WIFI_STATE"/>}</p>
     *
     * @param context 上下文
     * @return MAC地址
     */
    @SuppressLint("HardwareIds")
    private static String getMacAddressByWifiInfo(Context context) {
        try {
            WifiManager wifi = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
            if (wifi != null) {
                WifiInfo info = wifi.getConnectionInfo();
                if (info != null) return info.getMacAddress();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "02:00:00:00:00:00";
    }

    /**
     * 获取设备MAC地址
     * <p>需添加权限 {@code <uses-permission android:name="android.permission.INTERNET"/>}</p>
     *
     * @return MAC地址
     */
    private static String getMacAddressByNetworkInterface() {
        try {
            List<NetworkInterface> nis = Collections.list(NetworkInterface.getNetworkInterfaces());
            for (NetworkInterface ni : nis) {
                if (!ni.getName().equalsIgnoreCase("wlan0")) continue;
                byte[] macBytes = ni.getHardwareAddress();
                if (macBytes != null && macBytes.length > 0) {
                    StringBuilder res1 = new StringBuilder();
                    for (byte b : macBytes) {
                        res1.append(String.format("%02x:", b));
                    }
                    return res1.deleteCharAt(res1.length() - 1).toString();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "02:00:00:00:00:00";
    }

    /**
     * 获取设备MAC地址
     *
     * @return MAC地址
     */
    private static String getMacAddressByFile() {
        ShellUtils.CommandResult result = ShellUtils.execCmd("getprop wifi.interface", false);
        if (result.result == 0) {
            String name = result.successMsg;
            if (name != null) {
                result = ShellUtils.execCmd("cat /sys/class/net/" + name + "/address", false);
                if (result.result == 0) {
                    if (result.successMsg != null) {
                        return result.successMsg;
                    }
                }
            }
        }
        return "02:00:00:00:00:00";
    }


    /**
     * 获取蓝牙MAC地址
     * 需添加权限:  android.permission.BLUETOOTH
     *
     * @return
     */
    public static String getBT_MACAddress() {
        BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        return mBluetoothAdapter.getAddress();
    }

    /**
     * TelephonyManager对象
     *
     * @param context
     * @return
     */
    private static TelephonyManager getTelphoneManager(Context context) {
        return (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
    }

    /**
     * 获取设备sim卡的序列号
     * <p/>
     * 对于CDMA设备,返回的是一个空值
     *
     * @param context
     * @return
     */
    public static String getSimSerialNumber(Context context) {
        return getTelphoneManager(context).getSimSerialNumber();
    }

    /**
     * 获得GSM设备的IMEI号和CDMA手机的MEID
     * 需要权限:<uses-permission android:name="android.permission.READ_PHONE_STATE"/>
     * 限制:对于手机来说IMEI是唯一,其他设备如平板可能没有(返回null)
     *
     * @param context
     * @return
     */
    public static String getIMEI(Context context) {
        return getTelphoneManager(context).getDeviceId();
    }

    /**
     * 获取客户ID,GSM中是IMSI号(sim卡的电子串号)
     *
     * @param context
     * @return
     */
    public static String getImsi(Context context) {

        return getTelphoneManager(context).getSubscriberId();
    }


    /**
     * 获取设备型号
     *
     * @return
     */
    public static String getModel() {
        String model = Build.MODEL;
        if (model != null) {
            model = model.trim().replaceAll("\\s*", "");
        } else {
            model = "";
        }
        return model;

    }


    /**
     * 获取设备硬件厂商名字
     *
     * @return
     */
    public static String getManufacturer() {
        return Build.MANUFACTURER;
    }

    /**
     * 获取设备制造商名字
     *
     * @return
     */
    public static String getProduct() {
        return Build.PRODUCT;
    }

    /**
     * 获取设备参数
     *
     * @return
     */
    public static String getDevice() {
        return Build.DEVICE;
    }

    /**
     * 获取手机状态信息
     * <p>
     * 需添加权限
     * <uses-permission android:name="android.permission.READ_PHONE_STATE"/>
     *
     * @param context 上下文
     * @return DeviceId(IMEI) = 99000311726612<br>
     * DeviceSoftwareVersion = 00<br>
     * Line1Number =<br>
     * NetworkCountryIso = cn<br>
     * NetworkOperator = 46003<br>
     * NetworkOperatorName = 中国电信<br>
     * NetworkType = 6<br>
     * honeType = 2<br>
     * SimCountryIso = cn<br>
     * SimOperator = 46003<br>
     * SimOperatorName = 中国电信<br>
     * SimSerialNumber = 89860315045710604022<br>
     * SimState = 5<br>
     * SubscriberId(IMSI) = 460030419724900<br>
     * VoiceMailNumber = *86<br>
     */
    public static String getPhoneStatus(Context context) {
        TelephonyManager tm = (TelephonyManager) context
                .getSystemService(Context.TELEPHONY_SERVICE);
        String str = "";
        str += "DeviceId(IMEI) = " + tm.getDeviceId() + "\n";
        str += "DeviceSoftwareVersion = " + tm.getDeviceSoftwareVersion() + "\n";
        str += "Line1Number = " + tm.getLine1Number() + "\n";
        str += "NetworkCountryIso = " + tm.getNetworkCountryIso() + "\n";
        str += "NetworkOperator = " + tm.getNetworkOperator() + "\n";
        str += "NetworkOperatorName = " + tm.getNetworkOperatorName() + "\n";
        str += "NetworkType = " + tm.getNetworkType() + "\n";
        str += "honeType = " + tm.getPhoneType() + "\n";
        str += "SimCountryIso = " + tm.getSimCountryIso() + "\n";
        str += "SimOperator = " + tm.getSimOperator() + "\n";
        str += "SimOperatorName = " + tm.getSimOperatorName() + "\n";
        str += "SimSerialNumber = " + tm.getSimSerialNumber() + "\n";
        str += "SimState = " + tm.getSimState() + "\n";
        str += "SubscriberId(IMSI) = " + tm.getSubscriberId() + "\n";
        str += "VoiceMailNumber = " + tm.getVoiceMailNumber() + "\n";
        return str;
    }

    /**
     * SDK_INT 版本
     *
     * @return
     */
    public static int getSDKVersion() {
        return Build.VERSION.SDK_INT;
    }

    /**
     * sim卡运营商名字
     *
     * @param context
     * @return
     */
    public static String getSimOperatorName(Context context) {
        return getTelphoneManager(context).getSimOperatorName();
    }

    /**
     * 获取当前设备运营商(通过sim卡)
     *
     * @param context
     * @return 返回0 表示失败 1表示为中国移动 2为中国联通 3为中国电信
     */
    public static int getProviderName(Context context) {
        String IMSI = getImsi(context);
        if (IMSI == null) {
            return ERROR;
        }
        if (IMSI.startsWith("46000") || IMSI.startsWith("46002")) {
            return CHINA_MOBILE;
        } else if (IMSI.startsWith("46001")) {
            return UNICOM;
        } else if (IMSI.startsWith("46003")) {
            return TELECOMMUNICATIONS;
        }
        return ERROR;
    }


    /**
     * 获取移动端制式
     * <p>
     * TelephonyManager#PHONE_TYPE_NONE  : 0 手机制式未知
     * TelephonyManager#PHONE_TYPE_GSM   : 1 手机制式为GSM，移动和联通
     * TelephonyManager#PHONE_TYPE_CDMA  : 2 手机制式为CDMA，电信
     * TelephonyManager#PHONE_TYPE_SIP   : 3
     *
     * @param context 上下文
     * @return 手机制式
     */
    public static int getPhoneModelTpye(Context context) {
        TelephonyManager tm = (TelephonyManager) context
                .getSystemService(Context.TELEPHONY_SERVICE);
        return tm != null ? tm.getPhoneType() : -1;
    }

    /**
     * 设备CPU名字
     *
     * @return
     */
    public static String getCpuName() {
        FileReader fileReader = null;
        BufferedReader bufferedReader = null;
        try {
            // 读取文件CPU信息
            fileReader = new FileReader("/pro/cpuinfo");
            bufferedReader = new BufferedReader(fileReader);
            String string = bufferedReader.readLine();
            String[] strings = string.split(":\\s+", 2);
            return strings[1];
        } catch (FileNotFoundException e) {
            Logger.e(TAG, e.getLocalizedMessage());
        } catch (IOException e) {
            Logger.e(TAG, e.getLocalizedMessage());
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    Logger.e(TAG, e.getLocalizedMessage());
                }
            }
            if (fileReader != null) {
                try {
                    fileReader.close();
                } catch (IOException e) {
                    Logger.e(TAG, e.getLocalizedMessage());
                }
            }
        }
        return null;
    }

    /**
     * 得到CPU核心数
     *
     * @return CPU核心数
     */
    public static int getNumCores() {
        try {
            File dir = new File("/sys/devices/system/cpu/");
            File[] files = dir.listFiles(new FileFilter() {
                @Override
                public boolean accept(File pathname) {
                    return Pattern.matches("cpu[0-9]", pathname.getName());
                }
            });
            return files.length;
        } catch (Exception e) {
            return 1;
        }
    }

}
