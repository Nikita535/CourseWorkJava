package demo.Controllers;


import demo.Entity.User;
import demo.Services.EmailService;
import demo.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserProfileController {


    @Autowired
    private UserService userService;
    @Autowired
    private EmailService emailService;

    //Смена ника
    @GetMapping("/UserProfile_username")
    public String changeUsername(Model model){
        //Визуально отображаем поле для ввода
        model.addAttribute("changeUsername", true);
        return "UserProfile";
    }
    @PostMapping("/UserProfile_username")
    public String changeUsername(@ModelAttribute("username_input") String username, Model model){
        //Проверяем наличие пользователя с новым ником в базе
        //Если такой есть, то отображаем ошибку
        //Если нет, то выходим из аккаунта logout и просим авторизоваться с новым ником
        User user = userService.findUserByUsername(username);
        if (user != null){
            //Ошибка о наличии такого пользователя
            model.addAttribute("userAlreadyExists", true);
            //Отображение ошибки
            model.addAttribute("message", "Пользователь с таким ником уже существует");
            return "UserProfile";
        }

        //Создание нового объекта user из базы
        User changed_user = userService.findUserByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        //Установка ему нового ника
        changed_user.setUsername(username);
        if (!StringUtils.isEmpty(changed_user.getEmail())){
            //Отправка сообщения об изменении пользователю на почту
            String message = "Здравствуйте, ваш никнейм был изменён с "+SecurityContextHolder.getContext().getAuthentication().getName()+" на "+changed_user.getUsername()+"!";
            emailService.sendSimpleMessage(changed_user.getEmail(), message);
        }
        //Сохранение в базе с новым ником
        userService.resaveUser(changed_user);
        //Выходи из аккаунта
        return "redirect:/logout";
    }


    //Смена пароля
    @GetMapping("/UserProfile_password")
    public String chUser(Model model){

        //Визуально отображаем поле для ввода
        model.addAttribute("changeUsername", true);
        return "UserProfile";
    }
    @PostMapping("/UserProfile_username")
    public String confUser(@ModelAttribute("username_input") String username, Model model){
        //Проверяем наличие пользователя с новым ником в базе
        //Если такой есть, то отображаем ошибку
        //Если нет, то выходим из аккаунта logout и просим авторизоваться с новым ником
        User user = userService.findUserByUsername(username);
        if (user != null){
            //Ошибка о наличии такого пользователя
            model.addAttribute("userAlreadyExists", true);
            //Отображение ошибки
            model.addAttribute("message", "Пользователь с таким ником уже существует");
            return "UserProfile";
        }

        //Создание нового объекта user из базы
        User changed_user = userService.findUserByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        //Установка ему нового ника
        changed_user.setUsername(username);
        if (!StringUtils.isEmpty(changed_user.getEmail())){
            //Отправка сообщения об изменении пользователю на почту
            String message = "Здравствуйте, ваш никнейм был изменён с "+SecurityContextHolder.getContext().getAuthentication().getName()+" на "+changed_user.getUsername()+"!";
            emailService.sendSimpleMessage(changed_user.getEmail(), message);
        }
        //Сохранение в базе с новым ником
        userService.resaveUser(changed_user);
        //Выходи из аккаунта
        return "redirect:/logout";
    }
}
