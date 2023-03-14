package com.example.project.Library_management_system.Book;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import com.example.project.Library_management_system.User.User;

@Entity
@Table(name = "book")
public class Book   {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@NotFound(action = NotFoundAction.IGNORE)
	private int book_Id;
	
	private String book_title;
	private String author;
	private String publication;
	private String edition;
	

	private String status;
	private String issue_date;
	private String return_date;
	
	
	private int member_id;
	private String category;
	
	
	

	
	public Book()
	{
		
	}
	
	
	public Book(int book_Id, String book_title, String author, String publication, String edition, String status,
			String issue_date, String return_date, int member_id, String category) {
		super();
		this.book_Id = book_Id;
		this.book_title = book_title;
		this.author = author;
		this.publication = publication;
		this.edition = edition;
		this.status = status;
		this.issue_date = issue_date;
		this.return_date = return_date;
		this.member_id = member_id;
		this.category = category;
	}

	public int getBook_Id() {
		return book_Id;
	}

	public void setBook_Id(int book_Id) {
		this.book_Id = book_Id;
	}

	public String getBook_title() {
		return book_title;
	}

	public void setBook_title(String book_title) {
		this.book_title = book_title;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getPublication() {
		return publication;
	}

	public void setPublication(String publication) {
		this.publication = publication;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getIssue_date() {
		return issue_date;
	}

	public void setIssue_date(String issue_date) {
		this.issue_date = issue_date;
	}

	public String getReturn_date() {
		return return_date;
	}

	public void setReturn_date(String return_date) {
		this.return_date = return_date;
	}

	public int getMember_id() {
		return member_id;
	}

	public void setMember_id(int member_id) {
		this.member_id = member_id;
	}

	public String getEdition() {
		return edition;
	}

	public void setEdition(String edition) {
		this.edition = edition;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}
	@Override
	public String toString() {
		return "Book [book_Id=" + book_Id + ", book_title=" + book_title + ", author=" + author + ", publication="
				+ publication + ", edition=" + edition + ", status=" + status + ", issue_date=" + issue_date
				+ ", return_date=" + return_date + ", member_id=" + member_id + ", category=" + category + "]";
	}

	
	
	
	
}
