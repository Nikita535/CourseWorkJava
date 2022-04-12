package demo.Controllers;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class otherController {


    @GetMapping("/index")
    public String getHome(){
        return "index";
    }

    @GetMapping("/login")
    public String getLogin() throws Exception {
        return "login";
    }

    @GetMapping("/UserProfile")
    public String getLK(){
        return "userProfile";
    }

    }




