package com.iamedu.model.dao;

import com.iamedu.model.domain.DomainObject;

public interface SearchGenericDao<T extends DomainObject> extends GenericDao<T> {

	void indexEntity(T object);

	void indexAllEntities();

	void deleteAllIndexes();

	void deleteIndex(T object);

}
