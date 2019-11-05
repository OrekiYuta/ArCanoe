package cn.orekiyuta.ark.controller;

import cn.orekiyuta.ark.dto.CommentCreateDTO;
import cn.orekiyuta.ark.dto.CommentDTO;
import cn.orekiyuta.ark.dto.ResultDTO;
import cn.orekiyuta.ark.enums.CommentTypeEnum;
import cn.orekiyuta.ark.exception.CustomizeErrorCode;
import cn.orekiyuta.ark.model.Comment;
import cn.orekiyuta.ark.model.User;
import cn.orekiyuta.ark.service.CommentService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by orekiyuta on  2019/10/31 - 20:52
 **/
@Controller
public class CommentController {

    @Autowired
    private CommentService commentService;

    @ResponseBody
    @RequestMapping(value = "/comment",method = RequestMethod.POST)
    public  Object post(@RequestBody CommentCreateDTO commentCreateDTO,
                        HttpServletRequest request){

        User user = (User) request.getSession().getAttribute("user");
        if (user == null){
            return ResultDTO.errorof(CustomizeErrorCode.NO_LOGIN);
        }

//        if(commentCreateDTO == null || commentCreateDTO.getContent() == null || commentCreateDTO.getContent() == "" ){
        if(commentCreateDTO == null || StringUtils.isBlank(commentCreateDTO.getContent())){
            return ResultDTO.errorof(CustomizeErrorCode.COMMENT_IS_EMPTY);
        }

        Comment comment = new Comment();
        comment.setParentId(commentCreateDTO.getParentId());
        comment.setContent(commentCreateDTO.getContent());
        comment.setType(commentCreateDTO.getType());
        comment.setGmtCreate(System.currentTimeMillis());
        comment.setGmtModified(System.currentTimeMillis());
        comment.setCommentator(user.getId());
        comment.setLikeCount(0L);
        commentService.insert(comment);
        return ResultDTO.okof();
    }


    @ResponseBody
    @RequestMapping(value = "/comment/{id}",method = RequestMethod.GET)
    public ResultDTO<List<CommentDTO>> comments(@PathVariable(name = "id") Long id){
        List<CommentDTO> commentDTOS = commentService.listByTargetId(id, CommentTypeEnum.COMMENT);
        return ResultDTO.okof(commentDTOS);
    }
}
