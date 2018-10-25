package zs.xmx.lib_utils.moblie;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;

import static zs.xmx.utils.ConvertUtils.byte2FitMemorySize;


/*
 * @创建者     默小铭
 * @博客       http://blog.csdn.net/u012792686
 * @创建时间   2016/9/25 20:28
 * @本类描述	  手机内部存储_相关工具类
 * @内容说明   1.获取Data路径
 *            2.获取手机内部存储声音空间/总空间/已用空间
 *
 * 需要加一下权限:
 *      <------SD卡读取/写入权限:-------->
 *      <uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE"/>
 *      <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"/>
 *
 *      <------SD卡创建与删除文件权限:-------->
 *      <uses-permission android:name="android.permission.MOUNT_UNMOUNT_FILESYSTEMS"/>
 *
 * ---------------------------------------------     
 * @更新时间   2016/9/25
 * @更新说明
 */
public class SysInternalUtils {
    /**
     * 获取手机内部存储  Data路径
     *
     * @return Data路径
     */
    public static String getDataPath() {
        return Environment.getDataDirectory().getPath();
    }

    /**
     * 获取手机内部 缓存路径
     *
     * @param context 上下文
     * @return 手机内部缓存路径
     */
    public static String getInternalCache(Context context) {
        return context.getCacheDir().getPath();
    }

    /**
     * 获取手机内部存储  剩余空间
     *
     * @return
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    public static String getFreeSpace() {
        //Android 获取系统文件信息空间大小的类
        StatFs stat = new StatFs(getDataPath());
        long blockSize, availableBlocks;
        //扇区剩余空间大小
        availableBlocks = stat.getAvailableBlocksLong();
        //扇区大小
        blockSize = stat.getBlockSizeLong();
        return byte2FitMemorySize(availableBlocks * blockSize);
    }

    /**
     * 获取手机内部存储 总空间
     *
     * @return 手机内部存储 总空间
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    public static String getAmountSpace() {
        StatFs stat = new StatFs(getDataPath());
        long blockSize, totalBlocks;
        //扇区总大小
        totalBlocks = stat.getBlockCountLong();
        //扇区大小
        blockSize = stat.getBlockSizeLong();
        return byte2FitMemorySize(totalBlocks * blockSize);
    }

    /**
     * 获取手机内部存储 已用空间
     *
     * @return 手机内部存储 已用空间
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    public static String getUsedSpace() {
        StatFs stat = new StatFs(getDataPath());
        long blockSize, totalBlocks, availableBlocks;
        availableBlocks = stat.getAvailableBlocksLong();
        totalBlocks = stat.getBlockCountLong();
        blockSize = stat.getBlockSizeLong();
        return byte2FitMemorySize((totalBlocks - availableBlocks) * blockSize);
    }


}
