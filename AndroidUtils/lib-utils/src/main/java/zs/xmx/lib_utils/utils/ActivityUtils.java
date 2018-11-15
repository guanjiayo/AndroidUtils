package zs.xmx.lib_utils.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.AnimRes;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.util.Pair;
import android.view.View;



/*
 * @创建者     默小铭
 * @博客       http://blog.csdn.net/u012792686
 * @创建时间   2018/11/6 1:19
 * @本类描述	  Activity工具类
 * @内容说明   1. startActivity
 *            2. startActivityForResult
 *            3. finishToActivity
 *            4. 退出程序
 *            5. 一些页面跳转的转场动画
 *
 *            todo 改成kotlin,其中intent可以写成扩展方法(参考anko库)
 *
 *
 */
public class ActivityUtils {

    private ActivityUtils() {
    }

    //todo 上下文应该外面传进来
    private static Context mContext = GlobalUtils.getApp();

    //---------------------startActivity(直接传Class) start---------------------------------

    /**
     * 启动Activity
     *
     * @param cls activity类
     */
    public static void startActivity(@NonNull final Class<?> cls) {
        startActivity(mContext, null, mContext.getPackageName(), cls.getName(), null);
    }

    /**
     * 启动Activity
     *
     * @param extras extras
     * @param cls    activity类
     */
    public static void startActivity(@NonNull final Bundle extras,
                                     @NonNull final Class<?> cls) {
        startActivity(mContext, extras, mContext.getPackageName(), cls.getName(), null);
    }

    /**
     * 启动Activity
     *
     * @param cls     activity类
     * @param options 跳转动画
     */
    public static void startActivity(@NonNull final Class<?> cls,
                                     @NonNull final Bundle options) {

        startActivity(mContext, null, mContext.getPackageName(), cls.getName(), options);

    }

    /**
     * 启动Activity
     *
     * @param extras  extras
     * @param cls     activity类
     * @param options 跳转动画
     */
    public static void startActivity(@NonNull final Bundle extras,
                                     @NonNull final Class<?> cls,
                                     @NonNull final Bundle options) {
        startActivity(mContext, extras, mContext.getPackageName(), cls.getName(), options);
    }


    /**
     * 启动Activity
     *
     * @param clz       The activity class.
     * @param enterAnim A resource ID of the animation resource to use for the
     *                  incoming activity.
     * @param exitAnim  A resource ID of the animation resource to use for the
     *                  outgoing activity.
     */
    @SuppressLint("ObsoleteSdkInt")
    public static void startActivity(@NonNull final Class<?> clz,
                                     @AnimRes final int enterAnim,
                                     @AnimRes final int exitAnim) {
        Context context = mContext;
        startActivity(context, null, context.getPackageName(), clz.getName(),
                getOptionsBundle(context, enterAnim, exitAnim));
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN && context instanceof Activity) {
            ((Activity) context).overridePendingTransition(enterAnim, exitAnim);
        }
    }


    //---------------------startActivity end---------------------------------

    //----------------------startActivity(传Activity) start----------------------------------------------

    /**
     * Start the activity.
     *
     * @param activity       The activity.
     * @param clz            The activity class.
     * @param sharedElements The names of the shared elements to transfer to the called
     *                       Activity and their associated Views.
     */
    public static void startActivity(@NonNull final Activity activity,
                                     @NonNull final Class<?> clz,
                                     final View... sharedElements) {
        startActivity(activity, null, activity.getPackageName(), clz.getName(),
                getOptionsBundle(activity, sharedElements));
    }

    /**
     * Start the activity.
     *
     * @param extras         The Bundle of extras to add to this intent.
     * @param activity       The activity.
     * @param clz            The activity class.
     * @param sharedElements The names of the shared elements to transfer to the called
     *                       Activity and their associated Views.
     */
    public static void startActivity(@NonNull final Bundle extras,
                                     @NonNull final Activity activity,
                                     @NonNull final Class<?> clz,
                                     final View... sharedElements) {
        startActivity(activity, extras, activity.getPackageName(), clz.getName(),
                getOptionsBundle(activity, sharedElements));
    }


    //----------------------startActivity(传Activity) end----------------------------------------------


    //-----------------------startActivity 真正的实现类 start-----------------------------

    private static void startActivity(final Context context,
                                      final Bundle extras,
                                      final String pkg,
                                      final String cls,
                                      final Bundle options) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        if (extras != null)
            intent.putExtras(extras);
        intent.setComponent(new ComponentName(pkg, cls));
        startActivity(intent, context, options);
    }


    @SuppressLint("ObsoleteSdkInt")
    private static void startActivity(final Intent intent,
                                      final Context context,
                                      final Bundle options) {
        if (!(context instanceof Activity)) {
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        if (options != null && Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            context.startActivity(intent, options);
        } else {
            context.startActivity(intent);
        }
    }

    //-----------------------startActivity 真正的实现类 end-----------------------------

    //-----------------------startActivityForResult  start-----------------------------

    /**
     * Start the activity.
     *
     * @param activity    The activity.
     * @param clz         The activity class.
     * @param requestCode if &gt;= 0, this code will be returned in
     *                    onActivityResult() when the activity exits.
     */
    public static void startActivityForResult(@NonNull final Activity activity,
                                              @NonNull final Class<?> clz,
                                              final int requestCode) {
        startActivityForResult(activity, null, activity.getPackageName(), clz.getName(),
                requestCode, null);
    }

    /**
     * Start the activity.
     *
     * @param activity    The activity.
     * @param clz         The activity class.
     * @param requestCode if &gt;= 0, this code will be returned in
     *                    onActivityResult() when the activity exits.
     * @param options     Additional options for how the Activity should be started.
     */
    public static void startActivityForResult(@NonNull final Activity activity,
                                              @NonNull final Class<?> clz,
                                              final int requestCode,
                                              final Bundle options) {
        startActivityForResult(activity, null, activity.getPackageName(), clz.getName(),
                requestCode, options);
    }

    /**
     * Start the activity.
     *
     * @param activity       The activity.
     * @param clz            The activity class.
     * @param requestCode    if &gt;= 0, this code will be returned in
     *                       onActivityResult() when the activity exits.
     * @param sharedElements The names of the shared elements to transfer to the called
     *                       Activity and their associated Views.
     */
    public static void startActivityForResult(@NonNull final Activity activity,
                                              @NonNull final Class<?> clz,
                                              final int requestCode,
                                              final View... sharedElements) {
        startActivityForResult(activity, null, activity.getPackageName(), clz.getName(),
                requestCode, getOptionsBundle(activity, sharedElements));
    }

    /**
     * Start the activity.
     *
     * @param activity    The activity.
     * @param clz         The activity class.
     * @param requestCode if &gt;= 0, this code will be returned in
     *                    onActivityResult() when the activity exits.
     * @param enterAnim   A resource ID of the animation resource to use for the
     *                    incoming activity.
     * @param exitAnim    A resource ID of the animation resource to use for the
     *                    outgoing activity.
     */
    @SuppressLint("ObsoleteSdkInt")
    public static void startActivityForResult(@NonNull final Activity activity,
                                              @NonNull final Class<?> clz,
                                              final int requestCode,
                                              @AnimRes final int enterAnim,
                                              @AnimRes final int exitAnim) {
        startActivityForResult(activity, null, activity.getPackageName(), clz.getName(),
                requestCode, getOptionsBundle(activity, enterAnim, exitAnim));
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
            activity.overridePendingTransition(enterAnim, exitAnim);
        }
    }

    /**
     * Start the activity.
     *
     * @param extras      The Bundle of extras to add to this intent.
     * @param activity    The activity.
     * @param clz         The activity class.
     * @param requestCode if &gt;= 0, this code will be returned in
     *                    onActivityResult() when the activity exits.
     */
    public static void startActivityForResult(@NonNull final Bundle extras,
                                              @NonNull final Activity activity,
                                              @NonNull final Class<?> clz,
                                              final int requestCode) {
        startActivityForResult(activity, extras, activity.getPackageName(), clz.getName(),
                requestCode, null);
    }

    /**
     * Start the activity.
     *
     * @param extras      The Bundle of extras to add to this intent.
     * @param activity    The activity.
     * @param clz         The activity class.
     * @param requestCode if &gt;= 0, this code will be returned in
     *                    onActivityResult() when the activity exits.
     * @param options     Additional options for how the Activity should be started.
     */
    public static void startActivityForResult(@NonNull final Bundle extras,
                                              @NonNull final Activity activity,
                                              @NonNull final Class<?> clz,
                                              final int requestCode,
                                              @NonNull final Bundle options) {
        startActivityForResult(activity, extras, activity.getPackageName(), clz.getName(),
                requestCode, options);
    }

    /**
     * Start the activity.
     *
     * @param extras         The Bundle of extras to add to this intent.
     * @param activity       The activity.
     * @param clz            The activity class.
     * @param requestCode    if &gt;= 0, this code will be returned in
     *                       onActivityResult() when the activity exits.
     * @param sharedElements The names of the shared elements to transfer to the called
     *                       Activity and their associated Views.
     */
    public static void startActivityForResult(@NonNull final Bundle extras,
                                              @NonNull final Activity activity,
                                              @NonNull final Class<?> clz,
                                              final int requestCode,
                                              final View... sharedElements) {
        startActivityForResult(activity, extras, activity.getPackageName(), clz.getName(),
                requestCode, getOptionsBundle(activity, sharedElements));
    }

    /**
     * Start the activity.
     *
     * @param extras      The Bundle of extras to add to this intent.
     * @param activity    The activity.
     * @param clz         The activity class.
     * @param requestCode if &gt;= 0, this code will be returned in
     *                    onActivityResult() when the activity exits.
     * @param enterAnim   A resource ID of the animation resource to use for the
     *                    incoming activity.
     * @param exitAnim    A resource ID of the animation resource to use for the
     *                    outgoing activity.
     */
    @SuppressLint("ObsoleteSdkInt")
    public static void startActivityForResult(@NonNull final Bundle extras,
                                              @NonNull final Activity activity,
                                              @NonNull final Class<?> clz,
                                              final int requestCode,
                                              @AnimRes final int enterAnim,
                                              @AnimRes final int exitAnim) {
        startActivityForResult(activity, extras, activity.getPackageName(), clz.getName(),
                requestCode, getOptionsBundle(activity, enterAnim, exitAnim));
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
            activity.overridePendingTransition(enterAnim, exitAnim);
        }
    }


    //-----------------------startActivityForResult   end-----------------------------

    //-----------------------startActivityForResult 真正的实现类 start-----------------------------

    private static void startActivityForResult(final Activity activity,
                                               final Bundle extras,
                                               final String pkg,
                                               final String cls,
                                               final int requestCode,
                                               final Bundle options) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        if (extras != null)
            intent.putExtras(extras);
        intent.setComponent(new ComponentName(pkg, cls));
        startActivityForResult(intent, activity, requestCode, options);
    }

    @SuppressLint("ObsoleteSdkInt")
    private static void startActivityForResult(final Intent intent,
                                               final Activity activity,
                                               final int requestCode,
                                               final Bundle options) {
        if (options != null && Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            activity.startActivityForResult(intent, requestCode, options);
        } else {
            activity.startActivityForResult(intent, requestCode);
        }
    }

    //-----------------------startActivityForResult 真正的实现类 end-----------------------------


    //---------------------------------finishToActivity(直接传class) start----------------------------------------

    /**
     * 跳转到原来的页面,同时将之间的页面finish掉
     * <p>
     * 例子A -> B -> C -> D - > A,把 BCD finish掉
     * <p>
     * intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP|Intent.FLAG_ACTIVITY_CLEAR_TOP);
     * 这种方式A会被重用,并且调用A的onNewInstant()方法
     * <p>
     * intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
     * 这种方式A会被重用,但是不调用A的onNewInstant()方法
     */
    public static void finishToActivity(@NonNull final Class<?> clz) {
        Intent intent = new Intent(mContext, clz);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent, mContext, null);
    }

    public static void finishToActivity(@NonNull final Bundle extras, @NonNull final Class<?> clz) {
        Intent intent = new Intent(mContext, clz);
        intent.putExtras(extras);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent, mContext, null);
    }

    /**
     * finishToActivity
     *
     * @param options Additional options for how the Activity should be started.
     */
    public static void finishToActivity(@NonNull final Bundle extras,
                                        @NonNull final Class<?> clz,
                                        @NonNull final Bundle options) {
        Intent intent = new Intent(mContext, clz);
        intent.putExtras(extras);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent, mContext, options);
    }

    public static void finishToActivity(@NonNull final Class<?> clz,
                                        @NonNull final Bundle options) {
        Intent intent = new Intent(mContext, clz);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent, mContext, options);
    }

    /**
     * finishToActivity
     *
     * @param enterAnim A resource ID of the animation resource to use for the
     *                  incoming activity.
     * @param exitAnim  A resource ID of the animation resource to use for the
     *                  outgoing activity.
     */
    @SuppressLint("ObsoleteSdkInt")
    public static void finishToActivity(@NonNull final Class<?> clz,
                                        @AnimRes final int enterAnim,
                                        @AnimRes final int exitAnim) {
        Intent intent = new Intent(mContext, clz);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        Context context = mContext;
        startActivity(intent, context, getOptionsBundle(context, enterAnim, exitAnim));
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN && context instanceof Activity) {
            ((Activity) context).overridePendingTransition(enterAnim, exitAnim);
        }
    }

    @SuppressLint("ObsoleteSdkInt")
    public static void finishToActivity(@NonNull final Bundle extras,
                                        @NonNull final Class<?> clz,
                                        @AnimRes final int enterAnim,
                                        @AnimRes final int exitAnim) {
        Intent intent = new Intent(mContext, clz);
        intent.putExtras(extras);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        Context context = mContext;
        startActivity(intent, context, getOptionsBundle(context, enterAnim, exitAnim));
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN && context instanceof Activity) {
            ((Activity) context).overridePendingTransition(enterAnim, exitAnim);
        }
    }


    //---------------------------------finishToActivity(直接传class) end----------------------------------------

    //---------------------------------finishToActivity(传Activity,class) start----------------------------------------

    /**
     * finishToActivity
     *
     * @param activity       The activity.
     * @param sharedElements The names of the shared elements to transfer to the called
     *                       Activity and their associated Views.
     */
    public static void finishToActivity(@NonNull final Activity activity,
                                        @NonNull final Class<?> clz,
                                        final View... sharedElements) {
        Intent intent = new Intent(mContext, clz);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent, activity, getOptionsBundle(activity, sharedElements));
    }

    public static void finishToActivity(@NonNull final Bundle extras,
                                        @NonNull final Activity activity,
                                        @NonNull final Class<?> clz,
                                        final View... sharedElements) {
        Intent intent = new Intent(mContext, clz);
        intent.putExtras(extras);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent, activity, getOptionsBundle(activity, sharedElements));
    }

    /**
     * finishToActivity
     *
     * @param activity  The activity.
     * @param enterAnim A resource ID of the animation resource to use for the
     *                  incoming activity.
     * @param exitAnim  A resource ID of the animation resource to use for the
     *                  outgoing activity.
     */
    @SuppressLint("ObsoleteSdkInt")
    public static void finishToActivity(@NonNull final Activity activity,
                                        @NonNull final Class<?> clz,
                                        @AnimRes final int enterAnim,
                                        @AnimRes final int exitAnim) {
        Intent intent = new Intent(mContext, clz);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent, activity, getOptionsBundle(activity, enterAnim, exitAnim));
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
            activity.overridePendingTransition(enterAnim, exitAnim);
        }
    }

    @SuppressLint("ObsoleteSdkInt")
    public static void finishToActivity(@NonNull final Bundle extras,
                                        @NonNull final Activity activity,
                                        @NonNull final Class<?> clz,
                                        @AnimRes final int enterAnim,
                                        @AnimRes final int exitAnim) {
        Intent intent = new Intent(mContext, clz);
        intent.putExtras(extras);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent, activity, getOptionsBundle(activity, enterAnim, exitAnim));
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
            activity.overridePendingTransition(enterAnim, exitAnim);
        }
    }

    //---------------------------------finishToActivity(传Activity,class) end----------------------------------------


    //-----------------------getOptionsBundle start-----------------------------

    private static Bundle getOptionsBundle(final Context context,
                                           final int enterAnim,
                                           final int exitAnim) {
        return ActivityOptionsCompat.makeCustomAnimation(context, enterAnim, exitAnim).toBundle();
    }

    private static Bundle getOptionsBundle(final Activity activity,
                                           final View[] sharedElements) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP)
            return null;
        if (sharedElements == null)
            return null;
        int len = sharedElements.length;
        if (len <= 0)
            return null;
        @SuppressWarnings("unchecked")
        Pair<View, String>[] pairs = new Pair[len];
        for (int i = 0; i < len; i++) {
            pairs[i] = Pair.create(sharedElements[i], sharedElements[i].getTransitionName());
        }
        return ActivityOptionsCompat.makeSceneTransitionAnimation(activity, pairs).toBundle();
    }

    //-----------------------getOptionsBundle end-----------------------------


}
