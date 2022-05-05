package com.wsmt.middleware.students.service.helper;

import com.wsmt.middleware.students.dao.entity.StudentEntity;
import com.wsmt.middleware.students.service.model.StudentDTO;

public class StudentTransformerRMIIIOP {
    public static void fillStudentDTO(final StudentEntity input, final StudentDTO target) {
        target.setName(input.getName());
        target.setGroup(input.getGr());
    }

    public static void fillStudentEntityFromDTO(final StudentDTO input, final StudentEntity target) {
        target.setName(input.getName());
        target.setGr(input.getGroup());
    }

    public static StudentDTO transformStudentEntityToDTO(final StudentEntity input) {
        final StudentDTO target = new StudentDTO();
        if (input != null) {
            fillStudentDTO(input, target);
        }
        return target;
    }

    public static StudentEntity transformStudentFromDTO(final StudentDTO input) {
        final StudentEntity target = new StudentEntity();
        if (input != null) {
            fillStudentEntityFromDTO(input, target);
        }
        return target;
    }

    public static StudentDTO[] transformStudentEntitiesToDTO(final StudentEntity[] inputs) {
        final StudentDTO[] targets = new StudentDTO[inputs.length];
        for (int i = 0; i < inputs.length; i++) {
            targets[i] = transformStudentEntityToDTO(inputs[i]);
        }
        return targets;
    }
}
