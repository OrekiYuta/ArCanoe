package cn.orekiyuta.ark.controller;

import cn.orekiyuta.ark.dto.PaginationDTO;
import cn.orekiyuta.ark.model.User;
import cn.orekiyuta.ark.service.QuestionService;
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
            model.addAttribute("sectionName","My issue");

        }else  if ("replies".equals(action)){
            model.addAttribute("section","replies");
            model.addAttribute("sectionName","Latest Reply");

        }

        PaginationDTO paginationDTO = questionService.list(user.getId(), page, size);

        model.addAttribute("pagination",paginationDTO);
        return "profile";
    }
}
