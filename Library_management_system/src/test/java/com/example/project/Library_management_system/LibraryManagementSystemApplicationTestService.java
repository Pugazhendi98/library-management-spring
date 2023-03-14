package com.example.project.Library_management_system;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import javax.annotation.security.RunAs;
import javax.persistence.EntityNotFoundException;

import org.aspectj.lang.annotation.After;
import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.LazyInitializationException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.project.Library_management_system.Book.Book;
import com.example.project.Library_management_system.Book.BookService;
import com.example.project.Library_management_system.User.User;
import com.example.project.Library_management_system.User.UserService;

@RunWith(SpringRunner.class)
@SpringBootTest (webEnvironment = WebEnvironment.NONE)
class LibraryManagementSystemApplicationTestService {

	
	private static SessionFactory sessionFactory;
	
	
	
	@Autowired
	UserService userService;
	
	@Autowired
	BookService bookService;
	
	
	@Test
	@Order(1)
	public void addUserDetailsTest() {
		
		User user= new User();
		user.setUsername("John");
		user.setEmail_id("johnwick@gmail.com");
		user.setUserpassword("john@123");
		
		
		userService.save(user);
		
		User newUser=userService.getUserByMail("johnwick@gmail.com");
		
		assertNotNull(newUser);
		assertNotNull(newUser.getId());
		assertEquals("John", newUser.getUsername());
		
	}
	
	@Test
	@Order(2)
	public void deleteUserDetailsTest() {
		
		
		User newUser=userService.getUserByMail("johnwick@gmail.com");
		userService.removeUserByID(newUser.getId());;
		
		assertNull(userService.getUserByMail("johnwick@gmail.com"));
		
		
		
	}
	
	@Test
	@Order(3)
	public void addBookTest() {
		
		
		Book book =new Book();
		book.setBook_title("Programming in Java");
		book.setAuthor("Norman A C");
		
		
		bookService.saveBook(book);
		
		Book newBook = bookService.getBookById(bookService.findLastId());
		
		System.out.println(newBook.toString());
		assertNotNull(newBook);
		assertEquals("Programming in Java",newBook.getBook_title());
		assertEquals("Norman A C",newBook.getAuthor());
		
		
		
	}
	
	@Test
	@Order(4)
	public void removeBookTest() throws Exception {
		
		int id=bookService.findLastId();
		
		
		bookService.deleteBookById(id);
		
		Book book=bookService.getBookById(id);
		
		assertNull(book);
		
	}
	
	

}
