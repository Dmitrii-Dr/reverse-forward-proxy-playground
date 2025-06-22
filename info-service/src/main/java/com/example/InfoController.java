package com.example;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RestController
public class InfoController {

   @GetMapping("/info")
   public String getInfo() {
       // Add current timestamp to log
       LocalDateTime now = LocalDateTime.now();
       DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
       String formattedTime = now.format(formatter);
       System.out.println("Current Timestamp: " + formattedTime);
       
       return formattedTime + ": Info Service is running";
   }
}