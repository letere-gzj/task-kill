package taskkill.constant;

import java.util.Arrays;
import java.util.List;

/**
 * @author gaozijie
 * @date 2023-09-27
 */
public interface Constant {
    /**
     * 分割字符
     */
    String SPLIT = ",";

    /**
     * PID查找正则匹配样式
     */
    String PID_PATTERN = "LISTENING\\s*(\\d+)";
    /**
     * PID查找组index
     */
    Integer PID_GROUP_INDEX = 1;

    /**
     * LocalDateTime时间格式化样式
     */
    String DATE_FORMAT_PATTEN = "yyyy-MM-dd HH:mm:ss";

    /**
     * 系统退出关键词
     */
    List<String> EXIT_WORDS = Arrays.asList("e", "exit");
}
