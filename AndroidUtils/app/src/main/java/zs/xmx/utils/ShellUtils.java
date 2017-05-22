package zs.xmx.utils;

import android.content.Context;
import android.content.Intent;
import android.os.PowerManager;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

/*
 * @创建者     默小铭
 * @博客       http://blog.csdn.net/u012792686
 * @创建时间   2016/9/30 1:30
 * @本类描述	  Shell_相关工具
 * @内容说明   1.判断设备是否root
 *            2.是否是在root下执行命令
 *            3.修改文件读写权限
 *
 * ---------------------------------------------     
 * @更新时间   2016/9/30
 * @更新说明
 */
public class ShellUtils {
    private static final String COMMAND_SU       = "su";
    private static final String COMMAND_SH       = "sh";
    private static final String COMMAND_EXIT     = "exit\n";
    private static final String COMMAND_LINE_END = "\n";

    private ShellUtils() {

    }

    /**
     * 判断设备是否root
     *
     * @return the boolean{@code true}: 是<br>{@code false}: 否
     */
    public static boolean isDeviceRoot() {
        String su = "su";
        String[] locations = {"/system/bin/", "/system/xbin/", "/sbin/", "/system/sd/xbin/", "/system/bin/failsafe/",
                "/data/local/xbin/", "/data/local/bin/", "/data/local/"};
        for (String location : locations) {
            if (new File(location + su).exists()) {
                return true;
            }
        }
        return false;
    }

    /**
     * 是否是在root下执行命令
     *
     * @param command 命令
     * @param isRoot  是否root
     * @return CommandResult
     */
    public static CommandResult execCmd(String command, boolean isRoot) {
        return execCmd(new String[]{command}, isRoot, true);
    }

    /**
     * 是否是在root下执行命令
     *
     * @param commands 多条命令链表
     * @param isRoot   是否root
     * @return CommandResult
     */
    public static CommandResult execCmd(List<String> commands, boolean isRoot) {
        return execCmd(commands == null ? null : commands.toArray(new String[]{}), isRoot, true);
    }

    /**
     * 是否是在root下执行命令
     *
     * @param commands 多条命令数组
     * @param isRoot   是否root
     * @return CommandResult
     */
    public static CommandResult execCmd(String[] commands, boolean isRoot) {
        return execCmd(commands, isRoot, true);
    }

    /**
     * 是否是在root下执行命令
     *
     * @param command         命令
     * @param isRoot          是否root
     * @param isNeedResultMsg 是否需要结果消息
     * @return CommandResult
     */
    public static CommandResult execCmd(String command, boolean isRoot, boolean isNeedResultMsg) {
        return execCmd(new String[]{command}, isRoot, isNeedResultMsg);
    }

    /**
     * 是否是在root下执行命令
     *
     * @param commands        命令链表
     * @param isRoot          是否root
     * @param isNeedResultMsg 是否需要结果消息
     * @return CommandResult
     */
    public static CommandResult execCmd(List<String> commands, boolean isRoot, boolean isNeedResultMsg) {
        return execCmd(commands == null ? null : commands.toArray(new String[]{}), isRoot, isNeedResultMsg);
    }

    /**
     * 是否是在root下执行命令
     *
     * @param commands        命令数组
     * @param isRoot          是否root
     * @param isNeedResultMsg 是否需要结果消息
     * @return CommandResult
     */
    public static CommandResult execCmd(String[] commands, boolean isRoot, boolean isNeedResultMsg) {
        int result = -1;
        if (commands == null || commands.length == 0) {
            return new CommandResult(result, null, null);
        }
        Process process = null;
        BufferedReader successResult = null;
        BufferedReader errorResult = null;
        StringBuilder successMsg = null;
        StringBuilder errorMsg = null;
        DataOutputStream os = null;
        try {
            process = Runtime.getRuntime().exec(isRoot ? COMMAND_SU : COMMAND_SH);
            os = new DataOutputStream(process.getOutputStream());
            for (String command : commands) {
                if (command == null) {
                    continue;
                }
                os.write(command.getBytes());
                os.writeBytes(COMMAND_LINE_END);
                os.flush();
            }
            os.writeBytes(COMMAND_EXIT);
            os.flush();

            result = process.waitFor();
            if (isNeedResultMsg) {
                successMsg = new StringBuilder();
                errorMsg = new StringBuilder();
                successResult = new BufferedReader(new InputStreamReader(process.getInputStream()));
                errorResult = new BufferedReader(new InputStreamReader(process.getErrorStream()));
                String s;
                while ((s = successResult.readLine()) != null) {
                    successMsg.append(s);
                }
                while ((s = errorResult.readLine()) != null) {
                    errorMsg.append(s);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (os != null) {
                    os.close();
                }
                if (successResult != null) {
                    successResult.close();
                }
                if (errorResult != null) {
                    errorResult.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            if (process != null) {
                process.destroy();
            }
        }
        return new CommandResult(result, successMsg == null ? null : successMsg.toString(), errorMsg == null ? null
                : errorMsg.toString());
    }


    /**
     * 返回的命令结果
     */
    public static class CommandResult {

        /**
         * 结果码
         **/
        public int    result;
        /**
         * 成功的信息
         **/
        public String successMsg;
        /**
         * 错误信息
         **/
        public String errorMsg;

        public CommandResult(int result) {
            this.result = result;
        }

        public CommandResult(int result, String successMsg, String errorMsg) {
            this.result = result;
            this.successMsg = successMsg;
            this.errorMsg = errorMsg;
        }
    }

    /**
     * 修改文件读写权限
     *
     * @param file
     * @param mode
     */
    public static void changeFileMode(File file, String mode) {
        changeFileMode(file.getAbsolutePath(), mode);
    }

    /**
     * 修改文件读写权限
     *
     * @param fileAbsPath
     * @param mode
     */
    public static void changeFileMode(String fileAbsPath, String mode) {
        String cmd = "chmod " + mode + " " + fileAbsPath;
        try {
            Runtime.getRuntime().exec(cmd);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 关机
     * <p>需要root权限</p>
     */
    public static void shutdown() {
        ShellUtils.execCmd("reboot -p", true);
    }

    /**
     * 关机
     * <p>需系统权限 {@code <android:sharedUserId="android.uid.system"/>}</p>
     *
     * @param context 上下文
     */
    public static void shutdown(Context context) {
        Intent intent = new Intent("android.intent.action.ACTION_REQUEST_SHUTDOWN");
        intent.putExtra("android.intent.extra.KEY_CONFIRM", false);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    /**
     * 重启
     * <p>需要root权限</p>
     */
    public static void reboot() {
        ShellUtils.execCmd("reboot", true);
    }

    /**
     * 重启
     * <p>需系统权限 {@code <android:sharedUserId="android.uid.system"/>}</p>
     *
     * @param context 上下文
     */
    public static void reboot(Context context) {
        Intent intent = new Intent(Intent.ACTION_REBOOT);
        intent.putExtra("nowait", 1);
        intent.putExtra("interval", 1);
        intent.putExtra("window", 0);
        context.sendBroadcast(intent);
    }

    /**
     * 重启
     * <p>需系统权限 {@code <android:sharedUserId="android.uid.system"/>}</p>
     *
     * @param context 上下文
     * @param reason  传递给内核来请求特殊的引导模式，如"recovery"
     */
    public static void reboot(Context context, String reason) {
        PowerManager mPowerManager = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
        try {
            mPowerManager.reboot(reason);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 重启到recovery
     * <p>需要root权限</p>
     */
    public static void reboot2Recovery() {
        ShellUtils.execCmd("reboot recovery", true);
    }

    /**
     * 重启到bootloader
     * <p>需要root权限</p>
     */
    public static void reboot2Bootloader() {
        ShellUtils.execCmd("reboot bootloader", true);
    }

}
