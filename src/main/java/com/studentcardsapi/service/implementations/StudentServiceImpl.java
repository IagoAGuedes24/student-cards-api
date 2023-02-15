package com.studentcardsapi.service.implementations;

import com.studentcardsapi.repository.StudentRepository;
import com.studentcardsapi.service.interfaces.StudentService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class StudentServiceImpl implements StudentService {

    private StudentRepository studentRepository;
}
