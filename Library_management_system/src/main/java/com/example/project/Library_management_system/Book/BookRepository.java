package com.example.project.Library_management_system.Book;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer>{

	
	@Query("Select MAX(book_Id) from Book b")
	Integer getLastId();

	
	@Query("Select b from Book b where b.member_id=?1")
	List<Book> getBookByMember(int id);

	
	@Query("Select b from Book b where b.book_title like %:value% ")
	List<Book> getbookByTitle(String value);
	
	@Query("Select b from Book b where b.author like %:value% ")
	List<Book> getbookByAuthor(String value);
	
	@Query("Select b from Book b where b.publication like %:value% ")
	List<Book> getbookByPublication(String value);
	
	@Query("Select b from Book b where b.category like %:value% ")
	List<Book> getbookByCategory(String value);


	@Query("Select count(b.status) from Book b where b.status='Not Available'")
	int booksIssued();


	
	
	

}
