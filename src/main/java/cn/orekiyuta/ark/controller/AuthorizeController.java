package cn.orekiyuta.ark.controller;

import cn.orekiyuta.ark.dto.AccessTokenDTO;
import cn.orekiyuta.ark.dto.GithubUser;
import cn.orekiyuta.ark.provider.GithubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by orekiyuta on  2019/10/10 - 19:54
 **/

@Controller
public class AuthorizeController {

    @Autowired
    private GithubProvider githubProvider;

    @Value("${github.client.id}")//Spring启动时会把配置的key和value读到一个map里面；通过@Value注解使用
    private  String clientId;

    @Value("${github.client.secret}")
    private  String clientSecret;

    @Value("${github.redirect.uri}")
    private  String redirectUri;

    @GetMapping("/callback")
    public  String callback(@RequestParam(name = "code") String code,
                            @RequestParam(name="state") String state){
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setClient_id(clientId);
        accessTokenDTO.setClient_secret(clientSecret);
        accessTokenDTO.setCode(code);
        accessTokenDTO.setRedirect_uri(redirectUri);
        accessTokenDTO.setState(state);
        String accessToken = githubProvider.getAccessToken(accessTokenDTO);
        GithubUser user = githubProvider.getUser(accessToken);
        System.out.println(user.getName());
        return "index";
    }
}
