package com.example.project.Library_management_system.Controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.example.project.Library_management_system.Book.Book;
import com.example.project.Library_management_system.User.User;
import com.example.project.Library_management_system.User.UserService;

@Controller
public class User_Controller {

	PasswordEncoder pwdencoder = new BCryptPasswordEncoder();
	@Autowired
	UserService userService;
	
	
	@GetMapping("/member/signup")
	public String createaccount(Model model)
	{
		
		
		model.addAttribute("user",new User());
		
		System.out.println("createacc");
		return "membsignup";
	}
	
	
	@PostMapping("/member/add")
	public String addmember( @ModelAttribute User user,Model model)
	{
		String error="";
		
		
		
		User usertemp= userService.getUserByMail(user.getEmail_id());
		
		if(usertemp == null)
		{
			user.setRole("MEMBER");
			
			user.setUserpassword(pwdencoder.encode(user.getUserpassword()));
			
			userService.save(user);
			
			
			
			
				System.out.println(user.toString());
				model.addAttribute("username", user.getUsername());
				return "redirect:/";
		}
		
		else {
			
			error="Email-id already Exists";
			model.addAttribute("error",error);
			
			return "membsignup";
		
			
		}
			
			
		
		
		
	}
	
	@RequestMapping(value="/removemember")
	public String deleteMember( HttpServletRequest request, Model model  )
	{
		Cookie[] cookies = request.getCookies();
		
		   
		   if(userService.isLibrarian(cookies))
		   {	
			 
			return "removemember";
		   }
		  else {
			   return "redirect:/";
		      }
		
		
		
	}
	
	@RequestMapping(value="/viewmembers")
	public String viewMembers( HttpServletRequest request, Model model  )
	{
		Cookie[] cookies = request.getCookies();
		
		   
		   if(userService.isLibrarian(cookies))
		   {	
			 model.addAttribute("members", userService.getAllMembers());
			return "viewmembers";
		   }
		  else {
			   return "redirect:/";
		      }
		
		
		
	}
	
	
	@RequestMapping(value="/member/remove", method= RequestMethod.POST)
	public String deleteMember(  @ModelAttribute ("memberId") Integer member_id, Model model )
	{
		
		try {
			
			 userService.removeUserByID(member_id);
			
					String error="Member Removed Successfully !!!";
					model.addAttribute(new Book());
					model.addAttribute("response", error);
					return "removemember";
			
		} catch (Exception e) {
			// TODO: handle exception
			
			String error="Could not find Member with the Id !!!";
			model.addAttribute(new Book());
			model.addAttribute("response", error);
			return "removemember";
			
		}
		
		 
    }
	
}
