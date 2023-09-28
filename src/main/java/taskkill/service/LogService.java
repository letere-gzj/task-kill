package taskkill.service;

/**
 * @author gaozijie
 * @date 2023-09-27
 */
public interface LogService {

    /**
     * 输入日志
     * @param msg 信息
     */
    void info(String msg);

    /**
     * 输入报错日志
     * @param msg 信息
     */
    void error(String msg);
}
