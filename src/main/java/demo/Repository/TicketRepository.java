package demo.Repository;

import demo.Entity.Ticket;
import demo.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TicketRepository extends JpaRepository<Ticket, Integer> {
    List<Ticket> findAllByUserID(User user);
    Ticket findTicketByUserIDAndTicketNumber(User user, int ticketNumber);
}
