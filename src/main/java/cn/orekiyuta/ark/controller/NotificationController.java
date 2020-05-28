package cn.orekiyuta.ark.controller;

import cn.orekiyuta.ark.dto.NotificationDTO;
import cn.orekiyuta.ark.enums.NotificationTypeEnum;
import cn.orekiyuta.ark.model.User;
import cn.orekiyuta.ark.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by orekiyuta on  2019/11/9 - 1:19
 **/
@Controller
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    @GetMapping("/notification/{id}")
    public String profile(HttpServletRequest request,
                          @PathVariable(name ="id") Long id){
        User user = (User) request.getSession().getAttribute("user");

        if(user == null ){
            return "redirect:/";
        }

        NotificationDTO notificationDTO =notificationService.read(id,user);

        if (NotificationTypeEnum.REPLY_COMMENT.getType() == notificationDTO.getType()
                || NotificationTypeEnum.REPLY_QUESTION.getType() == notificationDTO.getType()){
            return "redirect:/question/" + notificationDTO.getOuterid();
        }else {
            return "redirect:/";
        }
    }

    @RequestMapping("/read")
    public String read (Long id ,HttpServletRequest request){
        User user = (User) request.getSession().getAttribute("user");

        if(user == null ){
            return "redirect:/";
        }

        notificationService.read(id,user);

        return "redirect:/profile/replies";
    }
}
