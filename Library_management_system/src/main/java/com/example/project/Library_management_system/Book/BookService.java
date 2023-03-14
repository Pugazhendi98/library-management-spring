package com.example.project.Library_management_system.Book;

import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.project.Library_management_system.User.UserRepository;

@Service
public class BookService {

	@Autowired
	BookRepository bookRepository;
	
	
	
	
	public Book getBookById(int id)
	{
		try {
			Book book = bookRepository.getById(id);
			
			return book;
			
		} catch (EntityNotFoundException e) {
			System.out.println(e);
			return null;
		}
		
	}
	
	
	public void saveBook(Book book)
	{
		bookRepository.save(book);
	}
	
	public List<Book> getAllBooks()
	{
		return bookRepository.findAll();
		
	}


	public void deleteBookById(int book_Id) {
		// TODO Auto-generated method stub
		bookRepository.deleteById(book_Id);
	}


	public int findLastId() {
		// TODO Auto-generated method stub
		return bookRepository.getLastId();
	}


	public List<Book> getBookByMemberId(int id) {
		// TODO Auto-generated method stub
		return bookRepository.getBookByMember(id);
	}


	public List<Book> getBookByTitle(String value) {
		// TODO Auto-generated method stub
		
		return bookRepository.getbookByTitle(value);
	}


	public List<Book> getBookByAuthor(String value) {
		// TODO Auto-generated method stub
		return bookRepository.getbookByAuthor(value);
	}
	
	public List<Book> getBookByPublication(String value) {
		// TODO Auto-generated method stub
		return bookRepository.getbookByPublication(value);
	}
	
	public List<Book> getBookByCategory(String value) {
		// TODO Auto-generated method stub
		return bookRepository.getbookByCategory(value);
	}


	public long booksCount() {
		
		// TODO Auto-generated method stub
		return bookRepository.count();
		
		
	}


	public int booksIssued() {
		// TODO Auto-generated method stub
		return bookRepository.booksIssued();
	}
	
	
}
