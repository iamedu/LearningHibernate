package com.iamedu.model.dao.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.iamedu.model.dao.GenericDao;
import com.iamedu.model.domain.DomainObject;

public class GenericDaoJpa<T extends DomainObject> implements GenericDao<T> {

	@PersistenceContext
	protected EntityManager entityManager;

	private final Class<T> type;

	protected GenericDaoJpa(final Class<T> type) {
		this.type = type;
	}

	public T get(Long id) {
		return entityManager.find(type, id);

	}

	public List<T> getAll() {
		return entityManager.createQuery(
				"select obj from " + type.getName() + " obj", type)
				.getResultList();

	}

	public void save(T object) {
		entityManager.persist(object);
	}

	public void delete(T object) {
		entityManager.remove(object);
	}

}
