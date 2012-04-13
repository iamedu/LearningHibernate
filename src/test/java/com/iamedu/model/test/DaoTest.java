package com.iamedu.model.test;

import java.util.List;

import javax.inject.Inject;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.iamedu.model.dao.PersonDao;
import com.iamedu.model.domain.Person;
import com.iamedu.model.domain.PersonWithExplanation;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/META-INF/spring/spring-test.xml" })
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = false)
public class DaoTest {

	@Inject
	private PersonDao personDao;

	@Transactional
	public void creaPersonita() {
		Person p = new Person();
		p.setName("Eduardo");
		p.setEmail("iamedu@gmail.com");
		p.setSlug("this is a prueba felicidad 1 relevante, esto es lo que es");
		personDao.save(p);
		personDao.indexEntity(p);
	}

	@Test
	@Transactional(readOnly = true)
	public void testBuscaPersonita() {
		creaPersonita();
		List<PersonWithExplanation> selfExplainPeople = personDao.soundsLikeSlugWithExplanation("felyzida elefante");
		for(PersonWithExplanation p : selfExplainPeople) {
			System.out.println(p.getExplanation().toHtml());
		}
	}

	@After
	public void borraTodo() {
		//personDao.deleteAllIndexes();
	}

}
