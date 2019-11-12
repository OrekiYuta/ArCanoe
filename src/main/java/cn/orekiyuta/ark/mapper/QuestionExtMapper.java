package cn.orekiyuta.ark.mapper;

import cn.orekiyuta.ark.dto.QuestionQueryDTO;
import cn.orekiyuta.ark.model.Question;

import java.util.List;

/**
 * Created by orekiyuta on  2019/10/29 - 0:15
 **/
public interface QuestionExtMapper {
    int incView(Question record);
    int incCommentCount(Question record);
    List<Question> selectRelated(Question question);

    Integer countBySearch(QuestionQueryDTO questionQueryDTO);

    List<Question> selectBySearch(QuestionQueryDTO questionQueryDTO);
}
