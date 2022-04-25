package demo.Services;


import demo.Entity.Ticket;
import demo.Entity.User;
import demo.Enums.Role;
import demo.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Objects;
import java.util.Set;


@RequiredArgsConstructor
@Slf4j
@Service
public class UserService implements UserDetailsService {
    @PersistenceContext
    private EntityManager em;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    EmailService emailService;


    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);

        if (user == null){
            log.error("error login");
            throw new UsernameNotFoundException("User not found");
        }

        return user;
    }

    @Transactional
    public User findUserByUsername(String username){
        return userRepository.findByUsername(username);
    }



    @Transactional
    public void updateUserPassword(User user){
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }


    @Transactional
    public boolean saveUser(User user){
        User userFromDB = userRepository.findByUsername(user.getUsername());

        if (userFromDB != null){
            return false;
        }

        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.getRoles().add(Role.ROLE_USER);
        user.setActive(true);
        userRepository.save(user);
        return true;
    }

    public List<User> showAllUser() {
        return userRepository.findUserBy();
    }


    @Transactional
    public String validateRegister(User user, Model model){

        if (!Objects.equals(user.getPassword(), user.getPasswordConfirm())){
            model.addAttribute("errorConfPassword", true);
            log.warn("error confirm pass");
            return "register";
        }
        if (user.getPassword().length() < 5){
            model.addAttribute("errorLenPassword", true);
            log.warn("error pass length");
            return "register";
        }
        if (findUserByUsername(user.getUsername()) != null){
            model.addAttribute("errorAlreadyExistsUsername", true);
            log.warn("error user already exists");
            return "register";
        }
        try{
            saveUser(user);
            log.info("user add");
            if (!StringUtils.isEmpty(user.getEmail())){
                String message = "Здравствуйте, "+user.getUsername()+"! Рады приветствовать вас на нашем сервисе. Удачных путешествий!";
                emailService.sendSimpleMessage(user.getEmail(), message);
            }else {log.error("email is NULL");}
            return "redirect:/login";
        } catch (Exception e){
            log.error(e.getClass().toString());
            return "register";
        }
    }

    @Transactional
    public void sendOrder(User user, Set<Ticket> tickets,String totalPrice){
        StringBuilder message= new StringBuilder("Спасибо за ваш заказ!\n");

        for(Ticket ticket: tickets){
            message.append(ticket.getName()).append(" | ")
                    .append(ticket.getPriceForOneTicket())
                    .append(" | ")
                    .append(ticket.getTicketCount())
                    .append(" билетов | ")
                    .append(ticket.getPriceForManyTicket());
        }
        message.append("\nОбщая сумма заказа: ").append(totalPrice);

        emailService.sendSimpleMessage(user.getEmail(), message.toString());
    }




}


