package com.example.demo.Controller;

import com.example.demo.Mapper.UserMapper;
import com.example.demo.Model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Controller
public class UserController {

    @Autowired
    private UserMapper userMapper;

    @GetMapping("/hello")
    public String greeting(@RequestParam(name="name", required=false, defaultValue="World") String name, Model model) {
        model.addAttribute("name", name);
        return "hello";
    }

    @GetMapping("/index")
    public String index(HttpServletRequest request) {
        //拿到页面的cookies
        Cookie[] cookies = request.getCookies();
        //判断是否为空
        if (cookies !=null){
            //遍历cookie并判断里面有token吗
            for (Cookie cookie : cookies) {
              if (cookie.getName().equals("token")){
                  String token = cookie.getValue();
                  //拿到的token去数据库里面判断
                  User user = userMapper.findToken(token);
                  //有的话返回到session
                  if (user != null){
                      request.getSession().setAttribute("user",user);
                  }
                  break;
              }
            }
        }

        return "index";
    }
}
