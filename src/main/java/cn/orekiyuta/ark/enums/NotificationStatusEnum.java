package cn.orekiyuta.ark.enums;

/**
 * Created by orekiyuta on  2019/11/8 - 16:58
 **/
public enum NotificationStatusEnum {
    UNREAD(0),READ(1)
    ;

    private int status;

    public int getStatus() {
        return status;
    }

    NotificationStatusEnum(int status) {
        this.status = status;
    }
}
