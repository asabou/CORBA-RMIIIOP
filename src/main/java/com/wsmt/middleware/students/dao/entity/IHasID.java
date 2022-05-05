package com.wsmt.middleware.students.dao.entity;

import java.io.Serializable;

public interface IHasID<E> extends Serializable {
    E getId();
}
