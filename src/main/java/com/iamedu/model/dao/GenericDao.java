package com.iamedu.model.dao;

import java.util.List;

import com.iamedu.model.domain.DomainObject;

public interface GenericDao<T extends DomainObject> {

	T get(Long id);

	List<T> getAll();

	void save(T object);

	void delete(T object);

}
