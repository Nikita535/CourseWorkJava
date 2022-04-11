package demo.Entity;


import javax.persistence.*;

@Entity(name = "ticket")
@Table(name = "tickets")
public class Ticket {
    @Id
    @GeneratedValue(strategy= GenerationType.SEQUENCE)
    private int id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User userID;

    private int ticketNumber;
    private int ticketCount=1;

    public int getTicketCount() {
        return ticketCount;
    }

    public void setTicketCount(int ticketCount) {
        this.ticketCount = ticketCount;
    }

    public Ticket(User userID, int ticketNumber) {
        this.userID = userID;
        this.ticketNumber = ticketNumber;
    }

    public Ticket() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUserID() {
        return userID;
    }

    public String getPhotoAddress(){
        return String.valueOf(ticketNumber) + ".jpg";
    }

//    public String getPrice(boolean totalCost){
//        switch (this.ticketNumber){
//            case 1:
//                if (totalCost)
//                    return String.valueOf(1500 * ticketCount) + " руб.";
//                else
//                    return String.valueOf(1500) + " руб.";
//            case 2:
//                if (totalCost)
//                    return String.valueOf(1800 * ticketCount) + " руб.";
//                else
//                    return String.valueOf(1800) + " руб.";
//            case 3:
//                if (totalCost)
//                    return String.valueOf(2300 * ticketCount) + " руб.";
//                else
//                    return String.valueOf(2300) + " руб.";
//            case 4:
//                if (totalCost)
//                    return String.valueOf(5000 * ticketCount) + " руб.";
//                else
//                    return String.valueOf(5000) + " руб.";
//            case 5:
//                if (totalCost)
//                    return String.valueOf(3100 * ticketCount) + " руб.";
//                else
//                    return String.valueOf(3100) + " руб.";
//            case 6:
//                if (totalCost)
//                    return String.valueOf(2800 * ticketCount) + " руб.";
//                else
//                    return String.valueOf(2800) + " руб.";
//            default:
//                return "Неизвестно";
//        }
//    }
//
//    public String getName(){
//        switch (this.ticketNumber){
//            case 1:
//                return "Букет Микс";
//            case 2:
//                return "Букет Комплимент";
//            case 3:
//                return "Букет Свадебный";
//            case 4:
//                return "Букет Джентельмен";
//            case 5:
//                return "Букет Вьюга";
//            case 6:
//                return "Букет Джунгли";
//            default:
//                return "Неизвестный букет";
//        }
//    }

    @Override
    public String toString() {
        return "Cart{" +
                "id=" + id +
                ", userID=" + userID +
                ", bouquetNumber=" + ticketNumber +
                ", bouquetCount=" + ticketCount +
                '}';
    }

    public void setUserID(User userID) {
        this.userID = userID;
    }

    public int getTicketNumber() {
        return ticketNumber;
    }

    public void setTicketNumber(int ticketNumber) {
        this.ticketNumber = ticketNumber;
    }
}

