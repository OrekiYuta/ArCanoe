package cn.orekiyuta.ark.exception;

/**
 * Created by orekiyuta on  2019/10/28 - 0:05
 **/
public enum  CustomizeErrorCode implements ICustomizeErrorCode {

    QUESTION_NOT_FOUND(2001,"The problem does not exist "),
    TARGET_PARAM_NOT_FOUND(2002,"No problem or comment is selected "),
    NO_LOGIN(2003,"Please log in first"),
    SYS_ERROR(2004,"Service exception: ╮(╯_╰)╭"),
    TYPE_PARAM_WRONG(2005,"Comment type is incorrect or does not exist"),
    COMMENT_NOT_FOUND(2006,"The replyed comment does not exist"),
    COMMENT_IS_EMPTY(2007,"The replyed comment can not be null")
    ;

    private String message;
    private Integer code;

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public Integer getCode() {
        return code;
    }

    CustomizeErrorCode(Integer code, String message) {
        this.message = message;
        this.code = code;
    }
}
