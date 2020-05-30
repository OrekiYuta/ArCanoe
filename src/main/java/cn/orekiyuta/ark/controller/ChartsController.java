package cn.orekiyuta.ark.controller;

import cn.orekiyuta.ark.cache.HotTagCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

/**
 * Created by orekiyuta on  2020/5/29 - 22:08
 **/
@Controller
public class ChartsController {

    @Autowired
    private HotTagCache hotTagCache;

    @GetMapping("/charts")
    public String charts(Model model){

        List<String> tags = hotTagCache.getHots();
        model.addAttribute("tags",tags);

        List<Integer> tagsNumber = hotTagCache.getHotP();
        model.addAttribute("tagsNumber",tagsNumber);
        return "charts";
    }
}
