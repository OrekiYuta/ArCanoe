package cn.orekiyuta.ark.controller;

import cn.orekiyuta.ark.dto.GithubUser;
import cn.orekiyuta.ark.dto.PaginationDTO;
import cn.orekiyuta.ark.model.User;
import cn.orekiyuta.ark.service.NotificationService;
import cn.orekiyuta.ark.service.QuestionService;
import cn.orekiyuta.ark.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import javax.servlet.http.HttpServletRequest;


/**
 * Created by orekiyuta on  2019/10/19 - 20:36
 **/
@Controller
public class ProfileController {

    @Autowired
    private QuestionService questionService;

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private UserService userService;

    @GetMapping("/profile/{action}")
    public  String profile(HttpServletRequest request,
                           @PathVariable(name = "action") String action,
                           Model model,
                           @RequestParam(name = "page",defaultValue = "1") Integer page,
                           @RequestParam(name = "size",defaultValue = "5") Integer size
                           ){

        User user = (User) request.getSession().getAttribute("user");
        if(user==null){
            return "redirect:/";
        }

        if ("questions".equals(action)){
            model.addAttribute("section","questions");
            model.addAttribute("sectionName","My Issue");

            PaginationDTO paginationDTO = questionService.list(user.getId(), page, size);
            model.addAttribute("pagination",paginationDTO);

//            Long unreadCount = notificationService.unreadCount(user.getId());
//            model.addAttribute("unreadCount",unreadCount);

        }else if ("replies".equals(action)){
            PaginationDTO paginationDTO = notificationService.list(user.getId(), page, size);
            model.addAttribute("section","replies");
            model.addAttribute("sectionName","Latest Reply");
            model.addAttribute("pagination",paginationDTO);

//            Long unreadCount = notificationService.unreadCount(user.getId());
//            model.addAttribute("unreadCount",unreadCount);

        }else if("information".equals(action)){
            model.addAttribute("section","information");
            model.addAttribute("sectionName","Information");
            model.addAttribute("userId",user.getId());
            model.addAttribute("userAccountId",user.getAccountId());
            model.addAttribute("userName",user.getName());
            model.addAttribute("userToken",user.getToken());
            model.addAttribute("userGmtCreate",user.getGmtCreate());
            model.addAttribute("userGmtModified",user.getGmtModified());
            model.addAttribute("userBio",user.getBio());
            model.addAttribute("userAvatarUrl",user.getAvatarUrl());


        }


        return "profile";
    }
}
