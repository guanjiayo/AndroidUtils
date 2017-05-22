package zs.xmx.utils;

import android.os.Handler;
import android.os.Message;

import java.lang.ref.WeakReference;

/*
 * @创建者     默小铭
 * @博客       http://blog.csdn.net/u012792686
 * @创建时间   2016/12/14 23:49
 * @本类描述	  Handler相关工具类
 * @内容说明   ${TODO} 各种消息处理
 * @补充内容
 *
 * ---------------------------------     
 * @新增内容
 *
 */
public final class HandlerUtils {

    private HandlerUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    public static class HandlerHolder extends Handler {
        WeakReference<Handler.Callback> mListenerWeakReference;

        /**
         * 使用必读：推荐在Activity或者Activity内部持有类中实现该接口，不要使用匿名类，可能会被GC
         *
         * @param listener 收到消息回调接口
         */
        public HandlerHolder(Handler.Callback listener) {
            mListenerWeakReference = new WeakReference<>(listener);
        }

        @Override
        public void handleMessage(Message msg) {
            if (mListenerWeakReference != null && mListenerWeakReference.get() != null) {
                mListenerWeakReference.get().handleMessage(msg);
            }
        }
    }


}
