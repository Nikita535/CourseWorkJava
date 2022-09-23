package demo;

import demo.Entity.User;
import demo.Repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;


import static org.assertj.core.api.Java6Assertions.assertThat;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@ExtendWith(MockitoExtension.class)
@DataJpaTest
@RunWith(SpringRunner.class)
@TestPropertySource("/application_test.properties")
@AutoConfigureTestDatabase (replace = AutoConfigureTestDatabase.Replace.NONE)
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void initUseCase() {
        List<User> customers = Arrays.asList(
                new User("ADMIN","ADMIN","ADMIN","nzhigulevskiy@bk.ru")
        );
        userRepository.saveAll(customers);
    }

    @AfterEach
    public void destroyAll(){
        userRepository.deleteAll();
    }

    @Test
    public void saveAll_success() {
        List<User> customers = Arrays.asList(
                new User("Nikita","Nikita","Nikita","aaaaaaaaa@bk.ru"),
                new User("Renat","Renat","Renat","bbbbbbbbb@bk.ru"),
                new User("Maxim","Maxim","Maxim","cccccccc@bk.ru")
        );
        Iterable<User> allCustomer = userRepository.saveAll(customers);

        AtomicInteger validIdFound = new AtomicInteger();
        allCustomer.forEach(customer -> {
            if(customer.getId()>0){
                validIdFound.getAndIncrement();
            }
        });

        assertThat(validIdFound.intValue()).isEqualTo(3);
    }

    @Test
    public void findAll_success() {
        List<User> allCustomer = userRepository.findAll();
        assertThat(allCustomer.size()).isGreaterThanOrEqualTo(1);
    }
}
