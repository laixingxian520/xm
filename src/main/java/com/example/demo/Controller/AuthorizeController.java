package com.example.demo.Controller;

import com.example.demo.dto.AccessTokenDto;
import com.example.demo.dto.GithubUser;
import com.example.demo.provider.GithubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

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

    @GetMapping("/callback")
    public String index(@RequestParam("code") String code, @RequestParam(name="state") String state) {
        AccessTokenDto accessTokenDto = new AccessTokenDto();
        accessTokenDto.setClient_id(id);
        accessTokenDto.setClient_secret(secret);
        accessTokenDto.setCode(code);
        accessTokenDto.setRedirect_uri(url);
        accessTokenDto.setState(state);
        String accessToken = githubProvider.getAccessToken(accessTokenDto);
        GithubUser user = githubProvider.getUser(accessToken);
        System.out.println(user.getName()+"2222222222222222222");
        return "index";
    }
}
