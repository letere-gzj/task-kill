package taskkill.service.impl;

import taskkill.constant.Constant;
import taskkill.service.LogService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author gaozijie
 * @date 2023-09-27
 */
public class LogServiceImpl implements LogService {

    @Override
    public void info(String msg) {
        this.log("INFO", msg);
    }

    @Override
    public void error(String msg) {
        this.log("ERROR", msg);
    }

    private void log(String type, String msg) {
        System.out.printf("%s[%s] --- %s\n", this.getNowTimeStr(), type, msg);
    }

    private String getNowTimeStr() {
       return LocalDateTime.now().format(DateTimeFormatter.ofPattern(Constant.DATE_FORMAT_PATTEN));
    }
}
