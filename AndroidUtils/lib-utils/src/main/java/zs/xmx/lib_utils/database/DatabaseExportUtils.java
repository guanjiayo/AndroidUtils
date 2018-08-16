package zs.xmx.lib_utils.database;

import android.content.Context;
import android.os.Environment;
import android.text.TextUtils;

import zs.xmx.utils.FileUtils;
import zs.xmx.utils.Logger;


/*
 * @创建者     默小铭
 * @博客       http://blog.csdn.net/u012792686
 * @创建时间   2016/10/19 0:10
 * @本类描述	  数据库_导出工具类
 * @内容说明   1.DEBUG 属性决定是否导出
 *             2.启动导出数据库方法
 * ---------------------------------------------     
 * @更新时间   2016/10/19 
 * @更新说明
 */
public class DatabaseExportUtils {
    private static final boolean DEBUG = true;
    private static final String  TAG   = "DatabaseExportUtils";

    /**
     * Don't let anyone instantiate this class.
     */
    private DatabaseExportUtils() {
        throw new Error("Do not need instantiate!");
    }

    /**
     * 开始导出数据 此操作比较耗时,建议在线程中进行
     *
     * @param context      上下文
     * @param targetFile   目标文件
     * @param databaseName 要拷贝的数据库文件名
     * @return 是否倒出成功
     */
    public boolean startExportDatabase(Context context, String targetFile,
                                       String databaseName) {
        if (DEBUG) {
            Logger.d(TAG, "start export ...");
        }
        if (!Environment.MEDIA_MOUNTED.equals(Environment
                .getExternalStorageState())) {
            if (DEBUG) {
                Logger.w(TAG, "cannot find SDCard");
            }
            return false;
        }
        String sourceFilePath = Environment.getDataDirectory() + "/data/"
                + context.getPackageName() + "/databases/" + databaseName;
        String destFilePath = Environment.getExternalStorageDirectory()
                + (TextUtils.isEmpty(targetFile) ? (context.getPackageName() + ".db")
                : targetFile);
        boolean isCopySuccess = FileUtils
                .copyFile(sourceFilePath, destFilePath);
        if (DEBUG) {
            if (isCopySuccess) {
                Logger.d(TAG, "copy database file success. target file : "
                        + destFilePath);
            } else {
                Logger.w(TAG, "copy database file failure");
            }
        }
        return isCopySuccess;
    }
}
