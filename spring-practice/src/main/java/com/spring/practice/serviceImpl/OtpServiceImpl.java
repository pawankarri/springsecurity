package com.spring.practice.serviceImpl;

import com.spring.practice.entites.User;
import com.spring.practice.repository.UserRepository;
import com.spring.practice.service.OtpService;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class OtpServiceImpl implements OtpService {
    @Autowired
    private UserRepository userRepository;
    @Value("${twilio.accountSid}")
    private String accountSid;

    @Value("${twilio.authToken}")
    private String authToken;

    @Value("${twilio.phoneNumber}")
    private String phoneNumber;
@Override
    public void sendOtp(String recipientPhoneNumber) {
    String otp=generateOTP(4);
    User user = this.userRepository.findByPhoneNumber(recipientPhoneNumber);
    if (user!= null) {
        Twilio.init(accountSid, authToken);

        Message message = Message.creator(
                        new PhoneNumber(user.getPhoneNumber()),
                        new PhoneNumber(phoneNumber),
                        "Your OTP is: " + otp)
                .create();
        System.out.println("OTP sent: " + otp);
    }
    else{
        throw new RuntimeException("mobile number invalid");
    }
}
    public String generateOTP(int length) {
        // Define the characters that can be used in the OTP
        String allowedChars = "0123456789";

        Random random = new Random();
        StringBuilder otp = new StringBuilder();

        for (int i = 0; i < length; i++) {
            // Generate a random index to pick a character from allowedChars
            int index = random.nextInt(allowedChars.length());

            // Append the randomly picked character to the OTP
            otp.append(allowedChars.charAt(index));
        }

        return otp.toString();
    }
}

