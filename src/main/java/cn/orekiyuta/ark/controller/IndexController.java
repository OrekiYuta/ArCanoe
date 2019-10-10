package cn.orekiyuta.ark.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by orekiyuta on  2019/10/9 - 19:42
 **/

@Controller
public class IndexController {

    @GetMapping("/")
    public  String index(){return "index";}

}
