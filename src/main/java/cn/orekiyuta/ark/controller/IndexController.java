package cn.orekiyuta.ark.controller;

import cn.orekiyuta.ark.dto.QuestionDTO;
import cn.orekiyuta.ark.mapper.UserMapper;
import cn.orekiyuta.ark.model.User;
import cn.orekiyuta.ark.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by orekiyuta on  2019/10/9 - 19:42
 **/

@Controller
public class IndexController {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private QuestionService questionServicer;

    @GetMapping("/")
    public  String index(HttpServletRequest request,
                         Model model){
        Cookie[] cookies = request.getCookies();
        if(cookies !=null && cookies.length!=0) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("token")) {
                    String token = cookie.getValue();
                    User user = userMapper.findByToken(token);
                    if (user != null) {
                        request.getSession().setAttribute("user", user);
                    }
                    break;
                }
            }
        }

        List<QuestionDTO> questionList =questionServicer.list();
        model.addAttribute("questions",questionList);
        return "index";
    }
}
