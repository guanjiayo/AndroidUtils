package zs.xmx.constant;

/*
 * @创建者     默小铭
 * @博客       http://blog.csdn.net/u012792686
 * @创建时间   2016/9/25 17:03
 * @本类描述	  内存相关_常量类
 * @内容说明  import static zs.xmx.constant.MemoryContants.*; 直接使用
 * ---------------------------------------------     
 *
 */
public class TimeContants {
    /**
     * 秒与毫秒的倍数
     */
    public static final int SEC  = 1000;
    /**
     * 分与毫秒的倍数
     */
    public static final int MIN  = 60000;
    /**
     * 时与毫秒的倍数
     */
    public static final int HOUR = 3600000;
    /**
     * 天与毫秒的倍数
     */
    public static final int DAY  = 86400000;

    public enum TimeUnit {
        MSEC,
        SEC,
        MIN,
        HOUR,
        DAY
    }
}
