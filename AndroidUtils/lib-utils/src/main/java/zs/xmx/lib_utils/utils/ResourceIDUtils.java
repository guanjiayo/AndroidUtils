package zs.xmx.lib_utils.utils;

import android.content.Context;
import android.content.res.Resources;

/*
 * @创建者     默小铭
 * @博客       http://blog.csdn.net/u012792686
 * @创建时间   2016/10/14 3:06
 * @本类描述	  资源ID_工具类
 * @内容说明   获取资源ID
 * ---------------------------------------------     
 * @更新时间   2016/10/14 
 * @更新说明
 */
public class ResourceIDUtils {

    /**
     * 获取 Drawable  ID
     *
     * @param context 上下文
     * @param name    资源名字
     * @return Drawable  ID
     */
    public static int getDrawableId(Context context, String name) {
        Resources res = context.getResources();
        return res.getIdentifier(name, "drawable", context.getPackageName());
    }

    /**
     * 获取 Layout  ID
     *
     * @param context 上下文
     * @param name    资源名字
     * @return Layout  ID
     */
    public static int getLayoutId(Context context, String name) {
        Resources res = context.getResources();
        return res.getIdentifier(name, "layout", context.getPackageName());
    }


    /**
     * 获取 Strings  ID
     *
     * @param context 上下文
     * @param name    资源名字
     * @return Strings  ID
     */
    public static int getStringsId(Context context, String name) {
        Resources res = context.getResources();
        return res.getIdentifier(name, "string", context.getPackageName());
    }


    /**
     * 获取 Color  ID
     *
     * @param context 上下文
     * @param name    资源名字
     * @return Color  ID
     */
    public static int getColorId(Context context, String name) {
        Resources res = context.getResources();
        return res.getIdentifier(name, "color", context.getPackageName());
    }


    /**
     * 获取 Id  ID
     *
     * @param context 上下文
     * @param name    资源名字
     * @return Id  ID
     */
    public static int getIdId(Context context, String name) {
        Resources res = context.getResources();
        return res.getIdentifier(name, "id", context.getPackageName());
    }


    /**
     * 获取 Raw  ID
     *
     * @param context 上下文
     * @param name    资源名字
     * @return Raw  ID
     */
    public static int getRawId(Context context, String name) {
        Resources res = context.getResources();
        return res.getIdentifier(name, "raw", context.getPackageName());
    }


    /**
     * 获取 Anim  ID
     *
     * @param context 上下文
     * @param name    资源名字
     * @return Anim  ID
     */
    public static int getAnimId(Context context, String name) {
        Resources res = context.getResources();
        return res.getIdentifier(name, "anim", context.getPackageName());
    }


    /**
     * 获取 Style  ID
     *
     * @param context 上下文
     * @param name    资源名字
     * @return Style  ID
     */
    public static int getStyleId(Context context, String name) {
        Resources res = context.getResources();
        return res.getIdentifier(name, "style", context.getPackageName());
    }


    /**
     * 获取 Styleable  ID
     *
     * @param context 上下文
     * @param name    资源名字
     * @return Styleable  ID
     */
    public static int getStyleableId(Context context, String name) {
        Resources res = context.getResources();
        return res.getIdentifier(name, "styleable", context.getPackageName());
    }


    /**
     * 获取 Attr  ID
     *
     * @param context 上下文
     * @param name    资源名字
     * @return Attr  ID
     */
    public static int getAttrId(Context context, String name) {
        Resources res = context.getResources();
        return res.getIdentifier(name, "attr", context.getPackageName());
    }


    /**
     * 获取 Array  ID
     *
     * @param context 上下文
     * @param name    资源名字
     * @return Array  ID
     */
    public static int getArrayId(Context context, String name) {
        Resources res = context.getResources();
        return res.getIdentifier(name, "array", context.getPackageName());
    }
}
