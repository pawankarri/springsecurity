package com.spring.practice.Controller;

import com.spring.practice.serviceImpl.OtpServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Random;

@RestController
@RequestMapping("/api/v1/otp")
public class OtpController {
    @Autowired
    private OtpServiceImpl otpService;

    @PostMapping("/send-otp")
    public void sendOtp(@RequestParam String phoneNumber) {

        // Send the OTP via SMS
        otpService.sendOtp(phoneNumber);
    }
}

