package com.studentcardsapi.DTO;

import com.studentcardsapi.enums.AppUserRole;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class UserDTO {

    private String name;

    private String username;

    private AppUserRole userRole;

}
