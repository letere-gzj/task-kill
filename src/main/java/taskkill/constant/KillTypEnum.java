package taskkill.constant;

import lombok.Getter;

/**
 * @author gaozijie
 * @date 2023-09-27
 */
@Getter
public enum KillTypEnum {
    /**
     * 自动杀死
     */
    AUTO_KILL(1),
    /**
     * 手动杀死
     */
    MANU_KILL(2);

    private final int num;

    KillTypEnum(int num) {
        this.num = num;
    }

    public static KillTypEnum getInstance(int num) {
        for (KillTypEnum killTypEnum : KillTypEnum.values()) {
            if (killTypEnum.getNum() == num) {
                return killTypEnum;
            }
        }
        return null;
    }
}
