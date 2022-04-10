package demo.Controllers;


import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class homeController{


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
        return "UserProfile";
    }

    }




