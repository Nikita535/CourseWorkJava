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
        return "img/products/"+String.valueOf(ticketNumber) + ".jpeg";
    }




    public String getPriceForManyTicket() {
        return switch (this.ticketNumber) {
            case 1 -> String.valueOf(10000*ticketCount) + " руб.";
            case 2 -> String.valueOf(12000*ticketCount) + " руб.";
            case 3 -> String.valueOf(13000*ticketCount) + " руб.";
            case 4 -> String.valueOf(14000*ticketCount) + " руб.";
            case 5 -> String.valueOf(15000*ticketCount) + " руб.";
            case 6 -> String.valueOf(16000*ticketCount) + " руб.";
            default -> "";
        };
    }


    public String getPriceForOneTicket(){
        return switch (this.ticketNumber) {
            case 1 -> String.valueOf(10000) + " руб.";
            case 2 -> String.valueOf(12000) + " руб.";
            case 3 -> String.valueOf(13000) + " руб.";
            case 4 -> String.valueOf(14000) + " руб.";
            case 5 -> String.valueOf(15000) + " руб.";
            case 6 -> String.valueOf(16000) + " руб.";
            default -> "";
        };
    }

    public String getName(){
        return switch (this.ticketNumber) {
            case 1 -> "Билет в Ереван";
            case 2 -> "Билет в Сочи";
            case 3 -> "Билет в Ташкент";
            case 4 -> "Билет в Стамбул";
            case 5 -> "Билет в Каир";
            case 6 -> "Билет в Тунис";
            default -> "";
        };
    }

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

