package zs.xmx.lib_utils.utils;

import android.content.Context;

import java.io.Closeable;
import java.io.File;
import java.io.IOException;

import zs.xmx.lib_utils.moblie.SDCardUtils;


/*
 * @创建者     默小铭
 * @博客       http://blog.csdn.net/u012792686
 * @创建时间   2016/10/1 15:54
 * @本类描述	  清除内容_相关工具类
 * @内容说明   1.清除手机内部缓存
 *            2.清除手机内部文件
 *            3.清除手机内部数据库
 *            4.根据数据库名,删除数据库
 *            5.清除SP
 *            6.清除SDK缓存
 *            7.清楚自定义目录下的文件(根据文件/文件路径)
 *            8.关闭IO流
 * ---------------------------------------------     
 * @更新时间   2016/10/1 
 * @更新说明
 */
public class CleanUtils {

    /**
     * 清除手机内部缓存
     * 一般是 /data/data/com.xxx.xxx/cache
     *
     * @param context 上下文
     * @return {@code true}: 清除成功<br>{@code false}: 清除失败
     */
    public static boolean cleanInternalCache(Context context) {
        return FileUtils.deleteFilesInDir(context.getCacheDir());
    }

    /**
     * 清除手机内部文件
     * <p>/data/data/com.xxx.xxx/files</p>
     *
     * @param context 上下文
     * @return {@code true}: 清除成功<br>{@code false}: 清除失败
     */
    public static boolean cleanInternalFiles(Context context) {
        return FileUtils.deleteFilesInDir(context.getFilesDir());
    }

    /**
     * 清除手机内部数据库
     * <p>/data/data/com.xxx.xxx/databases</p>
     *
     * @param context 上下文
     * @return {@code true}: 清除成功<br>{@code false}: 清除失败
     */
    public static boolean cleanInternalDbs(Context context) {
        return FileUtils.deleteFilesInDir(context.getFilesDir().getParent() + File.separator + "databases");
    }

    /**
     * 根据名称清除数据库
     * <p>/data/data/com.xxx.xxx/databases/dbName</p>
     *
     * @param context 上下文
     * @param dbName  数据库名称
     * @return {@code true}: 清除成功<br>{@code false}: 清除失败
     */
    public static boolean cleanInternalDbByName(Context context, String dbName) {
        return context.deleteDatabase(dbName);
    }

    /**
     * 清除内部SP
     * <p>/data/data/com.xxx.xxx/shared_prefs</p>
     *
     * @param context 上下文
     * @return {@code true}: 清除成功<br>{@code false}: 清除失败
     */
    public static boolean cleanInternalSP(Context context) {
        return FileUtils.deleteFilesInDir(context.getFilesDir().getParent() + File.separator + "shared_prefs");
    }

    /**
     * 清除sd卡缓存
     * <p>/storage/emulated/0/android/data/com.xxx.xxx/cache<p/>
     *
     * @param context 上下文
     * @return {@code true}: 清除成功<br>{@code false}: 清除失败
     */
    public static boolean cleanExternalCache(Context context) {
        return SDCardUtils.isSDCardEnable() && FileUtils.deleteFilesInDir(context.getExternalCacheDir());
    }

    /**
     * 清除自定义目录下的文件
     *
     * @param dirPath 目录路径
     * @return {@code true}: 清除成功<br>{@code false}: 清除失败
     */
    public static boolean cleanCustomCache(String dirPath) {
        return FileUtils.deleteFilesInDir(dirPath);
    }

    /**
     * 清除自定义目录下的文件
     *
     * @param dir 目录
     * @return {@code true}: 清除成功<br>{@code false}: 清除失败
     */
    public static boolean cleanCustomCache(File dir) {
        return FileUtils.deleteFilesInDir(dir);
    }

    /**
     * 关闭IO
     *
     * @param closeables closeable
     */
    public static void closeIO(Closeable... closeables) {
        if (closeables == null)
            return;
        try {
            for (Closeable closeable : closeables) {
                if (closeable != null) {
                    closeable.close();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 安静关闭IO
     *
     * @param closeables closeable
     */
    public static void closeIOQuietly(Closeable... closeables) {
        if (closeables == null)
            return;
        for (Closeable closeable : closeables) {
            if (closeable != null) {
                try {
                    closeable.close();
                } catch (IOException ignored) {
                }
            }
        }
    }

}
