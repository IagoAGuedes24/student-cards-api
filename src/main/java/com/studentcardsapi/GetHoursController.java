package com.studentcardsapi;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping(path = "/api")
@AllArgsConstructor

public class GetHoursController {
   @GetMapping ("/brhours")
   public ResponseEntity<?> brHours(){

      String hora = String.valueOf(LocalDateTime.now());


   return new ResponseEntity<>(hora , HttpStatus.OK);
   }

}
