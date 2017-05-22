package zs.xmx.utils.encrypt;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import static zs.xmx.utils.ConvertUtils.bytes2HexString;

/*
 * @创建者     默小铭
 * @博客       http://blog.csdn.net/u012792686
 * @创建时间   2016/9/6 16:52
 * @本类描述	  SHA 加密工具类
 * @内容说明   SHA安全性比MD5好一点,但是速度稍慢(SHA长度要比MD5多出8个字符(32比特))
 *      
 */
public class SHAUtils {
    /**
     * 获得16进制SHA1加密的字符串
     *
     * @param plainText 要加密的字符串
     * @return 16进制SHA1加密的字符串
     */
    public static String getHexSHA1(String plainText) {
        StringBuilder sb = new StringBuilder();
        MessageDigest sha;
        try {
            sha = MessageDigest.getInstance("SHA-1");
            sha.update(plainText.getBytes());
            for (byte b : sha.digest()) {
                // 10进制转16进制，X 表示以十六进制形式输出，02 表示不足两位前面补0输出
                sb.append(String.format("%02X", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 根据不同的编码格式(GB2312,GBK,UTF-8,ISO8859-1),返回16进制MD5加密字符串
     *
     * @param plainText  要加密的内容
     * @param Ecode_type 编码类型 (GB2312,GBK,UTF-8,ISO8859-1)
     * @return 16进制shA-1加密字符串
     */
    public static String getHexSHA1(String plainText, String Ecode_type) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            try {
                md.update(plainText.getBytes(Ecode_type));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
                return null;
            }
            byte b[] = md.digest();

            int i;
            StringBuffer buf = new StringBuffer("");
            for (int offset = 0; offset < b.length; offset++) {
                i = b[offset];
                if (i < 0)
                    i += 256;
                if (i < 16)
                    buf.append("0");
                buf.append(Integer.toHexString(i));
            }

            return buf.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();

        }
        return null;
    }

    /**
     * 获取加密,加盐的SHA1加密字符串
     *
     * @param plainText 加密内容
     * @param salt      盐 (给 加密内容 添加key)
     * @return
     */
    public static String getHexSHA1_salt(String plainText, String salt) {
        try {
            StringBuffer stingBuffer = new StringBuffer();
            // 1.指定加密算法
            MessageDigest digest = MessageDigest.getInstance("SHA-1");
            // 2.将需要加密的字符串转化成byte类型的数据，然后进行哈希过程
            byte[] bs = digest.digest((plainText + salt).getBytes());
            // 3.遍历bs,让其生成32位字符串，固定写法

            // 4.拼接字符串
            for (byte b : bs) {
                int i = b & 0xff;
                String hexString = Integer.toHexString(i);
                if (hexString.length() < 2) {
                    hexString = "0" + hexString;
                }
                stingBuffer.append(hexString);
            }
            return stingBuffer.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取文件的SHA-1校验码
     *
     * @param file 文件
     * @return 文件的SHA-1校验码
     */
    public static byte[] getFileSHACode(File file) {
        FileInputStream in = null;
        try {
            in = new FileInputStream(file);
            FileChannel channel = in.getChannel();
            MappedByteBuffer buffer = channel.map(FileChannel.MapMode.READ_ONLY, 0, file.length());
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            md.update(buffer);
            return md.digest();
        } catch (NoSuchAlgorithmException | IOException e) {
            e.printStackTrace();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException ignored) {
                }
            }
        }
        return null;
    }

    /**
     * 获取应用的SHA1 值,由此判断高德,百度地图 Key是否正确
     *
     * @param context 上下文
     * @return
     */
    public static String getAppSHA1(Context context) {
        try {
            PackageInfo info = context.getPackageManager().getPackageInfo(
                    context.getPackageName(), PackageManager.GET_SIGNATURES);
            byte[] cert = info.signatures[0].toByteArray();
            MessageDigest md = MessageDigest.getInstance("SHA1");
            byte[] publicKey = md.digest(cert);
            StringBuilder hexString = new StringBuilder();
            for (byte aPublicKey : publicKey) {
                String appendString = Integer.toHexString(0xFF & aPublicKey).toUpperCase();
                if (appendString.length() == 1)
                    hexString.append("0");
                hexString.append(appendString);
                hexString.append(":");
            }
            String result = hexString.toString();
            return result.substring(0, result.length() - 1);
        } catch (PackageManager.NameNotFoundException | NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * SHA1加密
     *
     * @param data 明文字符串
     * @return 16进制密文
     */
    public static String encryptSHA1ToString(String data) {
        return encryptSHA1ToString(data.getBytes());
    }

    /**
     * SHA1加密
     *
     * @param data 明文字节数组
     * @return 16进制密文
     */
    public static String encryptSHA1ToString(byte[] data) {
        return bytes2HexString(encryptSHA1(data));
    }

    /**
     * SHA1加密
     *
     * @param data 明文字节数组
     * @return 密文字节数组
     */
    public static byte[] encryptSHA1(byte[] data) {
        return hashTemplate(data, "SHA1");
    }

    /**
     * SHA224加密
     *
     * @param data 明文字符串
     * @return 16进制密文
     */
    public static String encryptSHA224ToString(String data) {
        return encryptSHA224ToString(data.getBytes());
    }

    /**
     * SHA224加密
     *
     * @param data 明文字节数组
     * @return 16进制密文
     */
    public static String encryptSHA224ToString(byte[] data) {
        return bytes2HexString(encryptSHA224(data));
    }

    /**
     * SHA224加密
     *
     * @param data 明文字节数组
     * @return 密文字节数组
     */
    public static byte[] encryptSHA224(byte[] data) {
        return hashTemplate(data, "SHA224");
    }

    /**
     * SHA256加密
     *
     * @param data 明文字符串
     * @return 16进制密文
     */
    public static String encryptSHA256ToString(String data) {
        return encryptSHA256ToString(data.getBytes());
    }

    /**
     * SHA256加密
     *
     * @param data 明文字节数组
     * @return 16进制密文
     */
    public static String encryptSHA256ToString(byte[] data) {
        return bytes2HexString(encryptSHA256(data));
    }

    /**
     * SHA256加密
     *
     * @param data 明文字节数组
     * @return 密文字节数组
     */
    public static byte[] encryptSHA256(byte[] data) {
        return hashTemplate(data, "SHA256");
    }

    /**
     * SHA384加密
     *
     * @param data 明文字符串
     * @return 16进制密文
     */
    public static String encryptSHA384ToString(String data) {
        return encryptSHA384ToString(data.getBytes());
    }

    /**
     * SHA384加密
     *
     * @param data 明文字节数组
     * @return 16进制密文
     */
    public static String encryptSHA384ToString(byte[] data) {
        return bytes2HexString(encryptSHA384(data));
    }

    /**
     * SHA384加密
     *
     * @param data 明文字节数组
     * @return 密文字节数组
     */
    public static byte[] encryptSHA384(byte[] data) {
        return hashTemplate(data, "SHA384");
    }

    /**
     * SHA512加密
     *
     * @param data 明文字符串
     * @return 16进制密文
     */
    public static String encryptSHA512ToString(String data) {
        return encryptSHA512ToString(data.getBytes());
    }

    /**
     * SHA512加密
     *
     * @param data 明文字节数组
     * @return 16进制密文
     */
    public static String encryptSHA512ToString(byte[] data) {
        return bytes2HexString(encryptSHA512(data));
    }

    /**
     * SHA512加密
     *
     * @param data 明文字节数组
     * @return 密文字节数组
     */
    public static byte[] encryptSHA512(byte[] data) {
        return hashTemplate(data, "SHA512");
    }

    /**
     * hash加密模板
     *
     * @param data      数据
     * @param algorithm 加密算法
     * @return 密文字节数组
     */
    private static byte[] hashTemplate(byte[] data, String algorithm) {
        if (data == null || data.length <= 0)
            return null;
        try {
            MessageDigest md = MessageDigest.getInstance(algorithm);
            md.update(data);
            return md.digest();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

}
