package zs.xmx.utils;


import android.content.Context;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

/*
 * @创建者     默小铭
 * @博客       http://blog.csdn.net/u012792686
 * @创建时间   2016/9/15 13:39
 * @本类描述	  Log 工具类
 * @内容说明   1.按等级日志显示(自定义tag的函数,可以在项目结束后设置isDebug停止打印Log)
 *            2.能定位打印Log的位置
 *            3.Log 打印位置 处于什么线程
 *            4.格式化输出 json,xml数据
 *            5.日志写入文件
 *      
 */
//TODO 1.加一个根据某个Activity,显示所有该Activity的 Log(一个界面,而不是现在一个个独立)
public class Logger {
    public static        boolean isDebug     = true; //是否需要打印Log标记(可在application类的onCreate()中初始化)
    private static final int     DEBUG       = 3;
    private static final int     ERROR       = 6;
    private static final int     ASSERT      = 7;
    private static final int     INFO        = 4;
    private static final int     VERBOSE     = 2;
    private static final int     WARN        = 5;
    private static final int     JSON_INDENT = 12;
    private static final int     XML         = 13;

    // 下面是传入自定义tag的函数
    public static void i(String tag, String msg) {
        if (isDebug)
            LogText.i(getFinalTag(tag), msg);
    }

    public static void d(String tag, String msg) {
        if (isDebug)
            LogText.d(getFinalTag(tag), msg);
    }

    public static void e(String tag, String msg) {
        if (isDebug)
            LogText.e(getFinalTag(tag), msg);
    }

    public static void v(String tag, String msg) {
        if (isDebug)
            LogText.v(getFinalTag(tag), msg);
    }

    public static void w(String tag, String msg) {
        if (isDebug)
            LogText.w(getFinalTag(tag), msg);
    }

    public static void wtf(String tag, String msg) {
        if (isDebug)
            LogText.wtf(getFinalTag(tag), msg);
    }

    /**
     * 获得JSON格式数据
     * 这个方法默认由 log.i 输出
     *
     * @param tag
     * @param msg
     */
    public static void getJson(String tag, String msg) {
        if (isDebug)
            LogText.i(getFinalTag(tag), JsonFormat(msg));
    }

    /**
     * 获得XML格式 数据
     * 这个方法默认有 log.i输出
     *
     * @param tag
     * @param msg
     */
    public static void getXml(String tag, String msg) {
        if (isDebug) {
            LogText.i(getFinalTag(tag), XmlFormat(msg));
        }
    }

    /**
     * 将xml 数据格式化
     *
     * @param XmlStr
     * @return xml数据
     */
    public static String XmlFormat(String XmlStr) {
        try {
            Source xmlInput = new StreamSource(new StringReader(XmlStr));
            StreamResult xmlOutput = new StreamResult(new StringWriter());
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
            transformer.transform(xmlInput, xmlOutput);
            return xmlOutput.getWriter().toString().replaceFirst(">", ">\n");
        } catch (TransformerException e) {
            e.printStackTrace();
        }
        return "Invalid Xml, Please Check: " + XmlStr;
    }

    /**
     * 将JSON 数据格式化
     *
     * @param jsonStr
     * @return json 数据
     */
    private static String JsonFormat(String jsonStr) {
        try {
            jsonStr = jsonStr.trim();
            if (jsonStr.startsWith("{")) {
                JSONObject jsonObject = new JSONObject(jsonStr);
                return jsonObject.toString(JSON_INDENT);
            }
            if (jsonStr.startsWith("[")) {
                JSONArray jsonArray = new JSONArray(jsonStr);
                return jsonArray.toString(JSON_INDENT);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return "Invalid Json, Please Check: " + jsonStr;
    }

    /**
     * 判断 tag 是否为 null
     *
     * @param tag 要打印的tag
     * @return TAG
     */
    private static String getFinalTag(String tag) {
        if (!TextUtils.isEmpty(tag)) {
            return tag;
        }
        return "TAG can not be empty";
    }

    /**
     * 判断 msg 是否为 null
     *
     * @param msg 要打印的tag
     * @return msg
     */
    private static String getFinalMsg(String msg) {
        if (!TextUtils.isEmpty(msg)) {
            return msg;
        }
        return "Message can not be empty";
    }

    /**
     * 自定义Log输出的样式
     */
    private static class LogText {
        private static final String DOUBLE_DIVIDER = "════════════════════   End   ════════════════════════\n";
        private static final String SINGLE_DIVIDER = "════════════════════   Start   ════════════════════════\n";


        private String mTag;

        public LogText(String tag) {
            mTag = tag;
        }

        public static void i(String tag, String content) {
            LogText logText = new LogText(tag);
            logText.setup(getFinalMsg(content), INFO);
        }

        public static void d(String tag, String content) {
            LogText logText = new LogText(tag);
            logText.setup(getFinalMsg(content), DEBUG);
        }

        public static void v(String tag, String content) {
            LogText logText = new LogText(tag);
            logText.setup(getFinalMsg(content), VERBOSE);
        }

        public static void e(String tag, String content) {
            LogText logText = new LogText(tag);
            logText.setup(getFinalMsg(content), ERROR);
        }

        public static void w(String tag, String content) {
            LogText logText = new LogText(tag);
            logText.setup(getFinalMsg(content), WARN);
        }

        public static void wtf(String tag, String content) {
            LogText logText = new LogText(tag);
            logText.setup(getFinalMsg(content), ASSERT);
        }

        /**
         * 设置要显示的内容
         *
         * @param content 内容
         * @param type    类型
         */
        public void setup(String content, int type) {
            setUpHeader();
            setUpContent(content, type);
            setUpFooter();

        }

        private void setUpHeader() {
            Log.e(mTag, SINGLE_DIVIDER);
        }

        private void setUpFooter() {
            Log.e(mTag, DOUBLE_DIVIDER);
        }


        /**
         * 设置要显示的LOG 内容
         * 根据不同级别 打印不同LOG
         *
         * @param content
         * @param type
         */
        public void setUpContent(String content, int type) {
            StackTraceElement targetStackTraceElement = getTargetStackTraceElement();

            switch (type) {  // 判断要打印的是什么级别的log
                case INFO:
                    Log.i(mTag, "Thread:" + Thread.currentThread().getName());
                    Log.i(mTag, "(" + targetStackTraceElement.getFileName()
                            + ":"
                            + targetStackTraceElement.getLineNumber() + ")");
                    Log.i(mTag, content);
                    break;
                case DEBUG:
                    Log.d(mTag, "Thread:" + Thread.currentThread().getName());
                    Log.d(mTag, "(" + targetStackTraceElement.getFileName() + ":"
                            + targetStackTraceElement.getLineNumber() + ")");
                    Log.d(mTag, content);
                    break;
                case WARN:
                    Log.w(mTag, "Thread:" + Thread.currentThread().getName());
                    Log.w(mTag, "(" + targetStackTraceElement.getFileName()
                            + ":"
                            + targetStackTraceElement.getLineNumber() + ")");
                    Log.w(mTag, content);
                    break;
                case ASSERT:
                    Log.wtf(mTag, "Thread:" + Thread.currentThread().getName());
                    Log.wtf(mTag, "(" + targetStackTraceElement.getFileName()
                            + ":"
                            + targetStackTraceElement.getLineNumber() + ")");
                    Log.wtf(mTag, content);
                    break;
                case ERROR:
                    Log.e(mTag, "Thread:" + Thread.currentThread().getName());
                    Log.e(mTag, "(" + targetStackTraceElement.getFileName()
                            + ":"
                            + targetStackTraceElement.getLineNumber() + ")");
                    Log.e(mTag, content);
                    break;
                case VERBOSE:
                    Log.v(mTag, "Thread:" + Thread.currentThread().getName());
                    Log.v(mTag, "(" + targetStackTraceElement.getFileName()
                            + ":"
                            + targetStackTraceElement.getLineNumber() + ")");
                    Log.v(mTag, content);
                    break;
                default:
                    break;
            }

        }

        /**
         * 获得目标类线程元素方法  StackTraceElement
         *
         * @return
         */
        private StackTraceElement getTargetStackTraceElement() {
            StackTraceElement targetStackTrace = null;
            boolean shouldTrace = false;
            StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
            for (StackTraceElement stackTraceElement : stackTrace) {
                boolean isLogMethod = stackTraceElement.getClassName().equals(Logger.class.getName());
                if (shouldTrace && !isLogMethod) {
                    targetStackTrace = stackTraceElement;
                    break;
                }
                shouldTrace = isLogMethod;
            }
            return targetStackTrace;
        }
    }

    /**
     * 打开日志文件并写入日志
     *
     * @param type    日志类型
     * @param tag     标签
     * @param content 内容
     **/
    private synchronized static void log2File(Context context, final char type, final String tag, final String
            content) {
        String dir;
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            dir = context.getExternalCacheDir().getPath() + File.separator + "log" + File.separator;
        } else {
            dir = context.getCacheDir().getPath() + File.separator + "log" + File.separator;
        }
        if (content == null)
            return;
        Date now = new Date();
        String date = new SimpleDateFormat("MM-dd", Locale.getDefault()).format(now);
        final String fullPath = dir + date + ".txt";
        if (!FileUtils.createOrExistsFile(fullPath))
            return;
        String time = new SimpleDateFormat("MM-dd HH:mm:ss.SSS", Locale.getDefault()).format(now);
        final String dateLogContent = time + ":" + type + ":" + tag + ":" + content + '\n';
        new Thread(new Runnable() {
            @Override
            public void run() {
                BufferedWriter bw = null;
                try {
                    bw = new BufferedWriter(new FileWriter(fullPath, true));
                    bw.write(dateLogContent);
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    CleanUtils.closeIO();
                }
            }
        }).start();

    }
}
