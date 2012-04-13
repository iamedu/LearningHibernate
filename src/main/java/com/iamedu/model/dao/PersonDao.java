package com.iamedu.model.dao;

import java.util.List;

import com.iamedu.model.domain.Person;
import com.iamedu.model.domain.PersonWithExplanation;

public interface PersonDao extends SearchGenericDao<Person> {

	public List<Person> soundsLikeSlug(String like);
	
	public List<PersonWithExplanation> soundsLikeSlugWithExplanation(String like);
	
}
