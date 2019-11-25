package cn.orekiyuta.ark.dto;

/**
 * Created by orekiyuta on  2019/11/23 - 22:11
 **/
public class HotTagDTO implements  Comparable{
    private String name;
    private Integer priority;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    @Override
    public int compareTo(Object o) {
        return this.getPriority() - ((HotTagDTO) o).getPriority();
    }
}
