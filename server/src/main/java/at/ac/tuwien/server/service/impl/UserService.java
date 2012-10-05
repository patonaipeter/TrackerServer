package at.ac.tuwien.server.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import at.ac.tuwien.server.Constants;
import at.ac.tuwien.server.dao.interfaces.ILocationDao;
import at.ac.tuwien.server.dao.interfaces.IMessageDao;
import at.ac.tuwien.server.dao.interfaces.IRaceDao;
import at.ac.tuwien.server.dao.interfaces.IUserDao;
import at.ac.tuwien.server.domain.Location;
import at.ac.tuwien.server.domain.Message;
import at.ac.tuwien.server.domain.MessageType;
import at.ac.tuwien.server.domain.Race;
import at.ac.tuwien.server.domain.User;
import at.ac.tuwien.server.domain.dtos.StatisticsDTO;
import at.ac.tuwien.server.service.interfaces.IRaceService;
import at.ac.tuwien.server.service.interfaces.IUserService;

@Service("userService")
public class UserService implements IUserService {
	@Autowired
	IUserDao userDao;
	@Autowired
	IRaceDao raceDao;
	@Autowired
	IRaceService raceService;
	@Autowired
	IMessageDao messageDao;
	@Autowired
	ILocationDao locationDao;
	
	@Override
	@Transactional
	public void createUser(User user) {
		user.setLast_activity_date(new Date());
		user.setRegister_date(new Date());
		userDao.addUser(user);
		
		//initiate default race (the race to that we store basic logging infos)
		Race race = new Race();
		List<User> userList = new ArrayList<User>();
		userList.add(user);
		race.setParticipants(userList);
		
		race.setRaceName(Constants.defaultRace);
		raceDao.saveRace(race);
	}

	@Override
	@Transactional
	public User getUser(String username, String pass) {
		User user = userDao.getUser(username);
		if (user.getPassword().equals(pass)){
			return user;
		}else{
			return null;
		}

	}

	@Override
	@Transactional
	public StatisticsDTO getStatisticsForUser(User u) {
		StatisticsDTO stat = new StatisticsDTO();
		Race race = raceDao.getDefaultRaceForUser(u);
		
		stat.setAvgSpeed(race.getAvgSpeed());
		stat.setDistance(race.getDistance());
		stat.setElevation(race.getOverallElevation());
		stat.setName(race.getRaceName());
		
		stat.setNumberOfRaces(raceDao.getNumberOfRaces(u));
		stat.setDistanceInRaceMode(raceDao.getDistanceInRaces(u));
		stat.setAvgSpeedInRaceMode(raceService.getAvgSpeedInRaces(u));
		
		//TODO racewin
		stat.setRaceWins(new Integer(0));
		return stat;
	}

	@Override
	@Transactional
	public List<User> retrieveAllUsers() {
		return userDao.retrieveAllUsers();
	}

	@Override
	@Transactional
	public User getUserById(Integer id) {
		return userDao.getUserById(id);
	}

	@Override
	@Transactional
	public Message getMessageById(Integer id) {
		return messageDao.getMessageById(id);
	}

	@Override
	@Transactional
	public void sendFriendRequest(User u, User friend) {
		Message msg = new Message();
		msg.setSender(u);
		msg.setReceiver(friend);
		msg.setMsgType(MessageType.FRIENDREQUEST);
		msg.setUnread(true);
		msg.setSentDate(new Date());
		messageDao.saveMsg(msg);
	}

	@Override
	@Transactional
	public void addFriend(User user, User friend) {
		user.addFriend(friend);
		friend.addFriend(user);
	}

	@Override
	@Transactional
	public void deleteMsgById(Integer id) {
		messageDao.deleteMsg(messageDao.getMessageById(id));
	}

	@Override
	@Transactional
	public List<Message> retrieveAllMessagesForUser(User u) {
		return messageDao.getAllMessagesForUser(u);
	}

	@Override
	@Transactional
	public List<Message> retrieveAllFriendRequestsForUser(User u) {
		List<Message> messages = this.retrieveAllMessagesForUser(u);
		List<Message> filteredMsgs = new ArrayList<Message>();
		for(Message m : messages){
			if(m.getMsgType().equals(MessageType.FRIENDREQUEST)) filteredMsgs.add(m);
		}
		return filteredMsgs;
	}

	@Override
	@Transactional
	public List<User> retrieveNearUsers(User user, Double longitude, Double latitude, Double radius) {
		//10min
		Long timeradius = (long) (1000*60*10);
		List<User> usersList = locationDao.getNearUsers(longitude, latitude, radius, timeradius);
		return usersList;
	}

	@Override
	@Transactional
	public Location getLastPositionOfUser(User u) {
		return locationDao.getLastPositionOfUser(u);
	}

	@Override
	public Location getUserLocationForDate(User u, Long time) {
		Location location = locationDao.getUserLocationForDate(u, time);
		return location;
	}

}
