package com.example.project.Library_management_system.User;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.Cookie;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;



@Service
public class UserService {
	
	@Autowired
	UserRepository userRepository;
	
	PasswordEncoder pwdEncoder = new BCryptPasswordEncoder();

	
	public User getUserByMail(String mail) {
		
		return userRepository.getUserByEmailId(mail);
	}

	public void logoutUserByid(int id)
	{
		userRepository.logoutUserById(id);
	}

	public void save(User user) {
		// TODO Auto-generated method stub
		user.setActive(false);
		
		userRepository.save(user);
	}

	public void setactive(int id) {
		// TODO Auto-generated method stub
		
		userRepository.setactive(id);
	}

	public boolean isLibrarian(Cookie[] cookies) {
		
		
		String pass=null;
		if(cookies!=null)
		{

           for(Cookie cookie:cookies)
           {        	   
        	   if(cookie.getName().equals("user"))
        	   {        		   
        		 pass=cookie.getValue();
        		 break;
        	   }
           }
                   
           if(pass==null)
           {
        	  return false;
           }
		
           else if(userRepository.isUser(pass)==null)
   		{
   			return false;
   		}
   		else if(userRepository.isUser(pass).toString().equals("LIB"))
   		{
   			return true;
   		}
   		else {
   			return false;
   		}
           
		}
		else {
			return false;
		}
		
			
		
		
	}

	public User getUserById(int user_id) {
		// TODO Auto-generated method stub
		return userRepository.getById(user_id);
	}

	public int getLibraryFine() {
		// TODO Auto-generated method stub
		return userRepository.getLibraryFineAmt();
	}

	public int getLibID(Cookie[] cookies) {
		// TODO Auto-generated method stub
		String pass=null;
		if(cookies!=null)
		{

           for(Cookie cookie:cookies)
           {        	   
        	   if(cookie.getName().equals("user"))
        	   {        		   
        		 pass=cookie.getValue();
        		 break;
        	   }
           }
                   
           if(pass==null)
           {
        	  return 0;
           }
		
           else if(userRepository.isUser(pass)==null)
   		{
   			return 0;
   		}
   		else if(userRepository.isUser(pass).toString().equals("LIB")|| userRepository.isUser(pass).toString().equals("MEMBER"))
   		{
   			return userRepository.getLibID(pass);
   		}
   		else {
   			return 0;
   		}
           
		}
		else {
			return 0;
		}
		
	}

	public boolean isUserLib(Cookie[] cookies) {
		// TODO Auto-generated method stub
		String pass=null;
		if(cookies!=null)
		{

           for(Cookie cookie:cookies)
           {        	   
        	   if(cookie.getName().equals("user"))
        	   {        		   
        		 pass=cookie.getValue();
        		 break;
        	   }
           }
                   
           if(pass==null)
           {
        	  return false;
           }
		
           else if(userRepository.isUser(pass)==null)
   		{
   			return false;
   		}
   		else if(userRepository.isUser(pass).toString().equals("LIB") || userRepository.isUser(pass).toString().equals("MEMBER"))
   		{
   			return true;
   		}
   		else {
   			return false;
   		}
           
		}
		else {
			return false;
		}
	}

	public boolean isMember(Cookie[] cookies) {
		// TODO Auto-generated method stub
		String pass=null;
		if(cookies!=null)
		{

           for(Cookie cookie:cookies)
           {        	   
        	   if(cookie.getName().equals("user"))
        	   {        		   
        		 pass=cookie.getValue();
        		 break;
        	   }
           }
                   
           if(pass==null)
           {
        	  return false;
           }
		
           else if(userRepository.isUser(pass)==null)
   		{
   			return false;
   		}
   		else if(userRepository.isUser(pass).toString().equals("MEMBER"))
   		{
   			return true;
   		}
   		else {
   			return false;
   		}
           
		}
		else {
			return false;
		}
	}

	public long membersCount() {
		// TODO Auto-generated method stub
		return userRepository.countMembers();
	}

	public void removeUserByID(int member_id) {
		// TODO Auto-generated method stub
		
		userRepository.deleteById(member_id);
	}

	public List<User> getAllMembers() {
		// TODO Auto-generated method stub
		return userRepository.getAllMembers();
	}

	


	



	



	



	



     
	
	
	
}
