package demo.Controllers;


import demo.Entity.User;
import demo.Services.EmailService;
import demo.Services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
@Slf4j
public class userProfileController {

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private UserService userService;
    @Autowired
    private EmailService emailService;

    //Смена ника
    @GetMapping("/UserProfile_username")
    public String changeUsername(Model model){
        //Визуально отображаем поле для ввода
        model.addAttribute("changeUsername", true);
        return "userProfile";
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
            log.warn("failed update username cz this user is already exists");
            return "userProfile";
        }

        //Создание нового объекта user из базы
        User changed_user = userService.findUserByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        //Установка ему нового ника
        changed_user.setUsername(username);
        if (!StringUtils.isEmpty(changed_user.getEmail())){
            //Отправка сообщения об изменении пользователю на почту
            String message = "Здравствуйте, ваш никнейм был изменён с "+SecurityContextHolder.getContext().getAuthentication().getName()+" на "+changed_user.getUsername()+"!";
            emailService.sendSimpleMessage(changed_user.getEmail(), message);
        }else {log.error("email is NULL");}
        //Сохранение в базе с новым ником
        userService.updateUser(changed_user);
        log.info("username changed on "+changed_user.getUsername());
        //Выходи из аккаунта
        return "redirect:/logout";
    }


    //Смена пароля
    @GetMapping("/UserProfile_password")
    public String changePassword(Model model){
        //Визуально отображаем поле для ввода
        model.addAttribute("changePassword", true);
        return "userProfile";
    }
    @PostMapping("/UserProfile_password")
    public String changePassword(@ModelAttribute("newPassword") String newPassword,
                                    @ModelAttribute("newPasswordConfirm") String newPasswordConfirm,
                           @ModelAttribute("oldPassword") String oldPassword,
                                Model model){
        //Берём текущего аунтифицированного пользователя, тк искать по паролю не можем
        User current_user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();


//        String oldPasswordEncode = bCryptPasswordEncoder.matches(oldPassword)

        if (!bCryptPasswordEncoder.matches(oldPassword,current_user.getPassword())){
            model.addAttribute("errorSetting", true);
            model.addAttribute("message", "Неправильный старый пароль");
            log.warn("Старый пароль не свопал");
            return "userProfile";
        }

        //Проверка пароля для сложность
        if (newPassword.length() < 5){
            model.addAttribute("errorSetting", true);
            model.addAttribute("message", "Слишком простой пароль");
            log.warn("error pass length");
            return "userProfile";
        }
        //Проверка пароля на соответсвтие
        if (!newPassword.equals(newPasswordConfirm)){
            model.addAttribute("errorSetting", true);
            model.addAttribute("message", "Пароли не совпадают");
            log.warn("error pass confirm");
            return "userProfile";
        }
        //Установка нового пароля
        current_user.setPassword(newPassword);
        current_user.setPasswordConfirm(newPasswordConfirm);

        if (!StringUtils.isEmpty(current_user.getEmail())){
            //Отправка сообщения об изменении пользователю на почту
            String message = "Здравствуйте, " + current_user.getUsername()+". Ваш пароль был изменён. Убедитесь, что это сделали вы!";
            emailService.sendSimpleMessage(current_user.getEmail(), message);
        }else {log.error("email is NULL");}

        userService.updateUserPassword(current_user);
        log.info("pass update");
        return "redirect:/logout";
    }


    //Смена почты
    @GetMapping("/UserProfile_email")
    public String changeEmail(Model model){
        model.addAttribute("changeEmail", true);
        return "userProfile";
    }

    @PostMapping("/UserProfile_email")
    public String changeEmail(@ModelAttribute("newEmail") String newEmail){
        User current_user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (!StringUtils.isEmpty(current_user.getEmail())){
            //Отправка сообщения об изменении пользователю на почту
            String message = "Здравствуйте, ваша почта была изменена на " +newEmail;
            emailService.sendSimpleMessage(current_user.getEmail(), message);
        }else {log.error("email is NULL");}

        current_user.setEmail(newEmail);
        userService.updateUser(current_user);
        log.info("email changed");
        return "redirect:/logout";
    }



}
