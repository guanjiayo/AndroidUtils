package zs.xmx.lib_utils.bean;

import android.graphics.drawable.Drawable;

/*
 * @创建者     默小铭
 * @博客       http://blog.csdn.net/u012792686
 * @创建时间   2016/10/11 16:21
 * @本类描述	  封装App信息的Bean类
 * @内容说明   AppUtils 获取App信息
 * ---------------------------------------------
 * @更新时间   2016/10/11
 * @更新说明
 */
public class AppInfo {

    private String   name;
    private Drawable icon;
    private String   packageName;
    private String   packagePath;
    private String   versionName;
    private int      versionCode;
    private boolean  isSystem;

    public Drawable getIcon() {
        return icon;
    }

    public void setIcon(Drawable icon) {
        this.icon = icon;
    }

    public boolean isSystem() {
        return isSystem;
    }

    public void setSystem(boolean isSystem) {
        this.isSystem = isSystem;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packagName) {
        this.packageName = packagName;
    }

    public String getPackagePath() {
        return packagePath;
    }

    public void setPackagePath(String packagePath) {
        this.packagePath = packagePath;
    }

    public int getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(int versionCode) {
        this.versionCode = versionCode;
    }

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    /**
     * @param name        名称
     * @param icon        图标
     * @param packageName 包名
     * @param packagePath 包路径
     * @param versionName 版本号
     * @param versionCode 版本Code
     * @param isSystem    是否系统应用
     */
    public AppInfo(String name, Drawable icon, String packageName, String packagePath,
                   String versionName, int versionCode, boolean isSystem) {
        this.setName(name);
        this.setIcon(icon);
        this.setPackageName(packageName);
        this.setPackagePath(packagePath);
        this.setVersionName(versionName);
        this.setVersionCode(versionCode);
        this.setSystem(isSystem);
    }

    @Override
    public String toString() {
        return getName() + "\n"
                + getIcon() + "\n"
                + getPackageName() + "\n"
                + getPackagePath() + "\n"
                + getVersionName() + "\n"
                + getVersionCode() + "\n"
                + isSystem() + "\n";
    }
}
