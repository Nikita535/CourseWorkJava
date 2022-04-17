package demo.Controllers;

import demo.Entity.User;
import demo.Services.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class ShopController {

    @Autowired
    private TicketService ticketService;

    public User getLoginUser(){
       return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    @GetMapping("/add_ticket_{number}")
    public String addB(@PathVariable int number){
        User current_user = getLoginUser();
        ticketService.changeCountTickets(current_user,number,true);
        if (number <= 3) return "redirect:/index";
        else return "redirect:/index#shop";
    }

    @GetMapping("/remove_ticket_{number}")
    public String remB(@PathVariable int number){
        User current_user = getLoginUser();
        ticketService.changeCountTickets(current_user,number,false);
        if (number <= 3) return "redirect:/index";
        else return "redirect:/index#shop";
    }


}

