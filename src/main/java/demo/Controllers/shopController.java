package demo.Controllers;

import demo.Entity.Ticket;
import demo.Entity.User;
import demo.Services.TicketService;
import demo.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class shopController {

    @Autowired
    private TicketService ticketService;


    private void makeChanges(User user,int ticketNumber,boolean plus){
        ticketService.changeCountTickets(user,ticketNumber,plus);
    }

    @GetMapping("/add_ticket_{number}")
    public String addB(@PathVariable int number){
        User current_user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        makeChanges(current_user, number, true);
        if (number <= 3) return "redirect:/index";
        else return "redirect:/index#shop";
    }

    @GetMapping("/remove_ticket_{number}")
    public String remB(@PathVariable int number){
        User current_user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        makeChanges(current_user, number, false);
        if (number <= 3) return "redirect:/index";
        else return "redirect:/index#shop";
    }


}

