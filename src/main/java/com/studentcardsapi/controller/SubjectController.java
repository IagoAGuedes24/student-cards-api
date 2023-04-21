package com.studentcardsapi.controller;

import com.studentcardsapi.DTO.SubjectDTO;
import com.studentcardsapi.enums.Year;
import com.studentcardsapi.model.Subject;
import com.studentcardsapi.service.interfaces.SubjectService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.studentcardsapi.utils.constants.EndpointConstants.*;

@RestController
@RequestMapping(path = API + SUBJECT)
@AllArgsConstructor
@Slf4j

public class SubjectController {

    SubjectService subjectService;

    @PostMapping(CREATION)
    public ResponseEntity<?> createsSubject(@RequestBody SubjectDTO subjectDTO){
        log.info("Starting Creation of subject " + subjectDTO.getName());
        Subject subject = this.subjectService.createSubject(subjectDTO);

        return new ResponseEntity<>(subject, HttpStatus.CREATED);
    }

    @GetMapping(YEAR + LIST_ALL)
    public ResponseEntity<?> listAllSubjects(@PathVariable Year year) {
        log.info("SubjectController.listAllSubjects() is now being executed");
        return new ResponseEntity<>(this.subjectService.listAllSubjects(year), HttpStatus.OK);
    }

    @GetMapping(ID)
    public ResponseEntity<?> viewSubject(@PathVariable Long id) {
        log.info("SubjectController.viewSubject() is now being executed");
        return new ResponseEntity<>(this.subjectService.viewSubject(id), HttpStatus.OK);
    }
}
