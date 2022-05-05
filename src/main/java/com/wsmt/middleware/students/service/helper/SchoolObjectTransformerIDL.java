package com.wsmt.middleware.students.service.helper;

import com.wsmt.middleware.students.dao.entity.SchoolObjectEntity;
import com.wsmt.middleware.students.service.abstracts.SchoolObject;

public class SchoolObjectTransformerIDL {
    public static void fillSchoolObjectIDL(final SchoolObjectEntity input, final SchoolObject target) {
        target.name = input.getName();
        target.teacher = input.getTeacher();
    }

    public static void fillSchoolObjectEntityFromIDL(final SchoolObject input, final SchoolObjectEntity target) {
        target.setName(input.name);
        target.setTeacher(input.teacher);
    }

    public static SchoolObject transformSchoolObjectEntityToIDL(final SchoolObjectEntity input) {
        final SchoolObject target = new SchoolObject();
        if (input != null) {
            fillSchoolObjectIDL(input, target);
        }
        return target;
    }

    public static SchoolObjectEntity transformSchoolObjectFromIDL(final SchoolObject input) {
        final SchoolObjectEntity target = new SchoolObjectEntity();
        if (input != null) {
            fillSchoolObjectEntityFromIDL(input, target);
        }
        return target;
    }

    public static SchoolObject[] transformSchoolObjectEntitiesToIDL(final SchoolObjectEntity[] inputs) {
        final SchoolObject[] targets = new SchoolObject[inputs.length];
        for (int i = 0; i < inputs.length; i++) {
            targets[i] = transformSchoolObjectEntityToIDL(inputs[i]);
        }
        return targets;
    }
}
