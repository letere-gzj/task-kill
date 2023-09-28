package taskkill.constant;

/**
 * @author gaozijie
 * @date 2023-09-27
 */
public interface CmdConstant {

    String FIND_PID = "netstat -aon|findstr \"%s\"";

    String KILL_TASK = "taskkill /F /PID %s";
}
