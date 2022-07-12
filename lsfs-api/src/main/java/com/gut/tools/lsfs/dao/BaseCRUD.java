package com.gut.tools.lsfs.dao;

import java.util.List;

public interface BaseCRUD <T, L> {

    T getById(L id);

    List<T> findAll();

    void deleteById(L id);

    boolean existsById(L id);
}
