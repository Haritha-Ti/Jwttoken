package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.dto.UserDto;
import com.example.demo.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{

	@Query("SELECT u FROM User u WHERE u.username=:username")
	User getUser(String username);
	
//	@Query("SELECT u FROM User u WHERE u.username=?1 AND u.password=?2")
//	User getUserdetails(String username, String password);

	@Query("SELECT u FROM User u WHERE u.username=?1")
	User getUserdetails(String username);

}
