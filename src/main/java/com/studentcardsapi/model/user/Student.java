package com.studentcardsapi.model.user;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

@Entity
public class Student extends AppUser {

    @OneToOne
    Assistant assistant;


}
