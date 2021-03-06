package com.chinaglia.service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.chinaglia.model.Role;
import com.chinaglia.model.User;
import com.chinaglia.repository.RoleRepository;
import com.chinaglia.repository.UserRepository;

@Service("userService")
public class UserServiceImpl implements UserService{

	@Autowired
	private UserRepository userRepository;
	@Autowired
    private RoleRepository roleRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Override
	public User findUserByEmail(String email) {
		return userRepository.findByEmail(email);
	}	
	
	//return role of user
	public Set<Role> findUsersRole(String email) {
		User user = findUserByEmail(email);
		Set<Role> userRole = user.getRoles(); 
		return userRole;
	}

	@Override
	public void saveUser(User user) {
		//encrypt the chosen password
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		//mark new user as active
        user.setActive(1);
        //set default role for user who is being registered
        Role userRole = roleRepository.findByRole("USER");
        user.setRoles(new HashSet<Role>(Arrays.asList(userRole)));
		userRepository.save(user);
	}

}