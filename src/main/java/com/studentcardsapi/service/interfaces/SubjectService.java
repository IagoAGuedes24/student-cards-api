package com.studentcardsapi.service.interfaces;

import com.studentcardsapi.DTO.SubjectDTO;
import com.studentcardsapi.enums.Year;
import com.studentcardsapi.model.Subject;

import java.util.List;

public interface SubjectService {
    Subject createSubject(SubjectDTO subjectDTO);

    List<Subject> listAllSubjects(Year year);
}
