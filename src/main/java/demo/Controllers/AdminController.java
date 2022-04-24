package demo.Controllers;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@RequiredArgsConstructor
@Slf4j
@Controller
@PreAuthorize("hasAuthority('ROLE_ADMIN')")
public class AdminController {

    @GetMapping("/admin")
    public String admin(Model model){
        return "admin";
    }
}
