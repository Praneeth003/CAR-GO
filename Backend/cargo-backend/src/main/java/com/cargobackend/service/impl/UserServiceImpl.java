package com.cargobackend.service.impl;

import org.springframework.stereotype.Service;

import com.cargobackend.dao.IUserDAO;
import com.cargobackend.pojo.dao.user.UserDetails;
import com.cargobackend.pojo.response.UserDetailsResponse;
import com.cargobackend.service.IUserService;

@Service
public class UserServiceImpl implements IUserService{
	
	
    private final IUserDAO userDAO;
    
	public UserServiceImpl(IUserDAO userDAO) {
		super();
		this.userDAO = userDAO;
	}


	@Override
	public UserDetailsResponse registerUser(UserDetails userDetails) {
		// TODO Auto-generated method stub
		return userDAO.registerUser(userDetails);
	}


	@Override
	public UserDetailsResponse getUserDetails(UserDetails userDetails) {
		// TODO Auto-generated method stub
		return userDAO.getUserDetails(userDetails);
	}


	@Override
	public UserDetailsResponse authenticateUser(UserDetails userDetails) {
		// TODO Auto-generated method stub
		return userDAO.authenticateUser(userDetails);
	}


	@Override
	public UserDetailsResponse logOutUser(UserDetails userDetails) {
		// TODO Auto-generated method stub
		return userDAO.logOutUser(userDetails);
	}

}
