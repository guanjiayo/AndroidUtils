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
public class MemoryContants {
    /**
     * Byte与Byte的倍数
     */
    public static final int BYTE = 1;
    /**
     * KB与Byte的倍数
     */
    public static final int KB   = 1024;
    /**
     * MB与Byte的倍数
     */
    public static final int MB   = 1048576;
    /**
     * GB与Byte的倍数
     */
    public static final int GB   = 1073741824;

    /**
     * 创建这样一个枚举,在调用的时候可以直接传枚举用作自定义的参:
     * <p>
     * 例:
     * public static double byte2Size(long byteNum, MemoryContants.MemoryUnit unit) {
     *     if (byteNum < 0)
     *         return -1;
     *     switch (unit) {
     *         default:
     *         case BYTE:
     *             return (double) byteNum / BYTE;
     *         case KB:
     *             return (double) byteNum / KB;
     *         case MB:
     *             return (double) byteNum / MB;
     *         case GB:
     *             return (double) byteNum / GB;
     *           }
     *    }
     */
    public enum MemoryUnit {
        BYTE,
        KB,
        MB,
        GB
    }

}
