package zs.xmx.lib_utils.constant;

/*
 * @创建者     默小铭
 * @博客       http://blog.csdn.net/u012792686
 * @创建时间   2016/9/2 10:44
 * @本类描述	  正则相关_常量类
 * @内容说明   import static zs.xmx.constant.RegularContants.*; 直接使用
 *      
 */
public class RegularContants {

    /**
     * 正则:手机电话号码(国内)
     */
    public static final String REGEX_MOBILE = "^((13[0-9])|(14[5,7])|(15[0-3,5-9])|(17[0,3,5-8])|(18[0-9])|(147))" +
            "\\d{8}$";

    /**
     * 正则：电话号码(座机)
     */
    public static final String REGEX_TEL = "^0\\d{2,3}[- ]?\\d{7,8}";

    /**
     * 正则：电话号码(座机)
     * 带区号的(区号与座机号之间可不添加"-",外部与内部号码之间一定要加"-")
     */
    public static final String REGEX_TEL_EXACT = "((^0[1,2]{1}\\d{1}-?\\d{8}|(^0[3-9]{1}\\d{2}-?\\d{7,8}))(-(\\d{1," +
            "4}))?$)";
    /**
     * 正则：身份证号码15位
     */
    public static final String REGEX_IDCARD15  = "^[1-9]\\d{7}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}$";
    /**
     * 正则：身份证号码18位
     */
    public static final String REGEX_IDCARD18  = "^[1-9]\\d{5}[1-9]\\d{3}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}" +
            "([0-9Xx])$";
    /**
     * 正则：邮箱
     */
    public static final String REGEX_EMAIL     = "^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$";
    /**
     * 正则：URL
     */
    public static final String REGEX_URL       = "http(s)?://([\\w-]+\\.)+[\\w-]+(/[\\w-./?%&=]*)?";
    /**
     * 正则：汉字
     */
    public static final String REGEX_CHZ       = "^[\\u4e00-\\u9fa5]+$";
    /**
     * 正则：用户名，取值范围为a-z,A-Z,0-9,"_",汉字，不能以"_"结尾,用户名必须是6-20位
     */
    public static final String REGEX_USERNAME  = "^[\\w\\u4e00-\\u9fa5]{6,20}(?<!_)$";

    /**
     * 正则：用户名1(中文、英文、数字包括下划线)
     */
    public static final String REGEX_USERNAME1 = "^[\\u4E00-\\u9FA5A-Za-z0-9_]+$";

    /**
     * 正则：用户名2(中文、英文、数字但不包括下划线等符号)
     */
    public static final String REGEX_USERNAME2 = "^[\\u4E00-\\u9FA5A-Za-z0-9]+$";


    /**
     * 正则：yyyy-MM-dd格式的日期校验，已考虑平闰年
     */
    public static final String REGEX_DATE = "^(?:(?!0000)[0-9]{4}-(?:(?:0[1-9]|1[0-2])-(?:0[1-9]|1[0-9]|2[0-8])|" +
            "(?:0[13-9]|1[0-2])-(?:29|30)|(?:0[13578]|1[02])-31)|(?:[0-9]{2}(?:0[48]|[2468][048]|[13579][26])|" +
            "(?:0[48]|[2468][048]|[13579][26])00)-02-29)$";
    /**
     * 正则：IP地址
     */
    public static final String REGEX_IP   = "((2[0-4]\\d|25[0-5]|[01]?\\d\\d?)\\.){3}" +
            "(2[0-4]\\d|25[0-5]|[01]?\\d\\d?)";

    /**
     * 正则:检验金额(精确到2位小数)
     */
    public static final String REGEX_PRICE = "^[0-9]+(.[0-9]{2})?$";

    /**
     * 正则:QQ号码
     */
    public static final String REGEX_QQ = "[1-9][0-9]{4,}";


    /**
     * 正则:邮政编码(国内)
     */
    public static final String REGEX_POSTAL_CODE = "[1-9]\\d{5}(?!\\d)";


    /**
     * 正则:帐号是否合法(字母开头,允许5-16字节,允许字母数字下划线)
     */
    public static final String REGEX_ACCOUT_NUMBER = "^[a-zA-Z][a-zA-Z0-9_]{4,15}$";

    /**
     * 正则:密码(以字母开头，长度在6~18之间，只能包含字母、数字和下划线)
     */
    public static final String REGEX_PASSWORD = "^[a-zA-Z]\\w{5,17}$";

    /**
     * 正则:强密码(必须包含大小写字母和数字的组合，不能使用特殊字符，长度在8-10之间)
     */
    public static final String REGEX_PASSWORD_EXACT = "^(?=.\\d)(?=.[a-z])(?=.*[A-Z]).{8,10}$";

    /**
     * 正则:特殊字符(可以输入含有^%&',;=?$\"等字符)
     */
    public static final String REGEX_SPECIAL_CHARACTERS = "[^%&',;=?$\\x22]+";

}
