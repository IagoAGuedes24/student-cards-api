package com.studentcardsapi.service.implementations;

import com.studentcardsapi.repository.AssistantRepository;
import com.studentcardsapi.service.interfaces.AssistantService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AssistantServiceImpl implements AssistantService {

    private AssistantRepository assistantRepository;

}
