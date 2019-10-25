package cn.orekiyuta.ark.service;

import cn.orekiyuta.ark.dto.PaginationDTO;
import cn.orekiyuta.ark.dto.QuestionDTO;
import cn.orekiyuta.ark.mapper.QuestionMapper;
import cn.orekiyuta.ark.mapper.UserMapper;
import cn.orekiyuta.ark.model.Question;
import cn.orekiyuta.ark.model.QuestionExample;
import cn.orekiyuta.ark.model.User;
import org.apache.ibatis.session.RowBounds;
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
    
    public PaginationDTO list(Integer page, Integer size) {


        PaginationDTO paginationDTO = new PaginationDTO();
        Integer totalPage;
        Integer totalCount = (int)questionMapper.countByExample(new QuestionExample());

        if(totalCount % size == 0){
            totalPage = totalCount / size;
        }else{
            totalPage = totalCount / size + 1 ;
        }

        if (page < 1){
            page = 1;
        }
        if (page > totalPage){
            page = totalPage;
        }
        paginationDTO.setPagination(totalPage,page);

        Integer offset=size * (page -1);
//        List<Question> questions = questionMapper.list(offset,size);

        List<Question> questions = questionMapper.selectByExampleWithRowbounds(new QuestionExample(), new RowBounds(offset, size));
        List<QuestionDTO> questionDTOList=new ArrayList<>();


        for (Question question : questions) {
            User user=userMapper.selectByPrimaryKey(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            //questionDTO.setId(question.getId());question对象逐个放入questionDTO
            BeanUtils.copyProperties(question,questionDTO);//Spring的工具类方法，根据变量名通过反射逐个赋值
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);

        }

        paginationDTO.setQuestions((questionDTOList));
        return paginationDTO;
    }

    public PaginationDTO list(Integer userId, Integer page, Integer size) {

        PaginationDTO paginationDTO = new PaginationDTO();

        Integer totalPage;

//        Integer totalCount = questionMapper.countByUserId(userId);
        QuestionExample questionExample = new QuestionExample();
        questionExample.createCriteria()
                .andCreatorEqualTo(userId);
        Integer totalCount = (int)questionMapper.countByExample(questionExample);

        if(totalCount % size == 0){
            totalPage = totalCount / size;
        }else{
            totalPage = totalCount / size + 1 ;
        }

        if (page < 1){
            page = 1;
        }
        if (page > totalPage){
            page = totalPage;
        }

        paginationDTO.setPagination(totalPage,page);

        Integer offset=size * (page -1);
//        List<Question> questions = questionMapper.listByUserId(userId,offset,size);

        QuestionExample example = new QuestionExample();
        example.createCriteria()
                .andCreatorEqualTo(userId);
        List<Question> questions = questionMapper.selectByExampleWithRowbounds(example, new RowBounds(offset, size));
        List<QuestionDTO> questionDTOList=new ArrayList<>();


        for (Question question : questions) {
            User user=userMapper.selectByPrimaryKey(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question,questionDTO);
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);

        }

        paginationDTO.setQuestions((questionDTOList));
        return paginationDTO;
    }

    public QuestionDTO getById(Integer id) {
        Question question=questionMapper.selectByPrimaryKey(id);
        QuestionDTO questionDTO = new QuestionDTO();
        BeanUtils.copyProperties(question,questionDTO);
        User user=userMapper.selectByPrimaryKey(question.getCreator());
        questionDTO.setUser(user);
        return questionDTO;
    }

    public void createOrUpdate(Question question) {

        if(question.getId() == null){
            question.setGmtCreate(System.currentTimeMillis());
            question.setGmtModified(question.getGmtCreate());
            questionMapper.insert(question);
        }else {
//            question.setGmtModified(question.getGmtCreate());
            Question updateQuestion = new Question();
            updateQuestion.setGmtModified(System.currentTimeMillis());
            updateQuestion.setTitle(question.getTitle());
            updateQuestion.setDescription(question.getDescription());
            updateQuestion.setTag(question.getTag());
            QuestionExample example = new QuestionExample();
            example.createCriteria()
                    .andIdEqualTo(question.getId());
            questionMapper.updateByExample(updateQuestion, example);
        }

    }
}
