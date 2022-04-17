package demo.Entity;


import demo.Services.EmailService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.*;

import static org.aspectj.weaver.tools.cache.SimpleCacheFactory.enabled;

@Entity(name = "user")
@Table(name = "users")
@Data
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE)
    private int id;
    private String username;
    private String password;
    @Transient
    private String passwordConfirm;
    private String email;
    private String role="";



    @OneToMany(mappedBy = "userID", fetch = FetchType.EAGER, orphanRemoval = true)
    private Set<Ticket> list = new HashSet<Ticket>();

    public User() {
    }

    public User(String username, String password, String passwordConfirm, String email) {
        this.username = username;
        this.password = password;
        this.passwordConfirm = passwordConfirm;
        this.email = email;
        this.role = "USER";
    }

    public User(String username, String password, String passwordConfirm, String email,String role) {
        this.username = username;
        this.password = password;
        this.passwordConfirm = passwordConfirm;
        this.email = email;
        this.role = role;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
//        final List<SimpleGrantedAuthority> authorities = new LinkedList<>();
//        if (enabled) {
//            if (this.getUser().isAdmin()) {
//                authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
//            }
//            authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
//        }
//        return authorities;
        return null;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public void setUsername(String username) {
        this.username = username;
    }


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPasswordConfirm() {
        return passwordConfirm;
    }

    public void setPasswordConfirm(String passwordConfirm) {
        this.passwordConfirm = passwordConfirm;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public int getTicketCount(int ticketNumber){
        for (Ticket item : list){
            if (item.getTicketNumber() == ticketNumber){
                return item.getTicketCount();
            }
        }
        return 0;
    }

    public Ticket getTicket(int ticketNumber){
        for (Ticket item: list){
            if (item.getTicketNumber() == ticketNumber)
                return item;
        }
        return null;
    }

    public String getTotalCost(){
        int total = 0;
        for (Ticket item: this.list){
            String temp = item.getPriceForManyTicket().replace(" руб.","");
            System.out.println(temp);
            total += Integer.parseInt(temp);
        }
        return String.valueOf(total) + " руб.";
    }

    @Override
    public String toString() {
        return "\nUser{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
