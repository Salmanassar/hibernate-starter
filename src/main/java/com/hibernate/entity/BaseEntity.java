package com.hibernate.entity;

import java.io.Serializable;

public interface BaseEntity<T extends Serializable> {

    T getId();

    T setId(T id);
}
