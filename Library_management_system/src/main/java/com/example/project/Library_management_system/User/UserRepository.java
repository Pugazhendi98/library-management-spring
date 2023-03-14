package com.example.project.Library_management_system.User;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


@Repository
@EnableJpaRepositories(basePackages = {"com.example"})
public interface UserRepository extends JpaRepository<User, Integer>  {
	
	
	@Query("Select u from User u where u.email_id=?1")
	public User getUserByEmailId(String email);
	
	 @Transactional
	  @Modifying
	@Query("Update User u SET active=0 where u.id=?1")
	public void logoutUserById(int  id);

	
	 @Transactional
	  @Modifying
	@Query("Update User u SET active=1 where u.id=?1")
	public void setactive(int id);

	@Query("Select role from User u where u.userpassword=?1")
	public String isUser(String pass);

	 
	 @Query("Select distinct(fine) from User u where u.role='LIB'")
	public int getLibraryFineAmt();

	 
	 @Query("Select id from User u where u.userpassword=?1")
	public int getLibID(String pass);

	@Query("Select count(*) from User u where u.role='MEMBER'")
	public long countMembers();

	
	@Query("Select u from User u where u.role='MEMBER'")
	public List<User> getAllMembers();

	//boolean exists(User user); 
	
	

}
