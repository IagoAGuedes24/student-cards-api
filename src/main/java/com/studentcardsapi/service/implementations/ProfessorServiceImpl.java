package com.studentcardsapi.service.implementations;

import com.studentcardsapi.repository.ProfessorRepository;
import com.studentcardsapi.service.interfaces.ProfessorService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ProfessorServiceImpl implements ProfessorService {

    private ProfessorRepository professorRepository;

}
