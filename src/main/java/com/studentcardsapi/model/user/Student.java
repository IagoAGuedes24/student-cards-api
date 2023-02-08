package com.studentcardsapi.model.user;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;

@Entity
public class Student extends AppUser {

    @OneToOne
    Assistant assistant;


}
