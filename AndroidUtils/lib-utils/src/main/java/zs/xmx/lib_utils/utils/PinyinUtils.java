package zs.xmx.lib_utils.utils;

import androidx.collection.SimpleArrayMap;

/*
 * @创建者     默小铭
 * @博客       http://blog.csdn.net/u012792686
 * @创建时间   2016/12/14 21:51
 * @本类描述	  拼音相关工具类
 * @内容说明
 * @补充内容
 *
 * ---------------------------------     
 * @新增内容
 *
 */
public class PinyinUtils {
    private PinyinUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * 汉字转拼音
     *
     * @param ccs 汉字字符串(Chinese characters)
     * @return 拼音
     */
    public static String ccs2Pinyin(CharSequence ccs) {
        return ccs2Pinyin(ccs, "");
    }

    /**
     * 汉字转拼音
     *
     * @param ccs   汉字字符串(Chinese characters)
     * @param split 汉字拼音之间的分隔符
     * @return 拼音
     */
    public static String ccs2Pinyin(CharSequence ccs, CharSequence split) {
        if (ccs == null || ccs.length() == 0)
            return null;
        StringBuilder sb = new StringBuilder();
        for (int i = 0, len = ccs.length(); i < len; i++) {
            char ch = ccs.charAt(i);
            if (ch >= 0x4E00 && ch <= 0x9FA5) {
                int sp = (ch - 0x4E00) * 6;
                sb.append(pinyinTable.substring(sp, sp + 6).trim());
            } else {
                sb.append(ch);
            }
            sb.append(split);
        }
        return sb.toString();
    }

    /**
     * 获取第一个汉字首字母
     *
     * @param ccs 汉字字符串(Chinese characters)
     * @return 拼音
     */
    public static String getPinyinFirstLetter(CharSequence ccs) {
        if (ccs == null || ccs.length() == 0)
            return null;
        return ccs2Pinyin(String.valueOf(ccs.charAt(0))).substring(0, 1);
    }

    /**
     * 获取所有汉字的首字母
     *
     * @param ccs 汉字字符串(Chinese characters)
     * @return 所有汉字的首字母
     */
    public static String getPinyinFirstLetters(CharSequence ccs) {
        return getPinyinFirstLetters(ccs, "");
    }

    /**
     * 获取所有汉字的首字母
     *
     * @param ccs   汉字字符串(Chinese characters)
     * @param split 首字母之间的分隔符
     * @return 所有汉字的首字母
     */
    public static String getPinyinFirstLetters(CharSequence ccs, CharSequence split) {
        if (ccs == null || ccs.length() == 0)
            return null;
        int len = ccs.length();
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++) {
            sb.append(ccs2Pinyin(String.valueOf(ccs.charAt(i))).substring(0, 1)).append(split);
        }
        return sb.toString();
    }

    /**
     * 根据名字获取姓氏的拼音
     *
     * @param name 名字
     * @return 姓氏的拼音
     */
    public static String getSurnamePinyin(CharSequence name) {
        if (name == null || name.length() == 0)
            return null;
        if (name.length() >= 2) {
            CharSequence str = name.subSequence(0, 2);
            if (str.equals("澹台"))
                return "tantai";
            else if (str.equals("尉迟"))
                return "yuchi";
            else if (str.equals("万俟"))
                return "moqi";
            else if (str.equals("单于"))
                return "chanyu";
        }
        char ch = name.charAt(0);
        if (surnames.containsKey(ch)) {
            return surnames.get(ch);
        }
        if (ch >= 0x4E00 && ch <= 0x9FA5) {
            int sp = (ch - 0x4E00) * 6;
            return pinyinTable.substring(sp, sp + 6).trim();
        } else {
            return String.valueOf(ch);
        }
    }

    /**
     * 根据名字获取姓氏的首字母
     *
     * @param name 名字
     * @return 姓氏的首字母
     */
    public static String getSurnameFirstLetter(CharSequence name) {
        String surname = getSurnamePinyin(name);
        if (surname == null || surname.length() == 0)
            return null;
        return String.valueOf(surname.charAt(0));
    }

    // 多音字姓氏映射表
    private static final SimpleArrayMap<Character, String> surnames;

    /**
     * 获取拼音对照表，对比过pinyin4j和其他方式，这样查表设计的好处就是读取快
     * <p>当该类加载后会一直占有123KB的内存</p>
     * <p>如果你想存进文件，然后读取操作的话也是可以，但速度肯定没有这样空间换时间快，毕竟现在设备内存都很大</p>
     * <p>如需更多用法可以用pinyin4j开源库</p>
     */
    private static final String pinyinTable;

    static {
        surnames = new SimpleArrayMap<>(35);
        surnames.put('乐', "yue");
        surnames.put('乘', "sheng");
        surnames.put('乜', "nie");
        surnames.put('仇', "qiu");
        surnames.put('会', "gui");
        surnames.put('便', "pian");
        surnames.put('区', "ou");
        surnames.put('单', "shan");
        surnames.put('参', "shen");
        surnames.put('句', "gou");
        surnames.put('召', "shao");
        surnames.put('员', "yun");
        surnames.put('宓', "fu");
        surnames.put('弗', "fei");
        surnames.put('折', "she");
        surnames.put('曾', "zeng");
        surnames.put('朴', "piao");
        surnames.put('查', "zha");
        surnames.put('洗', "xian");
        surnames.put('盖', "ge");
        surnames.put('祭', "zhai");
        surnames.put('种', "chong");
        surnames.put('秘', "bi");
        surnames.put('繁', "po");
        surnames.put('缪', "miao");
        surnames.put('能', "nai");
        surnames.put('蕃', "pi");
        surnames.put('覃', "qin");
        surnames.put('解', "xie");
        surnames.put('谌', "shan");
        surnames.put('适', "kuo");
        surnames.put('都', "du");
        surnames.put('阿', "e");
        surnames.put('难', "ning");
        surnames.put('黑', "he");

        pinyinTable = new StringBuilder(125412)
                .append("yi    ding  kao   qi    shang xia   none  wan   zhang san   shang xia   ji    bu    yu    " +
                        "mian  gai   chou  chou  zhuan qie   pi    shi   shi   qiu   bing  ye    cong  dong  si    " +
                        "cheng diu   qiu   liang diu   you   liang yan   bing  sang  shu   jiu   ge    ya    qiang " +
                        "zhong ji    jie   feng  guan  chuan chan  lin   zhuo  zhu   none  wan   dan   wei   zhu   " +
                        "jing  li    ju    pie   fu    yi    yi    nai   none  jiu   jiu   tuo   me    yi    none  " +
                        "zhi   wu    zha   hu    fa    le    zhong ping  pang  qiao  hu    guai  cheng cheng yi    " +
                        "yin   none  mie   jiu   qi    ye    xi    xiang gai   diu   none  none  shu   none  shi   ji" +
                        "    nang  jia   none  shi   none  none  mai   luan  none  ru    xi    yan   fu    sha   na  " +
                        "  gan   none  none  none  none  qian  zhi   gui   gan   luan  lin   yi    jue   le    none  " +
                        "yu    zheng shi   shi   er    chu   yu    kui   yu    yun   hu    qi    wu    jing  si    " +
                        "sui   gen   gen   ya    xie   ya    qi    ya    ji    tou   wang  kang  ta    jiao  hai   yi" +
                        "    chan  heng  mu    none  xiang jing  ting  liang heng  jing  ye    qin   bo    you   xie " +
                        "  dan   lian  duo   wei   ren   ren   ji    none  wang  yi    shen  ren   le    ding  ze    " +
                        "jin   pu    chou  ba    zhang jin   jie   bing  reng  cong  fo    san   lun   none  cang  zi" +
                        "    shi   ta    zhang fu    xian  xian  cha   hong  tong  ren   qian  gan   ge    di    dai " +
                        "  ling  yi    chao  chang sa    shang yi    mu    men   ren   jia   chao  yang  qian  zhong " +
                        "pi    wan   wu    jian  jia   yao   feng  cang  ren   wang  fen   di    fang  zhong qi    " +
                        "pei   yu    diao  dun   wen   yi    xin   kang  yi    ji    ai    wu    ji    fu    fa    " +
                        "xiu   jin   bei   chen  fu    tang  zhong you   huo   hui   yu    cui   yun   san   wei   " +
                        "chuan che   ya    xian  shang chang lun   cang  xun   xin   wei   zhu   chi   xuan  nao   bo" +
                        "    gu    ni    ni    xie   ban   xu    ling  zhou  shen  qu    si    beng  si    jia   pi  " +
                        "  yi    si    ai    zheng dian  han   mai   dan   zhu   bu    qu    bi    shao  ci    wei   " +
                        "di    zhu   zuo   you   yang  ti    zhan  he    bi    tuo   she   yu    yi    fo    zuo   " +
                        "gou   ning  tong  ni    xuan  ju    yong  wa    qian  none  ka    none  pei   huai  he    " +
                        "lao   xiang ge    yang  bai   fa    ming  jia   nai   bing  ji    heng  huo   gui   quan  " +
                        "tiao  jiao  ci    yi    shi   xing  shen  tuo   kan   zhi   gai   lai   yi    chi   kua   " +
                        "guang li    yin   shi   mi    zhu   xu    you   an    lu    mou   er    lun   dong  cha   " +
                        "chi   xun   gong  zhou  yi    ru    jian  xia   jia   zai   lu:   none  jiao  zhen  ce    " +
                        "qiao  kuai  chai  ning  nong  jin   wu    hou   jiong cheng zhen  cuo   chou  qin   lu:   ju" +
                        "    shu   ting  shen  tuo   bo    nan   hao   bian  tui   yu    xi    cu    e     qiu   xu  " +
                        "  kuang ku    wu    jun   yi    fu    lang  zu    qiao  li    yong  hun   jing  xian  san   " +
                        "pai   su    fu    xi    li    mian  ping  bao   yu    si    xia   xin   xiu   yu    ti    " +
                        "che   chou  none  yan   liang li    lai   si    jian  xiu   fu    he    ju    xiao  pai   " +
                        "jian  biao  ti    fei   feng  ya    an    bei   yu    xin   bi    chi   chang zhi   bing  " +
                        "zan   yao   cui   lia   wan   lai   cang  zong  ge    guan  bei   tian  shu   shu   men   " +
                        "dao   tan   jue   chui  xing  peng  tang  hou   yi    qi    ti    gan   jing  jie   xu    " +
                        "chang jie   fang  zhi   kong  juan  zong  ju    qian  ni    lun   zhuo  wo    luo   song  " +
                        "leng  hun   dong  zi    ben   wu    ju    nai   cai   jian  zhai  ye    zhi   sha   qing  " +
                        "none  ying  cheng qian  yan   nuan  zhong chun  jia   jie   wei   yu    bing  ruo   ti    " +
                        "wei   pian  yan   feng  tang  wo    e     xie   che   sheng kan   di    zuo   cha   ting  " +
                        "bei   ye    huang yao   zhan  qiu   yan   you   jian  xu    zha   chai  fu    bi    zhi   " +
                        "zong  mian  ji    yi    xie   xun   si    duan  ce    zhen  ou    tou   tou   bei   za    " +
                        "lou   jie   wei   fen   chang kui   sou   chi   su    xia   fu    yuan  rong  li    ru    " +
                        "yun   gou   ma    bang  dian  tang  hao   jie   xi    shan  qian  jue   cang  chu   san   " +
                        "bei   xiao  yong  yao   ta    suo   wang  fa    bing  jia   dai   zai   tang  none  bin   " +
                        "chu   nuo   zan   lei   cui   yong  zao   zong  peng  song  ao    chuan yu    zhai  zu    " +
                        "shang qian")
                .append("g qiang chi   sha   han   zhang qing  yan   di    xi    lou   bei   piao  jin   lian  lu    " +
                        "man   qian  xian  qiu   ying  dong  zhuan xiang shan  qiao  jiong tui   zun   pu    xi    " +
                        "lao   chang guang liao  qi    deng  chan  wei   zhang fan   hui   chuan tie   dan   jiao  " +
                        "jiu   seng  fen   xian  jue   e     jiao  jian  tong  lin   bo    gu    xian  su    xian  " +
                        "jiang min   ye    jin   jia   qiao  pi    feng  zhou  ai    sai   yi    jun   nong  shan  yi" +
                        "    dang  jing  xuan  kuai  jian  chu   dan   jiao  sha   zai   none  bin   an    ru    tai " +
                        "  chou  chai  lan   ni    jin   qian  meng  wu    neng  qiong ni    chang lie   lei   lu:   " +
                        "kuang bao   du    biao  zan   zhi   si    you   hao   qin   chen  li    teng  wei   long  " +
                        "chu   chan  rang  shu   hui   li    luo   zan   nuo   tang  yan   lei   nang  er    wu    " +
                        "yun   zan   yuan  xiong chong zhao  xiong xian  guang dui   ke    dui   mian  tu    chang er" +
                        "    dui   er    jin   tu    si    yan   yan   shi   shi   dang  qian  dou   fen   mao   xin " +
                        "  dou   bai   jing  li    kuang ru    wang  nei   quan  liang yu    ba    gong  liu   xi    " +
                        "none  lan   gong  tian  guan  xing  bing  qi    ju    dian  zi    none  yang  jian  shou  ji" +
                        "    yi    ji    chan  jiong mao   ran   nei   yuan  mao   gang  ran   ce    jiong ce    zai " +
                        "  gua   jiong mao   zhou  mao   gou   xu    mian  mi    rong  yin   xie   kan   jun   nong  " +
                        "yi    mi    shi   guan  meng  zhong zui   yuan  ming  kou   none  fu    xie   mi    bing  " +
                        "dong  tai   gang  feng  bing  hu    chong jue   hu    kuang ye    leng  pan   fu    min   " +
                        "dong  xian  lie   xia   jian  jing  shu   mei   shang qi    gu    zhun  song  jing  liang " +
                        "qing  diao  ling  dong  gan   jian  yin   cou   ai    li    cang  ming  zhun  cui   si    " +
                        "duo   jin   lin   lin   ning  xi    du    ji    fan   fan   fan   feng  ju    chu   none  " +
                        "feng  none  none  fu    feng  ping  feng  kai   huang kai   gan   deng  ping  qu    xiong " +
                        "kuai  tu    ao    chu   ji    dang  han   han   zao   dao   diao  dao   ren   ren   " +
                        "chuangfen   qie   yi    ji    kan   qian  cun   chu   wen   ji    dan   xing  hua   wan   " +
                        "jue   li    yue   lie   liu   ze    gang  chuangfu    chu   qu    ju    shan  min   ling  " +
                        "zhong pan   bie   jie   jie   bao   li    shan  bie   chan  jing  gua   gen   dao   " +
                        "chuangkui   ku    duo   er    zhi   shua  quan  cha   ci    ke    jie   gui   ci    gui   " +
                        "kai   duo   ji    ti    jing  lou   luo   ze    yuan  cuo   xue   ke    la    qian  cha   " +
                        "chuan gua   jian  cuo   li    ti    fei   pou   chan  qi    chuangzi    gang  wan   bo    ji" +
                        "    duo   qing  yan   zhuo  jian  ji    bo    yan   ju    huo   sheng jian  duo   duan  wu  " +
                        "  gua   fu    sheng jian  ge    zha   kai   chuangjuan  chan  tuan  lu    li    fou   shan  " +
                        "piao  kou   jiao  gua   qiao  jue   hua   zha   zhuo  lian  ju    pi    liu   gui   jiao  " +
                        "gui   jian  jian  tang  huo   ji    jian  yi    jian  zhi   chan  cuan  mo    li    zhu   li" +
                        "    ya    quan  ban   gong  jia   wu    mai   lie   jing  keng  xie   zhi   dong  zhu   nu  " +
                        "  jie   qu    shao  yi    zhu   mo    li    jing  lao   lao   juan  kou   yang  wa    xiao  " +
                        "mou   kuang jie   lie   he    shi   ke    jing  hao   bo    min   chi   lang  yong  yong  " +
                        "mian  ke    xun   juan  qing  lu    bu    meng  lai   le    kai   mian  dong  xu    xu    " +
                        "kan   wu    yi    xun   weng  sheng lao   mu    lu    piao  shi   ji    qin   qiang jiao  " +
                        "quan  xiang yi    qiao  fan   juan  tong  ju    dan   xie   mai   xun   xun   lu:   li    " +
                        "che   rang  quan  bao   shao  yun   jiu   bao   gou   wu    yun   none  none  gai   gai   " +
                        "bao   cong  none  xiong peng  ju    tao   ge    pu    an    pao   fu    gong  da    jiu   " +
                        "qiong bi    hua   bei   nao   chi   fang  jiu   yi    za    jiang kang  jiang kuang hu    " +
                        "xia   qu    fan   gui   qie   cang  kuang fei   hu    yu    gui   kui   hui   dan   kui   " +
                        "lian  lian  suan  du    jiu   qu    xi    pi    qu    yi    an    yan   bian  ni    qu    " +
                        "shi   xin   qian  nian  sa    zu    sheng wu    hui   ban   shi   xi    wan   hua   xie   " +
                        "wan   bei   zu    zhuo  xie   dan   mai   nan   dan   ji    bo    shuai bu    kuang bian  bu" +
                        "    zhan  ka    lu    you   lu    xi    gua   wo    xie   jie   jie   wei   ang   qiong zhi " +
                        "  mao   yin   we")
                .append("i   shao  ji    que   luan  shi   juan  xie   xu    jin   que   wu    ji    e     qing  xi  " +
                        "  none  chang han   e     ting  li    zhe   an    li    ya    ya    yan   she   zhi   zha   " +
                        "pang  none  ke    ya    zhi   ce    pang  ti    li    she   hou   ting  zui   cuo   fei   " +
                        "yuan  ce    yuan  xiang yan   li    jue   sha   dian  chu   jiu   qin   ao    gui   yan   si" +
                        "    li    chang lan   li    yan   yan   yuan  si    si    lin   qiu   qu    qu    none  lei " +
                        "  du    xian  zhuan san   can   can   san   can   ai    dai   you   cha   ji    you   " +
                        "shuangfan   shou  guai  ba    fa    ruo   shi   shu   zhui  qu    shou  bian  xu    jia   " +
                        "pan   sou   ji    yu    sou   die   rui   cong  kou   gu    ju    ling  gua   tao   kou   " +
                        "zhi   jiao  zhao  ba    ding  ke    tai   chi   shi   you   qiu   po    ye    hao   si    " +
                        "tan   chi   le    diao  ji    none  hong  mie   yu    mang  chi   ge    xuan  yao   zi    he" +
                        "    ji    diao  cun   tong  ming  hou   li    tu    xiang zha   he    ye    lu:   a     ma  " +
                        "  ou    xue   yi    jun   chou  lin   tun   yin   fei   bi    qin   qin   jie   pou   fou   " +
                        "ba    dun   fen   e     han   ting  hang  shun  qi    hu    zhi   yin   wu    wu    chao  na" +
                        "    chuo  xi    chui  dou   wen   hou   ou    wu    gao   ya    jun   lu:   e     ge    mei " +
                        "  dai   qi    cheng wu    gao   fu    jiao  hong  chi   sheng na    tun   m     yi    dai   " +
                        "ou    li    bei   yuan  guo   none  qiang wu    e     shi   quan  pen   wen   ni    mou   " +
                        "ling  ran   you   di    zhou  shi   zhou  zhan  ling  yi    qi    ping  zi    gua   ci    " +
                        "wei   xu    he    nao   xia   pei   yi    xiao  shen  hu    ming  da    qu    ju    gan   za" +
                        "    tuo   duo   pou   pao   bie   fu    bi    he    za    he    hai   jiu   yong  fu    da  " +
                        "  zhou  wa    ka    gu    ka    zuo   bu    long  dong  ning  zha   si    xian  huo   qi    " +
                        "er    e     guang zha   xi    yi    lie   zi    mie   mi    zhi   yao   ji    zhou  ge    " +
                        "shuai zan   xiao  ke    hui   kua   huai  tao   xian  e     xuan  xiu   guo   yan   lao   yi" +
                        "    ai    pin   shen  tong  hong  xiong duo   wa    ha    zai   you   di    pai   xiang ai  " +
                        "  gen   kuang ya    da    xiao  bi    hui   none  hua   none  kuai  duo   none  ji    nong  " +
                        "mou   yo    hao   yuan  long  pou   mang  ge    e     chi   shao  li    na    zu    he    ku" +
                        "    xiao  xian  lao   bei   zhe   zha   liang ba    mi    le    sui   fou   bu    han   heng" +
                        "  geng  shuo  ge    you   yan   gu    gu    bai   han   suo   chun  yi    ai    jia   tu    " +
                        "xian  guan  li    xi    tang  zuo   miu   che   wu    zao   ya    dou   qi    di    qin   ma" +
                        "    none  gong  dou   none  lao   liang suo   zao   huan  none  gou   ji    zuo   wo    feng" +
                        "  yin   hu    qi    shou  wei   shua  chang er    li    qiang an    jie   yo    nian  yu    " +
                        "tian  lai   sha   xi    tuo   hu    ai    zhou  nou   ken   zhuo  zhuo  shang di    heng  " +
                        "lin   a     xiao  xiang tun   wu    wen   cui   jie   hu    qi    qi    tao   dan   dan   " +
                        "wan   zi    bi    cui   chuo  he    ya    qi    zhe   fei   liang xian  pi    sha   la    ze" +
                        "    qing  gua   pa    zhe   se    zhuan nie   guo   luo   yan   di    quan  tan   bo    ding" +
                        "  lang  xiao  none  tang  chi   ti    an    jiu   dan   ka    yong  wei   nan   shan  yu    " +
                        "zhe   la    jie   hou   han   die   zhou  chai  kuai  re    yu    yin   zan   yao   wo    " +
                        "mian  hu    yun   chuan hui   huan  huan  xi    he    ji    kui   zhong wei   sha   xu    " +
                        "huang du    nie   xuan  liang yu    sang  chi   qiao  yan   dan   pen   shi   li    yo    " +
                        "zha   wei   miao  ying  pen   none  kui   xi    yu    jie   lou   ku    cao   huo   ti    " +
                        "yao   he    a     xiu   qiang se    yong  su    hong  xie   ai    suo   ma    cha   hai   ke" +
                        "    da    sang  chen  ru    sou   gong  ji    pang  wu    qian  shi   ge    zi    jie   luo " +
                        "  weng  wa    si    chi   hao   suo   jia   hai   suo   qin   nie   he    none  sai   ng    " +
                        "ge    na    dia   ai    none  tong  bi    ao    ao    lian  cui   zhe   mo    sou   sou   " +
                        "tan   di    qi    jiao  chong jiao  kai   tan   san   cao   jia   none  xiao  piao  lou   ga" +
                        "    gu    xiao  hu    hui   guo   ou    xian  ze    chang xu    po    de    ma    ma    hu  " +
                        "  lei   du    ga    tang  ye    beng  ying  none  jiao  mi    xiao  hua   ")
                .append("mai   ran   zuo   peng  lao   xiao  ji    zhu   chao  kui   zui   xiao  si    hao   fu    " +
                        "liao  qiao  xi    xu    chan  dan   hei   xun   wu    zun   pan   chi   kui   can   zan   cu" +
                        "    dan   yu    tun   cheng jiao  ye    xi    qi    hao   lian  xu    deng  hui   yin   pu  " +
                        "  jue   qin   xun   nie   lu    si    yan   ying  da    zhan  o     zhou  jin   nong  hui   " +
                        "hui   qi    e     zao   yi    shi   jiao  yuan  ai    yong  xue   kuai  yu    pen   dao   ga" +
                        "    xin   dun   dang  none  sai   pi    pi    yin   zui   ning  di    han   ta    huo   ru  " +
                        "  hao   xia   yan   duo   pi    chou  ji    jin   hao   ti    chang none  none  ca    ti    " +
                        "lu    hui   bao   you   nie   yin   hu    mo    huang zhe   li    liu   none  nang  xiao  mo" +
                        "    yan   li    lu    long  mo    dan   chen  pin   pi    xiang huo   mo    xi    duo   ku  " +
                        "  yan   chan  ying  rang  dian  la    ta    xiao  jiao  chuo  huan  huo   zhuan nie   xiao  " +
                        "ca    li    chan  chai  li    yi    luo   nang  zan   su    xi    none  jian  za    zhu   " +
                        "lan   nie   nang  none  none  wei   hui   yin   qiu   si    nin   jian  hui   xin   yin   " +
                        "nan   tuan  tuan  dun   kang  yuan  jiong pian  yun   cong  hu    hui   yuan  e     guo   " +
                        "kun   cong  wei   tu    wei   lun   guo   jun   ri    ling  gu    guo   tai   guo   tu    " +
                        "you   guo   yin   hun   pu    yu    han   yuan  lun   quan  yu    qing  guo   chui  wei   " +
                        "yuan  quan  ku    pu    yuan  yuan  e     tu    tu    tu    tuan  lu:e  hui   yi    yuan  " +
                        "luan  luan  tu    ya    tu    ting  sheng yan   lu    none  ya    zai   wei   ge    yu    wu" +
                        "    gui   pi    yi    di    qian  qian  zhen  zhuo  dang  qia   none  none  kuang chang qi  " +
                        "  nie   mo    ji    jia   zhi   zhi   ban   xun   tou   qin   fen   jun   keng  dun   fang  " +
                        "fen   ben   tan   kan   huai  zuo   keng  bi    xing  di    jing  ji    kuai  di    jing  " +
                        "jian  tan   li    ba    wu    fen   zhui  po    pan   tang  kun   qu    tan   zhi   tuo   " +
                        "gan   ping  dian  wa    ni    tai   pi    jiong yang  fo    ao    liu   qiu   mu    ke    " +
                        "gou   xue   ba    chi   che   ling  zhu   fu    hu    zhi   chui  la    long  long  lu    ao" +
                        "    none  pao   none  xing  tong  ji    ke    lu    ci    chi   lei   gai   yin   hou   dui " +
                        "  zhao  fu    guang yao   duo   duo   gui   cha   yang  yin   fa    gou   yuan  die   xie   " +
                        "ken   shang shou  e     none  dian  hong  ya    kua   da    none  dang  kai   none  nao   an" +
                        "    xing  xian  huan  bang  pei   ba    yi    yin   han   xu    chui  cen   geng  ai    peng" +
                        "  fang  que   yong  jun   jia   di    mai   lang  xuan  cheng shan  jin   zhe   lie   lie   " +
                        "pu    cheng none  bu    shi   xun   guo   jiong ye    nian  di    yu    bu    wu    juan  " +
                        "sui   pi    cheng wan   ju    lun   zheng kong  zhong dong  dai   tan   an    cai   shu   " +
                        "beng  kan   zhi   duo   yi    zhi   yi    pei   ji    zhun  qi    sao   ju    ni    ku    ke" +
                        "    tang  kun   ni    jian  dui   jin   gang  yu    e     peng  gu    tu    leng  none  ya  " +
                        "  qian  none  an    chen  duo   nao   tu    cheng yin   hun   bi    lian  guo   die   zhuan " +
                        "hou   bao   bao   yu    di    mao   jie   ruan  e     geng  kan   zong  yu    huang e     " +
                        "yao   yan   bao   ji    mei   chang du    tuo   an    feng  zhong jie   zhen  heng  gang  " +
                        "chuan jian  none  lei   gang  huang leng  duan  wan   xuan  ji    ji    kuai  ying  ta    " +
                        "cheng yong  kai   su    su    shi   mi    ta    weng  cheng tu    tang  qiao  zhong li    " +
                        "peng  bang  sai   zang  dui   tian  wu    cheng xun   ge    zhen  ai    gong  yan   kan   " +
                        "tian  yuan  wen   xie   liu   none  lang  chang peng  beng  chen  lu    lu    ou    qian  " +
                        "mei   mo    zhuan shuangshu   lou   chi   man   biao  jing  ce    shu   di    zhang kan   " +
                        "yong  dian  chen  zhi   ji    guo   qiang jin   di    shang mu    cui   yan   ta    zeng  qi" +
                        "    qiang liang none  zhui  qiao  zeng  xu    shan  shan  ba    pu    kuai  dong  fan   que " +
                        "  mo    dun   dun   zun   zui   sheng duo   duo   tan   deng  mu    fen   huang tan   da    " +
                        "ye    chu   none  ao    qiang ji    qiao  ken   yi    pi    bi    dian  jiang ye    yong  " +
                        "xue   tan   lan   ju    huai  dang  rang  qian  xuan  lan   mi    he    kai   ya    dao   " +
                        "hao   ruan  none  lei   kuang lu    yan   tan   wei   huai  long  long  rui   li  ")
                .append("  lin   rang  chan  xun   yan   lei   ba    none  shi   ren   none  zhuangzhuangsheng yi    " +
                        "mai   qiao  zhu   zhuanghu    hu    kun   yi    hu    xu    kun   shou  mang  zun   shou  yi" +
                        "    zhi   gu    chu   xiang feng  bei   none  bian  sui   qun   ling  fu    zuo   xia   " +
                        "xiong none  nao   xia   kui   xi    wai   yuan  mao   su    duo   duo   ye    qing  none  " +
                        "gou   gou   qi    meng  meng  yin   huo   chen  da    ze    tian  tai   fu    guai  yao   " +
                        "yang  hang  gao   shi   ben   tai   tou   yan   bi    yi    kua   jia   duo   none  kuang " +
                        "yun   jia   ba    en    lian  huan  di    yan   pao   juan  qi    nai   feng  xie   fen   " +
                        "dian  none  kui   zou   huan  qi    kai   she   ben   yi    jiang tao   zhuangben   xi    " +
                        "huang fei   diao  sui   beng  dian  ao    she   weng  pan   ao    wu    ao    jiang lian  " +
                        "duo   yun   jiang shi   fen   huo   bei   lian  che   nu:   nu    ding  nai   qian  jian  ta" +
                        "    jiu   nan   cha   hao   xian  fan   ji    shuo  ru    fei   wang  hong  zhuangfu    ma  " +
                        "  dan   ren   fu    jing  yan   xie   wen   zhong pa    du    ji    keng  zhong yao   jin   " +
                        "yun   miao  pei   chi   yue   zhuangniu   yan   na    xin   fen   bi    yu    tuo   feng  " +
                        "yuan  fang  wu    yu    gui   du    ba    ni    zhou  zhou  zhao  da    nai   yuan  tou   " +
                        "xuan  zhi   e     mei   mo    qi    bi    shen  qie   e     he    xu    fa    zheng ni    " +
                        "ban   mu    fu    ling  zi    zi    shi   ran   shan  yang  qian  jie   gu    si    xing  " +
                        "wei   zi    ju    shan  pin   ren   yao   tong  jiang shu   ji    gai   shang kuo   juan  " +
                        "jiao  gou   lao   jian  jian  yi    nian  zhi   ji    ji    xian  heng  guang jun   kua   " +
                        "yan   ming  lie   pei   yan   you   yan   cha   xian  yin   chi   gui   quan  zi    song  " +
                        "wei   hong  wa    lou   ya    rao   jiao  luan  ping  xian  shao  li    cheng xie   mang  " +
                        "none  suo   mu    wei   ke    lai   chuo  ding  niang keng  nan   yu    na    pei   sui   " +
                        "juan  shen  zhi   han   di    zhuange     pin   tui   xian  mian  wu    yan   wu    xi    " +
                        "yan   yu    si    yu    wa    li    xian  ju    qu    chui  qi    xian  zhui  dong  chang lu" +
                        "    ai    e     e     lou   mian  cong  pou   ju    po    cai   ling  wan   biao  xiao  shu " +
                        "  qi    hui   fu    wo    rui   tan   fei   none  jie   tian  ni    quan  jing  hun   jing  " +
                        "qian  dian  xing  hu    wan   lai   bi    yin   chou  chuo  fu    jing  lun   yan   lan   " +
                        "kun   yin   ya    none  li    dian  xian  none  hua   ying  chan  shen  ting  yang  yao   wu" +
                        "    nan   chuo  jia   tou   xu    yu    wei   ti    rou   mei   dan   ruan  qin   none  wu  " +
                        "  qian  chun  mao   fu    jie   duan  xi    zhong mei   huang mian  an    ying  xuan  none  " +
                        "wei   mei   yuan  zhen  qiu   ti    xie   tuo   lian  mao   ran   si    pian  wei   wa    " +
                        "jiu   hu    ao    none  bao   xu    tou   gui   zou   yao   pi    xi    yuan  ying  rong  ru" +
                        "    chi   liu   mei   pan   ao    ma    gou   kui   qin   jia   sao   zhen  yuan  cha   yong" +
                        "  ming  ying  ji    su    niao  xian  tao   pang  lang  niao  bao   ai    pi    pin   yi    " +
                        "piao  yu    lei   xuan  man   yi    zhang kang  yong  ni    li    di    gui   yan   jin   " +
                        "zhuan chang ce    han   nen   lao   mo    zhe   hu    hu    ao    nen   qiang none  bi    gu" +
                        "    wu    qiao  tuo   zhan  mao   xian  xian  mo    liao  lian  hua   gui   deng  zhi   xu  " +
                        "  none  hua   xi    hui   rao   xi    yan   chan  jiao  mei   fan   fan   xian  yi    wei   " +
                        "chan  fan   shi   bi    shan  sui   qiang lian  huan  none  niao  dong  yi    can   ai    " +
                        "niang ning  ma    tiao  chou  jin   ci    yu    pin   none  xu    nai   yan   tai   ying  " +
                        "can   niao  none  ying  mian  none  ma    shen  xing  ni    du    liu   yuan  lan   yan   " +
                        "shuangling  jiao  niang lan   xian  ying  shuangshuai quan  mi    li    luan  yan   zhu   " +
                        "lan   zi    jie   jue   jue   kong  yun   zi    zi    cun   sun   fu    bei   zi    xiao  " +
                        "xin   meng  si    tai   bao   ji    gu    nu    xue   none  chan  hai   luan  sun   nao   " +
                        "mie   cong  jian  shu   chan  ya    zi    ni    fu    zi    li    xue   bo    ru    nai   " +
                        "nie   nie   ying  luan  mian  ning  rong  ta    gui   zhai  qiong yu    shou  an    tu    " +
                        "song  wan   rou   yao   hong  yi    jing  zhun  mi    guai  dang  hong  zong  guan  zhou  " +
                        "ding  wa")
                .append("n   yi    bao   shi   shi   chong shen  ke    xuan  shi   you   huan  yi    tiao  shi   xian" +
                        "  gong  cheng qun   gong  xiao  zai   zha   bao   hai   yan   xiao  jia   shen  chen  rong  " +
                        "huang mi    kou   kuan  bin   su    cai   zan   ji    yuan  ji    yin   mi    kou   qing  he" +
                        "    zhen  jian  fu    ning  bing  huan  mei   qin   han   yu    shi   ning  jin   ning  zhi " +
                        "  yu    bao   kuan  ning  qin   mo    cha   ju    gua   qin   hu    wu    liao  shi   ning  " +
                        "zhai  shen  wei   xie   kuan  hui   liao  jun   huan  yi    yi    bao   qin   chong bao   " +
                        "feng  cun   dui   si    xun   dao   lu:   dui   shou  po    feng  zhuan fu    she   ke    " +
                        "jiang jiang zhuan wei   zun   xun   shu   dui   dao   xiao  ji    shao  er    er    er    ga" +
                        "    jian  shu   chen  shang shang yuan  ga    chang liao  xian  xian  none  wang  wang  you " +
                        "  liao  liao  yao   mang  wang  wang  wang  ga    yao   duo   kui   zhong jiu   gan   gu    " +
                        "gan   gan   gan   gan   shi   yin   chi   kao   ni    jin   wei   niao  ju    pi    ceng  xi" +
                        "    bi    ju    jie   tian  qu    ti    jie   wu    diao  shi   shi   ping  ji    xie   chen" +
                        "  xi    ni    zhan  xi    none  man   e     lou   ping  ti    fei   shu   xie   tu    lu:   " +
                        "lu:   xi    ceng  lu:   ju    xie   ju    jue   liao  jue   shu   xi    che   tun   ni    " +
                        "shan  wa    xian  li    e     none  none  long  yi    qi    ren   wu    han   shen  yu    " +
                        "chu   sui   qi    none  yue   ban   yao   ang   ya    wu    jie   e     ji    qian  fen   " +
                        "wan   qi    cen   qian  qi    cha   jie   qu    gang  xian  ao    lan   dao   ba    zhai  " +
                        "zuo   yang  ju    gang  ke    gou   xue   bo    li    tiao  qu    yan   fu    xiu   jia   " +
                        "ling  tuo   pei   you   dai   kuang yue   qu    hu    po    min   an    tiao  ling  chi   " +
                        "none  dong  none  kui   xiu   mao   tong  xue   yi    none  he    ke    luo   e     fu    " +
                        "xun   die   lu    lang  er    gai   quan  tong  yi    mu    shi   an    wei   hu    zhi   mi" +
                        "    li    ji    tong  kui   you   none  xia   li    yao   jiao  zheng luan  jiao  e     e   " +
                        "  yu    ye    bu    qiao  qun   feng  feng  nao   li    you   xian  hong  dao   shen  cheng " +
                        "tu    geng  jun   hao   xia   yin   wu    lang  kan   lao   lai   xian  que   kong  chong " +
                        "chong ta    none  hua   ju    lai   qi    min   kun   kun   zu    gu    cui   ya    ya    " +
                        "gang  lun   lun   leng  jue   duo   cheng guo   yin   dong  han   zheng wei   yao   pi    " +
                        "yan   song  jie   beng  zu    jue   dong  zhan  gu    yin   zi    ze    huang yu    wei   " +
                        "yang  feng  qiu   dun   ti    yi    zhi   shi   zai   yao   e     zhu   kan   lu:   yan   " +
                        "mei   gan   ji    ji    huan  ting  sheng mei   qian  wu    yu    zong  lan   jie   yan   " +
                        "yan   wei   zong  cha   sui   rong  ke    qin   yu    qi    lou   tu    dui   xi    weng  " +
                        "cang  dang  rong  jie   ai    liu   wu    song  qiao  zi    wei   beng  dian  cuo   qian  " +
                        "yong  nie   cuo   ji    none  none  song  zong  jiang liao  none  chan  di    cen   ding  tu" +
                        "    lou   zhang zhan  zhan  ao    cao   qu    qiang zui   zui   dao   dao   xi    yu    bo  " +
                        "  long  xiang ceng  bo    qin   jiao  yan   lao   zhan  lin   liao  liao  jin   deng  duo   " +
                        "zun   jiao  gui   yao   qiao  yao   jue   zhan  yi    xue   nao   ye    ye    yi    e     " +
                        "xian  ji    xie   ke    sui   di    ao    zui   none  yi    rong  dao   ling  za    yu    " +
                        "yue   yin   none  jie   li    sui   long  long  dian  ying  xi    ju    chan  ying  kui   " +
                        "yan   wei   nao   quan  chao  cuan  luan  dian  dian  nie   yan   yan   yan   nao   yan   " +
                        "chuan gui   chuan zhou  huang jing  xun   chao  chao  lie   gong  zuo   qiao  ju    gong  " +
                        "none  wu    none  none  cha   qiu   qiu   ji    yi    si    ba    zhi   zhao  xiang yi    " +
                        "jin   xun   juan  none  xun   jin   fu    za    bi    shi   bu    ding  shuai fan   nie   " +
                        "shi   fen   pa    zhi   xi    hu    dan   wei   zhang tang  dai   ma    pei   pa    tie   fu" +
                        "    lian  zhi   zhou  bo    zhi   di    mo    yi    yi    ping  qia   juan  ru    shuai dai " +
                        "  zhen  shui  qiao  zhen  shi   qun   xi    bang  dai   gui   chou  ping  zhang sha   wan   " +
                        "dai   wei   chang sha   qi    ze    guo   mao   du    hou   zhen  xu    mi    wei   wo    fu" +
                        "    yi    bang  ping  none  gong  pan   huang dao   mi    jia   teng  hui   zhong sen   ")
                .append("man   mu    biao  guo   ze    mu    bang  zhang jiong chan  fu    zhi   hu    fan   chuangbi" +
                        "    bi    none  mi    qiao  dan   fen   meng  bang  chou  mie   chu   jie   xian  lan   gan " +
                        "  ping  nian  jian  bing  bing  xing  gan   yao   huan  you   you   ji    guang pi    ting  " +
                        "ze    guang zhuangmo    qing  bi    qin   dun   chuanggui   ya    bai   jie   xu    lu    wu" +
                        "    none  ku    ying  di    pao   dian  ya    miao  geng  ci    fu    tong  pang  fei   " +
                        "xiang yi    zhi   tiao  zhi   xiu   du    zuo   xiao  tu    gui   ku    pang  ting  you   bu" +
                        "    bing  cheng lai   bi    ji    an    shu   kang  yong  tuo   song  shu   qing  yu    yu  " +
                        "  miao  sou   ce    xiang fei   jiu   he    hui   liu   sha   lian  lang  sou   jian  pou   " +
                        "qing  jiu   jiu   qin   ao    kuo   lou   yin   liao  dai   lu    yi    chu   chan  tu    si" +
                        "    xin   miao  chang wu    fei   guang none  guai  bi    qiang xie   lin   lin   liao  lu  " +
                        "  none  ying  xian  ting  yong  li    ting  yin   xun   yan   ting  di    po    jian  hui   " +
                        "nai   hui   gong  nian  kai   bian  yi    qi    nong  fen   ju    yan   yi    zang  bi    yi" +
                        "    yi    er    san   shi   er    shi   shi   gong  diao  yin   hu    fu    hong  wu    tui " +
                        "  chi   qiang ba    shen  di    zhang jue   tao   fu    di    mi    xian  hu    chao  nu    " +
                        "jing  zhen  yi    mi    quan  wan   shao  ruo   xuan  jing  diao  zhang jiang qiang beng  " +
                        "dan   qiang bi    bi    she   dan   jian  gou   none  fa    bi    kou   none  bie   xiao  " +
                        "dan   kuang qiang hong  mi    kuo   wan   jue   ji    ji    gui   dang  lu    lu    tuan  " +
                        "hui   zhi   hui   hui   yi    yi    yi    yi    huo   huo   shan  xing  zhang tong  yan   " +
                        "yan   yu    chi   cai   biao  diao  bin   peng  yong  piao  zhang ying  chi   chi   zhuo  " +
                        "tuo   ji    pang  zhong yi    wang  che   bi    di    ling  fu    wang  zheng cu    wang  " +
                        "jing  dai   xi    xun   hen   yang  huai  lu:   hou   wang  cheng zhi   xu    jing  tu    " +
                        "cong  none  lai   cong  de    pai   xi    none  qi    chang zhi   cong  zhou  lai   yu    " +
                        "xie   jie   jian  chi   jia   bian  huang fu    xun   wei   pang  yao   wei   xi    zheng " +
                        "piao  chi   de    zheng zhi   bie   de    chong che   jiao  wei   jiao  hui   mei   long  " +
                        "xiang bao   qu    xin   xin   bi    yi    le    ren   dao   ding  gai   ji    ren   ren   " +
                        "chan  tan   te    te    gan   qi    dai   cun   zhi   wang  mang  xi    fan   ying  tian  " +
                        "min   min   zhong chong wu    ji    wu    xi    ye    you   wan   zong  zhong kuai  yu    " +
                        "bian  zhi   chi   cui   chen  tai   tun   qian  nian  hun   xiong niu   wang  xian  xin   " +
                        "kang  hu    kai   fen   huai  tai   song  wu    ou    chang chuangju    yi    bao   chao  " +
                        "min   pi    zuo   zen   yang  kou   ban   nu    nao   zheng pa    bu    tie   hu    hu    ju" +
                        "    da    lian  si    zhou  di    dai   yi    tu    you   fu    ji    peng  xing  yuan  ni  " +
                        "  guai  fu    xi    bi    you   qie   xuan  zong  bing  huang xu    chu   pi    xi    xi    " +
                        "tan   none  zong  dui   none  none  yi    chi   nen   xun   shi   xi    lao   heng  kuang " +
                        "mou   zhi   xie   lian  tiao  huang die   hao   kong  gui   heng  xi    xiao  shu   sai   hu" +
                        "    qiu   yang  hui   hui   chi   jia   yi    xiong guai  lin   hui   zi    xu    chi   " +
                        "xiang nu:   hen   en    ke    dong  tian  gong  quan  xi    qia   yue   peng  ken   de    " +
                        "hui   e     none  tong  yan   kai   ce    nao   yun   mang  yong  yong  juan  mang  kun   " +
                        "qiao  yue   yu    yu    jie   xi    zhe   lin   ti    han   hao   qie   ti    bu    yi    " +
                        "qian  hui   xi    bei   man   yi    heng  song  quan  cheng kui   wu    wu    you   li    " +
                        "liang huan  cong  yi    yue   li    nin   nao   e     que   xuan  qian  wu    min   cong  " +
                        "fei   bei   de    cui   chang men   li    ji    guan  guan  xing  dao   qi    kong  tian  " +
                        "lun   xi    kan   kun   ni    qing  chou  dun   guo   chan  jing  wan   yuan  jin   ji    " +
                        "lin   yu    huo   he    quan  yan   ti    ti    nie   wang  chuo  hu    hun   xi    chang " +
                        "xin   wei   hui   e     rui   zong  jian  yong  dian  ju    can   cheng de    bei   qie   " +
                        "can   dan   guan  duo   nao   yun   xiang zhui  die   huang chun  qiong re    xing  ce    " +
                        "bian  hun   zong  ti    qiao  chou  bei   xuan  wei   ge    qian  wei   yu    yu    bi    " +
                        "xuan  huan")
                .append("  min   bi    yi    mian  yong  kai   dang  yin   e     chen  mou   qia   ke    yu    ai    " +
                        "qie   yan   nuo   gan   yun   zong  sai   leng  fen   none  kui   kui   que   gong  yun   su" +
                        "    su    qi    yao   song  huang none  gu    ju    chuangta    xie   kai   zheng yong  cao " +
                        "  sun   shen  bo    kai   yuan  xie   hun   yong  yang  li    sao   tao   yin   ci    xu    " +
                        "qian  tai   huang yun   shen  ming  none  she   cong  piao  mo    mu    guo   chi   can   " +
                        "can   can   cui   min   ni    zhang tong  ao    shuangman   guan  que   zao   jiu   hui   " +
                        "kai   lian  ou    song  jin   yin   lu:   shang wei   tuan  man   qian  zhe   yong  qing  " +
                        "kang  di    zhi   lu:   juan  qi    qi    yu    ping  liao  zong  you   chuangzhi   tong  " +
                        "cheng qi    qu    peng  bei   bie   chun  jiao  zeng  chi   lian  ping  kui   hui   qiao  " +
                        "cheng yin   yin   xi    xi    dan   tan   duo   dui   dui   su    jue   ce    xiao  fan   " +
                        "fen   lao   lao   chong han   qi    xian  min   jing  liao  wu    can   jue   chou  xian  " +
                        "tan   sheng pi    yi    chu   xian  nao   dan   tan   jing  song  han   jiao  wei   huan  " +
                        "dong  qin   qin   qu    cao   ken   xie   ying  ao    mao   yi    lin   se    jun   huai  " +
                        "men   lan   ai    lin   yan   gua   xia   chi   yu    yin   dai   meng  ai    meng  dui   qi" +
                        "    mo    lan   men   chou  zhi   nuo   nuo   yan   yang  bo    zhi   xing  kuang you   fu  " +
                        "  liu   mie   cheng none  chan  meng  lan   huai  xuan  rang  chan  ji    ju    huan  she   " +
                        "yi    lian  nan   mi    tang  jue   gang  gang  zhuangge    yue   wu    jian  xu    shu   " +
                        "rong  xi    cheng wo    jie   ge    jian  qiang huo   qiang zhan  dong  qi    jia   die   " +
                        "cai   jia   ji    shi   kan   ji    kui   gai   deng  zhan  chuangge    jian  jie   yu    " +
                        "jian  yan   lu    xi    zhan  xi    xi    chuo  dai   qu    hu    hu    hu    e     shi   li" +
                        "    mao   hu    li    fang  suo   bian  dian  jiong shang yi    yi    shan  hu    fei   yan " +
                        "  shou  shou  cai   zha   qiu   le    pu    ba    da    reng  fu    none  zai   tuo   zhang " +
                        "diao  kang  yu    ku    han   shen  cha   chi   gu    kou   wu    tuo   qian  zhi   cha   " +
                        "kuo   men   sao   yang  niu   ban   che   rao   xi    qian  ban   jia   yu    fu    ao    xi" +
                        "    pi    zhi   zi    e     dun   zhao  cheng ji    yan   kuang bian  chao  ju    wen   hu  " +
                        "  yue   jue   ba    qin   zhen  zheng yun   wan   na    yi    shu   zhua  pou   tou   dou   " +
                        "kang  zhe   pou   fu    pao   ba    ao    ze    tuan  kou   lun   qiang none  hu    bao   " +
                        "bing  zhi   peng  tan   pu    pi    tai   yao   zhen  zha   yang  bao   he    ni    yi    di" +
                        "    chi   pi    za    mo    mo    chen  ya    chou  qu    min   chu   jia   fu    zha   zhu " +
                        "  dan   chai  mu    nian  la    fu    pao   ban   pai   lin   na    guai  qian  ju    tuo   " +
                        "ba    tuo   tuo   ao    ju    zhuo  pan   zhao  bai   bai   di    ni    ju    kuo   long  " +
                        "jian  qia   yong  lan   ning  bo    ze    qian  hen   kuo   shi   jie   zheng nin   gong  " +
                        "gong  quan  shuan tun   zan   kao   chi   xie   ce    hui   pin   zhuai shi   na    bo    " +
                        "chi   gua   zhi   kuo   duo   duo   zhi   qie   an    nong  zhen  ge    jiao  kua   dong  ru" +
                        "    tiao  lie   zha   lu:   die   wa    jue   none  ju    zhi   luan  ya    wo    ta    xie " +
                        "  nao   dang  jiao  zheng ji    hui   xian  none  ai    tuo   nuo   cuo   bo    geng  ti    " +
                        "zhen  cheng suo   suo   keng  mei   long  ju    peng  jian  yi    ting  shan  nuo   wan   " +
                        "xie   cha   feng  jiao  wu    jun   jiu   tong  kun   huo   tu    zhuo  pou   lu:   ba    " +
                        "han   shao  nie   juan  she   shu   ye    jue   bu    huan  bu    jun   yi    zhai  lu:   " +
                        "sou   tuo   lao   sun   bang  jian  huan  dao   none  wan   qin   peng  she   lie   min   " +
                        "men   fu    bai   ju    dao   wo    ai    juan  yue   zong  chen  chui  jie   tu    ben   na" +
                        "    nian  nuo   zu    wo    xi    xian  cheng dian  sao   lun   qing  gang  duo   shou  diao" +
                        "  pou   di    zhang gun   ji    tao   qia   qi    pai   shu   qian  ling  ye    ya    jue   " +
                        "zheng liang gua   yi    huo   shan  ding  lu:e  cai   tan   che   bing  jie   ti    kong  " +
                        "tui   yan   cuo   zou   ju    tian  qian  ken   bai   shou  jie   lu    guai  none  none  " +
                        "zhi   dan   none  chan  sao   guan  peng  yuan  nuo   jian  zheng jiu   jian  yu    ya")
                .append("n   kui   nan   hong  rou   pi    wei   sai   zou   xuan  miao  ti    nie   cha   shi   zong" +
                        "  zhen  yi    shun  heng  bian  yang  huan  yan   zan   an    xu    ya    wo    ke    chuai " +
                        "ji    ti    la    la    cheng kai   jiu   jiu   tu    jie   hui   geng  chong shuo  she   " +
                        "xie   yuan  qian  ye    cha   zha   bei   yao   none  none  lan   wen   qin   chan  ge    " +
                        "lou   zong  geng  jiao  gou   qin   yong  que   chou  chuai zhan  sun   sun   bo    chu   " +
                        "rong  bang  cuo   sao   ke    yao   dao   zhi   nu    xie   jian  sou   qiu   gao   xian  " +
                        "shuo  sang  jin   mie   e     chui  nuo   shan  ta    jie   tang  pan   ban   da    li    " +
                        "tao   hu    zhi   wa    xia   qian  wen   qiang chen  zhen  e     xie   nuo   quan  cha   " +
                        "zha   ge    wu    en    she   gong  she   shu   bai   yao   bin   sou   tan   sha   chan  " +
                        "suo   liao  chong chuangguo   bing  feng  shuai di    qi    none  zhai  lian  cheng chi   " +
                        "guan  lu    luo   lou   zong  gai   hu    zha   chuangtang  hua   cui   nai   mo    jiang " +
                        "gui   ying  zhi   ao    zhi   chi   man   shan  kou   shu   suo   tuan  zhao  mo    mo    " +
                        "zhe   chan  keng  biao  jiang yin   gou   qian  liao  ji    ying  jue   pie   pie   lao   " +
                        "dun   xian  ruan  kui   zan   yi    xian  cheng cheng sa    nao   heng  si    han   huang da" +
                        "    zun   nian  lin   zheng hui   zhuangjiao  ji    cao   dan   dan   che   bo    che   jue " +
                        "  xiao  liao  ben   fu    qiao  bo    cuo   zhuo  zhuan tuo   pu    qin   dun   nian  none  " +
                        "xie   lu    jiao  cuan  ta    han   qiao  zhua  jian  gan   yong  lei   kuo   lu    shan  " +
                        "zhuo  ze    pu    chuo  ji    dang  se    cao   qing  jing  huan  jie   qin   kuai  dan   " +
                        "xie   ge    pi    bo    ao    ju    ye    none  none  sou   mi    ji    tai   zhuo  dao   " +
                        "xing  lan   ca    ju    ye    ru    ye    ye    ni    huo   ji    bin   ning  ge    zhi   " +
                        "jie   kuo   mo    jian  xie   lie   tan   bai   sou   lu    lu:e  rao   zhi   pan   yang  " +
                        "lei   sa    shu   zan   nian  xian  jun   huo   lu:e  la    han   ying  lu    long  qian  " +
                        "qian  zan   qian  lan   san   ying  mei   rang  chan  none  cuan  xie   she   luo   jun   mi" +
                        "    li    zan   luan  tan   zuan  li    dian  wa    dang  jiao  jue   lan   li    nang  zhi " +
                        "  gui   gui   qi    xin   po    po    shou  kao   you   gai   gai   gong  gan   ban   fang  " +
                        "zheng bo    dian  kou   min   wu    gu    ge    ce    xiao  mi    chu   ge    di    xu    " +
                        "jiao  min   chen  jiu   shen  duo   yu    chi   ao    bai   xu    jiao  duo   lian  nie   bi" +
                        "    chang dian  duo   yi    gan   san   ke    yan   dun   qi    dou   xiao  duo   jiao  jing" +
                        "  yang  xia   hun   shu   ai    qiao  ai    zheng di    zhen  fu    shu   liao  qu    xiong " +
                        "xi    jiao  none  qiao  zhuo  yi    lian  bi    li    xue   xiao  wen   xue   qi    qi    " +
                        "zhai  bin   jue   zhai  lang  fei   ban   ban   lan   yu    lan   wei   dou   sheng liao  " +
                        "jia   hu    xie   jia   yu    zhen  jiao  wo    tiao  dou   jin   chi   yin   fu    qiang " +
                        "zhan  qu    zhuo  zhan  duan  zhuo  si    xin   zhuo  zhuo  qin   lin   zhuo  chu   duan  " +
                        "zhu   fang  xie   hang  wu    shi   pei   you   none  pang  qi    zhan  mao   lu:   pei   pi" +
                        "    liu   fu    fang  xuan  jing  jing  ni    zu    zhao  yi    liu   shao  jian  none  yi  " +
                        "  qi    zhi   fan   piao  fan   zhan  guai  sui   yu    wu    ji    ji    ji    huo   ri    " +
                        "dan   jiu   zhi   zao   xie   tiao  xun   xu    ga    la    gan   han   tai   di    xu    " +
                        "chan  shi   kuang yang  shi   wang  min   min   tun   chun  wu    yun   bei   ang   ze    " +
                        "ban   jie   kun   sheng hu    fang  hao   gui   chang xuan  ming  hun   fen   qin   hu    yi" +
                        "    xi    xin   yan   ze    fang  tan   shen  ju    yang  zan   bing  xing  ying  xuan  pei " +
                        "  zhen  ling  chun  hao   mei   zuo   mo    bian  xu    hun   zhao  zong  shi   shi   yu    " +
                        "fei   die   mao   ni    chang wen   dong  ai    bing  ang   zhou  long  xian  kuang tiao  " +
                        "chao  shi   huang huang xuan  kui   xu    jiao  jin   zhi   jin   shang tong  hong  yan   " +
                        "gai   xiang shai  xiao  ye    yun   hui   han   han   jun   wan   xian  kun   zhou  xi    " +
                        "sheng sheng bu    zhe   zhe   wu    han   hui   hao   chen  wan   tian  zhuo  zui   zhou  pu" +
                        "    jing  xi    shan  yi    xi    qing  qi    jing  gui   zhen  yi    zhi   an    wan   lin " +
                        "  ")
                .append("liang chang wang  xiao  zan   none  xuan  geng  yi    xia   yun   hui   fu    min   kui   he" +
                        "    ying  du    wei   shu   qing  mao   nan   jian  nuan  an    yang  chun  yao   suo   pu  " +
                        "  ming  jiao  kai   gao   weng  chang qi    hao   yan   li    ai    ji    gui   men   zan   " +
                        "xie   hao   mu    mo    cong  ni    zhang hui   bao   han   xuan  chuan liao  xian  dan   " +
                        "jing  pie   lin   tun   xi    yi    ji    kuang dai   ye    ye    li    tan   tong  xiao  " +
                        "fei   qin   zhao  hao   yi    xiang xing  sen   jiao  bao   jing  none  ai    ye    ru    " +
                        "shu   meng  xun   yao   pu    li    chen  kuang die   none  yan   huo   lu    xi    rong  " +
                        "long  nang  luo   luan  shai  tang  yan   chu   yue   yue   qu    ye    geng  zhuai hu    he" +
                        "    shu   cao   cao   sheng man   ceng  ceng  ti    zui   can   xu    hui   yin   qie   fen " +
                        "  pi    yue   you   ruan  peng  ban   fu    ling  fei   qu    none  nu:   tiao  shuo  zhen  " +
                        "lang  lang  juan  ming  huang wang  tun   chao  ji    qi    ying  zong  wang  tong  lang  " +
                        "none  meng  long  mu    deng  wei   mo    ben   zha   zhu   shu   none  zhu   ren   ba    po" +
                        "    duo   duo   dao   li    qiu   ji    jiu   bi    xiu   ting  ci    sha   none  za    quan" +
                        "  qian  yu    gan   wu    cha   shan  xun   fan   wu    zi    li    xing  cai   cun   ren   " +
                        "shao  zhe   di    zhang mang  chi   yi    gu    gong  du    yi    qi    shu   gang  tiao  " +
                        "none  none  none  lai   shan  mang  yang  ma    miao  si    yuan  hang  fei   bei   jie   " +
                        "dong  gao   yao   xian  chu   chun  pa    shu   hua   xin   chou  zhu   chou  song  ban   " +
                        "song  ji    yue   yun   gou   ji    mao   pi    bi    wang  ang   fang  fen   yi    fu    " +
                        "nan   xi    hu    ya    dou   xun   zhen  yao   lin   rui   e     mei   zhao  guo   zhi   " +
                        "zong  yun   none  dou   shu   zao   none  li    lu    jian  cheng song  qiang feng  nan   " +
                        "xiao  xian  ku    ping  tai   xi    zhi   guai  xiao  jia   jia   gou   bao   mo    yi    ye" +
                        "    sang  shi   nie   bi    tuo   yi    ling  bing  ni    la    he    ban   fan   zhong dai " +
                        "  ci    yang  fu    bo    mou   gan   qi    ran   rou   mao   zhao  song  zhe   xia   you   " +
                        "shen  ju    tuo   zuo   nan   ning  yong  di    zhi   zha   cha   dan   gu    none  jiu   ao" +
                        "    fu    jian  bo    duo   ke    nai   zhu   bi    liu   chai  zha   si    zhu   pei   shi " +
                        "  guai  cha   yao   cheng jiu   shi   zhi   liu   mei   none  rong  zha   none  biao  zhan  " +
                        "zhi   long  dong  lu    none  li    lan   yong  shu   xun   shuan qi    zhen  qi    li    " +
                        "chi   xiang zhen  li    su    gua   kan   bing  ren   xiao  bo    ren   bing  zi    chou  yi" +
                        "    ci    xu    zhu   jian  zui   er    er    yu    fa    gong  kao   lao   zhan  li    none" +
                        "  yang  he    gen   zhi   chi   ge    zai   luan  fa    jie   heng  gui   tao   guang wei   " +
                        "kuang ru    an    an    juan  yi    zhuo  ku    zhi   qiong tong  sang  sang  huan  jie   " +
                        "jiu   xue   duo   zhui  yu    zan   none  ying  none  none  zhan  ya    rao   zhen  dang  qi" +
                        "    qiao  hua   gui   jiang zhuangxun   suo   suo   zhen  bei   ting  kuo   jing  bo    ben " +
                        "  fu    rui   tong  jue   xi    lang  liu   feng  qi    wen   jun   gan   cu    liang qiu   " +
                        "ting  you   mei   bang  long  peng  zhuangdi    xuan  tu    zao   ao    gu    bi    di    " +
                        "han   zi    zhi   ren   bei   geng  jian  huan  wan   nuo   jia   tiao  ji    xiao  lu:   " +
                        "kuan  shao  cen   fen   song  meng  wu    li    li    dou   cen   ying  suo   ju    ti    " +
                        "xie   kun   zhuo  shu   chan  fan   wei   jing  li    bing  none  none  tao   zhi   lai   " +
                        "lian  jian  zhuo  ling  li    qi    bing  lun   cong  qian  mian  qi    qi    cai   gun   " +
                        "chan  de    fei   pai   bang  pou   hun   zong  cheng zao   ji    li    peng  yu    yu    gu" +
                        "    hun   dong  tang  gang  wang  di    xi    fan   cheng zhan  qi    yuan  yan   yu    quan" +
                        "  yi    sen   ren   chui  leng  qi    zhuo  fu    ke    lai   zou   zou   zhao  guan  fen   " +
                        "fen   chen  qiong nie   wan   guo   lu    hao   jie   yi    chou  ju    ju    cheng zuo   " +
                        "liang qiang zhi   zhui  ya    ju    bei   jiao  zhuo  zi    bin   peng  ding  chu   shan  " +
                        "none  none  jian  gui   xi    du    qian  none  kui   none  luo   zhi   none  none  none  " +
                        "none  peng  shan  none  tuo   sen   duo   ye    fu    wei   wei   duan  jia   zong")
                .append("  jian  yi    shen  xi    yan   yan   chuan zhan  chun  yu    he    zha   wo    bian  bi    " +
                        "yao   huo   xu    ruo   yang  la    yan   ben   hun   kui   jie   kui   si    feng  xie   " +
                        "tuo   ji    jian  mu    mao   chu   hu    hu    lian  leng  ting  nan   yu    you   mei   " +
                        "song  xuan  xuan  ying  zhen  pian  die   ji    jie   ye    chu   shun  yu    cou   wei   " +
                        "mei   di    ji    jie   kai   qiu   ying  rou   heng  lou   le    none  gui   pin   none  " +
                        "gai   tan   lan   yun   yu    chen  lu:   ju    none  none  none  xie   jia   yi    zhan  fu" +
                        "    nuo   mi    lang  rong  gu    jian  ju    ta    yao   zhen  bang  sha   yuan  zi    ming" +
                        "  su    jia   yao   jie   huang gan   fei   zha   qian  ma    sun   yuan  xie   rong  shi   " +
                        "zhi   cui   yun   ting  liu   rong  tang  que   zhai  si    sheng ta    ke    xi    gu    qi" +
                        "    kao   gao   sun   pan   tao   ge    xun   dian  nou   ji    shuo  gou   chui  qiang cha " +
                        "  qian  huai  mei   xu    gang  gao   zhuo  tuo   qiao  yang  dian  jia   jian  zui   none  " +
                        "long  bin   zhu   none  xi    qi    lian  hui   yong  qian  guo   gai   gai   tuan  hua   qi" +
                        "    sen   cui   beng  you   hu    jiang hu    huan  kui   yi    yi    gao   kang  gui   gui " +
                        "  cao   man   jin   di    zhuangle    lang  chen  cong  li    xiu   qing  shuangfan   tong  " +
                        "guan  ji    suo   lei   lu    liang mi    lou   chao  su    ke    chu   tang  biao  lu    " +
                        "jiu   shu   zha   shu   zhang men   mo    niao  yang  tiao  peng  zhu   sha   xi    quan  " +
                        "heng  jian  cong  none  none  qiang none  ying  er    xin   zhi   qiao  zui   cong  pu    " +
                        "shu   hua   kui   zhen  zun   yue   zhan  xi    xun   dian  fa    gan   mo    wu    qiao  " +
                        "rao   lin   liu   qiao  xian  run   fan   zhan  tuo   lao   yun   shun  tui   cheng tang  " +
                        "meng  ju    cheng su    jue   jue   tan   hui   ji    nuo   xiang tuo   ning  rui   zhu   " +
                        "tong  zeng  fen   qiong ran   heng  cen   gu    liu   lao   gao   chu   none  none  none  " +
                        "none  ji    dou   none  lu    none  none  yuan  ta    shu   jiang tan   lin   nong  yin   xi" +
                        "    sui   shan  zui   xuan  cheng gan   ju    zui   yi    qin   pu    yan   lei   feng  hui " +
                        "  dang  ji    sui   bo    bi    ding  chu   zhua  gui   ji    jia   jia   qing  zhe   jian  " +
                        "qiang dao   yi    biao  song  she   lin   li    cha   meng  yin   tao   tai   mian  qi    " +
                        "none  bin   huo   ji    qian  mi    ning  yi    gao   jian  yin   er    qing  yan   qi    mi" +
                        "    zhao  gui   chun  ji    kui   po    deng  chu   none  mian  you   zhi   guang qian  lei " +
                        "  lei   sa    lu    none  cuan  lu:   mie   hui   ou    lu:   zhi   gao   du    yuan  li    " +
                        "fei   zhu   sou   lian  none  chu   none  zhu   lu    yan   li    zhu   chen  jie   e     su" +
                        "    huai  nie   yu    long  lai   none  xian  none  ju    xiao  ling  ying  jian  yin   you " +
                        "  ying  xiang nong  bo    chan  lan   ju    shuangshe   wei   cong  quan  qu    none  none  " +
                        "yu    luo   li    zan   luan  dang  jue   none  lan   lan   zhu   lei   li    ba    nang  yu" +
                        "    ling  none  qian  ci    huan  xin   yu    yu    qian  ou    xu    chao  chu   qi    kai " +
                        "  yi    jue   xi    xu    xia   yu    kuai  lang  kuan  shuo  xi    e     yi    qi    hu    " +
                        "chi   qin   kuan  kan   kuan  kan   chuan sha   none  yin   xin   xie   yu    qian  xiao  yi" +
                        "    ge    wu    tan   jin   ou    hu    ti    huan  xu    pen   xi    xiao  hu    she   none" +
                        "  lian  chu   yi    kan   yu    chuo  huan  zhi   zheng ci    bu    wu    qi    bu    bu    " +
                        "wai   ju    qian  chi   se    chi   se    zhong sui   sui   li    cuo   yu    li    gui   " +
                        "dai   dai   si    jian  zhe   mo    mo    yao   mo    cu    yang  tian  sheng dai   shang xu" +
                        "    xun   shu   can   jue   piao  qia   qiu   su    qing  yun   lian  yi    fou   zhi   ye  " +
                        "  can   hun   dan   ji    ye    none  yun   wen   chou  bin   ti    jin   shang yin   diao  " +
                        "cu    hui   cuan  yi    dan   du    jiang lian  bin   du    jian  jian  shu   ou    duan  " +
                        "zhu   yin   qing  yi    sha   ke    ke    yao   xun   dian  hui   hui   gu    que   ji    yi" +
                        "    ou    hui   duan  yi    xiao  wu    guan  mu    mei   mei   ai    zuo   du    yu    bi  " +
                        "  bi    bi    pi    pi    bi    chan  mao   none  none  pi    none  jia   zhan  sai   mu    " +
                        "tuo   xun   er    rong  xian  ju    mu    hao   qiu   dou   none  ta")
                .append("n   pei   ju    duo   cui   bi    san   none  mao   sui   shu   yu    tuo   he    jian  ta  " +
                        "  san   lu:   mu    li    tong  rong  chang pu    lu    zhan  sao   zhan  meng  lu    qu    " +
                        "die   shi   di    min   jue   mang  qi    pie   nai   qi    dao   xian  chuan fen   ri    " +
                        "nei   none  fu    shen  dong  qing  qi    yin   xi    hai   yang  an    ya    ke    qing  ya" +
                        "    dong  dan   lu:   qing  yang  yun   yun   shui  shui  zheng bing  yong  dang  shui  le  " +
                        "  ni    tun   fan   gui   ting  zhi   qiu   bin   ze    mian  cuan  hui   diao  han   cha   " +
                        "zhuo  chuan wan   fan   dai   xi    tuo   mang  qiu   qi    shan  pai   han   qian  wu    wu" +
                        "    xun   si    ru    gong  jiang chi   wu    none  none  tang  zhi   chi   qian  mi    gu  " +
                        "  wang  qing  jing  rui   jun   hong  tai   quan  ji    bian  bian  gan   wen   zhong fang  " +
                        "xiong jue   hu    none  qi    fen   xu    xu    qin   yi    wo    yun   yuan  hang  yan   " +
                        "shen  chen  dan   you   dun   hu    huo   qi    mu    rou   mei   ta    mian  wu    chong " +
                        "tian  bi    sha   zhi   pei   pan   zhui  za    gou   liu   mei   ze    feng  ou    li    " +
                        "lun   cang  feng  wei   hu    mo    mei   shu   ju    zan   tuo   tuo   duo   he    li    mi" +
                        "    yi    fu    fei   you   tian  zhi   zhao  gu    zhan  yan   si    kuang jiong ju    xie " +
                        "  qiu   yi    jia   zhong quan  bo    hui   mi    ben   zhuo  chu   le    you   gu    hong  " +
                        "gan   fa    mao   si    hu    ping  ci    fan   zhi   su    ning  cheng ling  pao   bo    qi" +
                        "    si    ni    ju    yue   zhu   sheng lei   xuan  xue   fu    pan   min   tai   yang  ji  " +
                        "  yong  guan  beng  xue   long  lu    dan   luo   xie   po    ze    jing  yin   zhou  jie   " +
                        "yi    hui   hui   zui   cheng yin   wei   hou   jian  yang  lie   si    ji    er    xing  fu" +
                        "    sa    zi    zhi   yin   wu    xi    kao   zhu   jiang luo   none  an    dong  yi    mou " +
                        "  lei   yi    mi    quan  jin   po    wei   xiao  xie   hong  xu    su    kuang tao   qie   " +
                        "ju    er    zhou  ru    ping  xun   xiong zhi   guang huan  ming  huo   wa    qia   pai   wu" +
                        "    qu    liu   yi    jia   jing  qian  jiang jiao  zhen  shi   zhuo  ce    none  hui   ji  " +
                        "  liu   chan  hun   hu    nong  xun   jin   lie   qiu   wei   zhe   jun   han   bang  mang  " +
                        "zhuo  you   xi    bo    dou   huan  hong  yi    pu    ying  lan   hao   lang  han   li    " +
                        "geng  fu    wu    li    chun  feng  yi    yu    tong  lao   hai   jin   jia   chong weng  " +
                        "mei   sui   cheng pei   xian  shen  tu    kun   pin   nie   han   jing  xiao  she   nian  tu" +
                        "    yong  xiao  xian  ting  e     su    tun   juan  cen   ti    li    shui  si    lei   shui" +
                        "  tao   du    lao   lai   lian  wei   wo    yun   huan  di    none  run   jian  zhang se    " +
                        "fu    guan  xing  shou  shuan ya    chuo  zhang ye    kong  wan   han   tuo   dong  he    wo" +
                        "    ju    gan   liang hun   ta    zhuo  dian  qie   de    juan  zi    xi    xiao  qi    gu  " +
                        "  guo   han   lin   tang  zhou  peng  hao   chang shu   qi    fang  chi   lu    nao   ju    " +
                        "tao   cong  lei   zhi   peng  fei   song  tian  pi    dan   yu    ni    yu    lu    gan   mi" +
                        "    jing  ling  lun   yin   cui   qu    huai  yu    nian  shen  piao  chun  hu    yuan  lai " +
                        "  hun   qing  yan   qian  tian  miao  zhi   yin   mi    ben   yuan  wen   re    fei   qing  " +
                        "yuan  ke    ji    she   yuan  se    lu    zi    du    none  jian  mian  pi    xi    yu    " +
                        "yuan  shen  shen  rou   huan  zhu   jian  nuan  yu    qiu   ting  qu    du    feng  zha   bo" +
                        "    wo    wo    di    wei   wen   ru    xie   ce    wei   ge    gang  yan   hong  xuan  mi  " +
                        "  ke    mao   ying  yan   you   hong  miao  xing  mei   zai   hun   nai   kui   shi   e     " +
                        "pai   mei   lian  qi    qi    mei   tian  cou   wei   can   tuan  mian  xu    mo    xu    ji" +
                        "    pen   jian  jian  hu    feng  xiang yi    yin   zhan  shi   jie   zhen  huang tan   yu  " +
                        "  bi    min   shi   tu    sheng yong  ju    zhong none  qiu   jiao  none  yin   tang  long  " +
                        "huo   yuan  nan   ban   you   quan  chui  liang chan  yan   chun  nie   zi    wan   shi   " +
                        "man   ying  la    kui   none  jian  xu    lou   gui   gai   none  none  po    jin   gui   " +
                        "tang  yuan  suo   yuan  lian  yao   meng  zhun  sheng ke    tai   ta    wa    liu   gou   " +
                        "sao   ming  zha   shi   yi    lun   ma    pu    wei   li    ")
                .append("cai   wu    xi    wen   qiang ce    shi   su    yi    zhen  sou   yun   xiu   yin   rong  " +
                        "hun   su    su    ni    ta    shi   ru    wei   pan   chu   chu   pang  weng  cang  mie   he" +
                        "    dian  hao   huang xi    zi    di    zhi   ying  fu    jie   hua   ge    zi    tao   teng" +
                        "  sui   bi    jiao  hui   gun   yin   gao   long  zhi   yan   she   man   ying  chun  lu:   " +
                        "lan   luan  xiao  bin   tan   yu    xiu   hu    bi    biao  zhi   jiang kou   shen  shang di" +
                        "    mi    ao    lu    hu    hu    you   chan  fan   yong  gun   man   qing  yu    piao  ji  " +
                        "  ya    jiao  qi    xi    ji    lu    lu:   long  jin   guo   cong  lou   zhi   gai   qiang " +
                        "li    yan   cao   jiao  cong  chun  tuan  ou    teng  ye    xi    mi    tang  mo    shang " +
                        "han   lian  lan   wa    li    qian  feng  xuan  yi    man   zi    mang  kang  luo   peng  " +
                        "shu   zhang zhang chong xu    huan  kuo   jian  yan   chuangliao  cui   ti    yang  jiang " +
                        "cong  ying  hong  xiu   shu   guan  ying  xiao  none  none  xu    lian  zhi   wei   pi    yu" +
                        "    jiao  po    xiang hui   jie   wu    pa    ji    pan   wei   xiao  qian  qian  xi    lu  " +
                        "  xi    sun   dun   huang min   run   su    liao  zhen  zhong yi    di    wan   dan   tan   " +
                        "chao  xun   kui   none  shao  tu    zhu   sa    hei   bi    shan  chan  chan  shu   tong  pu" +
                        "    lin   wei   se    se    cheng jiong cheng hua   jiao  lao   che   gan   cun   heng  si  " +
                        "  shu   peng  han   yun   liu   hong  fu    hao   he    xian  jian  shan  xi    ao    lu    " +
                        "lan   none  yu    lin   min   zao   dang  huan  ze    xie   yu    li    shi   xue   ling  " +
                        "man   zi    yong  kuai  can   lian  dian  ye    ao    huan  lian  chan  man   dan   dan   yi" +
                        "    sui   pi    ju    ta    qin   ji    zhuo  lian  nong  guo   jin   fen   se    ji    sui " +
                        "  hui   chu   ta    song  ding  se    zhu   lai   bin   lian  mi    shi   shu   mi    ning  " +
                        "ying  ying  meng  jin   qi    bi    ji    hao   ru    zui   wo    tao   yin   yin   dui   ci" +
                        "    huo   jing  lan   jun   ai    pu    zhuo  wei   bin   gu    qian  xing  bin   kuo   fei " +
                        "  none  bin   jian  dui   luo   luo   lu:   li    you   yang  lu    si    jie   ying  du    " +
                        "wang  hui   xie   pan   shen  biao  chan  mie   liu   jian  pu    se    cheng gu    bin   " +
                        "huo   xian  lu    qin   han   ying  rong  li    jing  xiao  ying  sui   wei   xie   huai  " +
                        "hao   zhu   long  lai   dui   fan   hu    lai   none  none  ying  mi    ji    lian  jian  " +
                        "ying  fen   lin   yi    jian  yue   chan  dai   rang  jian  lan   fan   shuangyuan  zhuo  " +
                        "feng  she   lei   lan   cong  qu    yong  qian  fa    guan  que   yan   hao   none  sa    " +
                        "zan   luan  yan   li    mi    dan   tan   dang  jiao  chan  none  hao   ba    zhu   lan   " +
                        "lan   nang  wan   luan  quan  xian  yan   gan   yan   yu    huo   biao  mie   guang deng  " +
                        "hui   xiao  xiao  none  hong  ling  zao   zhuan jiu   zha   xie   chi   zhuo  zai   zai   " +
                        "can   yang  qi    zhong fen   niu   gui   wen   po    yi    lu    chui  pi    kai   pan   " +
                        "yan   kai   pang  mu    chao  liao  gui   kang  dun   guang xin   zhi   guang xin   wei   " +
                        "qiang bian  da    xia   zheng zhu   ke    zhao  fu    ba    duo   duo   ling  zhuo  xuan  ju" +
                        "    tan   pao   jiong pao   tai   tai   bing  yang  tong  han   zhu   zha   dian  wei   shi " +
                        "  lian  chi   ping  none  hu    shuo  lan   ting  jiao  xu    xing  quan  lie   huan  yang  " +
                        "xiao  xiu   xian  yin   wu    zhou  yao   shi   wei   tong  tong  zai   kai   hong  luo   " +
                        "xia   zhu   xuan  zheng po    yan   hui   guang zhe   hui   kao   none  fan   shao  ye    " +
                        "hui   none  tang  jin   re    none  xi    fu    jiong che   pu    jing  zhuo  ting  wan   " +
                        "hai   peng  lang  shan  hu    feng  chi   rong  hu    none  shu   lang  xun   xun   jue   " +
                        "xiao  xi    yan   han   zhuangqu    di    xie   qi    wu    none  none  han   yan   huan  " +
                        "men   ju    dao   bei   fen   lin   kun   hun   chun  xi    cui   wu    hong  ju    fu    " +
                        "yue   jiao  cong  feng  ping  qiong cui   xi    qiong xin   zhuo  yan   yan   yi    jue   yu" +
                        "    gang  ran   pi    yan   none  sheng chang shao  none  none  none  none  chen  he    kui " +
                        "  zhong duan  ya    hui   feng  lian  xuan  xing  huang jiao  jian  bi    ying  zhu   wei   " +
                        "tuan  tian  xi    nuan  nuan  chan  yan   jiong jiong yu    mei   sha   wu    ye  ")
                .append("  xin   qiong rou   mei   huan  xu    zhao  wei   fan   qiu   sui   yang  lie   zhu   none  " +
                        "gao   gua   bao   hu    yun   xia   none  none  bian  wei   tui   tang  chao  shan  yun   bo" +
                        "    huang xie   xi    wu    xi    yun   he    he    xi    yun   xiong nai   kao   none  yao " +
                        "  xun   ming  lian  ying  wen   rong  none  none  qiang liu   xi    bi    biao  cong  lu    " +
                        "jian  shu   yi    lou   feng  sui   yi    teng  jue   zong  yun   hu    yi    zhi   ao    " +
                        "wei   liao  han   ou    re    jiong man   none  shang cuan  zeng  jian  xi    xi    xi    yi" +
                        "    xiao  chi   huang chan  ye    qian  ran   yan   xian  qiao  zun   deng  dun   shen  jiao" +
                        "  fen   si    liao  yu    lin   tong  shao  fen   fan   yan   xun   lan   mei   tang  yi    " +
                        "jing  men   none  none  ying  yu    yi    xue   lan   tai   zao   can   sui   xi    que   " +
                        "cong  lian  hui   zhu   xie   ling  wei   yi    xie   zhao  hui   none  none  lan   ru    " +
                        "xian  kao   xun   jin   chou  dao   yao   he    lan   biao  rong  li    mo    bao   ruo   di" +
                        "    lu:   ao    xun   kuang shuo  none  li    lu    jue   liao  yan   xi    xie   long  yan " +
                        "  none  rang  yue   lan   cong  jue   tong  guan  none  che   mi    tang  lan   zhu   lan   " +
                        "ling  cuan  yu    zhua  lan   pa    zheng pao   zhao  yuan  ai    wei   none  jue   jue   fu" +
                        "    ye    ba    die   ye    yao   zu    shuanger    pan   chuan ke    zang  zang  qiang die " +
                        "  qiang pian  ban   pan   shao  jian  pai   du    yong  tou   tou   bian  die   bang  bo    " +
                        "bang  you   none  du    ya    cheng niu   cheng pin   jiu   mou   ta    mu    lao   ren   " +
                        "mang  fang  mao   mu    ren   wu    yan   fa    bei   si    jian  gu    you   gu    sheng mu" +
                        "    di    qian  quan  quan  zi    te    xi    mang  keng  qian  wu    gu    xi    li    li  " +
                        "  pou   ji    gang  zhi   ben   quan  run   du    ju    jia   jian  feng  pian  ke    ju    " +
                        "kao   chu   xi    bei   luo   jie   ma    san   wei   li    dun   tong  se    jiang xi    li" +
                        "    du    lie   pi    piao  bao   xi    chou  wei   kui   chou  quan  quan  ba    fan   qiu " +
                        "  bo    chai  chuo  an    jie   zhuangguang ma    you   kang  bo    hou   ya    han   huan  " +
                        "zhuangyun   kuang niu   di    qing  zhong yun   bei   pi    ju    ni    sheng pao   xia   " +
                        "tuo   hu    ling  fei   pi    ni    sheng you   gou   yue   ju    dan   bo    gu    xian  " +
                        "ning  huan  hen   jiao  he    zhao  ji    huan  shan  ta    rong  shou  tong  lao   du    " +
                        "xia   shi   kuai  zheng yu    sun   yu    bi    mang  xi    juan  li    xia   yin   suan  " +
                        "lang  bei   zhi   yan   sha   li    zhi   xian  jing  han   fei   yao   ba    qi    ni    " +
                        "biao  yin   li    lie   jian  qiang kun   yan   guo   zong  mi    chang yi    zhi   zheng ya" +
                        "    meng  cai   cu    she   lie   none  luo   hu    zong  hu    wei   feng  wo    yuan  xing" +
                        "  zhu   mao   wei   yuan  xian  tuan  ya    nao   xie   jia   hou   bian  you   you   mei   " +
                        "cha   yao   sun   bo    ming  hua   yuan  sou   ma    yuan  dai   yu    shi   hao   none  yi" +
                        "    zhen  chuanghao   man   jing  jiang mo    zhang chan  ao    ao    hao   cui   ben   jue " +
                        "  bi    bi    huang bu    lin   yu    tong  yao   liao  shuo  xiao  shou  none  xi    ge    " +
                        "juan  du    hui   kuai  xian  xie   ta    xian  xun   ning  bian  huo   nou   meng  lie   " +
                        "nao   guang shou  lu    ta    xian  mi    rang  huan  nao   luo   xian  qi    qu    xuan  " +
                        "miao  zi    lu:   lu    yu    su    wang  qiu   ga    ding  le    ba    ji    hong  di    " +
                        "chuan gan   jiu   yu    qi    yu    yang  ma    hong  wu    fu    min   jie   ya    bin   " +
                        "bian  beng  yue   jue   yun   jue   wan   jian  mei   dan   pi    wei   huan  xian  qiang " +
                        "ling  dai   yi    an    ping  dian  fu    xuan  xi    bo    ci    gou   jia   shao  po    ci" +
                        "    ke    ran   sheng shen  yi    zu    jia   min   shan  liu   bi    zhen  zhen  jue   fa  " +
                        "  long  jin   jiao  jian  li    guang xian  zhou  gong  yan   xiu   yang  xu    luo   su    " +
                        "zhu   qin   ken   xun   bao   er    xiang yao   xia   heng  gui   chong xu    ban   pei   " +
                        "none  dang  ying  hun   wen   e     cheng ti    wu    wu    cheng jun   mei   bei   ting  " +
                        "xian  chuo  han   xuan  yan   qiu   quan  lang  li    xiu   fu    liu   ya    xi    ling  li" +
                        "    jin   lian  suo   suo   none  wan   dian  bing  zhan  cui   min   yu")
                .append("    ju    chen  lai   wen   sheng wei   dian  chu   zhuo  pei   cheng hu    qi    e     kun " +
                        "  chang qi    beng  wan   lu    cong  guan  yan   diao  bei   lin   qin   pi    pa    qiang " +
                        "zhuo  qin   fa    none  qiong du    jie   hun   yu    mao   mei   chun  xuan  ti    xing  " +
                        "dai   rou   min   zhen  wei   ruan  huan  xie   chuan jian  zhuan yang  lian  quan  xia   " +
                        "duan  yuan  ye    nao   hu    ying  yu    huang rui   se    liu   none  rong  suo   yao   " +
                        "wen   wu    jin   jin   ying  ma    tao   liu   tang  li    lang  gui   tian  qiang cuo   " +
                        "jue   zhao  yao   ai    bin   tu    chang kun   zhuan cong  jin   yi    cui   cong  qi    li" +
                        "    ying  suo   qiu   xuan  ao    lian  man   zhang yin   none  ying  wei   lu    wu    deng" +
                        "  none  zeng  xun   qu    dang  lin   liao  qiong su    huang gui   pu    jing  fan   jin   " +
                        "liu   ji    none  jing  ai    bi    can   qu    zao   dang  jiao  gun   tan   hui   huan  se" +
                        "    sui   tian  none  yu    jin   fu    bin   shu   wen   zui   lan   xi    ji    xuan  ruan" +
                        "  huo   gai   lei   du    li    zhi   rou   li    zan   qiong zhe   gui   sui   la    long  " +
                        "lu    li    zan   lan   ying  mi    xiang xi    guan  dao   zan   huan  gua   bao   die   " +
                        "pao   hu    zhi   piao  ban   rang  li    wa    none  jiang qian  ban   pen   fang  dan   " +
                        "weng  ou    none  none  none  hu    ling  yi    ping  ci    none  juan  chang chi   none  " +
                        "dang  meng  bu    chui  ping  bian  zhou  zhen  none  ci    ying  qi    xian  lou   di    ou" +
                        "    meng  zhuan beng  lin   zeng  wu    pi    dan   weng  ying  yan   gan   dai   shen  tian" +
                        "  tian  han   chang sheng qing  shen  chan  chan  rui   sheng su    shen  yong  shuai lu    " +
                        "fu    yong  beng  none  ning  tian  you   jia   shen  zha   dian  fu    nan   dian  ping  " +
                        "ding  hua   ting  quan  zai   meng  bi    qi    liu   xun   liu   chang mu    yun   fan   fu" +
                        "    geng  tian  jie   jie   quan  wei   fu    tian  mu    none  pan   jiang wa    da    nan " +
                        "  liu   ben   zhen  chu   mu    mu    ce    none  gai   bi    da    zhi   lu:e  qi    lu:e  " +
                        "pan   none  fan   hua   yu    yu    mu    jun   yi    liu   she   die   chou  hua   dang  " +
                        "chuo  ji    wan   jiang cheng chang tun   lei   ji    cha   liu   die   tuan  lin   jiang " +
                        "jiang chou  bo    die   die   pi    nie   dan   shu   shu   zhi   yi    chuangnai   ding  bi" +
                        "    jie   liao  gong  ge    jiu   zhou  xia   shan  xu    nu:e  li    yang  chen  you   ba  " +
                        "  jie   jue   xi    xia   cui   bi    yi    li    zong  chuangfeng  zhu   pao   pi    gan   " +
                        "ke    ci    xie   qi    dan   zhen  fa    zhi   teng  ju    ji    fei   ju    dian  jia   " +
                        "xuan  zha   bing  nie   zheng yong  jing  quan  chong tong  yi    jie   wei   hui   duo   " +
                        "yang  chi   zhi   hen   ya    mei   dou   jing  xiao  tong  tu    mang  pi    xiao  suan  pu" +
                        "    li    zhi   cuo   duo   wu    sha   lao   shou  huan  xian  yi    peng  zhang guan  tan " +
                        "  fei   ma    lin   chi   ji    tian  an    chi   bi    bi    min   gu    dui   e     wei   " +
                        "yu    cui   ya    zhu   xi    dan   shen  zhong ji    yu    hou   feng  la    yang  shen  tu" +
                        "    yu    gua   wen   huan  ku    jia   yin   yi    lou   sao   jue   chi   xi    guan  yi  " +
                        "  wen   ji    chuangban   lei   liu   chai  shou  nu:e  dian  da    bie   tan   zhang biao  " +
                        "shen  cu    luo   yi    zong  chou  zhang zhai  sou   suo   que   diao  lou   lou   mo    " +
                        "jin   yin   ying  huang fu    liao  long  qiao  liu   lao   xian  fei   dan   yin   he    ai" +
                        "    ban   xian  guan  guai  nong  yu    wei   yi    yong  pi    lei   li    shu   dan   lin " +
                        "  dian  lin   lai   bie   ji    chi   yang  xuan  jie   zheng none  li    huo   lai   ji    " +
                        "dian  xian  ying  yin   qu    yong  tan   dian  luo   luan  luan  bo    none  gui   po    fa" +
                        "    deng  fa    bai   bai   qie   bi    zao   zao   mao   de    pa    jie   huang gui   ci  " +
                        "  ling  gao   mo    ji    jiao  peng  gao   ai    e     hao   han   bi    wan   chou  qian  " +
                        "xi    ai    jiong hao   huang hao   ze    cui   hao   xiao  ye    po    hao   jiao  ai    " +
                        "xing  huang li    piao  he    jiao  pi    gan   pao   zhou  jun   qiu   cun   que   zha   gu" +
                        "    jun   jun   zhou  zha   gu    zhan  du    min   qi    ying  yu    bei   zhao  zhong pen " +
                        "  he    ying  he    yi    bo    wan   he    ang   zhan  yan   jian  ")
                .append("he    yu    kui   fan   gai   dao   pan   fu    qiu   sheng dao   lu    zhan  meng  lu    " +
                        "jin   xu    jian  pan   guan  an    lu    xu    zhou  dang  an    gu    li    mu    ding  " +
                        "gan   xu    mang  mang  zhi   qi    wan   tian  xiang dun   xin   xi    pan   feng  dun   " +
                        "min   ming  sheng shi   yun   mian  pan   fang  miao  dan   mei   mao   kan   xian  kou   " +
                        "shi   yang  zheng yao   shen  huo   da    zhen  kuang ju    shen  yi    sheng mei   mo    " +
                        "zhu   zhen  zhen  mian  di    yuan  die   yi    zi    zi    chao  zha   xuan  bing  mi    " +
                        "long  sui   tong  mi    die   yi    er    ming  xuan  chi   kuang juan  mou   zhen  tiao  " +
                        "yang  yan   mo    zhong mai   zhe   zheng mei   suo   shao  han   huan  di    cheng cuo   " +
                        "juan  e     wan   xian  xi    kun   lai   jian  shan  tian  hun   wan   ling  shi   qiong " +
                        "lie   ya    jing  zheng li    lai   sui   juan  shui  sui   du    pi    pi    mu    hun   ni" +
                        "    lu    gao   jie   cai   zhou  yu    hun   ma    xia   xing  hui   gun   none  chun  jian" +
                        "  mei   du    hou   xuan  ti    kui   gao   rui   mao   xu    fa    wen   miao  chou  kui   " +
                        "mi    weng  kou   dang  chen  ke    sou   xia   qiong mao   ming  man   shui  ze    zhang yi" +
                        "    diao  kou   mo    shun  cong  lou   chi   man   piao  cheng ji    meng  huan  run   pie " +
                        "  xi    qiao  pu    zhu   deng  shen  shun  liao  che   xian  kan   ye    xu    tong  wu    " +
                        "lin   kui   jian  ye    ai    hui   zhan  jian  gu    zhao  ju    wei   chou  ji    ning  " +
                        "xun   yao   huo   meng  mian  bin   mian  li    guang jue   xuan  mian  huo   lu    meng  " +
                        "long  guan  man   xi    chu   tang  kan   zhu   mao   jin   lin   yu    shuo  ce    jue   " +
                        "shi   yi    shen  zhi   hou   shen  ying  ju    zhou  jiao  cuo   duan  ai    jiao  zeng  " +
                        "huo   bai   shi   ding  qi    ji    zi    gan   wu    tuo   ku    qiang xi    fan   kuang " +
                        "dang  ma    sha   dan   jue   li    fu    min   nuo   hua   kang  zhi   qi    kan   jie   " +
                        "fen   e     ya    pi    zhe   yan   sui   zhuan che   dun   pan   yan   none  feng  fa    mo" +
                        "    zha   qu    yu    ke    tuo   tuo   di    zhai  zhen  e     fu    mu    zhu   la    bian" +
                        "  nu    ping  peng  ling  pao   le    po    bo    po    shen  za    ai    li    long  tong  " +
                        "none  li    kuang chu   keng  quan  zhu   kuang gui   e     nao   jia   lu    wei   ai    " +
                        "luo   ken   xing  yan   dong  peng  xi    none  hong  shuo  xia   qiao  none  wei   qiao  " +
                        "none  keng  xiao  que   chan  lang  hong  yu    xiao  xia   mang  long  none  che   che   wo" +
                        "    liu   ying  mang  que   yan   cuo   kun   yu    none  none  lu    chen  jian  none  song" +
                        "  zhuo  keng  peng  yan   zhui  kong  ceng  qi    zong  qing  lin   jun   bo    ding  min   " +
                        "diao  jian  he    liu   ai    sui   que   ling  bei   yin   dui   wu    qi    lun   wan   " +
                        "dian  gang  bei   qi    chen  ruan  yan   die   ding  zhou  tuo   jie   ying  bian  ke    bi" +
                        "    wei   shuo  zhen  duan  xia   dang  ti    nao   peng  jian  di    tan   cha   none  qi  " +
                        "  none  feng  xuan  que   que   ma    gong  nian  su    e     ci    liu   si    tang  bang  " +
                        "hua   pi    wei   sang  lei   cuo   tian  xia   xi    lian  pan   wei   yun   dui   zhe   ke" +
                        "    la    none  qing  gun   zhuan chan  qi    ao    peng  lu    lu    kan   qiang chen  yin " +
                        "  lei   biao  qi    mo    qi    cui   zong  qing  chuo  none  ji    shan  lao   qu    zeng  " +
                        "deng  jian  xi    lin   ding  dian  huang pan   za    qiao  di    li    jian  jiao  xi    " +
                        "zhang qiao  dun   jian  yu    zhui  he    huo   zhai  lei   ke    chu   ji    que   dang  wo" +
                        "    jiang pi    pi    yu    pin   qi    ai    ke    jian  yu    ruan  meng  pao   zi    bo  " +
                        "  none  mie   ca    xian  kuang lei   lei   zhi   li    li    fan   que   pao   ying  li    " +
                        "long  long  mo    bo    shuangguan  lan   zan   yan   shi   shi   li    reng  she   yue   si" +
                        "    qi    ta    ma    xie   yao   xian  zhi   qi    zhi   beng  shu   chong none  yi    shi " +
                        "  you   zhi   tiao  fu    fu    mi    zu    zhi   suan  mei   zuo   qu    hu    zhu   shen  " +
                        "sui   ci    chai  mi    lu:   yu    xiang wu    tiao  piao  zhu   gui   xia   zhi   ji    " +
                        "gao   zhen  gao   shui  jin   zhen  gai   kun   di    dao   huo   tao   qi    gu    guan  " +
                        "zui   ling  lu    bing  jin   dao   zhi   lu    shan  bei   zhe   hui   you   xi  ")
                .append("  yin   zi    huo   zhen  fu    yuan  wu    xian  yang  ti    yi    mei   si    di    none  " +
                        "zhuo  zhen  yong  ji    gao   tang  chi   ma    ta    none  xuan  qi    yu    xi    ji    si" +
                        "    chan  xuan  hui   sui   li    nong  ni    dao   li    rang  yue   ti    zan   lei   rou " +
                        "  yu    yu    li    xie   qin   he    tu    xiu   si    ren   tu    zi    cha   gan   yi    " +
                        "xian  bing  nian  qiu   qiu   zhong fen   hao   yun   ke    miao  zhi   jing  bi    zhi   yu" +
                        "    mi    ku    ban   pi    ni    li    you   zu    pi    ba    ling  mo    cheng nian  qin " +
                        "  yang  zuo   zhi   zhi   shu   ju    zi    tai   ji    cheng tong  zhi   huo   he    yin   " +
                        "zi    zhi   jie   ren   du    yi    zhu   hui   nong  fu    xi    kao   lang  fu    ze    " +
                        "shui  lu:   kun   gan   jing  ti    cheng tu    shao  shui  ya    lun   lu    gu    zuo   " +
                        "ren   zhun  bang  bai   ji    zhi   zhi   kun   leng  peng  ke    bing  chou  zui   yu    su" +
                        "    none  none  yi    xi    bian  ji    fu    bi    nuo   jie   zhong zong  xu    cheng dao " +
                        "  wen   lian  zi    yu    ji    xu    zhen  zhi   dao   jia   ji    gao   gao   gu    rong  " +
                        "sui   none  ji    kang  mu    shan  men   zhi   ji    lu    su    ji    ying  wen   qiu   se" +
                        "    none  yi    huang qie   ji    sui   xiao  pu    jiao  zhuo  tong  none  lu:   sui   nong" +
                        "  se    hui   rang  nuo   yu    none  ji    tui   wen   cheng huo   gong  lu:   biao  none  " +
                        "rang  jue   li    zan   xue   wa    jiu   qiong xi    qiong kong  yu    sen   jing  yao   " +
                        "chuan zhun  tu    lao   qie   zhai  yao   bian  bao   yao   bing  yu    zhu   jiao  qiao  " +
                        "diao  wu    gui   yao   zhi   chuan yao   tiao  jiao  chuangjiong xiao  cheng kou   cuan  wo" +
                        "    dan   ku    ke    zhui  xu    su    none  kui   dou   none  yin   wo    wa    ya    yu  " +
                        "  ju    qiong yao   yao   tiao  liao  yu    tian  diao  ju    liao  xi    wu    kui   " +
                        "chuangju    none  kuan  long  cheng cui   piao  zao   cuan  qiao  qiong dou   zao   zao   " +
                        "qie   li    chu   shi   fu    qian  chu   hong  qi    qian  gong  shi   shu   miao  ju    " +
                        "zhan  zhu   ling  long  bing  jing  jing  zhang yi    si    jun   hong  tong  song  jing  " +
                        "diao  yi    shu   jing  qu    jie   ping  duan  shao  zhuan ceng  deng  cun   huai  jing  " +
                        "kan   jing  zhu   zhu   le    peng  yu    chi   gan   mang  zhu   none  du    ji    xiao  ba" +
                        "    suan  ji    zhen  zhao  sun   ya    zhui  yuan  hu    gang  xiao  cen   pi    bi    jian" +
                        "  yi    dong  shan  sheng xia   di    zhu   na    chi   gu    li    qie   min   bao   tiao  " +
                        "si    fu    ce    ben   fa    da    zi    di    ling  ze    nu    fu    gou   fan   jia   ge" +
                        "    fan   shi   mao   po    none  jian  qiong long  none  bian  luo   gui   qu    chi   yin " +
                        "  yao   xian  bi    qiong gua   deng  jiao  jin   quan  sun   ru    fa    kuang zhu   tong  " +
                        "ji    da    hang  ce    zhong kou   lai   bi    shai  dang  zheng ce    fu    yun   tu    pa" +
                        "    li    lang  ju    guan  jian  han   tong  xia   zhi   cheng suan  shi   zhu   zuo   xiao" +
                        "  shao  ting  jia   yan   gao   kuai  gan   chou  kuang gang  yun   none  qian  xiao  jian  " +
                        "pu    lai   zou   bi    bi    bi    ge    chi   guai  yu    jian  zhao  gu    chi   zheng " +
                        "qing  sha   zhou  lu    bo    ji    lin   suan  jun   fu    zha   gu    kong  qian  qian  " +
                        "jun   chui  guan  yuan  ce    ju    bo    ze    qie   tuo   luo   dan   xiao  ruo   jian  " +
                        "none  bian  sun   xiang xian  ping  zhen  sheng hu    shi   zhu   yue   chun  fu    wu    " +
                        "dong  shuo  ji    jie   huang xing  mei   fan   chuan zhuan pian  feng  zhu   hong  qie   " +
                        "hou   qiu   miao  qian  none  kui   none  lou   yun   he    tang  yue   chou  gao   fei   " +
                        "ruo   zheng gou   nie   qian  xiao  cuan  gong  pang  du    li    bi    zhuo  chu   shai  " +
                        "chi   zhu   qiang long  lan   jian  bu    li    hui   bi    di    cong  yan   peng  sen   " +
                        "cuan  pai   piao  dou   yu    mie   zhuan ze    xi    guo   yi    hu    chan  kou   cu    " +
                        "ping  zao   ji    gui   su    lou   zha   lu    nian  suo   cuan  none  suo   le    duan  " +
                        "liang xiao  bo    mi    shai  dang  liao  dan   dian  fu    jian  min   kui   dai   qiao  " +
                        "deng  huang sun   lao   zan   xiao  lu    shi   zan   none  pai   qi    pai   gan   ju    du" +
                        "    lu    yan   bo    dang  sai   ke    gou   qian  lian  bu    zhou  lai   none  la")
                .append("n   kui   yu    yue   hao   zhen  tai   ti    mi    chou  ji    none  qi    teng  zhuan zhou" +
                        "  fan   sou   zhou  qian  kuo   teng  lu    lu    jian  tuo   ying  yu    lai   long  none  " +
                        "lian  lan   qian  yue   zhong qu    lian  bian  duan  zuan  li    shai  luo   ying  yue   " +
                        "zhuo  xu    mi    di    fan   shen  zhe   shen  nu:   xie   lei   xian  zi    ni    cun   " +
                        "zhang qian  none  bi    ban   wu    sha   kang  rou   fen   bi    cui   yin   li    chi   " +
                        "tai   none  ba    li    gan   ju    po    mo    cu    zhan  zhou  li    su    tiao  li    xi" +
                        "    su    hong  tong  zi    ce    yue   zhou  lin   zhuangbai   none  fen   mian  qu    none" +
                        "  liang xian  fu    liang can   jing  li    yue   lu    ju    qi    cui   bai   chang lin   " +
                        "zong  jing  guo   none  san   san   tang  bian  rou   mian  hou   xu    zong  hu    jian  " +
                        "zan   ci    li    xie   fu    nuo   bei   gu    xiu   gao   tang  qiu   none  cao   " +
                        "zhuangtang  mi    san   fen   zao   kang  jiang mo    san   san   nuo   chi   liang jiang " +
                        "kuai  bo    huan  shu   zong  jian  nuo   tuan  nie   li    zuo   di    nie   tiao  lan   mi" +
                        "    mi    jiu   xi    gong  zheng jiu   you   ji    cha   zhou  xun   yue   hong  yu    he  " +
                        "  wan   ren   wen   wen   qiu   na    zi    tou   niu   fou   jie   shu   chun  pi    yin   " +
                        "sha   hong  zhi   ji    fen   yun   ren   dan   jin   su    fang  suo   cui   jiu   zha   ba" +
                        "    jin   fu    zhi   qi    zi    chou  hong  zha   lei   xi    fu    xie   shen  bei   zhu " +
                        "  qu    ling  zhu   shao  gan   yang  fu    tuo   zhen  dai   chu   shi   zhong xian  zu    " +
                        "jiong ban   ju    pa    shu   zui   kuang jing  ren   heng  xie   jie   zhu   chou  gua   " +
                        "bai   jue   kuang hu    ci    geng  geng  tao   xie   ku    jiao  quan  gai   luo   xuan  " +
                        "beng  xian  fu    gei   tong  rong  tiao  yin   lei   xie   quan  xu    hai   die   tong  si" +
                        "    jiang xiang hui   jue   zhi   jian  juan  chi   mian  zhen  lu:   cheng qiu   shu   bang" +
                        "  tong  xiao  wan   qin   geng  xiu   ti    xiu   xie   hong  xi    fu    ting  sui   dui   " +
                        "kun   fu    jing  hu    zhi   yan   jiong feng  ji    xu    none  zong  lin   duo   li    " +
                        "lu:   liang chou  quan  shao  qi    qi    zhun  qi    wan   qian  xian  shou  wei   qi    " +
                        "tao   wan   gang  wang  beng  zhui  cai   guo   cui   lun   liu   qi    zhan  bei   chuo  " +
                        "ling  mian  qi    jie   tan   zong  gun   zou   yi    zi    xing  liang jin   fei   rui   " +
                        "min   yu    zong  fan   lu:   xu    none  shang none  xu    xiang jian  ke    xian  ruan  " +
                        "mian  ji    duan  zhong di    min   miao  yuan  xie   bao   si    qiu   bian  huan  geng  " +
                        "zong  mian  wei   fu    wei   yu    gou   miao  jie   lian  zong  bian  yun   yin   ti    " +
                        "gua   zhi   yun   cheng chan  dai   jia   yuan  zong  xu    sheng none  geng  none  ying  " +
                        "jin   yi    zhui  ni    bang  gu    pan   zhou  jian  cuo   quan  shuangyun   xia   shuai xi" +
                        "    rong  tao   fu    yun   zhen  gao   ru    hu    zai   teng  xian  su    zhen  zong  tao " +
                        "  huang cai   bi    feng  cu    li    suo   yin   xi    zong  lei   zhuan qian  man   zhi   " +
                        "lu:   mo    piao  lian  mi    xuan  zong  ji    shan  sui   fan   shuai beng  yi    sao   " +
                        "mou   zhou  qiang hun   xian  xi    none  xiu   ran   xuan  hui   qiao  zeng  zuo   zhi   " +
                        "shan  san   lin   yu    fan   liao  chuo  zun   jian  rao   chan  rui   xiu   hui   hua   " +
                        "zuan  xi    qiang none  da    sheng hui   xi    se    jian  jiang huan  qiao  cong  jie   " +
                        "jiao  bo    chan  yi    nao   sui   yi    shai  xu    ji    bin   qian  jian  pu    xun   " +
                        "zuan  qi    peng  li    mo    lei   xie   zuan  kuang you   xu    lei   xian  chan  none  lu" +
                        "    chan  ying  cai   xiang xian  zui   zuan  luo   xi    dao   lan   lei   lian  mi    jiu " +
                        "  yu    hong  zhou  xian  he    yue   ji    wan   kuang ji    ren   wei   yun   hong  chun  " +
                        "pi    sha   gang  na    ren   zong  lun   fen   zhi   wen   fang  zhu   zhen  niu   shu   " +
                        "xian  gan   xie   fu    lian  zu    shen  xi    zhi   zhong zhou  ban   fu    chu   shao  yi" +
                        "    jing  dai   bang  rong  jie   ku    rao   die   hang  hui   ji    xuan  jiang luo   jue " +
                        "  jiao  tong  geng  xiao  juan  xiu   xi    sui   tao   ji    ti    ji    xu    ling  yin   " +
                        "xu    qi    fei   chuo  shang gun   sheng wei   mian  shou  beng  chou  tao   liu   quan  ")
                .append("zong  zhan  wan   lu:   zhui  zi    ke    xiang jian  mian  lan   ti    miao  ji    yun   " +
                        "hui   si    duo   duan  bian  xian  gou   zhui  huan  di    lu:   bian  min   yuan  jin   fu" +
                        "    ru    zhen  feng  cui   gao   chan  li    yi    jian  bin   piao  man   lei   ying  suo " +
                        "  mou   sao   xie   liao  shan  zeng  jiang qian  qiao  huan  jiao  zuan  fou   xie   gang  " +
                        "fou   que   fou   que   bo    ping  hou   none  gang  ying  ying  qing  xia   guan  zun   " +
                        "tan   none  qing  weng  ying  lei   tan   lu    guan  wang  gang  wang  wang  han   none  " +
                        "luo   fu    mi    fa    gu    zhu   ju    mao   gu    min   gang  ba    gua   ti    juan  fu" +
                        "    lin   yan   zhao  zui   gua   zhuo  yu    zhi   an    fa    lan   shu   si    pi    ma  " +
                        "  liu   ba    fa    li    chao  wei   bi    ji    zeng  tong  liu   ji    juan  mi    zhao  " +
                        "luo   pi    ji    ji    luan  yang  mie   qiang ta    mei   yang  you   you   fen   ba    " +
                        "gao   yang  gu    qiang zang  gao   ling  yi    zhu   di    xiu   qiang yi    xian  rong  " +
                        "qun   qun   qian  huan  suo   xian  yi    yang  qiang xian  yu    geng  jie   tang  yuan  xi" +
                        "    fan   shan  fen   shan  lian  lei   geng  nou   qiang chan  yu    gong  yi    chong weng" +
                        "  fen   hong  chi   chi   cui   fu    xia   pen   yi    la    yi    pi    ling  liu   zhi   " +
                        "qu    xi    xie   xiang xi    xi    qi    qiao  hui   hui   shu   se    hong  jiang zhai  " +
                        "cui   fei   tao   sha   chi   zhu   jian  xuan  shi   pian  zong  wan   hui   hou   he    he" +
                        "    han   ao    piao  yi    lian  qu    none  lin   pen   qiao  ao    fan   yi    hui   xuan" +
                        "  dao   yao   lao   none  kao   mao   zhe   qi    gou   gou   gou   die   die   er    shua  " +
                        "ruan  er    nai   zhuan lei   ting  zi    geng  chao  hao   yun   pa    pi    chi   si    qu" +
                        "    jia   ju    huo   chu   lao   lun   ji    tang  ou    lou   nou   jiang pang  ze    lou " +
                        "  ji    lao   huo   you   mo    huai  er    zhe   ding  ye    da    song  qin   yun   chi   " +
                        "dan   dan   hong  geng  zhi   none  nie   dan   zhen  che   ling  zheng you   wa    liao  " +
                        "long  zhi   ning  tiao  er    ya    die   guo   none  lian  hao   sheng lie   pin   jing  ju" +
                        "    bi    di    guo   wen   xu    ping  cong  none  none  ting  yu    cong  kui   lian  kui " +
                        "  cong  lian  weng  kui   lian  lian  cong  ao    sheng song  ting  kui   nie   zhi   dan   " +
                        "ning  none  ji    ting  ting  long  yu    yu    zhao  si    su    yi    su    si    zhao  " +
                        "zhao  rou   yi    lei   ji    qiu   ken   cao   ge    di    huan  huang yi    ren   xiao  ru" +
                        "    zhou  yuan  du    gang  rong  gan   cha   wo    chang gu    zhi   qin   fu    fei   ban " +
                        "  pei   pang  jian  fang  zhun  you   na    ang   ken   ran   gong  yu    wen   yao   jin   " +
                        "pi    qian  xi    xi    fei   ken   jing  tai   shen  zhong zhang xie   shen  wei   zhou  " +
                        "die   dan   fei   ba    bo    qu    tian  bei   gua   tai   zi    ku    zhi   ni    ping  zi" +
                        "    fu    pang  zhen  xian  zuo   pei   jia   sheng zhi   bao   mu    qu    hu    ke    yi  " +
                        "  yin   xu    yang  long  dong  ka    lu    jing  nu    yan   pang  kua   yi    guang hai   " +
                        "ge    dong  zhi   jiao  xiong xiong er    an    xing  pian  neng  zi    none  cheng tiao  " +
                        "zhi   cui   mei   xie   cui   xie   mo    mai   ji    xie   none  kuai  sa    zang  qi    " +
                        "nao   mi    nong  luan  wan   bo    wen   wan   qiu   jiao  jing  you   heng  cuo   lie   " +
                        "shan  ting  mei   chun  shen  xie   none  juan  cu    xiu   xin   tuo   pao   cheng nei   fu" +
                        "    dou   tuo   niao  nao   pi    gu    luo   li    lian  zhang cui   jie   liang shui  pi  " +
                        "  biao  lun   pian  guo   juan  chui  dan   tian  nei   jing  jie   la    ye    a     ren   " +
                        "shen  chuo  fu    fu    ju    fei   qiang wan   dong  pi    guo   zong  ding  wu    mei   " +
                        "ruan  zhuan zhi   cou   gua   ou    di    an    xing  nao   shu   shuan nan   yun   zhong " +
                        "rou   e     sai   tu    yao   jian  wei   jiao  yu    jia   duan  bi    chang fu    xian  ni" +
                        "    mian  wa    teng  tui   bang  qian  lu:   wa    shou  tang  su    zhui  ge    yi    bo  " +
                        "  liao  ji    pi    xie   gao   lu:   bin   none  chang lu    guo   pang  chuai biao  jiang " +
                        "fu    tang  mo    xi    zhuan lu:   jiao  ying  lu:   zhi   xue   chun  lin   tong  peng  ni" +
                        "    chuai liao  cui   gui   xiao  teng  fan   zhi   jiao  shan  hu  ")
                .append("  cui   run   xin   sui   fen   ying  shan  gua   dan   kuai  nong  tun   lian  bei   yong  " +
                        "jue   chu   yi    juan  la    lian  sao   tun   gu    qi    cui   bin   xun   nao   huo   " +
                        "zang  xian  biao  xing  kuan  la    yan   lu    hu    za    luo   qu    zang  luan  ni    za" +
                        "    chen  qian  wo    guang zang  lin   guang zi    jiao  nie   chou  ji    gao   chou  mian" +
                        "  nie   zhi   zhi   ge    jian  die   zhi   xiu   tai   zhen  jiu   xian  yu    cha   yao   " +
                        "yu    chong xi    xi    jiu   yu    yu    xing  ju    jiu   xin   she   she   she   jiu   " +
                        "shi   tan   shu   shi   tian  dan   pu    pu    guan  hua   tian  chuan shun  xia   wu    " +
                        "zhou  dao   chuan shan  yi    none  pa    tai   fan   ban   chuan hang  fang  ban   bi    lu" +
                        "    zhong jian  cang  ling  zhu   ze    duo   bo    xian  ge    chuan jia   lu    hong  pang" +
                        "  xi    none  fu    zao   feng  li    shao  yu    lang  ting  none  wei   bo    meng  nian  " +
                        "ju    huang shou  zong  bian  mao   die   none  bang  cha   yi    sou   cang  cao   lou   " +
                        "dai   none  yao   chong none  dang  qiang lu    yi    jie   jian  huo   meng  qi    lu    lu" +
                        "    chan  shuanggen   liang jian  jian  se    yan   fu    ping  yan   yan   cao   cao   yi  " +
                        "  le    ting  jiao  ai    nai   tiao  jiao  jie   peng  wan   yi    chai  mian  mi    gan   " +
                        "qian  yu    yu    shao  xiong du    xia   qi    mang  zi    hui   sui   zhi   xiang bi    fu" +
                        "    tun   wei   wu    zhi   qi    shan  wen   qian  ren   fou   kou   jie   lu    zhu   ji  " +
                        "  qin   qi    yan   fen   ba    rui   xin   ji    hua   hua   fang  wu    jue   gou   zhi   " +
                        "yun   qin   ao    chu   mao   ya    fei   reng  hang  cong  yin   you   bian  yi    none  " +
                        "wei   li    pi    e     xian  chang cang  zhu   su    yi    yuan  ran   ling  tai   tiao  di" +
                        "    miao  qing  li    rao   ke    mu    pei   bao   gou   min   yi    yi    ju    pie   ruo " +
                        "  ku    zhu   ni    bo    bing  shan  qiu   yao   xian  ben   hong  ying  zha   dong  ju    " +
                        "die   nie   gan   hu    ping  mei   fu    sheng gu    bi    wei   fu    zhuo  mao   fan   " +
                        "qie   mao   mao   ba    zi    mo    zi    di    chi   gou   jing  long  none  niao  none  " +
                        "xue   ying  qiong ge    ming  li    rong  yin   gen   qian  chai  chen  yu    xiu   zi    " +
                        "lie   wu    duo   kui   ce    jian  ci    gou   guang mang  cha   jiao  jiao  fu    yu    " +
                        "zhu   zi    jiang hui   yin   cha   fa    rong  ru    chong mang  tong  zhong none  zhu   " +
                        "xun   huan  kua   quan  gai   da    jing  xing  chuan cao   jing  er    an    shou  chi   " +
                        "ren   jian  ti    huang ping  li    jin   lao   rong  zhuangda    jia   rao   bi    ce    " +
                        "qiao  hui   ji    dang  none  rong  hun   ying  luo   ying  qian  jin   sun   yin   mai   " +
                        "hong  zhou  yao   du    wei   chu   dou   fu    ren   yin   he    bi    bu    yun   di    tu" +
                        "    sui   sui   cheng chen  wu    bie   xi    geng  li    pu    zhu   mo    li    zhuangji  " +
                        "  duo   qiu   sha   suo   chen  feng  ju    mei   meng  xing  jing  che   xin   jun   yan   " +
                        "ting  you   cuo   guan  han   you   cuo   jia   wang  you   niu   shao  xian  lang  fu    e " +
                        "    mo    wen   jie   nan   mu    kan   lai   lian  shi   wo    tu    xian  huo   you   ying" +
                        "  ying  none  chun  mang  mang  ci    yu    jing  di    qu    dong  jian  zou   gu    la    " +
                        "lu    ju    wei   jun   nie   kun   he    pu    zai   gao   guo   fu    lun   chang chou  " +
                        "song  chui  zhan  men   cai   ba    li    tu    bo    han   bao   qin   juan  xi    qin   di" +
                        "    jie   pu    dang  jin   zhao  tai   geng  hua   gu    ling  fei   jin   an    wang  beng" +
                        "  zhou  yan   zu    jian  lin   tan   shu   tian  dao   hu    ji    he    cui   tao   chun  " +
                        "bei   chang huan  fei   lai   qi    meng  ping  wei   dan   sha   huan  yan   yi    tiao  qi" +
                        "    wan   ce    nai   none  tuo   jiu   tie   luo   none  none  meng  none  none  ding  ying" +
                        "  ying  ying  xiao  sa    qiu   ke    xiang wan   yu    yu    fu    lian  xuan  xuan  nan   " +
                        "ze    wo    chun  xiao  yu    pian  mao   an    e     luo   ying  huo   gua   jiang wan   " +
                        "zuo   zuo   ju    bao   rou   xi    xie   an    qu    jian  fu    lu:   lu:   pen   feng  " +
                        "hong  hong  hou   yan   tu    zhu   zi    xiang shen  ge    qia   jing  mi    huang shen  pu" +
                        "    ge    dong  zhou  qian  wei   bo    wei   pa    ji    hu    zang  ji")
                .append("a   duan  yao   jun   cong  quan  wei   xian  kui   ting  hun   xi    shi   qi    lan   zong" +
                        "  yao   yuan  mei   yun   shu   di    zhuan guan  none  qiong chan  kai   kui   none  jiang " +
                        "lou   wei   pai   none  sou   yin   shi   chun  shi   yun   zhen  lang  nu    meng  he    " +
                        "que   suan  yuan  li    ju    xi    bang  chu   xu    tu    liu   huo   zhen  qian  zu    po" +
                        "    cuo   yuan  chu   yu    kuai  pan   pu    pu    na    shuo  xi    fen   yun   zheng jian" +
                        "  ji    ruo   cang  en    mi    hao   sun   zhen  ming  none  xu    liu   xi    gu    lang  " +
                        "rong  weng  gai   cuo   shi   tang  luo   ru    suo   xian  bei   yao   gui   bi    zong  " +
                        "gun   none  xiu   ce    none  lan   none  ji    li    can   lang  yu    none  ying  mo    " +
                        "diao  xiu   wu    tong  zhu   peng  an    lian  cong  xi    ping  qiu   jin   chun  jie   " +
                        "wei   tui   cao   yu    yi    ji    liao  bi    lu    xu    bu    zhang luo   qiang man   " +
                        "yan   leng  ji    biao  gun   han   di    su    lu    she   shang di    mie   xun   man   bo" +
                        "    di    cuo   zhe   sen   xuan  yu    hu    ao    mi    lou   cu    zhong cai   po    " +
                        "jiang mi    cong  niao  hui   jun   yin   jian  nian  shu   yin   kui   chen  hu    sha   " +
                        "kou   qian  ma    cang  ze    qiang dou   lian  lin   kou   ai    bi    li    wei   ji    " +
                        "qian  sheng fan   meng  ou    chan  dian  xun   jiao  rui   rui   lei   yu    qiao  chu   " +
                        "hua   jian  mai   yun   bao   you   qu    lu    rao   hui   e     ti    fei   jue   zui   fa" +
                        "    ru    fen   kui   shun  rui   ya    xu    fu    jue   dang  wu    tong  si    xiao  xi  " +
                        "  yong  wen   shao  qi    jian  yun   sun   ling  yu    xia   weng  ji    hong  si    deng  " +
                        "lei   xuan  yun   yu    xi    hao   bo    hao   ai    wei   hui   wei   ji    ci    xiang " +
                        "luan  mie   yi    leng  jiang can   shen  qiang lian  ke    yuan  da    ti    tang  xue   bi" +
                        "    zhan  sun   lian  fan   ding  xiao  gu    xie   shu   jian  kao   hong  sa    xin   xun " +
                        "  yao   bai   sou   shu   xun   dui   pin   wei   neng  chou  mai   ru    piao  tai   qi    " +
                        "zao   chen  zhen  er    ni    ying  gao   cong  xiao  qi    fa    jian  xu    kui   jie   " +
                        "bian  di    mi    lan   jin   zang  miao  qiong qie   xian  none  ou    xian  su    lu:   yi" +
                        "    xu    xie   li    yi    la    lei   jiao  di    zhi   pi    teng  yao   mo    huan  biao" +
                        "  fan   sou   tan   tui   qiong qiao  wei   liu   hui   shu   gao   yun   none  li    zhu   " +
                        "zhu   ai    lin   zao   xuan  chen  lai   huo   tuo   wu    rui   rui   qi    heng  lu    su" +
                        "    tui   mang  yun   pin   yu    xun   ji    jiong xuan  mo    none  su    jiong none  nie " +
                        "  bo    rang  yi    xian  yu    ju    lian  lian  yin   qiang ying  long  tou   wei   yue   " +
                        "ling  qu    yao   fan   mi    lan   kui   lan   ji    dang  none  lei   lei   tong  feng  " +
                        "zhi   wei   kui   zhan  huai  li    ji    mi    lei   huai  luo   ji    nao   lu    jian  " +
                        "none  none  lei   quan  xiao  yi    luan  men   bie   hu    hu    lu    nu:e  lu:   zhi   " +
                        "xiao  qian  chu   hu    xu    cuo   fu    xu    xu    lu    hu    yu    hao   jiao  ju    " +
                        "guo   bao   yan   zhan  zhan  kui   ban   xi    shu   chong qiu   diao  ji    qiu   ding  " +
                        "shi   none  di    zhe   she   yu    gan   zi    hong  hui   meng  ge    sui   xia   chai  " +
                        "shi   yi    ma    xiang fang  e     pa    chi   qian  wen   wen   rui   bang  pi    yue   " +
                        "yue   jun   qi    ran   yin   qi    can   yuan  jue   hui   qian  qi    zhong ya    hao   mu" +
                        "    wang  fen   fen   hang  gong  zao   fu    ran   jie   fu    chi   dou   bao   xian  ni  " +
                        "  te    qiu   you   zha   ping  chi   you   he    han   ju    li    fu    ran   zha   gou   " +
                        "pi    bo    xian  zhu   diao  bie   bing  gu    ran   qu    she   tie   ling  gu    dan   gu" +
                        "    ying  li    cheng qu    mou   ge    ci    hui   hui   mang  fu    yang  wa    lie   zhu " +
                        "  yi    xian  kuo   jiao  li    yi    ping  ji    ha    she   yi    wang  mo    qiong qie   " +
                        "gui   gong  zhi   man   none  zhe   jia   nao   si    qi    xing  lie   qiu   shao  yong  " +
                        "jia   tui   che   bai   e     han   shu   xuan  feng  shen  zhen  fu    xian  zhe   wu    fu" +
                        "    li    lang  bi    chu   yuan  you   jie   dan   yan   ting  dian  tui   hui   wo    zhi " +
                        "  song  fei   ju    mi    qi    qi    yu    jun   la    meng  qiang si    xi    ")
                .append("lun   li    die   tiao  tao   kun   gan   han   yu    bang  fei   pi    wei   dun   yi    " +
                        "yuan  su    quan  qian  rui   ni    qing  wei   liang guo   wan   dong  e     ban   zhuo  " +
                        "wang  can   yang  ying  guo   chan  none  la    ke    ji    xie   ting  mai   xu    mian  yu" +
                        "    jie   shi   xuan  huang yan   bian  rou   wei   fu    yuan  mei   wei   fu    ruan  xie " +
                        "  you   you   mao   xia   ying  shi   chong tang  zhu   zong  ti    fu    yuan  kui   meng  " +
                        "la    du    hu    qiu   die   li    gua   yun   ju    nan   lou   chun  rong  ying  jiang " +
                        "tun   lang  pang  si    xi    xi    xi    yuan  weng  lian  sou   ban   rong  rong  ji    wu" +
                        "    xiu   han   qin   yi    bi    hua   tang  yi    du    nai   he    hu    xi    ma    ming" +
                        "  yi    wen   ying  teng  yu    cang  none  none  man   none  shang shi   cao   chi   di    " +
                        "ao    lu    wei   zhi   tang  chen  piao  qu    pi    yu    jian  luo   lou   qin   zhong " +
                        "yin   jiang shuai wen   jiao  wan   zhe   zhe   ma    ma    guo   liao  mao   xi    cong  li" +
                        "    man   xiao  none  zhang mang  xiang mo    zi    si    qiu   te    zhi   peng  peng  jiao" +
                        "  qu    bie   liao  pan   gui   xi    ji    zhuan huang fei   lao   jue   jue   hui   yin   " +
                        "chan  jiao  shan  rao   xiao  wu    chong xun   si    none  cheng dang  li    xie   shan  yi" +
                        "    jing  da    chan  qi    zi    xiang she   luo   qin   ying  chai  li    ze    xuan  lian" +
                        "  zhu   ze    xie   mang  xie   qi    rong  jian  meng  hao   ru    huo   zhuo  jie   bin   " +
                        "he    mie   fan   lei   jie   la    mi    li    chun  li    qiu   nie   lu    du    xiao  " +
                        "zhu   long  li    long  feng  ye    pi    rang  gu    juan  ying  none  xi    can   qu    " +
                        "quan  du    can   man   qu    jie   zhu   zha   xue   huang nu:   pei   nu:   xin   zhong mo" +
                        "    er    mie   mie   shi   xing  yan   kan   yuan  none  ling  xuan  shu   xian  tong  long" +
                        "  jie   xian  ya    hu    wei   dao   chong wei   dao   zhun  heng  qu    yi    yi    bu    " +
                        "gan   yu    biao  cha   yi    shan  chen  fu    gun   fen   shuai jie   na    zhong dan   ri" +
                        "    zhong zhong xie   qi    xie   ran   zhi   ren   qin   jin   jun   yuan  mei   chai  ao  " +
                        "  niao  hui   ran   jia   tuo   ling  dai   bao   pao   yao   zuo   bi    shao  tan   ju    " +
                        "he    xue   xiu   zhen  yi    pa    bo    di    wa    fu    gun   zhi   zhi   ran   pan   yi" +
                        "    mao   none  na    kou   xuan  chan  qu    bei   yu    xi    none  bo    none  fu    yi  " +
                        "  chi   ku    ren   jiang jia   cun   mo    jie   er    ge    ru    zhu   gui   yin   cai   " +
                        "lie   none  none  zhuangdang  none  kun   ken   niao  shu   jia   kun   cheng li    juan  " +
                        "shen  pou   ge    yi    yu    chen  liu   qiu   qun   ji    yi    bu    zhuangshui  sha   " +
                        "qun   li    lian  lian  ku    jian  fou   tan   bi    gun   tao   yuan  ling  chi   chang " +
                        "chou  duo   biao  liang shang pei   pei   fei   yuan  luo   guo   yan   du    ti    zhi   ju" +
                        "    qi    ji    zhi   gua   ken   none  ti    shi   fu    chong xie   bian  die   kun   duan" +
                        "  xiu   xiu   he    yuan  bao   bao   fu    yu    tuan  yan   hui   bei   chu   lu:   none  " +
                        "none  yun   ta    gou   da    huai  rong  yuan  ru    nai   jiong suo   ban   tun   chi   " +
                        "sang  niao  ying  jie   qian  huai  ku    lian  lan   li    zhe   shi   lu:   yi    die   " +
                        "xie   xian  wei   biao  cao   ji    qiang sen   bao   xiang none  pu    jian  zhuan jian  " +
                        "zui   ji    dan   za    fan   bo    xiang xin   bie   rao   man   lan   ao    duo   hui   " +
                        "cao   sui   nong  chan  lian  bi    jin   dang  shu   tan   bi    lan   pu    ru    zhi   " +
                        "none  shu   wa    shi   bai   xie   bo    chen  lai   long  xi    xian  lan   zhe   dai   " +
                        "none  zan   shi   jian  pan   yi    none  ya    xi    xi    yao   feng  tan   none  none  fu" +
                        "    ba    he    ji    ji    jian  guan  bian  yan   gui   jue   pian  mao   mi    mi    mie " +
                        "  shi   si    zhan  luo   jue   mo    tiao  lian  yao   zhi   jun   xi    shan  wei   xi    " +
                        "tian  yu    lan   e     du    qin   pang  ji    ming  ping  gou   qu    zhan  jin   guan  " +
                        "deng  jian  luo   qu    jian  wei   jue   qu    luo   lan   shen  di    guan  jian  guan  " +
                        "yan   gui   mi    shi   chan  lan   jue   ji    xi    di    tian  yu    gou   jin   qu    " +
                        "jiao  jiu   jin   cu    jue   zhi   chao  ji    gu    dan   zui   di    shan")
                .append("g hua   quan  ge    zhi   jie   gui   gong  chu   jie   huan  qiu   xing  su    ni    ji    " +
                        "lu    zhi   zhu   bi    xing  hu    shang gong  zhi   xue   chu   xi    yi    li    jue   xi" +
                        "    yan   xi    yan   yan   ding  fu    qiu   qiu   jiao  hong  ji    fan   xun   diao  hong" +
                        "  cha   tao   xu    jie   yi    ren   xun   yin   shan  qi    tuo   ji    xun   yin   e     " +
                        "fen   ya    yao   song  shen  yin   xin   jue   xiao  ne    chen  you   zhi   xiong fang  " +
                        "xin   chao  she   xian  sa    zhun  xu    yi    yi    su    chi   he    shen  he    xu    " +
                        "zhen  zhu   zheng gou   zi    zi    zhan  gu    fu    jian  die   ling  di    yang  li    " +
                        "nao   pan   zhou  gan   shi   ju    ao    zha   tuo   yi    qu    zhao  ping  bi    xiong " +
                        "chu   ba    da    zu    tao   zhu   ci    zhe   yong  xu    xun   yi    huang he    shi   " +
                        "cha   jiao  shi   hen   cha   gou   gui   quan  hui   jie   hua   gai   xiang hui   shen  " +
                        "chou  tong  mi    zhan  ming  e     hui   yan   xiong gua   er    beng  tiao  chi   lei   " +
                        "zhu   kuang kua   wu    yu    teng  ji    zhi   ren   su    lang  e     kuang e^    shi   " +
                        "ting  dan   bei   chan  you   heng  qiao  qin   shua  an    yu    xiao  cheng jie   xian  wu" +
                        "    wu    gao   song  pu    hui   jing  shuo  zhen  shuo  du    none  chang shui  jie   ke  " +
                        "  qu    cong  xiao  sui   wang  xuan  fei   chi   ta    yi    na    yin   diao  pi    chuo  " +
                        "chan  chen  zhun  ji    qi    tan   chui  wei   ju    qing  jian  zheng ze    zou   qian  " +
                        "zhuo  liang jian  zhu   hao   lun   shen  biao  huai  pian  yu    die   xu    pian  shi   " +
                        "xuan  shi   hun   hua   e     zhong di    xie   fu    pu    ting  jian  qi    yu    zi    " +
                        "chuan xi    hui   yin   an    xian  nan   chen  feng  zhu   yang  yan   heng  xuan  ge    " +
                        "nuo   qi    mou   ye    wei   none  teng  zou   shan  jian  bo    none  huang huo   ge    " +
                        "ying  mi    xiao  mi    xi    qiang chen  nu:e  si    su    bang  chi   qian  shi   jiang " +
                        "yuan  xie   xue   tao   yao   yao   hu    yu    biao  cong  qing  li    mo    mo    shang " +
                        "zhe   miu   jian  ze    zha   lian  lou   can   ou    guan  xi    zhuo  ao    ao    jin   " +
                        "zhe   yi    hu    jiang man   chao  han   hua   chan  xu    zeng  se    xi    she   dui   " +
                        "zheng nao   lan   e     ying  jue   ji    zun   jiao  bo    hui   zhuan wu    jian  zha   " +
                        "shi   qiao  tan   zen   pu    sheng xuan  zao   zhan  dang  sui   qian  ji    jiao  jing  " +
                        "lian  nou   yi    ai    zhan  pi    hui   hua   yi    yi    shan  rang  nou   qian  zhui  ta" +
                        "    hu    zhou  hao   ni    ying  jian  yu    jian  hui   du    zhe   xuan  zan   lei   shen" +
                        "  wei   chan  li    yi    bian  zhe   yan   e     chou  wei   chou  yao   chan  rang  yin   " +
                        "lan   chen  huo   zhe   huan  zan   yi    dang  zhan  yan   du    yan   ji    ding  fu    " +
                        "ren   ji    jie   hong  tao   rang  shan  qi    tuo   xun   yi    xun   ji    ren   jiang " +
                        "hui   ou    ju    ya    ne    xu    e     lun   xiong song  feng  she   fang  jue   zheng gu" +
                        "    he    ping  zu    shi   xiong zha   su    zhen  di    zhou  ci    qu    zhao  bi    yi  " +
                        "  yi    kuang lei   shi   gua   shi   jie   hui   cheng zhu   shen  hua   dan   gou   quan  " +
                        "gui   xun   yi    zheng gai   xiang cha   hun   xu    zhou  jie   wu    yu    qiao  wu    " +
                        "gao   you   hui   kuang shuo  song  ei    qing  zhu   zou   nuo   du    zhuo  fei   ke    " +
                        "wei   yu    shei  shen  diao  chan  liang zhun  sui   tan   shen  yi    mou   chen  die   " +
                        "huang jian  xie   xue   ye    wei   e     yu    xuan  chan  zi    an    yan   di    mi    " +
                        "pian  xu    mo    dang  su    xie   yao   bang  shi   qian  mi    jin   man   zhe   jian  " +
                        "miu   tan   jian  qiao  lan   pu    jue   yan   qian  zhan  chen  gu    qian  hong  ya    " +
                        "jue   hong  han   hong  qi    xi    huo   liao  han   du    long  dou   jiang qi    chi   " +
                        "feng  deng  wan   bi    shu   xian  feng  zhi   zhi   yan   yan   shi   chu   hui   tun   yi" +
                        "    tun   yi    jian  ba    hou   e     cu    xiang huan  jian  ken   gai   qu    fu    xi  " +
                        "  bin   hao   yu    zhu   jia   fen   xi    hu    wen   huan  bin   di    zong  fen   yi    " +
                        "zhi   bao   chai  han   pi    na    pi    gou   duo   you   diao  mo    si    xiu   huan  " +
                        "kun   he    he    mo    an    mao   li    ni    bi    yu    jia   tuan  mao   pi    xi    e " +
                        "    ju")
                .append("    mo    chu   tan   huan  qu    bei   zhen  yuan  fu    cai   gong  te    yi    hang  wan " +
                        "  pin   huo   fan   tan   guan  ze    zhi   er    zhu   shi   bi    zi    er    gui   pian  " +
                        "bian  mai   dai   sheng kuang fei   tie   yi    chi   mao   he    bi    lu    lin   hui   " +
                        "gai   pian  zi    jia   xu    zei   jiao  gai   zang  jian  ying  xun   zhen  she   bin   " +
                        "bin   qiu   she   chuan zang  zhou  lai   zan   si    chen  shang tian  pei   geng  xian  " +
                        "mai   jian  sui   fu    dan   cong  cong  zhi   ji    zhang du    jin   xiong shun  yun   " +
                        "bao   zai   lai   feng  cang  ji    sheng ai    zhuan fu    gou   sai   ze    liao  wei   " +
                        "bai   chen  zhuan zhi   zhui  biao  yun   zeng  tan   zan   yan   none  shan  wan   ying  " +
                        "jin   gan   xian  zang  bi    du    shu   yan   none  xuan  long  gan   zang  bei   zhen  fu" +
                        "    yuan  gong  cai   ze    xian  bai   zhang huo   zhi   fan   tan   pin   bian  gou   zhu " +
                        "  guan  er    jian  bi    shi   tie   gui   kuang dai   mao   fei   he    yi    zei   zhi   " +
                        "jia   hui   zi    lin   lu    zang  zi    gai   jin   qiu   zhen  lai   she   fu    du    ji" +
                        "    shu   shang ci    bi    zhou  geng  pei   dan   lai   feng  zhui  fu    zhuan sai   ze  " +
                        "  yan   zan   yun   zeng  shan  ying  gan   chi   xi    she   nan   xiong xi    cheng he    " +
                        "cheng zhe   xia   tang  zou   zou   li    jiu   fu    zhao  gan   qi    shan  qiong qin   " +
                        "xian  ci    jue   qin   chi   ci    chen  chen  die   ju    chao  di    se    zhan  zhu   " +
                        "yue   qu    jie   chi   chu   gua   xue   zi    tiao  duo   lie   gan   suo   cu    xi    " +
                        "zhao  su    yin   ju    jian  que   tang  chuo  cui   lu    qu    dang  qiu   zi    ti    qu" +
                        "    chi   huang qiao  qiao  yao   zao   yue   none  zan   zan   zu    pa    bao   ku    he  " +
                        "  dun   jue   fu    chen  jian  fang  zhi   ta    yue   pa    qi    yue   qiang tuo   tai   " +
                        "yi    nian  ling  mei   ba    die   ku    tuo   jia   ci    pao   qia   zhu   ju    die   " +
                        "zhi   fu    pan   ju    shan  bo    ni    ju    li    gen   yi    ji    dai   xian  jiao  " +
                        "duo   chu   quan  kua   zhuai gui   qiong kui   xiang chi   lu    beng  zhi   jia   tiao  " +
                        "cai   jian  da    qiao  bi    xian  duo   ji    ju    ji    shu   tu    chu   xing  nie   " +
                        "xiao  bo    xue   qun   mou   shu   liang yong  jiao  chou  xiao  none  ta    jian  qi    wo" +
                        "    wei   chuo  jie   ji    nie   ju    ju    lun   lu    leng  huai  ju    chi   wan   quan" +
                        "  ti    bo    zu    qie   qi    cu    zong  cai   zong  pan   zhi   zheng dian  zhi   yu    " +
                        "duo   dun   chun  yong  zhong di    zha   chen  chuai jian  gua   tang  ju    fu    zu    " +
                        "die   pian  rou   nuo   ti    cha   tui   jian  dao   cuo   xi    ta    qiang zhan  dian  ti" +
                        "    ji    nie   pan   liu   zhan  bi    chong lu    liao  cu    tang  dai   su    xi    kui " +
                        "  ji    zhi   qiang di    man   zong  lian  beng  zao   nian  bie   tui   ju    deng  ceng  " +
                        "xian  fan   chu   zhong dun   bo    cu    zu    jue   jue   lin   ta    qiao  qiao  pu    " +
                        "liao  dun   cuan  kuang zao   ta    bi    bi    zhu   ju    chu   qiao  dun   chou  ji    wu" +
                        "    yue   nian  lin   lie   zhi   li    zhi   chan  chu   duan  wei   long  lin   xian  wei " +
                        "  zuan  lan   xie   rang  xie   nie   ta    qu    jie   cuan  zuan  xi    kui   jue   lin   " +
                        "shen  gong  dan   none  qu    ti    duo   duo   gong  lang  none  luo   ai    ji    ju    " +
                        "tang  none  none  yan   none  kang  qu    lou   lao   duo   zhi   none  ti    dao   none  yu" +
                        "    che   ya    gui   jun   wei   yue   xin   di    xuan  fan   ren   shan  qiang shu   tun " +
                        "  chen  dai   e     na    qi    mao   ruan  ren   qian  zhuan hong  hu    qu    huang di    " +
                        "ling  dai   ao    zhen  fan   kuang ang   peng  bei   gu    gu    pao   zhu   rong  e     ba" +
                        "    zhou  zhi   yao   ke    yi    qing  shi   ping  er    qiong ju    jiao  guang lu    kai " +
                        "  quan  zhou  zai   zhi   ju    liang yu    shao  you   huan  yun   zhe   wan   fu    qing  " +
                        "zhou  ni    ling  zhe   zhan  liang zi    hui   wang  chuo  guo   kan   yi    peng  qian  " +
                        "gun   nian  ping  guan  bei   lun   pai   liang ruan  rou   ji    yang  xian  chuan cou   " +
                        "chun  ge    you   hong  shu   fu    zi    fu    wen   ben   zhan  yu    wen   tao   gu    " +
                        "zhen  xia   yuan  lu    jiu   chao  zhuan wei   hun   none  che   jiao  zhan  ")
                .append("pu    lao   fen   fan   lin   ge    se    kan   huan  yi    ji    dui   er    yu    xian  " +
                        "hong  lei   pei   li    li    lu    lin   che   ya    gui   xuan  dai   ren   zhuan e     " +
                        "lun   ruan  hong  gu    ke    lu    zhou  zhi   yi    hu    zhen  li    yao   qing  shi   " +
                        "zai   zhi   jiao  zhou  quan  lu    jiao  zhe   fu    liang nian  bei   hui   gun   wang  " +
                        "liang chuo  zi    cou   fu    ji    wen   shu   pei   yuan  xia   zhan  lu    zhe   lin   " +
                        "xin   gu    ci    ci    pi    zui   bian  la    la    ci    xue   ban   bian  bian  bian  " +
                        "none  bian  ban   ci    bian  bian  chen  ru    nong  nong  zhen  chuo  chuo  none  reng  " +
                        "bian  bian  none  none  liao  da    chan  gan   qian  yu    yu    qi    xun   yi    guo   " +
                        "mai   qi    za    wang  none  zhun  ying  ti    yun   jin   hang  ya    fan   wu    ta    e " +
                        "    hai   zhei  none  jin   yuan  wei   lian  chi   che   ni    tiao  zhi   yi    jiong jia " +
                        "  chen  dai   er    di    po    wang  die   ze    tao   shu   tuo   none  jing  hui   tong  " +
                        "you   mi    beng  ji    nai   yi    jie   zhui  lie   xun   tui   song  shi   tao   pang  " +
                        "hou   ni    dun   jiong xuan  xun   bu    you   xiao  qiu   tou   zhu   qiu   di    di    tu" +
                        "    jing  ti    dou   yi    zhe   tong  guang wu    shi   cheng su    zao   qun   feng  lian" +
                        "  suo   hui   li    none  zui   ben   cuo   jue   beng  huan  dai   lu    you   zhou  jin   " +
                        "yu    chuo  kui   wei   ti    yi    da    yuan  luo   bi    nuo   yu    dang  sui   dun   " +
                        "sui   yan   chuan chi   ti    yu    shi   zhen  you   yun   e     bian  guo   e     xia   " +
                        "huang qiu   dao   da    wei   none  yi    gou   yao   chu   liu   xun   ta    di    chi   " +
                        "yuan  su    ta    qian  none  yao   guan  zhang ao    shi   ce    su    su    zao   zhe   " +
                        "dun   zhi   lou   chi   cuo   lin   zun   rao   qian  xuan  yu    yi    wu    liao  ju    " +
                        "shi   bi    yao   mai   xie   sui   huan  zhan  deng  er    miao  bian  bian  la    li    " +
                        "yuan  you   luo   li    yi    ting  deng  qi    yong  shan  han   yu    mang  ru    qiong " +
                        "none  kuang fu    kang  bin   fang  xing  nei   none  shen  bang  yuan  cun   huo   xie   " +
                        "bang  wu    ju    you   han   tai   qiu   bi    pi    bing  shao  bei   wa    di    zou   ye" +
                        "    lin   kuang gui   zhu   shi   ku    yu    gai   he    qie   zhi   ji    xun   hou   xing" +
                        "  jiao  xi    gui   nuo   lang  jia   kuai  zheng lang  yun   yan   cheng dou   xi    lu:   " +
                        "fu    wu    fu    gao   hao   lang  jia   geng  jun   ying  bo    xi    bei   li    yun   bu" +
                        "    xiao  qi    pi    qing  guo   none  tan   zou   ping  lai   ni    chen  you   bu    " +
                        "xiang dan   ju    yong  qiao  yi    dou   yan   mei   ruo   bei   e     yu    juan  yu    " +
                        "yun   hou   kui   xiang xiang sou   tang  ming  xi    ru    chu   zi    zou   ju    wu    " +
                        "xiang yun   hao   yong  bi    mao   chao  fu    liao  yin   zhuan hu    qiao  yan   zhang " +
                        "fan   wu    xu    deng  bi    xin   bi    ceng  wei   zheng mao   shan  lin   po    dan   " +
                        "meng  ye    cao   kuai  feng  meng  zou   kuang lian  zan   chan  you   qi    yan   chan  " +
                        "cuo   ling  huan  xi    feng  zan   li    you   ding  qiu   zhuo  pei   zhou  yi    gan   yu" +
                        "    jiu   yan   zui   mao   dan   xu    tou   zhen  fen   none  none  yun   tai   tian  qia " +
                        "  tuo   zuo   han   gu    su    fa    chou  dai   ming  lao   chuo  chou  you   tong  zhi   " +
                        "xian  jiang cheng yin   tu    jiao  mei   ku    suan  lei   pu    zui   hai   yan   shi   " +
                        "niang wei   lu    lan   yan   tao   pei   zhan  chun  tan   zui   chuo  cu    kun   ti    " +
                        "xian  du    hu    xu    xing  tan   qiu   chun  yun   fa    ke    sou   mi    quan  chou  " +
                        "cuo   yun   yong  ang   zha   hai   tang  jiang piao  lao   yu    li    zao   lao   yi    " +
                        "jiang bu    jiao  xi    tan   fa    nong  yi    li    ju    yan   yi    niang ru    xun   " +
                        "chou  yan   ling  mi    mi    niang xin   jiao  shi   mi    yan   bian  cai   shi   you   " +
                        "shi   shi   li    zhong ye    liang li    jin   jin   ga    yi    liao  dao   zhao  ding  li" +
                        "    qiu   he    fu    zhen  zhi   ba    luan  fu    nai   diao  shan  qiao  kou   chuan zi  " +
                        "  fan   yu    hua   han   gong  qi    mang  jian  di    si    xi    yi    chai  ta    tu    " +
                        "xi    nu:   qian  none  jian  pi    ye    yin   ba    fang  chen  jian  tou   yue   yan   fu" +
                        "    bu  ")
                .append("  na    xin   e     jue   dun   gou   yin   qian  ban   ji    ren   chao  niu   fen   yun   " +
                        "yi    qin   pi    guo   hong  yin   jun   shi   yi    zhong nie   gai   ri    huo   tai   " +
                        "kang  none  lu    none  none  duo   zi    ni    tu    shi   min   gu    ke    ling  bing  yi" +
                        "    gu    ba    pi    yu    si    zuo   bu    you   dian  jia   zhen  shi   shi   tie   ju  " +
                        "  zhan  ta    she   xuan  zhao  bao   he    bi    sheng chu   shi   bo    zhu   chi   za    " +
                        "po    tong  qian  fu    zhai  liu   qian  fu    li    yue   pi    yang  ban   bo    jie   " +
                        "gou   shu   zheng mu    ni    xi    di    jia   mu    tan   shen  yi    si    kuang ka    " +
                        "bei   jian  tong  xing  hong  jiao  chi   er    ge    bing  shi   mou   jia   yin   jun   " +
                        "zhou  chong shang tong  mo    lei   ji    yu    xu    ren   cun   zhi   qiong shan  chi   " +
                        "xian  xing  quan  pi    yi    zhu   hou   ming  kua   yao   xian  xian  xiu   jun   cha   " +
                        "lao   ji    yong  ru    mi    yi    yin   guang an    diu   you   se    kao   qian  luan  " +
                        "none  ai    diao  han   rui   shi   keng  qiu   xiao  zhe   xiu   zang  ti    cuo   gua   " +
                        "gong  zhong dou   lu:   mei   lang  wan   xin   yun   bei   wu    su    yu    chan  ting  bo" +
                        "    han   jia   hong  cuan  feng  chan  wan   zhi   si    xuan  wu    wu    tiao  gong  zhuo" +
                        "  lu:e  xing  qin   shen  han   none  ye    chu   zeng  ju    xian  e     mang  pu    li    " +
                        "shi   rui   cheng gao   li    te    none  zhu   none  tu    liu   zui   ju    chang yuan  " +
                        "jian  gang  diao  tao   chang lun   guo   ling  bei   lu    li    qing  pei   juan  min   " +
                        "zui   peng  an    pi    xian  ya    zhui  lei   a     kong  ta    kun   du    wei   chui  zi" +
                        "    zheng ben   nie   cong  chun  tan   ding  qi    qian  zhuo  qi    yu    jin   guan  mao " +
                        "  chang dian  xi    lian  tao   gu    cuo   shu   zhen  lu    meng  lu    hua   biao  ga    " +
                        "lai   ken   zhui  none  nai   wan   zan   none  de    xian  none  huo   liang none  men   " +
                        "kai   ying  di    lian  guo   xian  du    tu    wei   cong  fu    rou   ji    e     rou   " +
                        "chen  ti    zha   hong  yang  duan  xia   yu    keng  xing  huang wei   fu    zhao  cha   " +
                        "qie   she   hong  kui   nuo   mou   qiao  qiao  hou   zhen  huo   huan  ye    min   jian  " +
                        "duan  jian  si    kui   hu    xuan  zang  jie   zhen  bian  zhong zi    xiu   ye    mei   " +
                        "pai   ai    jie   none  mei   cha   ta    bang  xia   lian  suo   xi    liu   zu    ye    " +
                        "nou   weng  rong  tang  suo   qiang ge    shuo  chui  bo    pan   ta    bi    sang  gang  zi" +
                        "    wu    ying  huang tiao  liu   kai   sun   sha   sou   wan   hao   zhen  zhen  luo   yi  " +
                        "  yuan  tang  nie   xi    jia   ge    ma    juan  rong  none  suo   none  none  none  na    " +
                        "lu    suo   kou   zu    tuan  xiu   guan  xuan  lian  shou  ao    man   mo    luo   bi    " +
                        "wei   liu   di    qiao  huo   yin   lu    ao    keng  qiang cui   qi    chang tang  man   " +
                        "yong  chan  feng  jing  biao  shu   lou   xiu   cong  long  zan   jian  cao   li    xia   xi" +
                        "    kang  none  beng  none  none  zheng lu    hua   ji    pu    hui   qiang po    lin   suo " +
                        "  xiu   san   cheng kui   san   liu   nao   huang pie   sui   fan   qiao  chuan yang  tang  " +
                        "xiang jue   jiao  zun   liao  jie   lao   dui   tan   zan   ji    jian  zhong deng  lou   " +
                        "ying  dui   jue   nou   ti    pu    tie   none  none  ding  shan  kai   jian  fei   sui   lu" +
                        "    juan  hui   yu    lian  zhuo  qiao  qian  zhuo  lei   bi    tie   huan  ye    duo   guo " +
                        "  dang  ju    fen   da    bei   yi    ai    dang  xun   diao  zhu   heng  zhui  ji    nie   " +
                        "ta    huo   qing  bin   ying  kui   ning  xu    jian  jian  qiang cha   zhi   mie   li    " +
                        "lei   ji    zuan  kuang shang peng  la    du    shuo  chuo  lu:   biao  bao   lu    none  " +
                        "none  long  e     lu    xin   jian  lan   bo    jian  yao   chan  xiang jian  xi    guan  " +
                        "cang  nie   lei   cuan  qu    pan   luo   zuan  luan  zao   nie   jue   tang  shu   lan   " +
                        "jin   ga    yi    zhen  ding  zhao  po    liao  tu    qian  chuan shan  sa    fan   diao  " +
                        "men   nu:   yang  chai  xing  gai   bu    tai   ju    dun   chao  zhong na    bei   gang  " +
                        "ban   qian  yao   qin   jun   wu    gou   kang  fang  huo   tou   niu   ba    yu    qian  " +
                        "zheng qian  gu    bo    ke    po    bu    bo    yue   zuan  mu    tan   jia   dian  you   ti")
                .append("e   bo    ling  shuo  qian  mao   bao   shi   xuan  tuo   bi    ni    pi    duo   xing  kao " +
                        "  lao   er    mang  ya    you   cheng jia   ye    nao   zhi   dang  tong  lu:   diao  yin   " +
                        "kai   zha   zhu   xian  ting  diu   xian  hua   quan  sha   ha    yao   ge    ming  zheng se" +
                        "    jiao  yi    chan  chong tang  an    yin   ru    zhu   lao   pu    wu    lai   te    lian" +
                        "  keng  xiao  suo   li    zeng  chu   guo   gao   e     xiu   cuo   lu:e  feng  xin   liu   " +
                        "kai   jian  rui   ti    lang  qin   ju    a     qing  zhe   nuo   cuo   mao   ben   qi    de" +
                        "    ke    kun   chang xi    gu    luo   chui  zhui  jin   zhi   xian  juan  huo   pei   tan " +
                        "  ding  jian  ju    meng  zi    qie   ying  kai   qiang si    e     cha   qiao  zhong duan  " +
                        "sou   huang huan  ai    du    mei   lou   zi    fei   mei   mo    zhen  bo    ge    nie   " +
                        "tang  juan  nie   na    liu   hao   bang  yi    jia   bin   rong  biao  tang  man   luo   " +
                        "beng  yong  jing  di    zu    xuan  liu   chan  jue   liao  pu    lu    dun   lan   pu    " +
                        "cuan  qiang deng  huo   lei   huan  zhuo  lian  yi    cha   biao  la    chan  xiang chang " +
                        "chang jiu   ao    die   qu    liao  mi    zhang men   ma    shuan shan  huo   men   yan   bi" +
                        "    han   bi    none  kai   kang  beng  hong  run   san   xian  xian  jian  min   xia   min " +
                        "  dou   zha   nao   none  peng  ke    ling  bian  bi    run   he    guan  ge    he    fa    " +
                        "chu   hong  gui   min   none  kun   lang  lu:   ting  sha   yan   yue   yue   chan  qu    " +
                        "lin   chang shai  kun   yan   min   yan   e     hun   yu    wen   xiang none  xiang qu    " +
                        "yao   wen   ban   an    wei   yin   kuo   que   lan   du    none  none  tian  nie   da    " +
                        "kai   he    que   chuangguan  dou   qi    kui   tang  guan  piao  kan   xi    hui   chan  pi" +
                        "    dang  huan  ta    wen   none  men   shuan shan  yan   han   bi    wen   chuangrun   wei " +
                        "  xian  hong  jian  min   kang  men   zha   nao   gui   wen   ta    min   lu:   kai   fa    " +
                        "ge    he    kun   jiu   yue   lang  du    yu    yan   chang xi    wen   hun   yan   yan   " +
                        "chan  lan   qu    hui   kuo   que   he    tian  da    que   kan   huan  fu    fu    le    " +
                        "dui   xin   qian  wu    yi    tuo   yin   yang  dou   e     sheng ban   pei   keng  yun   " +
                        "ruan  zhi   pi    jing  fang  yang  yin   zhen  jie   cheng e     qu    di    zu    zuo   " +
                        "dian  ling  a     tuo   tuo   po    bing  fu    ji    lu    long  chen  xing  duo   lou   mo" +
                        "    jiang shu   duo   xian  er    gui   wu    gai   shan  jun   qiao  xing  chun  fu    bi  " +
                        "  shan  shan  sheng zhi   pu    dou   yuan  zhen  chu   xian  zhi   nie   yun   xian  pei   " +
                        "pei   zou   yi    dui   lun   yin   ju    chui  chen  pi    ling  tao   xian  lu    none  " +
                        "xian  yin   zhu   yang  reng  shan  chong yan   yin   yu    ti    yu    long  wei   wei   " +
                        "nie   dui   sui   an    huang jie   sui   yin   gai   yan   hui   ge    yun   wu    wei   ai" +
                        "    xi    tang  ji    zhang dao   ao    xi    yin   sa    rao   lin   tui   deng  pi    sui " +
                        "  sui   yu    xian  fen   ni    er    ji    dao   xi    yin   zhi   hui   long  xi    li    " +
                        "li    li    zhui  he    zhi   sun   juan  nan   yi    que   yan   qin   ya    xiong ya    ji" +
                        "    gu    huan  zhi   gou   jun   ci    yong  ju    chu   hu    za    luo   yu    chou  diao" +
                        "  sui   han   huo   shuangguan  chu   za    yong  ji    sui   chou  liu   li    nan   xue   " +
                        "za    ji    ji    yu    yu    xue   na    fou   se    mu    wen   fen   pang  yun   li    li" +
                        "    yang  ling  lei   an    bao   meng  dian  dang  hang  wu    zhao  xu    ji    mu    chen" +
                        "  xiao  zha   ting  zhen  pei   mei   ling  qi    chou  huo   sha   fei   weng  zhan  ying  " +
                        "ni    chou  tun   lin   none  dong  ying  wu    ling  shuangling  xia   hong  yin   mai   mo" +
                        "    yun   liu   meng  bin   wu    wei   kuo   yin   xi    yi    ai    dan   deng  xian  yu  " +
                        "  lu    long  dai   ji    pang  yang  ba    pi    wei   none  xi    ji    mai   meng  meng  " +
                        "lei   li    huo   ai    fei   dai   long  ling  ai    feng  li    bao   none  he    he    " +
                        "bing  qing  qing  jing  qi    zhen  jing  cheng qing  jing  jing  dian  jing  tian  fei   " +
                        "fei   kao   mi    mian  mian  pao   ye    tian  hui   ye    ge    ding  ren   jian  ren   di" +
                        "    du    wu    ren   qin   jin   xue   niu   ba    yin   sa    ren   ")
                .append("mo    zu    da    ban   yi    yao   tao   bei   jia   hong  pao   yang  mo    yin   jia   " +
                        "tao   ji    xie   an    an    hen   gong  gong  da    qiao  ting  man   ying  sui   tiao  " +
                        "qiao  xuan  kong  beng  ta    zhang bing  kuo   ju    la    xie   rou   bang  yi    qiu   " +
                        "qiu   he    xiao  mu    ju    jian  bian  di    jian  none  tao   gou   ta    bei   xie   " +
                        "pan   ge    bi    kuo   tang  lou   gui   qiao  xue   ji    jian  jiang chan  da    huo   " +
                        "xian  qian  du    wa    jian  lan   wei   ren   fu    mei   juan  ge    wei   qiao  han   " +
                        "chang none  rou   xun   she   wei   ge    bei   tao   gou   yun   gao   bi    wei   hui   " +
                        "shu   wa    du    wei   ren   fu    han   wei   yun   tao   jiu   jiu   xian  xie   xian  ji" +
                        "    yin   za    yun   shao  luo   peng  huang ying  yun   peng  yin   yin   xiang hu    ye  " +
                        "  ding  qing  pan   xiang shun  han   xu    yi    xu    gu    song  kui   qi    hang  yu    " +
                        "wan   ban   dun   di    dan   pan   po    ling  cheng jing  lei   he    qiao  e     e     " +
                        "wei   jie   gua   shen  yi    yi    ke    dui   pian  ping  lei   fu    jia   tou   hui   " +
                        "kui   jia   le    ting  cheng ying  jun   hu    han   jing  tui   tui   pin   lai   tui   zi" +
                        "    zi    chui  ding  lai   yan   han   qian  ke    cui   jiong qin   yi    sai   ti    e   " +
                        "  e     yan   hun   kan   yong  zhuan yan   xian  xin   yi    yuan  sang  dian  dian  jiang " +
                        "ku    lei   liao  piao  yi    man   qi    yao   hao   qiao  gu    xun   qian  hui   zhan  ru" +
                        "    hong  bin   xian  pin   lu    lan   nie   quan  ye    ding  qing  han   xiang shun  xu  " +
                        "  xu    wan   gu    dun   qi    ban   song  hang  yu    lu    ling  po    jing  jie   jia   " +
                        "ting  he    ying  jiong ke    yi    pin   hui   tui   han   ying  ying  ke    ti    yong  e " +
                        "    zhuan yan   e     nie   man   dian  sang  hao   lei   zhan  ru    pin   quan  feng  biao" +
                        "  none  fu    xia   zhan  biao  sa    fa    tai   lie   gua   xuan  shao  ju    biao  si    " +
                        "wei   yang  yao   sou   kai   sao   fan   liu   xi    liao  piao  piao  liu   biao  biao  " +
                        "biao  liao  none  se    feng  biao  feng  yang  zhan  biao  sa    ju    si    sou   yao   " +
                        "liu   piao  biao  biao  fei   fan   fei   fei   shi   shi   can   ji    ding  si    tuo   " +
                        "jian  sun   xiang tun   ren   yu    juan  chi   yin   fan   fan   sun   yin   zhu   yi    " +
                        "zhai  bi    jie   tao   liu   ci    tie   si    bao   shi   duo   hai   ren   tian  jiao  " +
                        "jia   bing  yao   tong  ci    xiang yang  yang  er    yan   le    yi    can   bo    nei   e " +
                        "    bu    jun   dou   su    yu    shi   yao   hun   guo   shi   jian  zhui  bing  xian  bu  " +
                        "  ye    tan   fei   zhang wei   guan  e     nuan  hun   hu    huang tie   hui   jian  hou   " +
                        "he    xing  fen   wei   gu    cha   song  tang  bo    gao   xi    kui   liu   sou   tao   ye" +
                        "    yun   mo    tang  man   bi    yu    xiu   jin   san   kui   zhuan shan  chi   dan   yi  " +
                        "  ji    rao   cheng yong  tao   hui   xiang zhan  fen   hai   meng  yan   mo    chan  xiang " +
                        "luo   zuan  nang  shi   ding  ji    tuo   xing  tun   xi    ren   yu    chi   fan   yin   " +
                        "jian  shi   bao   si    duo   yi    er    rao   xiang he    le    jiao  xi    bing  bo    " +
                        "dou   e     yu    nei   jun   guo   hun   xian  guan  cha   kui   gu    sou   chan  ye    mo" +
                        "    bo    liu   xiu   jin   man   san   zhuan nang  shou  kui   guo   xiang fen   ba    ni  " +
                        "  bi    bo    tu    han   fei   jian  yan   ai    fu    xian  wen   xin   fen   bin   xing  " +
                        "ma    yu    feng  han   di    tuo   tuo   chi   xun   zhu   zhi   pei   xin   ri    sa    " +
                        "yin   wen   zhi   dan   lu:   you   bo    bao   kuai  tuo   yi    qu    wen   qu    jiong bo" +
                        "    zhao  yuan  peng  zhou  ju    zhu   nu    ju    pi    zang  jia   ling  zhen  tai   fu  " +
                        "  yang  shi   bi    tuo   tuo   si    liu   ma    pian  tao   zhi   rong  teng  dong  xun   " +
                        "quan  shen  jiong er    hai   bo    none  yin   luo   none  dan   xie   liu   ju    song  " +
                        "qin   mang  liang han   tu    xuan  tui   jun   e     cheng xing  ai    lu    zhui  zhou  " +
                        "she   pian  kun   tao   lai   zong  ke    qi    qi    yan   fei   sao   yan   jie   yao   wu" +
                        "    pian  cong  pian  qian  fei   huang jian  huo   yu    ti    quan  xia   zong  kui   rou " +
                        "  si    gua   tuo   kui   sou   qian  cheng zhi   liu   pang  teng  xi    cao ")
                .append("  du    yan   yuan  zou   sao   shan  li    zhi   shuanglu    xi    luo   zhang mo    ao    " +
                        "can   piao  cong  qu    bi    zhi   yu    xu    hua   bo    su    xiao  lin   zhan  dun   " +
                        "liu   tuo   zeng  tan   jiao  tie   yan   luo   zhan  jing  yi    ye    tuo   bin   zou   " +
                        "yan   peng  lu:   teng  xiang ji    shuangju    xi    huan  li    biao  ma    yu    tuo   " +
                        "xun   chi   qu    ri    bo    lu:   zang  shi   si    fu    ju    zou   zhu   tuo   nu    " +
                        "jia   yi    tai   xiao  ma    yin   jiao  hua   luo   hai   pian  biao  li    cheng yan   " +
                        "xing  qin   jun   qi    qi    ke    zhui  zong  su    can   pian  zhi   kui   sao   wu    ao" +
                        "    liu   qian  shan  piao  luo   cong  zhan  zhou  ji    shuangxiang gu    wei   wei   wei " +
                        "  yu    gan   yi    ang   tou   jie   bo    bi    ci    ti    di    ku    hai   qiao  hou   " +
                        "kua   ge    tui   geng  pian  bi    ke    qia   yu    sui   lou   bo    xiao  bang  bo    " +
                        "cuo   kuan  bin   mo    liao  lou   nao   du    zang  sui   ti    bin   kuan  lu    gao   " +
                        "gao   qiao  kao   qiao  lao   zao   biao  kun   kun   ti    fang  xiu   ran   mao   dan   " +
                        "kun   bin   fa    tiao  pi    zi    fa    ran   ti    pao   pi    mao   fu    er    rong  qu" +
                        "    none  xiu   gua   ji    peng  zhua  shao  sha   ti    li    bin   zong  ti    peng  song" +
                        "  zheng quan  zong  shun  jian  duo   hu    la    jiu   qi    lian  zhen  bin   peng  mo    " +
                        "san   man   man   seng  xu    lie   qian  qian  nong  huan  kuai  ning  bin   lie   rang  " +
                        "dou   dou   nao   hong  xi    dou   kan   dou   dou   jiu   chang yu    yu    li    juan  fu" +
                        "    qian  gui   zong  liu   gui   shang yu    gui   mei   ji    qi    jie   kui   hun   ba  " +
                        "  po    mei   xu    yan   xiao  liang yu    tui   qi    wang  liang wei   jian  chi   piao  " +
                        "bi    mo    ji    xu    chou  yan   zhan  yu    dao   ren   ji    ba    hong  tuo   diao  ji" +
                        "    yu    e     que   sha   hang  tun   mo    gai   shen  fan   yuan  pi    lu    wen   hu  " +
                        "  lu    za    fang  fen   na    you   none  none  he    xia   qu    han   pi    ling  tuo   " +
                        "ba    qiu   ping  fu    bi    ji    wei   ju    diao  ba    you   gun   pi    nian  xing  " +
                        "tai   bao   fu    zha   ju    gu    none  none  none  ta    jie   shua  hou   xiang er    an" +
                        "    wei   tiao  zhu   yin   lie   luo   tong  yi    qi    bing  wei   jiao  pu    gui   xian" +
                        "  ge    hui   none  none  kao   none  duo   jun   ti    mian  shao  za    suo   qin   yu    " +
                        "nei   zhe   gun   geng  none  wu    qiu   ting  fu    huan  chou  li    sha   sha   gao   " +
                        "meng  none  none  none  none  yong  ni    zi    qi    qing  xiang nei   chun  ji    diao  " +
                        "qie   gu    zhou  dong  lai   fei   ni    yi    kun   lu    jiu   chang jing  lun   ling  " +
                        "zou   li    meng  zong  zhi   nian  none  none  none  shi   sao   hun   ti    hou   xing  ju" +
                        "    la    zong  ji    bian  bian  huan  quan  ji    wei   wei   yu    chun  rou   die   " +
                        "huang lian  yan   qiu   qiu   jian  bi    e     yang  fu    sai   jian  ha    tuo   hu    " +
                        "none  ruo   none  wen   jian  hao   wu    pang  sao   liu   ma    shi   shi   guan  zi    " +
                        "teng  ta    yao   ge    rong  qian  qi    wen   ruo   none  lian  ao    le    hui   min   ji" +
                        "    tiao  qu    jian  sao   man   xi    qiu   biao  ji    ji    zhu   jiang qiu   zhuan yong" +
                        "  zhang kang  xue   bie   jue   qu    xiang bo    jiao  xun   su    huang zun   shan  shan  " +
                        "fan   gui   lin   xun   miao  xi    none  xiang fen   guan  hou   kuai  zei   sao   zhan  " +
                        "gan   gui   sheng li    chang none  none  ai    ru    ji    xu    huo   none  li    lie   li" +
                        "    mie   zhen  xiang e     lu    guan  li    xian  yu    dao   ji    you   tun   lu    fang" +
                        "  ba    ke    ba    ping  nian  lu    you   zha   fu    ba    bao   hou   pi    tai   gui   " +
                        "jie   kao   wei   er    tong  zei   hou   kuai  ji    jiao  xian  zha   xiang xun   geng  li" +
                        "    lian  jian  li    shi   tiao  gun   sha   huan  jun   ji    yong  qing  ling  qi    zou " +
                        "  fei   kun   chang gu    ni    nian  diao  jing  shen  shi   zi    fen   die   bi    chang " +
                        "ti    wen   wei   sai   e     qiu   fu    huang quan  jiang bian  sao   ao    qi    ta    " +
                        "guan  yao   pang  jian  le    biao  xue   bie   man   min   yong  wei   xi    gui   shan  " +
                        "lin   zun   hu    gan   li    shan  guan  niao  yi    fu    li    jiu   bu    ya")
                .append("n   fu    diao  ji    feng  none  gan   shi   feng  ming  bao   yuan  zhi   hu    qian  fu  " +
                        "  fen   wen   jian  shi   yu    fou   yiao  ju    jue   pi    huan  zhen  bao   yan   ya    " +
                        "zheng fang  feng  wen   ou    te    jia   nu    ling  mie   fu    tuo   wen   li    bian  " +
                        "zhi   ge    yuan  zi    qu    xiao  chi   dan   ju    you   gu    zhong yu    yang  rong  ya" +
                        "    zhi   yu    none  ying  zhui  wu    er    gua   ai    zhi   yan   heng  jiao  ji    lie " +
                        "  zhu   ren   ti    hong  luo   ru    mou   ge    ren   jiao  xiu   zhou  chi   luo   none  " +
                        "none  none  luan  jia   ji    yu    huan  tuo   bu    wu    juan  yu    bo    xun   xun   bi" +
                        "    xi    jun   ju    tu    jing  ti    e     e     kuang hu    wu    shen  la    none  none" +
                        "  lu    bing  shu   fu    an    zhao  peng  qin   qian  bei   diao  lu    que   jian  ju    " +
                        "tu    ya    yuan  qi    li    ye    zhui  kong  duo   kun   sheng qi    jing  ni    e     " +
                        "jing  zi    lai   dong  qi    chun  geng  ju    qu    none  none  ji    shu   none  chi   " +
                        "miao  rou   fu    qiu   ti    hu    ti    e     jie   mao   fu    chun  tu    yan   he    " +
                        "yuan  pian  yun   mei   hu    ying  dun   mu    ju    none  cang  fang  ge    ying  yuan  " +
                        "xuan  weng  shi   he    chu   tang  xia   ruo   liu   ji    gu    jian  zhun  han   zi    ci" +
                        "    yi    yao   yan   ji    li    tian  kou   ti    ti    ni    tu    ma    jiao  liu   zhen" +
                        "  chen  li    zhuan zhe   ao    yao   yi    ou    chi   zhi   liao  rong  lou   bi    " +
                        "shuangzhuo  yu    wu    jue   yin   tan   si    jiao  yi    hua   bi    ying  su    huang " +
                        "fan   jiao  liao  yan   kao   jiu   xian  xian  tu    mai   zun   yu    ying  lu    tuan  " +
                        "xian  xue   yi    pi    shu   luo   qi    yi    ji    zhe   yu    zhan  ye    yang  pi    " +
                        "ning  hu    mi    ying  meng  di    yue   yu    lei   bo    lu    he    long  shuangyue   " +
                        "ying  guan  qu    li    luan  niao  jiu   ji    yuan  ming  shi   ou    ya    cang  bao   " +
                        "zhen  gu    dong  lu    ya    xiao  yang  ling  chi   qu    yuan  xue   tuo   si    zhi   er" +
                        "    gua   xiu   heng  zhou  ge    luan  hong  wu    bo    li    juan  hu    e     yu    xian" +
                        "  ti    wu    que   miao  an    kun   bei   peng  qian  chun  geng  yuan  su    hu    he    " +
                        "e     gu    qiu   ci    mei   wu    yi    yao   weng  liu   ji    yi    jian  he    yi    " +
                        "ying  zhe   liu   liao  jiao  jiu   yu    lu    huan  zhan  ying  hu    meng  guan  shuanglu" +
                        "    jin   ling  jian  xian  cuo   jian  jian  yan   cuo   lu    you   cu    ji    biao  cu  " +
                        "  pao   zhu   jun   zhu   jian  mi    mi    wu    liu   chen  jun   lin   ni    qi    lu    " +
                        "jiu   jun   jing  li    xiang yan   jia   mi    li    she   zhang lin   jing  qi    ling  " +
                        "yan   cu    mai   mai   ge    chao  fu    mian  mian  fu    pao   qu    qu    mou   fu    " +
                        "xian  lai   qu    mian  chi   feng  fu    qu    mian  ma    ma    mo    hui   none  zou   " +
                        "nen   fen   huang huang jin   guang tian  tou   hong  xi    kuang hong  shu   li    nian  " +
                        "chi   hei   hei   yi    qian  zhen  xi    tuan  mo    mo    qian  dai   chu   you   dian  yi" +
                        "    xia   yan   qu    mei   yan   qing  yu    li    dang  du    can   yin   an    yan   tan " +
                        "  an    zhen  dai   can   yi    mei   dan   yan   du    lu    zhi   fen   fu    fu    min   " +
                        "min   yuan  cu    qu    chao  wa    zhu   zhi   mang  ao    bie   tuo   bi    yuan  chao  " +
                        "tuo   ding  mi    nai   ding  zi    gu    gu    dong  fen   tao   yuan  pi    chang gao   qi" +
                        "    yuan  tang  teng  shu   shu   fen   fei   wen   ba    diao  tuo   tong  qu    sheng shi " +
                        "  you   shi   ting  wu    nian  jing  hun   ju    yan   tu    si    xi    xian  yan   lei   " +
                        "bi    yao   yan   han   hui   wu    hou   xi    ge    zha   xiu   weng  zha   nong  nang  qi" +
                        "    zhai  ji    zi    ji    ji    qi    ji    chi   chen  chen  he    ya    ken   xie   bao " +
                        "  ze    shi   zi    chi   nian  ju    tiao  ling  ling  chu   quan  xie   yin   nie   jiu   " +
                        "nie   chuo  kun   yu    chu   yi    ni    cuo   chuo  qu    nian  xian  yu    e     wo    yi" +
                        "    chi   zou   dian  chu   jin   ya    chi   chen  he    yin   ju    ling  bao   tiao  zi  " +
                        "  yin   yu    chuo  qu    wo    long  pang  gong  pang  yan   long  long  gong  kan   ta    " +
                        "ling  ta    long  gong  kan   gui   qiu   bie   gui   yue   chui  he    jue   ")
                .append("xie   yue   ").toString();
    }
}
