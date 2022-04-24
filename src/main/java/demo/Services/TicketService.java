package demo.Services;

import demo.Entity.Ticket;
import demo.Entity.User;
import demo.Repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Set;

@Service
public class TicketService {
    @Autowired
    private TicketRepository ticketRepository;
    @Autowired
    private UserService userService;

    @Transactional
    public List<Ticket> findAllByUserID(User user){
        return ticketRepository.findAllByUserID(user);
    }

    @Transactional
    public void changeCountTickets(User user, int ticketNumber, boolean plus){
        if (user.getTicketCount(ticketNumber) == 0){
            if (!plus) return;
            Ticket ticket = new Ticket(user,ticketNumber);
            addTicket(ticket,user);
        }   else {
            Ticket ticket = user.getTicket(ticketNumber);
            //Чтобы не хранить 0 в бд
            if (user.getTicketCount(ticketNumber) == 1 & !plus){
                deleteTicketfromUser(ticket,user);
            }
            else
                changeCountOfTicket(ticket, plus);
        }

    }


    public void addTicket(Ticket ticket,User user){
        user.getList().add(ticket);
        saveTicket(ticket);
    }

    @Transactional
    public void deleteTicketfromUser(Ticket ticket,User user){
        user.getList().remove(ticket);
        deleteTicket(ticket);
    }

    @Transactional
    public void saveTicket(Ticket ticket){
        ticketRepository.save(ticket);
    }

    @Transactional
    public Ticket findTicketByUserAndTNumber(User user, int ticket_number){
        return ticketRepository.findTicketByUserIDAndTicketNumber(user, ticket_number);
    }

    @Transactional
    public void deleteTicket(Ticket ticket){
        ticketRepository.deleteById(ticket.getId());
    }

    @Transactional
    public void deleteTickets(Set<Ticket> ticketSet){
        ticketRepository.deleteAll(ticketSet);
    }

    @Transactional
    public void changeCountOfTicket(Ticket ticket, boolean plus){
        if (plus){
            ticket.setTicketCount(ticket.getTicketCount() + 1);
        } else
            ticket.setTicketCount(ticket.getTicketCount() - 1);
        ticketRepository.save(ticket);
    }


}
