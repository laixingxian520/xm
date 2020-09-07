package com.example.demo.Controller;

import com.example.demo.Mapper.QUuestionMapper;
import com.example.demo.Mapper.UserMapper;
import com.example.demo.Model.Question;
import com.example.demo.Model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Controller
public class PublishContoller {

    @Autowired
    private QUuestionMapper qUuestionMapper;
    @Autowired
    private UserMapper userMapper;


    @GetMapping("/Publish")
    public String greeting() {
        return "Publish";
    }

    @PostMapping("/Publish")
    public String doPublish(@RequestParam("title") String title,
                            @RequestParam("description") String description,
                            @RequestParam("tag") String tag,
                            HttpServletRequest request,
                            Model model
    ) {
        //方便回显
        model.addAttribute("title",title);
        model.addAttribute("description",description);
        model.addAttribute("tag",tag);


        User user = null;
        //拿到页面的cookies
        Cookie[] cookies = request.getCookies();
        //判断是否为空
        if (cookies !=null){
            //遍历cookie并判断里面有token吗
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("token")){
                    String token = cookie.getValue();
                    //拿到的token去数据库里面判断
                    user = userMapper.findToken(token);
                    //有的话返回到session
                    if (user != null){
                        request.getSession().setAttribute("user",user);
                    }
                    break;
                }
            }

        }

        //非空验证

        if (title ==null || title==""){
            model.addAttribute("error","标题不能为空");
            return "Publish";
        }
        if (description ==null || description==""){
            model.addAttribute("error","问题不能为空");
            return "Publish";
        }
        if (tag ==null || tag==""){
            model.addAttribute("error","标签不能为空");
            return "Publish";
        }

        //获取文章属性并存入数据库
        Question question = new Question();
        if (question.getComment_count()== null){
            question.setComment_count(0);
        }

        question.setTitle(title);
        question.setDescription(description);
        question.setTag(tag);
        question.setCreator(user.getId());
        question.setGmt_create(System.currentTimeMillis());
        question.setGmt_modified(question.getComment_count());
        qUuestionMapper.create(question);

        return   "redirect:index";
    }
}
