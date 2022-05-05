package com.wsmt.middleware.students.service.helper;

import com.wsmt.middleware.students.dao.entity.GradeEntity;
import com.wsmt.middleware.students.dao.entity.StudentSchoolObject;
import com.wsmt.middleware.students.service.model.GradeDTO;

public class GradeTransformerRMIIIOP {
    public static void fillGradeDTO(final GradeEntity input, final GradeDTO target) {
        target.setStudent(StudentTransformerRMIIIOP.transformStudentEntityToDTO(input.getId().getStudent()));
        target.setSchoolObject(SchoolObjectTransformerRMIIIOP.transformSchoolObjectEntityToDTO(input.getId().getSchoolObject()));
        target.setValue(input.getValue());
    }

    public static void fillGradeEntityFromDTO(final GradeDTO input, final GradeEntity target) {
        target.setId(new StudentSchoolObject(StudentTransformerRMIIIOP.transformStudentFromDTO(input.getStudent()),
                SchoolObjectTransformerRMIIIOP.transformSchoolObjectFromDTO(input.getSchoolObject())));
        target.setValue(input.getValue());
    }

    public static GradeDTO transformGradeEntityToDTO(final GradeEntity input) {
        final GradeDTO target = new GradeDTO();
        if (input != null) {
            fillGradeDTO(input, target);
        }
        return target;
    }

    public static GradeEntity transformGradeFromDTO(final GradeDTO input) {
        final GradeEntity target = new GradeEntity();
        if (input != null) {
            fillGradeEntityFromDTO(input, target);
        }
        return target;
    }

    public static GradeDTO[] transformGradeEntitiesToDTO(final GradeEntity[] inputs) {
        final GradeDTO[] targets = new GradeDTO[inputs.length];
        for (int i = 0; i < inputs.length; i++) {
            targets[i] = transformGradeEntityToDTO(inputs[i]);
        }
        return targets;
    }
}
