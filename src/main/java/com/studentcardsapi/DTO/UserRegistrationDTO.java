package com.studentcardsapi.DTO;

import com.studentcardsapi.enums.AppUserRole;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString

public class UserRegistrationDTO {

    private String name;

    private AppUserRole userRole;
    private String cpf;

    private String username;

}
