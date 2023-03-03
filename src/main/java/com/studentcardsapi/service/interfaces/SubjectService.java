package com.studentcardsapi.service.interfaces;

import com.studentcardsapi.DTO.SubjectRegistrationDTO;
import com.studentcardsapi.model.Subject;

public interface SubjectService {
    Subject createSubject(SubjectRegistrationDTO subjectRegistrationDTO);
}
