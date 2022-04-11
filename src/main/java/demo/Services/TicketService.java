package demo.Services;

import demo.Entity.Ticket;
import demo.Entity.User;
import demo.Repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Set;

@Service
public class TicketService {
    @Autowired
    private TicketRepository ticketRepository;

    @Transactional
    public List<Ticket> findCartByUserID(User user){
        return ticketRepository.findAllByUserID(user);
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
    public void deleteCarts(Set<Ticket> ticketSet){
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
