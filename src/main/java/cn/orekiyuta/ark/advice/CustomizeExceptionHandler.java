package cn.orekiyuta.ark.advice;

import cn.orekiyuta.ark.exception.CustomizeException;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by orekiyuta on  2019/10/27 - 21:33
 **/
@ControllerAdvice
public class CustomizeExceptionHandler {
    @ExceptionHandler(Exception.class)
    ModelAndView handle(Throwable e, Model model) {


        if(e instanceof CustomizeException){
            model.addAttribute("message",e.getMessage());
        }else {
            model.addAttribute("message","Service exception: ╮(╯_╰)╭");
        }

        return new ModelAndView("error");
    }

}
