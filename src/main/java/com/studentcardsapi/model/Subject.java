package com.studentcardsapi.model;

import com.studentcardsapi.enums.Year;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;

@Entity
@NoArgsConstructor
@Data

public class Subject extends GenericModel{

    private String name;
    private Year year;
}
