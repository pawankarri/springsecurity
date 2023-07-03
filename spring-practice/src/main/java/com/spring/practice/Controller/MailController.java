package com.spring.practice.Controller;

import com.spring.practice.dto.Emaildto;
import com.spring.practice.entites.User;
import com.spring.practice.entites.UserInfo;
import com.spring.practice.mail.MailServiceImpl;
import com.spring.practice.repository.UserRepository;
import com.spring.practice.serviceImpl.UserServiceImpl;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/mail")
public class MailController {
    @Autowired
    private MailServiceImpl mailService;


    @PostMapping("sendMails")
    public String sendAttachments(@RequestParam String email,
                                  @RequestParam String subject,
                                  @RequestParam String body,
                                  @RequestParam("file") MultipartFile file) throws IOException, MessagingException {
        if(file.isEmpty()||file==null){
            this.mailService.sendMail(email,subject,body);
            return "Mail sent successfully";
        }
        else{
            byte[] attachmentData = file.getBytes();
            String attachmentName = file.getOriginalFilename();
            this.mailService.sendAttachments(email,subject,body,attachmentData,attachmentName);
            return "Mail sent successfully with attachments";
        }


    }
    @PostMapping("/forgot-mail")
    public ResponseEntity<Map<String,Object>> forgotMail(@RequestBody UserInfo details) {
        if(StringUtils.isEmpty(details.getEmail())){
            Map<String,Object> map=new HashMap<>();
            map.put("message","please provide your mailId");
            return ResponseEntity.ok(map);
        }
        else{

        /*  LoginDetails details1=new LoginDetails(details.getEmail());
          String baseUrl="http://localhost:8010/Security/resetPassword";
          serviceClass.forgotPassword(details.getEmail(),baseUrl);*/
            //String password= UUID.randomUUID().toString().substring(0, 8);
            String password=this.mailService.generateRandomPassword(8,48,122);
            System.out.println(password);
            mailService.forgotPassword(details.getEmail(), password);
        }
        Map<String,Object> map=new HashMap<>();
        map.put("message","sent successfully");
        return ResponseEntity.ok(map);
    }










    }

