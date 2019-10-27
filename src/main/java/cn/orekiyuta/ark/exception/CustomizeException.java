package cn.orekiyuta.ark.exception;

/**
 * Created by orekiyuta on  2019/10/27 - 23:48
 **/
public class CustomizeException extends RuntimeException{
    private String message;

    public CustomizeException(String message) {
        this.message = message;
    }
    public CustomizeException(ICustomizeErrorCode errorCode) {
        this.message = errorCode.getMessage();
    }

    @Override
    public String getMessage() {
        return message;
    }
}
