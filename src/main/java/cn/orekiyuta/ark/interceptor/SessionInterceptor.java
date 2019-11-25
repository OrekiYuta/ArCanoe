package cn.orekiyuta.ark.interceptor;

import cn.orekiyuta.ark.enums.AdPosEnum;
import cn.orekiyuta.ark.mapper.UserMapper;
import cn.orekiyuta.ark.model.User;
import cn.orekiyuta.ark.model.UserExample;
import cn.orekiyuta.ark.service.AdService;
import cn.orekiyuta.ark.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Created by orekiyuta on  2019/10/20 - 19:47
 **/
@Service
public class SessionInterceptor implements HandlerInterceptor {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private AdService adService;

    @Value("${github.redirect.uri}")
    private String redirectUri;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //设置 context 级别的属性
        request.getServletContext().setAttribute("redirectUri", redirectUri);
        // 没有登录的时候也可以查看导航
        for (AdPosEnum adPos : AdPosEnum.values()) {
            request.getServletContext().setAttribute(adPos.name(), adService.list(adPos.name()));
        }


        Cookie[] cookies = request.getCookies();
        if(cookies !=null && cookies.length!=0) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("token")) {
                    String token = cookie.getValue();
                    UserExample userExample = new UserExample();
                    userExample.createCriteria()
                            .andTokenEqualTo(token);
                    List<User> users = userMapper.selectByExample(userExample);
//                    User user = userMapper.findByToken(token);
                    if (users.size() != 0 ) {
                        HttpSession session =request.getSession();
                        session.setAttribute("user", users.get(0));
                        Long unreadCount = notificationService.unreadCount(users.get(0).getId());
                        session.setAttribute("unreadCount",unreadCount);
                    }
                    break;
                }
            }
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
