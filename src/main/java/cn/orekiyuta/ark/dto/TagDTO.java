package cn.orekiyuta.ark.dto;

import java.util.List;

/**
 * Created by orekiyuta on  2019/11/6 - 21:43
 **/
public class TagDTO {
    private String categoryName;
    private List<String> tags;


    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }
}
