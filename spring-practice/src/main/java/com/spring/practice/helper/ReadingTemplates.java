package com.spring.practice.helper;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
@Service
public class ReadingTemplates {

//    @Value("${files.storage}")
//    public String folderLocation;


//    private String readingMailTemplateFromText(String fileName) throws IOException {
//
//
//            String fullFileName = folderLocation+"templates/" + fileName + ".txt";
//            File file = ResourceUtils.getFile(fullFileName);
//            return new String(Files.readAllBytes(file.toPath()));
//
//    }

//    public String generateForgotPasswordMailtext(String fileName, String name, String password) throws IOException {
//        String mailText = readingMailTemplateFromText(fileName);
//        mailText = mailText.replace("empName", name);
//        mailText = mailText.replace("Password-Text", password);
//
//        return mailText;
//    }

}
