package demo.Controllers;

import demo.Entity.User;
import demo.Services.TicketService;
import demo.Services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@RequiredArgsConstructor
@Slf4j
@Controller
public class ShopBasketController {

    @Autowired
    private UserService userService;

    @Autowired
    private TicketService ticketService;

    //вынесенный метод (повторяющиеся строки)
    public User getLoginUser(){
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }


    @GetMapping("/shopBasket")
    public String PrintBasket(Model model) {
        try {
            //Получаем текущего юзера
            User current_user = getLoginUser();
            //отображаем список его билетов
            model.addAttribute("tickets", current_user.getList());
        }catch (Exception e){
            log.error("printBasket failed");
        }
        return "shopBasket";
    }



    @GetMapping("/clear")
    public String ClearBasket(Model model){
        try {
            //получаем текущего юзера
            User current_user = getLoginUser();
            //очищаем список покупок текущего юзера в бд
            ticketService.deleteTickets(current_user.getList());
//            System.out.println(ticketService.GetTickets(current_user.getList()));
            //Очистка у текущего пользователя (для быстрого визуального отображения)
            current_user.getList().clear();
        }catch (Exception e){
            log.error("clear failed");
        }
        return "shopBasket";
    }

    @GetMapping("/order")
    public String GetOrder(){
        try {
            User current_user = getLoginUser();
            String totalPrice = current_user.getTotalCost();
            userService.sendOrder(current_user, current_user.getList(), totalPrice);
//            userService.updateUser(current_user);
            ticketService.deleteTickets(current_user.getList());
            current_user.getList().clear();

        }catch(Exception e){
            log.error("getOrder failed");
        }
        return "shopBasket";
    }


}
