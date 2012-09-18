package at.ac.tuwien.server.service.interfaces;

import org.springframework.transaction.annotation.Transactional;

import at.ac.tuwien.server.domain.User;
import at.ac.tuwien.server.domain.dtos.StatisticsDTO;

public interface IUserService {

	/**
	 * register an user
	 * @param user
	 */
	@Transactional
	public void createUser(User user);
	
	@Transactional
	public User getUser(String username, String pass);

	@Transactional
	public StatisticsDTO getStatisticsForUser(User u);
	
}
