package com.example.project.Library_management_system;

import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.anything;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.hamcrest.beans.HasProperty;
import org.hamcrest.beans.HasPropertyWithValue;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.example.project.Library_management_system.LibraryManagementSystemApplication;
import com.example.project.Library_management_system.Book.Book;
import com.example.project.Library_management_system.Book.BookService;
import com.example.project.Library_management_system.Controller.Login;
import com.example.project.Library_management_system.Controller.Login_Controller;
import com.example.project.Library_management_system.User.UserService;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc 
public class ControllerTest {

	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	BookService bookService;
	
	@MockBean
	private UserService userService;
	
	@InjectMocks
	private Login_Controller loginController;
	
	@Before
	public void setUp()
	{
		MockitoAnnotations.openMocks(this);
	}
	
	
	@Test
	void testLoginController() throws Exception {
		
		String mail="";
		
		when(userService.getUserByMail(  mail)).thenReturn(null);
		
		Login loginCredentials=new Login();
		
		loginCredentials.setUsername("lib1@gmail.com");
		loginCredentials.setPassword("Admin@123");
		
		mockMvc.perform(post("/login", loginCredentials))
		.andExpect(status().isOk())
		.andExpect(view().name("/login"))
		.andExpect(model().attribute("error","User Not Found !!!"))
		.andReturn();
			
			
	}
	
	@Test
	void searchBooksGETControllerTest() throws Exception {
		
		
		
		mockMvc.perform(get("/searchbooks"))
		.andExpect(status().isFound())
		.andExpect(view().name("redirect:/"))
		.andReturn();
			
			
	}
	
	@Test
	void searchBooksPOSTControllerTest() throws Exception {
		
		
		int bookid=0;
		mockMvc.perform(post("/book/search").flashAttr("bookid", 0))
		.andExpect(status().isOk())
		.andExpect(view().name("searchbooks"))
		.andReturn();
			
			
	}
	
	@Test
	void searchBooksPOSTControllerTest2() throws Exception {
		
		
		int bookid=0;
		mockMvc.perform(post("/book/search").flashAttr("bookid", 42))
		.andExpect(status().isOk())
		.andExpect(view().name("searchbooks"))
		.andExpect(model().attributeExists("books"))
		.andReturn();
			
			
	}

}
