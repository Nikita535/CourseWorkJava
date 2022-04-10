package demo.Controllers;

import demo.Entity.User;
import demo.Services.EmailService;
import demo.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;


import javax.validation.Valid;
import java.util.Objects;

@Controller
public class registerController {

    @Autowired
    private UserService userService;
    @Autowired
    private EmailService emailService;

    @GetMapping("/register")
    public String getForm(Model model){
        User user = new User();
        model.addAttribute("user", user);
        return "register";
    }

    @PostMapping("/register")
    public String registerSave(@ModelAttribute("user") @Valid User user,
                               Model model){


        if (!Objects.equals(user.getPassword(), user.getPasswordConfirm())){
            model.addAttribute("errorConfPassword", true);
            return "register";
        }
        if (user.getPassword().length() < 5){
            model.addAttribute("errorLenPassword", true);
            return "register";
        }
        if (userService.findUserByUsername(user.getUsername()) != null){
            model.addAttribute("errorAlreadyExistsUsername", true);
            return "register";
        }
        try{
            userService.saveUser(user);
            if (!StringUtils.isEmpty(user.getEmail())){
                String message = "Здравствуйте, "+user.getUsername()+"! Рады приветствовать вас на нашем сервисе. Удачных путешествий!";
                emailService.sendSimpleMessage(user.getEmail(), message);
            }
            return "redirect:/login";
        } catch (Exception e){
            model.addAttribute("errorAnomaly", true);
            return "register";
        }
    }

}

