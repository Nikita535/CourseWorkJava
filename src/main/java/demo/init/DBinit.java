package demo.init;

import demo.Entity.User;
import demo.Enums.Role;
import demo.Repository.UserRepository;
import demo.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class DBinit implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private UserService userService;

    public DBinit(UserRepository userRepository,BCryptPasswordEncoder bCryptPasswordEncoder,UserService userService) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.userService = userService;
    }

    @Override
    public void run(String... args) throws Exception {
        String password = bCryptPasswordEncoder.encode("ADMIN");
        User admin = new User("ADMIN",password,password,"nzhigulevskiy@bk.ru");
        admin.getRoles().add(Role.ROLE_ADMIN);
        admin.setActive(true);
        if (userRepository.findByUsername("ADMIN")==null) {
            userRepository.save(admin);
        }
        System.out.println(admin.getRoles().toArray()[0].toString().getClass());
    }
}
