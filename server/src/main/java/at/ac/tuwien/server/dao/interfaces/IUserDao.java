package at.ac.tuwien.server.dao.interfaces;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import at.ac.tuwien.server.domain.User;

public interface IUserDao {

	/**
	 * Saves an User in the DB
	 * @param User
	 */
	@Transactional
	void addUser(User User);

	@Transactional
	User getUser(String username);
	@Transactional
	List<User> retrieveAllUsers();
	@Transactional
	User getUserById(Integer id);

	@Transactional
	List<User> getTopList();
	
}
