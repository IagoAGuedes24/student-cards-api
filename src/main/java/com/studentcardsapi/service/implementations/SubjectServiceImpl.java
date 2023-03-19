package com.studentcardsapi.service.implementations;

import com.studentcardsapi.DTO.SubjectDTO;
import com.studentcardsapi.enums.Year;
import com.studentcardsapi.exception.ApiRequestException;
import com.studentcardsapi.model.Subject;
import com.studentcardsapi.repository.SubjectRepository;
import com.studentcardsapi.service.interfaces.SubjectService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.studentcardsapi.utils.messages.ErrorMessages.SUBJECT_ALREADY_EXISTS;

@Service
@AllArgsConstructor
@Slf4j

public class SubjectServiceImpl implements SubjectService {

    private SubjectRepository subjectRepository;

    private ModelMapper modelMapper;

    @Override
    public Subject createSubject(SubjectDTO subjectDTO) {

        log.info("SubjectService.createSubject() is being executed");
        if(this.subjectRepository.findByNameAndYear(subjectDTO.getName(), subjectDTO.getYear()).isPresent()){
            throw new ApiRequestException(SUBJECT_ALREADY_EXISTS);
        }

        log.info("subjectRegistrationDTO is valid");

        Subject subject = this.modelMapper.map(subjectDTO,Subject.class);
        this.subjectRepository.save(subject);

        return subject;
    }

    @Override
    public List<Subject> listAllSubjects(Year year) {
        log.info("SubjectService.listAllSubjects.listAllSubjects() is now being executed.");
        List<Subject> subjectList = this.subjectRepository.findAllByYear(year);
        log.info("Found " + subjectList.size() + "Subjects in the year " + year.name());
        return subjectList;
    }


}
