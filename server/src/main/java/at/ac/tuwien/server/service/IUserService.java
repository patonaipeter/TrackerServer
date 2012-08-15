package at.ac.tuwien.server.service;

import org.springframework.transaction.annotation.Transactional;

import at.ac.tuwien.server.domain.User;

public interface IUserService {

	/**
	 * register an user
	 * @param user
	 */
	public void createUser(User user);
	
}
