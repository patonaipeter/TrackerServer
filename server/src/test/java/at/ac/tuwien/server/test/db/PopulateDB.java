package at.ac.tuwien.server.test.db;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import at.ac.tuwien.server.Constants;
import at.ac.tuwien.server.dao.IRaceDao;
import at.ac.tuwien.server.dao.IUserDao;
import at.ac.tuwien.server.domain.Race;
import at.ac.tuwien.server.domain.User;
import at.ac.tuwien.server.service.interfaces.IUserService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:root-context-test.xml")
public class PopulateDB {

	@Autowired
	IUserDao userDao;
	@Autowired
	IRaceDao raceDao;
	
	@Autowired
	IUserService userService;
	
	@Test
	@Transactional
	@Rollback(false)
	public void fillDB(){
		
		
		this.createTestUser("admin","admin","admin");
		this.createTestUser("peter","admin","peter@patonai.at");
		this.createTestUser("androiduser","admin","peter@android.at");
		this.createTestUser("test","test","test@test.at");
		this.createTestUser("chalkidiki","admin","chalkidiki@patonai.gr");
		this.createTestUser("samos","admin","samos@patonai.gr");
		this.createTestUser("ikarios","admin","ikarios@patonai.gr");
		
		User user1 = userService.getUser("peter", "admin");
		User user2 = userService.getUser("admin", "admin");
	
		userService.sendFriendRequest(user1, user2);
	}

	private void createTestUser(String user, String pass, String email) {
		
		User u = new User();
		u.setEmail(email);
		u.setLast_activity_date(new Date());
		u.setPassword(pass);
		u.setRegister_date(new Date());
		u.setScore(10);
		u.setUsername(user);
		
		userDao.addUser(u);
		
		Race race = new Race();
		List<User> userList = new ArrayList<User>();
		userList.add(u);
		race.setParticipants(userList);
		
		race.setRaceName(Constants.defaultRace);
		raceDao.saveRace(race);
		
	}
	
	
}
