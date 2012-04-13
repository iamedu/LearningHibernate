package com.iamedu.model.dao.jpa;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.Explanation;
import org.apache.lucene.util.Version;
import org.hibernate.search.jpa.FullTextQuery;
import org.springframework.stereotype.Repository;

import com.iamedu.model.dao.PersonDao;
import com.iamedu.model.domain.Person;
import com.iamedu.model.domain.PersonWithExplanation;

@Repository
public class PersonDaoJpa extends SearchGenericDaoJpa<Person> implements
		PersonDao {

	public PersonDaoJpa() {
		super(Person.class);
	}

	public List<Person> soundsLikeSlug(String like) {
		Analyzer analyzer = getFullTextEntityManager().getSearchFactory()
				.getAnalyzer("phonetic");

		QueryParser queryParser = new QueryParser(Version.LUCENE_35,
				"slug_phonetic", analyzer);

		org.apache.lucene.search.Query luceneQuery = null;

		try {
			luceneQuery = queryParser.parse(like);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Query query = getFullTextEntityManager().createFullTextQuery(
				luceneQuery, Person.class);

		@SuppressWarnings("unchecked")
		List<Person> result = query.getResultList();

		return result;
	}

	public List<PersonWithExplanation> soundsLikeSlugWithExplanation(String like) {

		Analyzer analyzer = getFullTextEntityManager().getSearchFactory()
				.getAnalyzer("phonetic");

		QueryParser queryParser = new QueryParser(Version.LUCENE_35,
				"slug_phonetic", analyzer);

		org.apache.lucene.search.Query luceneQuery = null;
		List<PersonWithExplanation> result = new ArrayList<PersonWithExplanation>();

		try {
			luceneQuery = queryParser.parse(like);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		FullTextQuery query = getFullTextEntityManager()
				.createFullTextQuery(luceneQuery, Person.class);

		query = query.setProjection(FullTextQuery.THIS,
				FullTextQuery.EXPLANATION);

		@SuppressWarnings("unchecked")
		List<Object[]> preeliminarResults = (List<Object[]>) query
				.getResultList();

		for (Object[] tuple : preeliminarResults) {
			result.add(new PersonWithExplanation((Person) tuple[0],
					(Explanation) tuple[1]));
		}

		return result;
	}

}
