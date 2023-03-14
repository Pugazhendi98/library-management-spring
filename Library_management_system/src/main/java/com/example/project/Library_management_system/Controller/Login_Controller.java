package com.example.project.Library_management_system.Controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcProperties.View;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.example.project.Library_management_system.Book.Book;
import com.example.project.Library_management_system.Book.BookService;
import com.example.project.Library_management_system.User.User;
import com.example.project.Library_management_system.User.UserService;

import ch.qos.logback.core.util.Duration;

@Controller 
public class Login_Controller {

	
	PasswordEncoder pwdEncoder = new BCryptPasswordEncoder();
	@Autowired
	UserService userService;
	
	@Autowired
	BookService bookService;
	
	 
	@GetMapping("/")
	public String homeController(Model model) {
       
          
      
       return "login"; 
	}
	
	
	
	@RequestMapping(value = "/login",method = RequestMethod.POST)
	public String loginController(HttpServletRequest req, HttpServletResponse res, @ModelAttribute Login login,Model model)
	{
		String error="";
		
		
		
		
		
		model.addAttribute("name", login.getUsername());
		
		User user=userService.getUserByMail(login.getUsername());
		
		if(user==null)
		{
			error="User Not Found !!!";
			model.addAttribute("error",error);
			return "/login";
			
		}
		
		try {
			
			
		   
			
		if( pwdEncoder.matches(login.getPassword(), user.getUserpassword()))
		{
			if(!user.isActive())
			{
				
				String updatePass=pwdEncoder.encode(login.getPassword());
				
				
				 String newpass=pwdEncoder.encode(login.getPassword());
				Cookie cookie=new Cookie("user", newpass);
				
				cookie.setMaxAge(7 * 24 * 60 * 60); // expires in 7 days
				cookie.setSecure(false);
				cookie.setHttpOnly(false);
				cookie.setPath("/");
				res.addCookie(cookie);
			   		    
			    user.setUserpassword(newpass);
			    
			    userService.save(user);
			    userService.setactive(user.getId());
			   
				
			 
			  error="";
			  model.addAttribute("error",error);
			
			if(user.getRole().equals("LIB"))
			{
				
				
				
				return "redirect:/adminconsole";
				
			}
			else {
				
			 if(user.getRole().equals("MEMBER"))
			 {
				 
				userService.setactive(user.getId());
			    model.addAttribute("member", user);
			    //model.addAttribute("book",new Book());
				return "redirect:/memberconsole";
			 }
			 
			 else {
					error="Invalid User !!!";
					model.addAttribute("error",error);
					return "login";
			 }
			}
		  }
			else {
				error="Already logged in !!!";
				model.addAttribute("error",error);
				return "login";
			}
			
			
		}
		else {
			error="Invalid Login Credentials !!!";
			model.addAttribute("error",error);
			return "login";
			
		}
		
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e);
			return "login";
		}
		
	} 
	
	
	
	@RequestMapping("/logout/{id}")
	public String Logout( HttpServletRequest req, HttpServletResponse res, @PathVariable int id ) {
		
		
		Cookie cookie = new Cookie("user", null);
		cookie.setMaxAge(0);
		cookie.setSecure(false);
		cookie.setHttpOnly(false);
		cookie.setPath("/");
		res.addCookie(cookie);
       System.out.println(id);
       
       userService.logoutUserByid(id);
      
       return "redirect:/"; 
	}
	
	@RequestMapping("/adminconsole")
	public String adminConsole( HttpServletRequest req, HttpServletResponse res,Model model ) {
		
		Cookie[] cookies = req.getCookies();
		
		
		   
		   if(userService.isLibrarian(cookies))
		   {
			   int libId=userService.getLibID(cookies);
		       
			   User admin =userService.getUserById(libId);
			   
			   model.addAttribute("admin",admin);
				
				model.addAttribute("book",new Book());
				model.addAttribute("booksCount", bookService.booksCount());
				model.addAttribute("membersCount", userService.membersCount());
				model.addAttribute("booksIssued", bookService.booksIssued());
				return "adminconsole";
			   
		   }
		   
		   else {
			return "redirect:/";
		}
		   
			 
	}
	
	@RequestMapping("/memberconsole")
	public String memberConsole( HttpServletRequest req, HttpServletResponse res, Model model ) {
		
		Cookie[] cookies = req.getCookies();
		
		long elapsedDays=0;
		int fineAmount =0;
		   
		   if(userService.isMember(cookies))
		   {
			   int memberId=userService.getLibID(cookies);
			 
			   User member =userService.getUserById(memberId);
			  
			   DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd"); 
				
				 LocalDateTime todayDate = LocalDateTime.now(); 
				 
				List<Book> books = bookService.getBookByMemberId(memberId);
				
				for(Book book:books)
				{
					LocalDate issueDate= LocalDate.parse(book.getIssue_date(),dtf);
				    LocalDate returnDate= LocalDate.parse(book.getReturn_date(),dtf);
				   
				    elapsedDays+= ChronoUnit.DAYS.between(returnDate,todayDate);
					
				}
				
				 if(elapsedDays>0)
			     {
			    	 fineAmount=(int) (elapsedDays*fineAmount);
			    	 member.setFine(fineAmount);
			     }
			   
			   
			   
			   model.addAttribute("member",member);
				
				
				model.addAttribute("books", bookService.getBookByMemberId(member.getId()));
				
				
				
				return "memberconsole";
			   
		   }
		   
		   else {
			return "redirect:/";
		}
		
      
	}
	
}
