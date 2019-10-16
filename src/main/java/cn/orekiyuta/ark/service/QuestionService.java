package cn.orekiyuta.ark.service;

import cn.orekiyuta.ark.dto.QuestionDTO;
import cn.orekiyuta.ark.mapper.QuestionMapper;
import cn.orekiyuta.ark.mapper.UserMapper;
import cn.orekiyuta.ark.model.Question;
import cn.orekiyuta.ark.model.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by orekiyuta on  2019/10/16 - 14:44
 **/

@Service
public class QuestionService {

    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private UserMapper userMapper;
    
    public List<QuestionDTO> list() {
        List<Question> questions = questionMapper.list();
        List<QuestionDTO> questionDTOList=new ArrayList<>();
        for (Question question : questions) {
            User user=userMapper.findById(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            //questionDTO.setId(question.getId());question对象逐个放入questionDTO
            BeanUtils.copyProperties(question,questionDTO);//Spring的工具类方法，根据变量名通过反射逐个赋值
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);

        }
        return questionDTOList;
    }
}
