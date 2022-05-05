package com.wsmt.middleware.students.service.helper;

import com.wsmt.middleware.students.dao.entity.SchoolObjectEntity;
import com.wsmt.middleware.students.service.model.SchoolObjectDTO;

public class SchoolObjectTransformerRMIIIOP {
    public static void fillSchoolObjectDTO(final SchoolObjectEntity input, final SchoolObjectDTO target) {
        target.setName(input.getName());
        target.setTeacher(input.getTeacher());
    }


    public static void fillSchoolObjectEntityFromDTO(final SchoolObjectDTO input, final SchoolObjectEntity target) {
        target.setName(input.getName());
        target.setTeacher(input.getTeacher());
    }

    public static SchoolObjectDTO transformSchoolObjectEntityToDTO(final SchoolObjectEntity input) {
        final SchoolObjectDTO target = new SchoolObjectDTO();
        if (input != null) {
            fillSchoolObjectDTO(input, target);
        }
        return target;
    }

    public static SchoolObjectEntity transformSchoolObjectFromDTO(final SchoolObjectDTO input) {
        final SchoolObjectEntity target = new SchoolObjectEntity();
        if (input != null) {
            fillSchoolObjectEntityFromDTO(input, target);
        }
        return target;
    }

    public static SchoolObjectDTO[] transformSchoolObjectEntitiesToDTO(final SchoolObjectEntity[] inputs) {
        final SchoolObjectDTO[] targets = new SchoolObjectDTO[inputs.length];
        for (int i = 0; i < inputs.length; i++) {
            targets[i] = transformSchoolObjectEntityToDTO(inputs[i]);
        }
        return targets;
    }
}
