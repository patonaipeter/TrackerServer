package at.ac.tuwien.server.service;

import at.ac.tuwien.server.domain.User;

public interface IUserService {

	/**
	 * register an user
	 * @param user
	 */
	public void createUser(User user);
	
	public void getUser(String username, String pass);
	
}
