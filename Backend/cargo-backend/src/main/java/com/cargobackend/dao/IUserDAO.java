package com.cargobackend.dao;

import com.cargobackend.pojo.dao.user.UserDetails;
import com.cargobackend.pojo.response.UserDetailsResponse;

public interface IUserDAO {

	public UserDetailsResponse registerUser(UserDetails userDetails);
	
	public UserDetailsResponse getUserDetails(UserDetails userDetails);
	
	public UserDetailsResponse authenticateUser(UserDetails userDetails);
	
	public UserDetailsResponse logOutUser(UserDetails userDetails);
}
