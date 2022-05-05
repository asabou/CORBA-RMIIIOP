package com.wsmt.middleware.students.service.model;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class StudentDTO implements Serializable {
    private String name;
    private String group;
}
