package com.example.demo.provider;

import com.alibaba.fastjson.JSON;
import com.example.demo.dto.AccessTokenDto;
import com.example.demo.dto.GithubUser;
import okhttp3.*;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class GithubProvider {
    /**
     * okhttp方法
     * @param accessTokenDto
     * @return
     */
 public String getAccessToken(AccessTokenDto accessTokenDto){
      MediaType mediaType = MediaType.get("application/json; charset=utf-8");
     OkHttpClient client = new OkHttpClient();
       //  RequestBody body = RequestBody.create(mediaType,JSON.toJSONString(accessTokenDto));
     RequestBody body = RequestBody.create(JSON.toJSONString(accessTokenDto), mediaType);
     Request request = new Request.Builder()
                 .url("https://github.com/login/oauth/access_token")
                 .post(body)
                 .build();
         try (Response response = client.newCall(request).execute()) {
             String string = response.body().string();
             String token=string.split("&")[0].split("=")[1];
             return token;
         } catch (Exception e) {
             e.printStackTrace();
         }
         return  "翻车";
     }

    /**
     * 获取用户之后返回结果
     * @param accessToken
     * @return
     */
     public GithubUser getUser(String accessToken){

         OkHttpClient client = new OkHttpClient();
         Request request = new Request.Builder()
                 .url("https://api.github.com/user?access_token="+accessToken)
                 .build();
         try {
             Response response = client.newCall(request).execute();
            // JSON.parseObject(String,GithubUser.class);
             //拿到string 对象
             String string = response.body().string();
             GithubUser githubUser = JSON.parseObject(string, GithubUser.class);
             return githubUser;
         } catch (IOException e) {
             e.printStackTrace();
         }
         return null;
     }
}
