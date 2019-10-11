package cn.orekiyuta.ark.provider;

import cn.orekiyuta.ark.dto.AccessTokenDTO;
import cn.orekiyuta.ark.dto.GithubUser;
import com.alibaba.fastjson.JSON;
import okhttp3.*;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * Created by orekiyuta on  2019/10/10 - 20:04
 **/

@Component
public class GithubProvider {

    private String[] split;
    private String tokenstr;

    public String getAccessToken(AccessTokenDTO accessTokenDTO){
            MediaType mediaType= MediaType.get("application/json; charset=utf-8");
            OkHttpClient client = new OkHttpClient();
            RequestBody body = RequestBody.create(mediaType, JSON.toJSONString(accessTokenDTO));
            Request request = new Request.Builder()
                    .url("https://github.com/login/oauth/access_token")
                    .post(body)
                    .build();
            try (Response response = client.newCall(request).execute()) {
                String string = response.body().string();
                String token =string.split("&")[0].split("=")[1];
                return token;

            } catch (Exception e) {
                 e.printStackTrace();
            }
            return null;
    }

    public GithubUser getUser(String accessToken){
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                    .url("https://api.github.com/user?access_token="+ accessToken)
                    .build();
        try {
            Response response = client.newCall(request).execute();
            String string =response.body().string();

            GithubUser githubUser = JSON.parseObject(string, GithubUser.class);//把string的json的对象自动转换解析成Java的类对象

            return  githubUser;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;

    }

}
