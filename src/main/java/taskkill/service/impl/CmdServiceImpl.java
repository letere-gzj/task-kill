package taskkill.service.impl;

import taskkill.constant.Constant;
import taskkill.service.CmdService;
import taskkill.constant.CmdConstant;
import taskkill.exception.BusinessException;
import taskkill.service.LogService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author gaozijie
 * @date 2023-09-27
 */
public class CmdServiceImpl implements CmdService {

    private final LogService log = new LogServiceImpl();

    @Override
    public String findPidByPort(String port) {
        List<String> processInfo = this.findPidAndGetProcessInfo(port);
        // 提取端口信息（正则匹配）
        String pid = null;
        Matcher matcher;
        Pattern pattern = Pattern.compile(Constant.PID_PATTERN);
        for (String info : processInfo) {
            matcher = pattern.matcher(info);
            if (matcher.find()) {
                if (pid == null) {
                    pid = matcher.group(Constant.PID_GROUP_INDEX);
                    continue;
                }
                if (!Objects.equals(pid, matcher.group(Constant.PID_GROUP_INDEX))) {
                    throw new BusinessException("该端口存在多个pid，请改用手动杀死进程");
                }
            }
        }
        if (pid == null) {
            throw new BusinessException("未找到对应端口所属进程");
        }
        log.info(String.format("端口[%s]对应pid[%s]", port, pid));
        return pid;
    }

    @Override
    public void findPidAndPrint(String port) {
        List<String> processInfo = this.findPidAndGetProcessInfo(port);
        for (String info : processInfo) {
            System.out.println(info);
        }
        System.out.println();
    }

    @Override
    public void killTaskByPid(String pid) {
        if (pid == null || pid.trim().length() == 0) {
            return;
        }
        String[] pidArr = pid.split(Constant.SPLIT);
        String cmd;
        for (String pidStr : pidArr) {
            cmd = String.format(CmdConstant.KILL_TASK, pidStr);
            this.executeCmd(cmd);
            log.info(String.format("pid[%s]已杀死", pidStr));
        }
    }

    /**
     * 查找进程号，并获取进程信息
     * @param port 端口号
     * @return 进程信息
     */
    private List<String> findPidAndGetProcessInfo(String port) {
        // 执行查找进程命令
        String cmd = String.format(CmdConstant.FIND_PID, port);
        Process process = this.executeCmd(cmd);
        List<String> processInfo = this.getProcessInfo(process);
        if (processInfo.size() == 0) {
            throw new BusinessException("未找到对应端口所属进程");
        }
        return processInfo;
    }

    /**
     * 执行命令
     * @param cmd 命令
     * @return 命令进程信息
     */
    private Process executeCmd(String cmd) {
        String osName = System.getProperties().getProperty("os.name");
        // 执行命令
        if (osName.toLowerCase().startsWith("win")) {
            cmd = "cmd /c " + cmd;
        }
        Process process;
        try {
            process = Runtime.getRuntime().exec("cmd /c " + cmd);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return process;
    }

    /**
     * 获取进程信息
     * @param process 进程
     * @return 进程信息
     */
    private List<String> getProcessInfo(Process process) {
        List<String> lines = new ArrayList<>(16);
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
            String line = "";
            while ((line = bufferedReader.readLine()) != null) {
                lines.add(line);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return lines;
    }
}
