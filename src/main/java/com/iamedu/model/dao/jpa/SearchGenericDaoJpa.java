package com.iamedu.model.dao.jpa;

import java.util.List;

import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.Search;
import org.springframework.transaction.annotation.Transactional;

import com.iamedu.model.dao.SearchGenericDao;
import com.iamedu.model.domain.DomainObject;

public class SearchGenericDaoJpa<T extends DomainObject> extends
		GenericDaoJpa<T> implements SearchGenericDao<T> {

	private final Class<T> type;

	protected SearchGenericDaoJpa(final Class<T> type) {
		super(type);
		this.type = type;
	}

	@Override
	public void save(final T object) {
		entityManager.persist(object);
		indexEntity(object);
	}

	@Override
	public void delete(final T object) {
		deleteIndex(object);
		super.delete(object);
	}

	public void indexEntity(T object) {
		getFullTextEntityManager().index(object);
	}

	@Transactional(readOnly = true)
	public void indexAllEntities() {
		final FullTextEntityManager fullTextEntityManager = getFullTextEntityManager();
		final List<T> results = getAll();
		int counter = 0;
		final int numItemsInGroup = 10;
		for (final T result : results) {
			fullTextEntityManager.index(fullTextEntityManager.merge(result));
			if (counter++ % numItemsInGroup == 0) {
				fullTextEntityManager.flushToIndexes();
				fullTextEntityManager.clear();
			}
		}
	}

	public void deleteAllIndexes() {
		getFullTextEntityManager().purgeAll(type);
	}

	public void deleteIndex(T object) {
		getFullTextEntityManager().purge(type, object.getId());
	}

	protected FullTextEntityManager getFullTextEntityManager() {
		return Search.getFullTextEntityManager(entityManager);
	}

}
