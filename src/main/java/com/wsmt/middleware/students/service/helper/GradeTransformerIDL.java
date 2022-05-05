package com.wsmt.middleware.students.service.helper;

import com.wsmt.middleware.students.dao.entity.GradeEntity;
import com.wsmt.middleware.students.dao.entity.StudentSchoolObject;
import com.wsmt.middleware.students.service.abstracts.Grade;

public class GradeTransformerIDL {

    public static void fillGradeIDL(final GradeEntity input, final Grade target) {
        target.schoolObject = SchoolObjectTransformerIDL.transformSchoolObjectEntityToIDL(input.getId().getSchoolObject());
        target.student = StudentTransformerIDL.transformStudentEntityToIDL(input.getId().getStudent());
        target.value = input.getValue();
    }

    public static void fillGradeEntityFromIDL(final Grade input, final GradeEntity target) {
        target.setId(new StudentSchoolObject(StudentTransformerIDL.transformStudentFromIDL(input.student),
                SchoolObjectTransformerIDL.transformSchoolObjectFromIDL(input.schoolObject)));
        target.setValue(input.value);
    }

    public static Grade transformGradeEntityToIDL(final GradeEntity input) {
        final Grade target = new Grade();
        if (input != null) {
            fillGradeIDL(input, target);
        }
        return target;
    }

    public static GradeEntity transformGradeFromIDL(final Grade input) {
        final GradeEntity target = new GradeEntity();
        if (input != null) {
            fillGradeEntityFromIDL(input, target);
        }
        return target;
    }

    public static Grade[] transformGradeEntitiesToIDL(final GradeEntity[] inputs) {
        final Grade[] targets = new Grade[inputs.length];
        for (int i = 0; i < inputs.length; i++) {
            targets[i] = transformGradeEntityToIDL(inputs[i]);
        }
        return targets;
    }
}
