package demo.Controllers;

import demo.Entity.Ticket;
import demo.Entity.User;
import demo.Services.EmailService;
import demo.Services.TicketService;
import demo.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.HashSet;
import java.util.Set;

@Controller
public class shopBasketController {

    @Autowired
    private UserService userService;

    @Autowired
    private TicketService ticketService;


    @GetMapping("/shopBasket")
    public String PrintBasket(Model model) {
        User current_user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("tickets", current_user.getList());
        return "shopBasket";
    }



    @GetMapping("/clear")
    public String ClearBasket(){
        User current_user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        userService.updateUser(current_user);
        ticketService.deleteCarts(current_user.getList());
        current_user.getList().clear();
        return "shopBasket";
    }

    @GetMapping("/order")
    public String GetOrder(){
        User current_user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String totalPrice = current_user.getTotalCost();
        userService.sendOrder(current_user, current_user.getList(), totalPrice);
        userService.updateUser(current_user);
        ticketService.deleteCarts(current_user.getList());
        current_user.getList().clear();;
        return "shopBasket";
    }


}
