package at.ac.tuwien.server.service;

import org.springframework.transaction.annotation.Transactional;

import at.ac.tuwien.server.domain.User;

public interface IUserService {

	/**
	 * register an user
	 * @param user
	 */
	@Transactional
	public void createUser(User user);
	
	@Transactional
	public User getUser(String username, String pass);
	
}
