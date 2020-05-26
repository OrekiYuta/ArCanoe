package cn.orekiyuta.ark.controller;

import cn.orekiyuta.ark.dto.GithubUser;
import cn.orekiyuta.ark.dto.ResultDTO;
import cn.orekiyuta.ark.model.User;
import cn.orekiyuta.ark.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by orekiyuta on  2020/5/9 - 23:19
 **/

@Controller
public class UserInformationController {

    @Autowired
    private UserService userService;

    @PostMapping("/information")
    public String SaveInformation(
            @RequestParam(value = "user_name",required = false) String user_name,
            @RequestParam(value = "user_bio",required = false) String user_bio,
            @RequestParam(value = "user_accountId",required = false) String user_accountId,
            @RequestParam(value = "user_id",required = false) String user_id,
            @RequestParam(value = "user_token",required = false) String user_token,
            @RequestParam(value = "user_avatarUrl",required = false) String user_avatarUrl,
            @RequestParam(value = "user_gmtCreate",required = false) String user_gmtCreate,
            @RequestParam(value = "user_getModified",required = false) String user_getModified,
            Model model){
        model.addAttribute("user_name",user_name);
        model.addAttribute("user_bio",user_bio);
        model.addAttribute("user_accountId",user_accountId);

        if (user_name ==null||user_name==""){
            model.addAttribute("error","The user_name can not be blank");
            return "redirect:/profile/information";
        }
        if (user_bio ==null||user_bio==""){
            model.addAttribute("error","The user_bio can not be blank");
            return "redirect:/profile/information";
        }


        User user = new User();
        user.setName(user_name);
        user.setBio(user_bio);
        user.setAccountId(user_accountId);
//        user.setId(user_id);
        user.setToken(user_token);
        user.setAvatarUrl(user_avatarUrl);
//        user.setGmtCreate(user_gmtCreate);
//        user.setGmtModified(user_getModified);

        userService.createOrUpdate(user);

        return "redirect:/profile/information";

    }
}
