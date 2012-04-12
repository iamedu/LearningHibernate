package com.iamedu.model.test;

import static org.junit.Assert.*;

import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@TransactionConfiguration(defaultRollback = true)
@ContextConfiguration("classpath*:/META-INF/spring/spring-test.xml")
public class DaoTests {

	@Test
	public void test() {
		fail("Not yet implemented");
	}

}
