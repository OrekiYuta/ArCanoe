package cn.orekiyuta.ark.controller;

import cn.orekiyuta.ark.mapper.UserMapper;
import cn.orekiyuta.ark.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by orekiyuta on  2019/10/9 - 19:42
 **/

@Controller
public class IndexController {

    @Autowired
    private UserMapper userMapper;

    @GetMapping("/")
    public  String index(HttpServletRequest request){
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            if(cookie.getName().equals("token")){
                String token = cookie.getValue();
                User user = userMapper.findByToken(token);
                if(user !=null) {
                    request.getSession().setAttribute("user",user);
                }
                break;
            }
        }

        return "index";
    }
}
