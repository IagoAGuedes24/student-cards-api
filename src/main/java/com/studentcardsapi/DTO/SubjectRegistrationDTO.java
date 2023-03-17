package com.studentcardsapi.DTO;

import com.studentcardsapi.enums.Year;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class SubjectRegistrationDTO {

    private String name;
    private Year year;
}
