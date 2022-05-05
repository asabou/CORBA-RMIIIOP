package com.wsmt.middleware.students.service.model;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class GradeDTO implements Serializable {
    private StudentDTO student;
    private SchoolObjectDTO schoolObject;
    private int value;
}
