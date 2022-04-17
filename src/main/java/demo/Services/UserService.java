package demo.Services;


import demo.Entity.Ticket;
import demo.Entity.User;
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
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
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
    public void updateUser(User user){
        userRepository.save(user);
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
        user.setRole("USER");
        userRepository.save(user);
        return true;
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


