package com.example.project.Library_management_system.Controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.example.project.Library_management_system.Book.Book;
import com.example.project.Library_management_system.Book.BookService;
import com.example.project.Library_management_system.User.User;
import com.example.project.Library_management_system.User.UserService;


@Controller
public class BookController {

	
	PasswordEncoder pwdencoder = new BCryptPasswordEncoder();
	
	@Autowired
	BookService bookService;
	
	@Autowired
	UserService userService;
	
	static List<String> optionList=new ArrayList<String>();
	
	static {
		optionList.add("Title");
		optionList.add("Author");
		optionList.add("Publication");
		optionList.add("Category");
	}
	
	
	@RequestMapping(value="/addbook")
	public String addBook( HttpServletRequest request, Model model  )
	{
		//String pass="hello";
		Cookie[] cookies = request.getCookies();
		
		   
		   if(userService.isLibrarian(cookies))
		   {	
			 int libId=userService.getLibID(cookies);
			   Book book= new Book();
			   book.setMember_id(libId);
			   System.out.println(book.toString());
		     model.addAttribute( book);
			return "addbook";
		   }
		  else {
			   return "redirect:/";
		      }
			
		
		
		//return "redirect:/";
		
	}
	
	
	@RequestMapping(value="/updatebook")
	public String updateBook( HttpServletRequest request, Model model  )
	{
		Cookie[] cookies = request.getCookies();
		
		   
		   if(userService.isLibrarian(cookies))
		   {	
			 //int libId=userService.getLibID(cookies);
			   Book book= new Book();
			  
			 
		     model.addAttribute( book);
			return "updatebook";
		   }
		  else {
			   return "redirect:/";
		      }
			
		
		
		
	}
	
	@RequestMapping(value="/deletebook")
	public String deleteBook( HttpServletRequest request, Model model  )
	{
		Cookie[] cookies = request.getCookies();
		
		   
		   if(userService.isLibrarian(cookies))
		   {	
			 //int libId=userService.getLibID(cookies);
			   Book book= new Book();
			  
			 
		     model.addAttribute( book);
			return "deletebook";
		   }
		  else {
			   return "redirect:/";
		      }
		
		
		
	}
	
	
	@RequestMapping(value="/issuebook")
	public String issueBook( HttpServletRequest request, Model model  )
	{
		Cookie[] cookies = request.getCookies();
		
		   
		   if(userService.isLibrarian(cookies))
		   {	
			 //int libId=userService.getLibID(cookies);
			   Book book= new Book();
			  
			 
		     model.addAttribute( book);
			return "issuebook";
		   }
		  else {
			   return "redirect:/";
		      }
		
		
		
	}
	
	@RequestMapping(value="/returnbook")
	public String returnBook( HttpServletRequest request, Model model  )
	{
		Cookie[] cookies = request.getCookies();
		
		   
		   if(userService.isLibrarian(cookies))
		   {	
			 //int libId=userService.getLibID(cookies);
			   Book book= new Book();
			  
			 
		     model.addAttribute( book);
			return "returnbook";
		   }
		  else {
			   return "redirect:/";
		      }
		
		
		
	}
	
	
	@RequestMapping(value="/searchbooks")
	public String searchBooks(HttpServletRequest request,Model model  )
	{
		
		
		
		Cookie[] cookies = request.getCookies();
		
		if(userService.isLibrarian(cookies))
		{
			 Book book= new Book();
			  model.addAttribute("bookid", 0);
			   model.addAttribute("books",bookService.getAllBooks());
				model.addAttribute("options", optionList);
				return "searchbooksadmin";
		}
		   
		   if(userService.isUserLib(cookies))
		   {	
			 //int libId=userService.getLibID(cookies);
			   Book book= new Book();
			  model.addAttribute("bookid", 0);
			   model.addAttribute("books",bookService.getAllBooks());
				model.addAttribute("options", optionList);
				return "searchbooks";
		   }
		  else {
			   return "redirect:/";
		      }
		
		
		
	}
	
	//---------------------------------------------------------------------------------------------------------------------------------//
	
	
	@RequestMapping(value="/book/add", method= RequestMethod.POST)
	public String addBook(HttpServletRequest request, @ModelAttribute Book book, @ModelAttribute User user, Model model )
	{
		
		
		Cookie[] cookies = request.getCookies();
		
		   
		   if(userService.isLibrarian(cookies))
		   {	
			 int libId=userService.getLibID(cookies);
			   
			   book.setMember_id(0);
			  
		    // model.addAttribute( book);
		     //System.out.println(book.toString());
				
			    book.setStatus("Available");
			
				bookService.saveBook(book);
				
				int bId = bookService.findLastId();
				
				String error="Book Added  ,   ";
				model.addAttribute(new Book());
				model.addAttribute("response", error+" "+"  Book_Number - "+bId);
				return "addbook";
		     
			 }
		   
		  else {
			   return "redirect:/";
		      }
			
			
    }
	
	@RequestMapping(value="/book/update", method= RequestMethod.POST)
	public String updateBook(HttpServletRequest request,  @ModelAttribute Book book, @ModelAttribute User user, Model model )
	{
		
		Cookie[] cookies = request.getCookies();
		
		  
	if(userService.isLibrarian(cookies))
	  {	
			 int libId=userService.getLibID(cookies);
			   
			   
		
		try {
		Book existingBook = bookService.getBookById(book.getBook_Id());
		System.out.println(existingBook.toString());
		
		existingBook.setMember_id(libId);
		
		if(book.getAuthor()!=null)
		{
		existingBook.setAuthor(book.getAuthor());
		}
		
		if(book.getBook_title()!=null)
		{
		existingBook.setBook_title(book.getBook_title());
		}
		
		if(book.getPublication()!=null)
		{
		existingBook.setPublication(book.getPublication());
		}
		    
		if(book.getEdition()!=null)
		{
		existingBook.setEdition(book.getEdition());
		}
		
		if(book.getCategory()!=null)
		{
		existingBook.setCategory(book.getCategory());
		}
		
		System.out.println(existingBook.toString());
			System.out.println(book.toString());
		
			
			bookService.saveBook(existingBook);
			
			String error="Book Updated";
			model.addAttribute(new Book());
			model.addAttribute("response", error);
			return "updatebook";
		} catch (Exception e) {
			// TODO: handle exception
			
			String error="Book not Found !!!";
			model.addAttribute(new Book());
			model.addAttribute("response", error);
			return "updatebook";
		}
			
		   }
		   
		   else {
			   return "redirect:/";
		      }
			
				
				
    }
	
	@RequestMapping(value="/book/delete", method= RequestMethod.POST)
	public String deleteBook(  @ModelAttribute Book book, @ModelAttribute User user, Model model )
	{
		
		try {
			  
			 
			book.setMember_id(bookService.getBookById(book.getBook_Id()).getMember_id());
			
			if(book.getMember_id()>0)
			{
				String error="Book is Issued,Can't Delete";
				model.addAttribute(new Book());
				model.addAttribute("response", error);
				return "deletebook";
			}
			else {
				bookService.deleteBookById(book.getBook_Id());
				
				String error="Book Deleted";
				model.addAttribute(new Book());
				model.addAttribute("response", error);
				return "deletebook";
			}
			
			
		} catch (Exception e) {
			// TODO: handle exception
			
			String error="Couldnot find book !!!";
			model.addAttribute(new Book());
			model.addAttribute("response", error);
			return "deletebook";
			
		}
		
		 
    }
	
	@RequestMapping(value="/book/issue", method= RequestMethod.POST)
	public String issueBook(  @ModelAttribute("book_id") int book_id, @ModelAttribute("member_id") int user_id, Model model )
	{
		
		try {
			
			System.out.println(book_id+" "+user_id);
			
			User user =userService.getUserById(user_id);
			Book book = bookService.getBookById(book_id);
			
			System.out.println(book+" "+user);
			
			   DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");  
		       LocalDateTime now = LocalDateTime.now();  
		       System.out.println(dtf.format(now));  
		       LocalDate issueDate= LocalDate.parse(dtf.format(now).toString(),dtf);
		       LocalDate returnDate= LocalDate.parse(issueDate.plusDays(15).toString(),dtf);
		       
			
		     book.setIssue_date(issueDate.toString());
		     book.setReturn_date(returnDate.toString());
		     book.setStatus("Not Available");
		     if(book.getMember_id()==0  )
		     {
		     book.setMember_id(user_id);
		     bookService.saveBook(book);
		     
		     user.setBooks_issued(user.getBooks_issued()+1);
		     userService.save(user);
			
			String error="Book Issued Successfully !!!";
			model.addAttribute("response", error);
			return "issuebook";
		     }
		     
		     else if(book.getMember_id()== user_id) {
		    	 String error="Book Already Issued !!!";
					model.addAttribute(new Book());
					model.addAttribute("response", error);
					return "issuebook";
			}
		     else if (book.getBook_title()==null) {
				
		    	 String error="Book Dosent Exist !!!";
					model.addAttribute(new Book());
					model.addAttribute("response", error);
					return "issuebook";
			}
		     
		     
		     else {
				
		    	 String error="Book Already Issued to Someone!!!";
					model.addAttribute(new Book());
					model.addAttribute("response", error);
					return "issuebook";
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			
			String error="Couldnot find book or user !!!";
			model.addAttribute(new Book());
			model.addAttribute("response", error);
			return "issuebook";
			
		}
		
		 
    }
	
	@RequestMapping(value="/book/return", method= RequestMethod.POST)
	public String returnBook(@ModelAttribute("book_id") int book_id, @ModelAttribute("member_id") int user_id, Model model )
	{
		int fineAmount = userService.getLibraryFine();
		
		String response="";
		try {
			
             System.out.println(book_id+" "+user_id);
			
			User user =userService.getUserById(user_id);
			Book book = bookService.getBookById(book_id);
			
			System.out.println(book+" "+user);
			
			
			
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd"); 
			
			 LocalDateTime todayDate = LocalDateTime.now();  
		     System.out.println(dtf.format(todayDate));  
			
			
			
			if(book.getMember_id()==user_id)
			{
				
				LocalDate issueDate= LocalDate.parse(book.getIssue_date(),dtf);
			    LocalDate returnDate= LocalDate.parse(book.getReturn_date(),dtf);
			    
			    long elapsedDays= ChronoUnit.DAYS.between(returnDate,todayDate);
			    
			   
			     book.setIssue_date(null);
			     book.setReturn_date(null);
			     book.setMember_id(0);
			     book.setStatus("Available");
			     bookService.saveBook(book);
			     user.setBooks_issued(user.getBooks_issued()-1);
			     
				
			     if(elapsedDays>0)
			     {
			    	 fineAmount=(int) (elapsedDays*fineAmount);
			    	 user.setFine(0);
			    	 response=" Book Retruned  Successful";
			    	 model.addAttribute("fineAmt", fineAmount);
			    	 model.addAttribute("response", response);
			    	 model.addAttribute("username", user.getUsername());
			    	 return "displayfine";
			    	 
			     }
			     else {
			    	 model.addAttribute("fineAmt",0);
			    	 model.addAttribute("username", user.getUsername());
					
			    	 response=" Book Retruned Successfully";
			    	 
			    	 model.addAttribute("response", response);
			    	
			    	 return "displayfine";
				}
			     
				
			}
			
			
			else {
				
				 response=" Invalid User";
		    	 
		    	 model.addAttribute("response", response);
		    	
		    	 return "returnbook";
			}
		
			
			
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e);
		    response="Couldnot find book or user !!!";
			model.addAttribute(new Book());
			model.addAttribute("response", response);
			return "returnbook";
			
		}
		
		 
    }
	
	
	@RequestMapping(value="/book/search", method= RequestMethod.POST)
	public String searchBooks(HttpServletRequest request,@ModelAttribute("bookid") int bookid, @ModelAttribute("option") String option, @ModelAttribute("value") String value, Model model )
	{
		
		String response="";
		
		 List<Book> books;
		 
		
		 
		        if(bookid<0)
		        {
		        	  response="Invalid Book Number!!!";
		        	  model.addAttribute("options", optionList);
						model.addAttribute("response", response);
						

		        }
		     
		        else  if(bookid==0 && value.equals(""))
		        {
		        	model.addAttribute("options", optionList);
		        	model.addAttribute("books", bookService.getAllBooks());
		        }
		        
		        else  if(bookid>0 && value.equals(""))
		        {
		           try {
		        	   
		        	   Book book=  bookService.getBookById(bookid);
		        		System.out.println(book);
		        	   
		        	   if(book==null)
		        	   {
		        		   response="Book not Found !!!";
						    model.addAttribute("options", optionList);
							model.addAttribute("response", response);
							model.addAttribute("books", bookService.getAllBooks());
							return "searchbooks";
		        	   }
		        	   else {
		        		   model.addAttribute("options", optionList);
		        		   model.addAttribute("books", book);
		        		   return "searchbooks";
					}
		        	  
		        		
		        		
					} catch (EntityNotFoundException e) {
						// TODO: handle exception
						System.out.println(e);
						 response="Book not Found !!!";
						    model.addAttribute("options", optionList);
							model.addAttribute("response", response);
							model.addAttribute("books",  bookService.getAllBooks());
						
					}
		        }
		           
		           else  if(option.equals("Title") && value.length()>0)
		          {
		        	  System.out.println(value);
	        		  books=  bookService.getBookByTitle(value);
	        		  System.out.println(bookService.getBookByTitle(value));
	        			     model.addAttribute("books",books);
		        	  
		        	  
						
					}
		        
		           else  if(option.equals("Author") && value.length()>0)
			          {
			        	  System.out.println(value);
		        		 
		        		  System.out.println();
		        			     model.addAttribute("books",bookService.getBookByAuthor(value));
			        	  
			        	  
							
						}
		        
		           else  if(option.equals("Publication") && value.length()>0)
			          {
			        	  System.out.println(value);
		        		 
		        		  System.out.println(bookService.getBookByPublication(value));
		        			     model.addAttribute("books",bookService.getBookByPublication(value));
			        	  
			        	  
							
						}
		           else  if(option.equals("Category") && value.length()>0)
			          {
			        	  System.out.println(value);
		        		  
		        		  System.out.println(bookService.getBookByCategory(value));
		        			     model.addAttribute("books",bookService.getBookByCategory(value));
			        	  
			        							
						}
		           else {
		        	   response="Book not Found !!!";
					    model.addAttribute("options", optionList);
					    model.addAttribute("books",  bookService.getAllBooks());
						model.addAttribute("response", response);
				}
		          
		          
          
		        Cookie[] cookies = request.getCookies();
				
				if(userService.isLibrarian(cookies))
				{
					model.addAttribute("options", optionList);
					return "searchbooksadmin";
		        
				}
			
				
				
				model.addAttribute("options", optionList);
				return "searchbooks";
    }
	
	
}
