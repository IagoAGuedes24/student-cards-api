package com.studentcardsapi.service.implementations;


import com.studentcardsapi.service.interfaces.AppUserService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@AllArgsConstructor
public class AppUserServiceImpl  implements AppUserService {

}
