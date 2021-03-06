package demo.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;


@EnableAsync
@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;


    @Async
    public void sendSimpleMessage(String To,String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(To);
        message.setText(text);
        message.setSubject("Сообщение от комапании S7");
        mailSender.send(message);

    }
}
