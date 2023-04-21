package com.studentcardsapi.model;

import com.studentcardsapi.enums.Year;
import com.studentcardsapi.model.user.Professor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@NoArgsConstructor
@Data

public class Subject extends GenericModel{

    private String name;

    @Enumerated(EnumType.STRING)
    private Year year;

    @ManyToMany
    private List<Professor> professors;

    @OneToMany
    private List<Topic> topics;


}
