package demo.Controllers;


import demo.Entity.User;
import demo.Enums.Role;
import demo.Services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RequiredArgsConstructor
@Slf4j
@Controller
@PreAuthorize("hasAuthority('ROLE_ADMIN')")
public class AdminController {


    @Autowired
    private UserService userService;


    @GetMapping("/admin")
    public String admin(Model model){
        model.addAttribute("users",userService.showAllUser());
        return "admin";
    }

    @GetMapping("/ban_{username}")
    public String ban(@PathVariable String username, Model model){
        User user = userService.findUserByUsername(username);
        if(!user.getRoles().contains(Role.ROLE_ADMIN)) {
            user.setActive(false);
            userService.saveUser(user);
        }else {
            model.addAttribute("errorBan", true);
            model.addAttribute("message", "Нельзя забанить администратора");
            return "redirect:/admin";
        }
        return "redirect:/admin";
    }

    @GetMapping("/unban_{username}")
    public String unban(@PathVariable String username){
        User user = userService.findUserByUsername(username);
        user.setActive(true);
        userService.saveUser(user);
        return "redirect:/admin";
    }
}
