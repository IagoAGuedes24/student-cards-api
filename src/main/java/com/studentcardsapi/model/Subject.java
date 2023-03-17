package com.studentcardsapi.model;

import com.studentcardsapi.enums.Year;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Entity
@NoArgsConstructor
@Data

public class Subject extends GenericModel{

    private String name;

    @Enumerated(EnumType.STRING)
    private Year year;
}
