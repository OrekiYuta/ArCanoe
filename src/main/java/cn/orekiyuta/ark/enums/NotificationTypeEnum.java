package cn.orekiyuta.ark.enums;

/**
 * Created by orekiyuta on  2019/11/8 - 16:42
 **/
public enum NotificationTypeEnum {
    REPLY_QUESTION(1,"Replied the question"),
    REPLY_COMMENT(2,"Replyed to a comment")
    ;

    private int type;
    private String name;

    public int getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    NotificationTypeEnum(int type, String name) {
        this.type = type;
        this.name = name;
    }

    public static String nameOfTyoe(int type){
        for (NotificationTypeEnum notificationTypeEnum : NotificationTypeEnum.values()) {
            if(notificationTypeEnum.getType() == type){
                return notificationTypeEnum.getName();
            }
        }
        return "";
    }
}
