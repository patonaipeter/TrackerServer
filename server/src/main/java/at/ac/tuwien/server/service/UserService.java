package at.ac.tuwien.server.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import at.ac.tuwien.server.Constants;
import at.ac.tuwien.server.dao.IRaceDao;
import at.ac.tuwien.server.dao.IUserDao;
import at.ac.tuwien.server.domain.Race;
import at.ac.tuwien.server.domain.User;

@Service("userService")
public class UserService implements IUserService {


	@Autowired
	IUserDao userDao;
	
	@Autowired
	IRaceDao raceDao;
	
	@Override
	@Transactional
	public void createUser(User user) {
		user.setLast_activity_date(new Date());
		user.setRegister_date(new Date());
		userDao.addUser(user);
		
		//initiate default race (the race to that we store baseic logging infos)
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

	
	
}
