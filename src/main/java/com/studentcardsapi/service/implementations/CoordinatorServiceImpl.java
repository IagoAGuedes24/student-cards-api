package com.studentcardsapi.service.implementations;

import com.studentcardsapi.repository.CoordinatorRepository;
import com.studentcardsapi.service.interfaces.CoordinatorService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CoordinatorServiceImpl implements CoordinatorService {

    private CoordinatorRepository coordinatorRepository;

}
