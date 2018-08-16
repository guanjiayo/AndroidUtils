package zs.xmx.lib_utils.Moblie;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;

import java.io.File;

import static zs.xmx.utils.ConvertUtils.byte2FitMemorySize;


/*
 * @创建者     默小铭
 * @博客       http://blog.csdn.net/u012792686
 * @创建时间   2016/9/24 21:54
 * @本类描述	  SD卡_相关工具类
 * @内容说明   1.判断SD卡是否可用
 *             2.获取SD卡路径
 *             3.获取SD卡下载缓存路径
 *             4.获取SD卡剩余空间/总空间/已用空间
 *
 *
 * 需要添加以下权限:
 *     <------SD卡读取/写入权限:-------->
 *     <uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE"/>
       <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"/>
       <------SD卡创建与删除文件权限:-------->
       <uses-permission android:name="android.permission.MOUNT_UNMOUNT_FILESYSTEMS"/>
 * ---------------------------------------------     
 * @更新时间   2016/9/24 
 * @更新说明
 */
public class SDCardUtils {
    /**
     * 判断SD卡是否可用
     *
     * @return true : 可用 ; false : 不可用
     */
    public static boolean isSDCardEnable() {
        return Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState());
    }

    /**
     * 获取SD卡路径
     * 一般是/storage/emulated/0/
     *
     * @return SD卡路径
     */
    public static String getSDCardPath() {
        if (!isSDCardEnable())
            return "sdcard unable!";
        return Environment.getExternalStorageDirectory().getPath() + File.separator;
    }

    /**
     * 获取SD卡下载 缓存路径
     * 一般是 /cache/
     *
     * @return SD卡缓存路径
     */
    public static String getDownloadCachePath() {
        if (!isSDCardEnable())
            return "sdcard unable";
        return Environment.getDownloadCacheDirectory().getPath() + File.separator;
    }

    /**
     * 获取SD卡剩余空间
     *
     * @return SD卡剩余空间
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    public static String getFreeSpace() {
        if (!isSDCardEnable())
            return "sdcard unable!";
        //Android 获取系统文件信息空间大小的类
        StatFs stat = new StatFs(getSDCardPath());
        long blockSize, availableBlocks;
        //扇区剩余空间大小
        availableBlocks = stat.getAvailableBlocksLong();
        //扇区大小
        blockSize = stat.getBlockSizeLong();
        return byte2FitMemorySize(availableBlocks * blockSize);
    }

    /**
     * 获取SD卡 总空间
     *
     * @return SD卡总空间
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    public static String getAmountSpace() {
        if (!isSDCardEnable())
            return "sdcard unable!";
        StatFs stat = new StatFs(getSDCardPath());
        long blockSize, totalBlocks;
        totalBlocks = stat.getBlockCountLong();
        blockSize = stat.getBlockSizeLong();
        return byte2FitMemorySize(totalBlocks * blockSize);
    }

    /**
     * 获取SD卡 已用空间
     *
     * @return SD卡已用空间
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    public static String getUsedSpace() {
        if (!isSDCardEnable())
            return "sdcard unable!";
        StatFs stat = new StatFs(getSDCardPath());
        long blockSize, totalBlocks, availableBlocks;
        availableBlocks = stat.getAvailableBlocksLong();
        totalBlocks = stat.getBlockCountLong();
        blockSize = stat.getBlockSizeLong();
        return byte2FitMemorySize((totalBlocks - availableBlocks) * blockSize);
    }


}
