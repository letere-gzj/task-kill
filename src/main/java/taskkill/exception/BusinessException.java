package taskkill.exception;

import lombok.Data;

/**
 * @author gaozijie
 * @date 2023-09-27
 */
@Data
public class BusinessException extends RuntimeException{

    private String message;

    public BusinessException(String message) {
        this.message = message;
    }
}
