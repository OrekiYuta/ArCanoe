package cn.orekiyuta.ark.exception;

/**
 * Created by orekiyuta on  2019/10/28 - 0:05
 **/
public enum  CustomizeErrorCode implements ICustomizeErrorCode {

    QUESTION_NOT_FOUND("The problem does not exist ");
    private String message;

    CustomizeErrorCode(String message) {
        this.message = message;
    }
    @Override
    public String getMessage() {
        return message;
    }
}
