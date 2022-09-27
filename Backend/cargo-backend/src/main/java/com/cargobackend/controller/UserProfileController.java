package com.cargobackend.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cargobackend.pojo.dao.user.UserDetails;
import com.cargobackend.pojo.response.UserDetailsResponse;
import com.cargobackend.service.IUserService;

@Controller
@CrossOrigin(origins = "*")
@RequestMapping({"/user"})
public class UserProfileController {

    private final IUserService userService;
    
	
	public UserProfileController(IUserService userService) {
		super();
		this.userService = userService;
	}
	
    @PostMapping(value = {"/register"})
    @ResponseBody
    public UserDetailsResponse registerUser(@RequestBody UserDetails userDetails) {
    	System.out.println("\n UserProfileController  registerUser "+userDetails);
    	return userService.registerUser(userDetails);
    }
    
    @PostMapping(value = {"/getUserDetails"})
    @ResponseBody
    public UserDetailsResponse getUserDetails(@RequestBody UserDetails userDetails) {
    	System.out.println("\n UserProfileController  getUserDetails "+userDetails);
    	return userService.getUserDetails(userDetails);
    }
    

    @PostMapping(value = {"/authenticate"})
    @ResponseBody
    public UserDetailsResponse authenticateUser(@RequestBody UserDetails userDetails) {
    	System.out.println("\n UserProfileController  authenticate "+userDetails);
    	return userService.authenticateUser(userDetails);
    }

    @PostMapping(value = {"/logOut"})
    @ResponseBody
    public UserDetailsResponse logOutUser(@RequestBody UserDetails userDetails) {
    	System.out.println("\n UserProfileController  authenticate "+userDetails);
    	return userService.logOutUser(userDetails);
    }
	
}
