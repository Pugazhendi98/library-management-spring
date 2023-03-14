package com.example.project.Library_management_system.User;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Email;

import org.springframework.validation.annotation.Validated;

import com.example.project.Library_management_system.Book.Book;

import aj.org.objectweb.asm.Type;

@Entity(name = "User")
@Table(name = "user")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String username;
	private String userpassword;
	
	
	@Column(unique = true, nullable = false)
	private String email_id;
	private String role;
	private boolean active;
	
	private int books_issued;
	private int fine;
	
	
	
	public User() {
		// TODO Auto-generated constructor stub
	}
	@Override
	public String toString() {
		return "User [id=" + id + ", username=" + username + ", upassword=" + userpassword + ", email_id=" + email_id
				+ ", role=" + role + ", active=" + active + ", books_issued=" + books_issued + ", fine=" + fine + "]";
	}
	public User(int id, String username, String userpassword, String email_id, String role, boolean active,
			int books_issued, int fine) {
		super();
		this.id = id;
		this.username = username;
		this.userpassword = userpassword;
		this.email_id = email_id;
		this.role = role;
		this.active = active;
		this.books_issued = books_issued;
	
		
		this.fine = fine;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getUserpassword() {
		return userpassword;
	}
	public void setUserpassword(String upassword) {
		this.userpassword = upassword;
	}
	public String getEmail_id() {
		return email_id;
	}
	public void setEmail_id(String email_id) {
		this.email_id = email_id;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public boolean isActive() {
		return active;
	}
	public void setActive(boolean active) {
		this.active = active;
	}
	public int getBooks_issued() {
		return books_issued;
	}
	public void setBooks_issued(int books_issued) {
		this.books_issued = books_issued;
	}
	public int getFine() {
		return fine;
	}
	public void setFine(int fine) {
		this.fine = fine;
	}
	
	
	
	
	
}
