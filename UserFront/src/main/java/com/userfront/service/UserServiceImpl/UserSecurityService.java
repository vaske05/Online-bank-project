package com.userfront.service.UserServiceImpl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.userfront.DataAccessObject.UserDao;
import com.userfront.domain.User;

public class UserSecurityService implements UserDetailsService {
	
	// The Application logger
	private static final Logger LOG = LoggerFactory.getLogger(UserSecurityService.class);
	
	@Autowired
	private UserDao userDao;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		User user = userDao.findByUsername(username);
		if(user == null) {
			LOG.warn("Username {} not found", username);
			throw new UsernameNotFoundException("Username " + username + " not found.");
		}
		return user;
	}
}
