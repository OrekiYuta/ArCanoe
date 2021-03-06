package cn.orekiyuta.ark.controller;

import cn.orekiyuta.ark.cache.TagCache;
import cn.orekiyuta.ark.dto.QuestionDTO;
import cn.orekiyuta.ark.model.Question;
import cn.orekiyuta.ark.model.User;
import cn.orekiyuta.ark.service.NotificationService;
import cn.orekiyuta.ark.service.QuestionService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by orekiyuta on  2019/10/15 - 19:32
 **/
@Controller
public class PublishController {


    @Autowired
    private QuestionService questionService;

    @Autowired
    private NotificationService notificationService;

    @GetMapping("/publish/{id}")
    public String edit(@PathVariable(name = "id") Long id,
                       Model model){
        QuestionDTO question = questionService.getById(id);
        model.addAttribute("title",question.getTitle());
        model.addAttribute("description",question.getDescription());
        model.addAttribute("tag",question.getTag());
        model.addAttribute("id",question.getId());
        model.addAttribute("tags", TagCache.get());

        return  "publish";
    }

    @GetMapping("/publish")
    public  String publish(Model model){
        model.addAttribute("tags", TagCache.get());
        return "publish";

    }

    @RequestMapping("/delete")
    public String delete(Long id,Model model){
        if(id != null) {
            questionService.delete(id);
            notificationService.deleteByQuestionId(id);
            return "redirect:/profile/questions";
        }else {

            model.addAttribute("error", "question does not exist!");
            return "redirect:/profile/questions";
        }


    }

    @PostMapping("/publish")
    public String doPublish(
            @RequestParam(value = "title",required = false) String title,
            @RequestParam(value = "description",required =false ) String description,
            @RequestParam(value = "tag",required = false) String tag,
            @RequestParam(value = "id",required = false ) Long id,
            HttpServletRequest request,
            Model model){

        model.addAttribute("title",title);
        model.addAttribute("description",description);
        model.addAttribute("tag",tag);
        model.addAttribute("tags", TagCache.get());

        if (title ==null||title==""){
            model.addAttribute("error","The title can not be blank");
            return "publish";
        }
        if (description ==null||description==""){
            model.addAttribute("error","The description can not be blank");
            return "publish";
        }
        if (tag ==null||tag==""){
            model.addAttribute("error","The tag can not be blank");
            return "publish";
        }

        String invalid = TagCache.filterInvalid(tag);
        if (StringUtils.isNotBlank(invalid)){
            model.addAttribute("error","Illegal tag : " +invalid);
            return "publish";
        }


        User user = (User) request.getSession().getAttribute("user");
        if(user == null) {
            model.addAttribute("error", "Please log in first!");
            return "publish";
        }

        Question question = new Question();
        question.setTitle(title);
        question.setDescription(description);
        question.setTag(tag);
        question.setCreator(user.getId());
        question.setId(id);


        questionService.createOrUpdate(question);
        return "redirect:/";
    }
}

