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

    @Autowired
    private UserService userService;

    private void changeCountTickets(User user, int ticketNumber, boolean plus){
        if (user.getTicketCount(ticketNumber) == 0){
            if (!plus) return;

            Ticket ticket = new Ticket(user, ticketNumber);
            user.getList().add(ticket);
            ticketService.saveTicket(ticket);

        }   else {
            Ticket ticket = user.getTicket(ticketNumber);

            if (user.getTicketCount(ticketNumber) == 1 & !plus){
                user.getList().remove(ticket);
                userService.updateUser(user);
                ticketService.deleteTicket(ticket);
            }
            else
                ticketService.changeCountOfTicket(ticket, plus);
        }
    }

    @GetMapping("/add_ticket_{number}")
    public String addB(@PathVariable int number){
        User current_user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        changeCountTickets(current_user, number, true);
        return "redirect:/index#shop";
    }

    @GetMapping("/remove_ticket_{number}")
    public String remB(@PathVariable int number){
        User current_user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        changeCountTickets(current_user, number, false);
        return "redirect:/index#shop";
    }


}

