package demo.Controllers;


import demo.Entity.User;
import demo.Services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

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
        User current_user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        System.out.println(current_user.checkRole());
        return "admin";
    }
}
