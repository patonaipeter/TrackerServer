package at.ac.tuwien.server.service.interfaces;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import at.ac.tuwien.server.domain.Location;
import at.ac.tuwien.server.domain.Message;
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
	@Transactional
	public List<User> retrieveAllUsers();
	@Transactional
	public User getUserById(Integer id);
	@Transactional
	public Message getMessageById(Integer id);
	@Transactional
	public void sendFriendRequest(User u, User friend);
	@Transactional
	public void addFriend(User user, User friend);
	@Transactional
	public void deleteMsgById(Integer id);
	@Transactional
	public List<Message> retrieveAllMessagesForUser(User u);
	@Transactional
	public List<Message> retrieveAllFriendRequestsForUser(User u);
	@Transactional
	public List<User> retrieveNearUsers(User user, Double longitude,
			Double latitude, Double radius);
	@Transactional
	public Location getLastPositionOfUser(User u);
	@Transactional
	public Location getUserLocationForDate(User u, Long time);
	@Transactional
	public void sendMessage(User u, List<String> userids, String msgTopic,
			String msgText);
	@Transactional
	public void sendNearToYouNotification(Message msg);
	
	
	
}
