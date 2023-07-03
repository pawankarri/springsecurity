package com.spring.practice.mail;

import com.spring.practice.dto.Emaildto;
import jakarta.mail.MessagingException;

public interface MailService {
    public void sendAttachments(String email, String subject, String body, byte[] attachmentData, String attachmentName) throws MessagingException;
    public void forgotPassword(String email,String resetUrl);
}
