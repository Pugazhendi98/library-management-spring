package com.example.project.Library_management_system.Controller;

import javax.persistence.Entity;
import javax.persistence.Id;

import org.springframework.stereotype.Component;



public class Login {

	
	
	String username;
	String password;
	
	public Login()
	{
		
	}
	 @Override
	public String toString() {
		return "Login [  username=" + username + ", password=" + password + "]";
	}
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Login(int id, String username, String password) {
			super();
			
			this.username = username;
			this.password = password;
		}
}
