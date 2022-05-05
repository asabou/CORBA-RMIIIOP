package com.wsmt.middleware.students.dao.entity;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "grades")
public class GradeEntity implements IHasID<StudentSchoolObject> {
    private int value;

    @EmbeddedId
    private StudentSchoolObject id;

    @Override
    public StudentSchoolObject getId() {
        return id;
    }
}
