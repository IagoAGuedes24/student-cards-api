package com.studentcardsapi.controller;

import com.studentcardsapi.DTO.SubjectRegistrationDTO;
import com.studentcardsapi.model.Subject;
import com.studentcardsapi.service.interfaces.SubjectService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.studentcardsapi.utils.constants.EndpointConstants.*;

@RestController
@RequestMapping(path = API + COORDINATOR)
@AllArgsConstructor
@Slf4j

public class CoordinatorController {

    SubjectService subjectService;

    @PostMapping(SUBJECT_CREATION)
    public ResponseEntity<?> createsSubject(@RequestBody SubjectRegistrationDTO subjectRegistrationDTO){
        log.info("Starting Creation of subject " + subjectRegistrationDTO.getName());
        Subject subject = this.subjectService.createSubject(subjectRegistrationDTO);


        return new ResponseEntity<>(subject, HttpStatus.CREATED);
    }

}
