package com.iamedu.model.domain;

import org.apache.lucene.search.Explanation;

public class PersonWithExplanation {

	private Person person;
	private Explanation explanation;

	public PersonWithExplanation(Person person, Explanation explanation) {
		this.person = person;
		this.explanation = explanation;
	}

	public Person getPerson() {
		return person;
	}

	public Explanation getExplanation() {
		return explanation;
	}

}
