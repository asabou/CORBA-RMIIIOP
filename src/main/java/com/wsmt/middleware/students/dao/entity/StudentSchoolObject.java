package com.wsmt.middleware.students.dao.entity;

import lombok.*;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class StudentSchoolObject implements Serializable {
    @ManyToOne(optional = false)
    @JoinColumn(name = "student_name_id", nullable = false)
    private StudentEntity student;

    @ManyToOne(optional = false)
    @JoinColumn(name = "school_object_id", nullable = false)
    private SchoolObjectEntity schoolObject;
}
