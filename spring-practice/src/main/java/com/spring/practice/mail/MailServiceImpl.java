package com.spring.practice.mail;

import com.spring.practice.dto.Emaildto;
import com.spring.practice.entites.UserInfo;
import com.spring.practice.repository.UserInfoRepository;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;

@Service
public class MailServiceImpl implements  MailService{
    @Autowired
    private JavaMailSender javaMailSender;
    @Autowired
    private UserInfoRepository userInfoRepository;

    public void sendAttachments(String email, String subject, String body, byte[] attachmentData, String attachmentName) throws MessagingException {
        MimeMessage mail=javaMailSender.createMimeMessage();
        MimeMessageHelper helper=new MimeMessageHelper(mail,true);
        helper.setTo(email);
        helper.setSubject(subject);
        helper.setText(body);

        ByteArrayResource resource=new ByteArrayResource(attachmentData);
        helper.addAttachment(attachmentName,resource);
        javaMailSender.send(mail);
    }
    public String sendMail(String email,String subject,String body){
        SimpleMailMessage message=new SimpleMailMessage();
        message.setTo(email);
        message.setSubject(subject);
        message.setText(body);
        javaMailSender.send(message);
        return "mail sent successfully";

    }
    public void forgotPassword(String email,String resetUrl){
        UserInfo details=this.userInfoRepository.findByEmail(email);

        if(details!=null){
            SimpleMailMessage message=new SimpleMailMessage();
            message.setTo(email);
            message.setSubject("Forgot-password-mail");
            message.setText(resetUrl);
            javaMailSender.send(message);
        }
        else{
            throw new RuntimeException("Please provide correct emailId");
        }

    }
    public static String generateRandomPassword(int len, int randNumOrigin, int randNumBound) {
        SecureRandom random = new SecureRandom();
        return random.ints(randNumOrigin, randNumBound + 1)
                .filter(i -> Character.isAlphabetic(i) || Character.isDigit(i)).limit(len)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append).toString();
    }
}
