package cn.orekiyuta.ark.controller;

import cn.orekiyuta.ark.mapper.QuestionMapper;
import cn.orekiyuta.ark.mapper.UserMapper;
import cn.orekiyuta.ark.model.Question;
import cn.orekiyuta.ark.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by orekiyuta on  2019/10/15 - 19:32
 **/
@Controller
public class PublishController {

    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private UserMapper userMapper;

    @GetMapping("/publish")
    public  String publish(){
        return "publish";
    }

    @PostMapping("/publish")
    public String doPublish(
            @RequestParam(value = "title",required = false) String title,
            @RequestParam(value = "description",required =false ) String description,
            @RequestParam(value = "tag",required = false) String tag,
            HttpServletRequest request,
            Model model){

        model.addAttribute("title",title);
        model.addAttribute("description",description);
        model.addAttribute("tag",tag);

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


        User user1 = null;
        Cookie[] cookies = request.getCookies();
        if(cookies !=null && cookies.length!=0) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("token")) {
                    String token = cookie.getValue();
                    user1 = userMapper.findByToken(token);
                    if (user1 != null) {
                        request.getSession().setAttribute("user", user1);
                    }
                    break;
                }
            }
        }
        if(user1 == null) {
            model.addAttribute("error", "Please log in first!");
            return "publish";
        }

        Question question = new Question();
        question.setTitle(title);
        question.setDescription(description);
        question.setTag(tag);
        question.setCreator(user1.getId());
        question.setGmtCreate(System.currentTimeMillis());
        question.setGmtModified(question.getGmtCreate());

        questionMapper.create(question);

        return "redirect:/";
    }
}

