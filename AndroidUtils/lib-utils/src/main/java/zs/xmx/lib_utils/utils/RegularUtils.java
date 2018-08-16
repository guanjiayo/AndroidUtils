package zs.xmx.lib_utils.utils;

import android.text.TextUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static zs.xmx.constant.RegularContants.REGEX_ACCOUT_NUMBER;
import static zs.xmx.constant.RegularContants.REGEX_CHZ;
import static zs.xmx.constant.RegularContants.REGEX_DATE;
import static zs.xmx.constant.RegularContants.REGEX_EMAIL;
import static zs.xmx.constant.RegularContants.REGEX_IDCARD15;
import static zs.xmx.constant.RegularContants.REGEX_IDCARD18;
import static zs.xmx.constant.RegularContants.REGEX_IP;
import static zs.xmx.constant.RegularContants.REGEX_PASSWORD;
import static zs.xmx.constant.RegularContants.REGEX_PASSWORD_EXACT;
import static zs.xmx.constant.RegularContants.REGEX_POSTAL_CODE;
import static zs.xmx.constant.RegularContants.REGEX_PRICE;
import static zs.xmx.constant.RegularContants.REGEX_QQ;
import static zs.xmx.constant.RegularContants.REGEX_SPECIAL_CHARACTERS;
import static zs.xmx.constant.RegularContants.REGEX_TEL;
import static zs.xmx.constant.RegularContants.REGEX_TEL_EXACT;
import static zs.xmx.constant.RegularContants.REGEX_URL;
import static zs.xmx.constant.RegularContants.REGEX_USERNAME;
import static zs.xmx.constant.RegularContants.REGEX_USERNAME1;
import static zs.xmx.constant.RegularContants.REGEX_USERNAME2;


/*
 * @创建者     默小铭
 * @博客       http://blog.csdn.net/u012792686
 * @创建时间   2016/9/2 10:36
 * @本类描述	  正则相关工具类
 * @内容说明
 *
 */
public class RegularUtils {
    /**
     * 验证手机号
     *
     * @param str 要验证的文本
     * @return {@code true}: 匹配<br>{@code false}: 不匹配
     */
    public static boolean isMobile(String str) {
        return isMatch(REGEX_SPECIAL_CHARACTERS, str);
    }

    /**
     * 验证座机号
     *
     * @param str 要验证的文本
     * @return {@code true}: 匹配<br>{@code false}: 不匹配
     */
    public static boolean isTel(String str) {
        return isMatch(REGEX_TEL, str);
    }

    /**
     * 验证座机号(带区号的)
     *
     * @param str 要验证的文本
     * @return {@code true}: 匹配<br>{@code false}: 不匹配
     */
    public static boolean isTelExact(String str) {
        return isMatch(REGEX_TEL_EXACT, str);
    }

    /**
     * 验证身份证号码(15位)
     *
     * @param str 要验证的文本
     * @return {@code true}: 匹配<br>{@code false}: 不匹配
     */
    public static boolean isIDCard15(String str) {
        return isMatch(REGEX_IDCARD15, str);
    }

    /**
     * 验证身份证号码(18位)
     *
     * @param str 要验证的文本
     * @return {@code true}: 匹配<br>{@code false}: 不匹配
     */
    public static boolean isIDCard18(String str) {
        return isMatch(REGEX_IDCARD18, str);
    }

    /**
     * 验证邮箱
     *
     * @param str 要验证的文本
     * @return {@code true}: 匹配<br>{@code false}: 不匹配
     */
    public static boolean isEmail(String str) {
        return isMatch(REGEX_EMAIL, str);
    }

    /**
     * 验证URL
     *
     * @param str 要验证的文本
     * @return {@code true}: 匹配<br>{@code false}: 不匹配
     */
    public static boolean isUrl(String str) {
        return isMatch(REGEX_URL, str);
    }

    /**
     * 验证汉字(中文)
     *
     * @param str 要验证的文本
     * @return {@code true}: 匹配<br>{@code false}: 不匹配
     */
    public static boolean isCHZ(String str) {
        return isMatch(REGEX_CHZ, str);
    }

    /**
     * 验证用户名(取值范围为a-z,A-Z,0-9,"_",汉字，不能以"_"结尾,用户名必须是6-20位)
     *
     * @param str 要验证的文本
     * @return {@code true}: 匹配<br>{@code false}: 不匹配
     */
    public static boolean isUserName(String str) {
        return isMatch(REGEX_USERNAME, str);
    }

    /**
     * 验证用户名(中文、英文、数字包括下划线)
     *
     * @param str 要验证的文本
     * @return {@code true}: 匹配<br>{@code false}: 不匹配
     */
    public static boolean isUserName1(String str) {
        return isMatch(REGEX_USERNAME1, str);
    }

    /**
     * 验证用户名(中文、英文、数字但不包括下划线等符号)
     *
     * @param str 要验证的文本
     * @return {@code true}: 匹配<br>{@code false}: 不匹配
     */
    public static boolean isUserName2(String str) {
        return isMatch(REGEX_USERNAME2, str);
    }

    /**
     * 验证日期(yyyy-MM-dd,已考虑平闰年)
     *
     * @param str 要验证的文本
     * @return {@code true}: 匹配<br>{@code false}: 不匹配
     */
    public static boolean isDate(String str) {
        return isMatch(REGEX_DATE, str);
    }

    /**
     * 验证IP地址
     *
     * @param str 要验证的文本
     * @return {@code true}: 匹配<br>{@code false}: 不匹配
     */
    public static boolean isIP(String str) {
        return isMatch(REGEX_IP, str);
    }

    /**
     * 验证金额(精确到两位小数)
     *
     * @param str 要验证的文本
     * @return {@code true}: 匹配<br>{@code false}: 不匹配
     */
    public static boolean isPrice(String str) {
        return isMatch(REGEX_PRICE, str);
    }

    /**
     * 验证QQ号码
     *
     * @param str 要验证的文本
     * @return {@code true}: 匹配<br>{@code false}: 不匹配
     */
    public static boolean isQQ(String str) {
        return isMatch(REGEX_QQ, str);
    }

    /**
     * 验证邮政编码
     *
     * @param str 要验证的文本
     * @return {@code true}: 匹配<br>{@code false}: 不匹配
     */
    public static boolean isPostalCode(String str) {
        return isMatch(REGEX_POSTAL_CODE, str);
    }

    /**
     * 验证帐号是否合法(字母开头,允许5-16字节,允许字母数字下划线)
     *
     * @param str 要验证的文本
     * @return {@code true}: 匹配<br>{@code false}: 不匹配
     */
    public static boolean isAccoutNumber(String str) {
        return isMatch(REGEX_ACCOUT_NUMBER, str);
    }

    /**
     * 验证密码(以字母开头，长度在6~18之间，只能包含字母、数字和下划线)
     *
     * @param str 要验证的文本
     * @return {@code true}: 匹配<br>{@code false}: 不匹配
     */
    public static boolean isPassword(String str) {
        return isMatch(REGEX_PASSWORD, str);
    }

    /**
     * 验证强密码(必须包含大小写字母和数字的组合，不能使用特殊字符，长度在8-10之间)
     *
     * @param str 要验证的文本
     * @return {@code true}: 匹配<br>{@code false}: 不匹配
     */
    public static boolean isPasswordExact(String str) {
        return isMatch(REGEX_PASSWORD_EXACT, str);
    }

    /**
     * 验证特殊字符(可以输入含有^%&',;=?$\"等字符)
     *
     * @param str 要验证的文本
     * @return {@code true}: 匹配<br>{@code false}: 不匹配
     */
    public static boolean isSpecialCharacters(String str) {
        return isMatch(REGEX_SPECIAL_CHARACTERS, str);
    }

    /**
     * string是否匹配regex
     * (如果在Android项目,可以把StringUtils换成TextUtils)
     *
     * @param regex  正则表达式字符串
     * @param string 要匹配的字符串
     * @return {@code true}: 匹配<br>{@code false}: 不匹配
     */
    public static boolean isMatch(String regex, String string) {
        return !TextUtils.isEmpty(string) && Pattern.matches(regex, string);
    }

    /**
     * 校验银行卡卡号
     *
     * @param cardId 卡号
     * @return 是否有效卡号
     */
    public static boolean isBankCard(String cardId) {
        char bit = getBankCardCheckCode(cardId
                .substring(0, cardId.length() - 1));
        return bit != 'N' && cardId.charAt(cardId.length() - 1) == bit;
    }

    /**
     * 从不含校验位的银行卡卡号采用 Luhm 校验算法获得校验位
     *
     * @param nonCheckCodeCardId 不含校验位的银行卡
     * @return 校验结果
     */
    public static char getBankCardCheckCode(String nonCheckCodeCardId) {
        if (nonCheckCodeCardId == null
                || nonCheckCodeCardId.trim().length() == 0
                || !nonCheckCodeCardId.matches("\\d+")) {
            // 如果传的不是数据返回N
            return 'N';
        }
        char[] chs = nonCheckCodeCardId.trim().toCharArray();
        int luhmSum = 0;
        for (int i = chs.length - 1, j = 0; i >= 0; i--, j++) {
            int k = chs[i] - '0';
            if (j % 2 == 0) {
                k *= 2;
                k = k / 10 + k % 10;
            }
            luhmSum += k;
        }
        return (luhmSum % 10 == 0) ? '0' : (char) ((10 - luhmSum % 10) + '0');
    }

    /**
     * 验证是否为汉字.
     *
     * @param toCheckStr 待验证的字符串
     * @return 是否通过验证
     */
    public static boolean isCH(String toCheckStr) {
        String patternStr = "^[\u4e00-\u9fa5]+$";
        return isMatch(toCheckStr, patternStr);
    }

    /**
     * 验证是否为正常的文本内容. 内容只能为：汉字、数字、字母、下划线、 中文标点符号
     * 英文标点符号，且不能为空.
     *
     * @param toCheckStr 待验证的字符串
     * @return 是否通过验证
     */
    public static boolean isNormalText(String toCheckStr) {
        String patternStr = "^[a-zA-Z0-9_\u4e00-\u9fa5" // 汉字、数字、字母、下划线
                // 中文标点符号（。 ； ， ： “ ”（ ） 、 ！ ？ 《 》）
                + "\u3002\uff1b\uff0c\uff1a\u201c\u201d\uff08\uff09\u3001\uff01\uff1f\u300a\u300b"
                // 英文标点符号（. ; , : ' ( ) / ! ? < >）
                + "\u002e\u003b\u002c\u003a\u0027\u0028\u0029\u002f\u0021\u003f\u003c\u003e\r\n"
                + "]+$";
        return isMatch(toCheckStr, patternStr);
    }

    /**
     * 验证是否为Url的文本内容. 内容只能为：数字、字母、英文标点符号（. : / ），且不能为空.
     *
     * @param toCheckStr 待验证的字符串
     * @return 是否通过验证
     */
    public static boolean isUrlText(String toCheckStr) {
        String patternStr = "^[a-zA-Z0-9" // 数字、字母
                // 英文标点符号（. : /）
                + "\u002e\u003a\u002f"
                + "]+$";
        return isMatch(toCheckStr, patternStr);
    }

    /**
     * 判断房间号是否符合规范：例如102,1202... 先判断3位或者4位的数字
     *
     * @param roomNumber roomNumber
     * @return boolean
     */
    public static boolean checkRoomNumber(String roomNumber) {
        String regex = "^\\d{3,4}$";
        return Pattern.matches(regex, roomNumber);
    }

    /**
     * 将身份证后六位隐藏,不显示
     *
     * @param identityID identityID
     * @return String
     */
    public static String hideIdentityID(String identityID) {
        if (identityID != null && identityID.length() > 6) {
            identityID = identityID.substring(0, identityID.length() - 6)
                    + "******";
        }
        return identityID;
    }

    /**
     * 获取正则匹配的部分
     *
     * @param regex 正则表达式
     * @param input 要匹配的字符串
     * @return 正则匹配的部分
     */
    public static List<String> getMatches(String regex, CharSequence input) {
        if (input == null)
            return null;
        List<String> matches = new ArrayList<>();
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);
        while (matcher.find()) {
            matches.add(matcher.group());
        }
        return matches;
    }

    /**
     * 获取正则匹配分组
     *
     * @param input 要分组的字符串
     * @param regex 正则表达式
     * @return 正则匹配分组
     */
    public static String[] getSplits(String input, String regex) {
        if (input == null)
            return null;
        return input.split(regex);
    }

    /**
     * 替换正则匹配的第一部分
     *
     * @param input       要替换的字符串
     * @param regex       正则表达式
     * @param replacement 代替者
     * @return 替换正则匹配的第一部分
     */
    public static String getReplaceFirst(String input, String regex, String replacement) {
        if (input == null)
            return null;
        return Pattern.compile(regex).matcher(input).replaceFirst(replacement);
    }

    /**
     * 替换所有正则匹配的部分
     *
     * @param input       要替换的字符串
     * @param regex       正则表达式
     * @param replacement 代替者
     * @return 替换所有正则匹配的部分
     */
    public static String getReplaceAll(String input, String regex, String replacement) {
        if (input == null)
            return null;
        return Pattern.compile(regex).matcher(input).replaceAll(replacement);
    }
}
