package jpabook.jpashop.services;

import jpabook.jpashop.domains.Item.Book;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.EntityManager;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ItemServiceTest {

	@Autowired
	EntityManager em;

	@Test
	public void updateTest() throws Exception {
		Book book = em.find(Book.class, 1L);

		book.setName("aaaaa");

		// 변경감지 === dirty checking
		
	}

}