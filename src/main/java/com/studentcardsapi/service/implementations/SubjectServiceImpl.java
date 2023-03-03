package com.studentcardsapi.service.implementations;

import com.studentcardsapi.DTO.SubjectRegistrationDTO;
import com.studentcardsapi.exception.ApiRequestException;
import com.studentcardsapi.model.Subject;
import com.studentcardsapi.repository.SubjectRepository;
import com.studentcardsapi.service.interfaces.SubjectService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import static com.studentcardsapi.utils.messages.ErrorMessages.SUBJECT_ALREADY_EXISTS;

@Service
@AllArgsConstructor
@Slf4j

public class SubjectServiceImpl implements SubjectService {

    private SubjectRepository subjectRepository;

    private ModelMapper modelMapper;

    @Override
    public Subject createSubject(SubjectRegistrationDTO subjectRegistrationDTO) {

        log.info("SubjectService.createSubject() is being executed");
        if(this.subjectRepository.findByNameAndYear(subjectRegistrationDTO.getName(),subjectRegistrationDTO.getYear().name()).isPresent()){
            throw new ApiRequestException(SUBJECT_ALREADY_EXISTS);
        }

        log.info("subjectRegistrationDTO is valid");

        Subject subject = this.modelMapper.map(subjectRegistrationDTO,Subject.class);
        this.subjectRepository.save(subject);

        return subject;
    }


}
