package taskkill.service;

/**
 * @author gaozijie
 * @date 2023-09-27
 */
public interface CmdService {

    /**
     * 寻找进程号（通过端口号）
     * @param port 端口号
     * @return 进程号
     */
    String findPidByPort(String port);

    /**
     * 寻找进程，并打印数据
     * @param port 端口号
     */
    void findPidAndPrint(String port);

    /**
     * 杀死进程（通过进程号）
     * @param pid 进程号
     */
    void killTaskByPid(String pid);
}
