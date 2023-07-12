package jpabook.jpashop.service;

import jakarta.persistence.EntityManager;
import jpabook.jpashop.item.Book;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class itemUpdateTest {

    @Autowired
    EntityManager em;

    @Test
    void updateTest(){

        Book book = em.find(Book.class, 1L);

        //TX
        book.setName("NewBook");

        //변경감지   == dirty check


    }
}
