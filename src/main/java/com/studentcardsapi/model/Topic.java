package com.studentcardsapi.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;

@Entity
@NoArgsConstructor
@Data

public class Topic extends GenericModel{

    private String name;
}
