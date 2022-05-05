package com.wsmt.middleware.students.service.helper;

import com.wsmt.middleware.students.dao.entity.StudentEntity;
import com.wsmt.middleware.students.service.abstracts.Student;

public class StudentTransformerIDL {
    public static void fillStudentIDL(final StudentEntity input, final Student target) {
        target.name = input.getName();
        target.group = input.getGr();
    }

    public static void fillStudentEntityFromIDL(final Student input, final StudentEntity target) {
        target.setName(input.name);
        target.setGr(input.group);
    }

    public static Student transformStudentEntityToIDL(final StudentEntity input) {
        final Student target = new Student();
        if (input != null) {
            fillStudentIDL(input, target);
        }
        return target;
    }

    public static StudentEntity transformStudentFromIDL(final Student input) {
        final StudentEntity target = new StudentEntity();
        if (input != null) {
            fillStudentEntityFromIDL(input, target);
        }
        return target;
    }

    public static Student[] transformStudentEntitiesToIDL(final StudentEntity[] inputs) {
        final Student[] targets = new Student[inputs.length];
        for (int i = 0; i < inputs.length; i++) {
            targets[i] = transformStudentEntityToIDL(inputs[i]);
        }
        return targets;
    }
}
