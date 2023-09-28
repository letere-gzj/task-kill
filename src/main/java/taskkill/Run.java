package taskkill;

import taskkill.constant.Constant;
import taskkill.constant.KillTypEnum;
import taskkill.service.CmdService;
import taskkill.exception.BusinessException;
import taskkill.service.LogService;
import taskkill.service.impl.CmdServiceImpl;
import taskkill.service.impl.LogServiceImpl;

import java.util.Scanner;

/**
 * @author gaozijie
 * @date 2023-09-27
 */
public class Run {

    private final static CmdService cmdService = new CmdServiceImpl();
    private final static LogService log = new LogServiceImpl();

    public static void main(String[] args) {
        try {
            Scanner scanner = new Scanner(System.in);
            System.out.print("选择杀死方式(1:自动杀死 2:手动杀死)：");
            KillTypEnum killTypEnum;
            while ((killTypEnum = KillTypEnum.getInstance(scanner.nextInt())) == null) {
                System.out.print("输入错误，请重新输入：");
            }
            if (killTypEnum == KillTypEnum.AUTO_KILL) {
                log.info("<< 自动杀死... >>");
                autoKill(scanner);
            }
            if (killTypEnum == KillTypEnum.MANU_KILL) {
                log.info("<< 手动杀死... >>");
                manuKill(scanner);
            }
        } catch (BusinessException e) {
            log.error(e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 自动杀死
     * @param scanner 扫描器
     */
    public static void autoKill(Scanner scanner) {
        System.out.print("请输入端口号：");
        String port = scanner.next();
        String pid = cmdService.findPidByPort(port);
        cmdService.killTaskByPid(pid);
    }

    /**
     * 手动杀死
     * @param scanner 扫描器
     */
    public static void manuKill(Scanner scanner) {
        System.out.print("请输入端口号：");
        String port = scanner.next();
        cmdService.findPidAndPrint(port);
        System.out.print("输入要杀死的进程号[杀死多个进程用','分割](e:退出)：");
        String pid = scanner.next();
        if (Constant.EXIT_WORDS.contains(pid.toLowerCase())) {
            log.info("系统终止");
            return;
        }
        cmdService.killTaskByPid(pid);
    }
}
