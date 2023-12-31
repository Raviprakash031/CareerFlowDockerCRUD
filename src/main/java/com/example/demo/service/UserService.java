package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepo;
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	public User addUser(User user) {
		
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		User user1=userRepo.save(user);
		if(user1 !=null) {
			return user1;
		}
		
		return null;
	}
	
	public User loginUser(User user) {
		System.out.println(user.getPassword());
	User user1 =userRepo.findByEmail(user.getEmail());
	System.out.println(user1.getPassword());
	
	if(passwordEncoder.matches(user.getPassword(), user1.getPassword())) {
		System.out.println("checking");
		return user1;
	}
		return null;
	}
   public User findByEmail(String email) {
	   return userRepo.findByEmail(email);
   }
   public User updateUser(User updatedUser) {
	    try {
	        User existingUser = userRepo.findByEmail(updatedUser.getEmail());
	        if (existingUser != null) {
	            // Update user fields as needed
	            existingUser.setUsername(updatedUser.getUsername());
	            existingUser.setEmail(updatedUser.getEmail());
	            existingUser.setMobileNumber(updatedUser.getMobileNumber());

	            // Check if the password is not empty and encode it
	            String newPassword = updatedUser.getPassword();
	            if (newPassword != null && !newPassword.isEmpty()) {
	                String encodedPassword = passwordEncoder.encode(newPassword);
	                existingUser.setPassword(encodedPassword);
	            }

	            // Update other fields as needed

	            // Save the updated user
	            return userRepo.save(existingUser);
	        } else {
	            // Handle the case where existingUser is null (e.g., throw an exception or return a default value)
	        }
	    } catch (Exception e) {
	        System.out.println(e.getMessage());
	    }

	    return null; // Handle appropriately based on your use case
	}

	

	public String deleteUser(String email) {
		User user1=userRepo.findByEmail(email);
		userRepo.delete(user1);
		return "User Deleted Successfully";
	}
	
	public List<User> getAllUser() {
		List<User> users=userRepo.findAll();
		return users;
		
	}
	
	
}
