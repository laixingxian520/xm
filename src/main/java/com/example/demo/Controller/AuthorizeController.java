package com.example.demo.Controller;

import com.example.demo.Mapper.UserMapper;
import com.example.demo.Model.User;
import com.example.demo.dto.AccessTokenDto;
import com.example.demo.dto.GithubUser;
import com.example.demo.provider.GithubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@Controller
public class AuthorizeController {
    @Autowired
    private GithubProvider githubProvider;

    @Value("${github.client.id}")
    private String id;
    @Value("${github.client.secret}")
    private String secret;
    @Value("${github.redirect.url}")
    private String url;

    @Autowired
    private UserMapper userMapper;
    /**
     * 点击登录
     * @param code 给的code值
     * @param state 给的状态
     * @param request 要返回的结果结果集
     * @return  返回地址
     */
    @GetMapping("/callback")
    public String index(@RequestParam("code") String code,
                        @RequestParam(name="state") String state,
                        HttpServletRequest request,
                        HttpServletResponse response) {
        AccessTokenDto accessTokenDto = new AccessTokenDto();
        accessTokenDto.setClient_id(id);
        accessTokenDto.setClient_secret(secret);
        accessTokenDto.setCode(code);
        accessTokenDto.setRedirect_uri(url);
        accessTokenDto.setState(state);
        String accessToken = githubProvider.getAccessToken(accessTokenDto);
        GithubUser githubUser = githubProvider.getUser(accessToken);
        if (githubUser !=null && githubUser.getId()!=null){
            //不为空把当前数值传过去
            User user=new User();
            user.setToken(UUID.randomUUID().toString());
            user.setName(githubUser.getName());
            user.setAccountId(String.valueOf(githubUser.getId()));
            //输出当前时间
            user.setGmtCreate(System.currentTimeMillis());
            user.setGmtModified(user.getGmtCreate());
            userMapper.insert(user);
            //添加tcookie 身份识别
            response.addCookie(new Cookie("token",user.getToken()));
            return "redirect:index";
        } else {
          //登录失败
          return   "redirect:index";
        }
    }
}
