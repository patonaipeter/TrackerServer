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
import at.ac.tuwien.server.dao.ILocationDao;
import at.ac.tuwien.server.dao.IRaceDao;
import at.ac.tuwien.server.dao.IUserDao;
import at.ac.tuwien.server.domain.Location;
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
	ILocationDao locationDao;
	
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
	
	@Test
	@Transactional
	@Rollback(false)
	public void fillDb2(){
		//this needs another transaction (race must be saved before)
		User user2 = userService.getUser("admin", "admin");
		this.addLocationToUser(48.2,16.3,user2);
	}
	
	
	@Test
	@Transactional
	@Rollback(false)
	public void fillDb3(){
		User user1 = userService.getUser("peter", "admin");
		this.addLocationToUser(48.200002,16.3,user1);
		
		User user2 = userService.getUser("androiduser", "admin");
		this.addLocationToUser(48.200001,16.3,user2);
		
		User user3 = userService.getUser("test", "test");
		this.addLocationToUser(48.2,16.300001,user3);
		
	}

	@Test
	@Transactional
	@Rollback(false)
	public void testDbLayer1(){
		System.out.println("HALLLLLLLLLLLLLLLLLLOOO");
		
		List<User> users = locationDao.getNearUsers(48.2, 16.3, 0.0001, new Long(1000*60*20));
		System.out.println(users.size());
	}
	
	
	private void addLocationToUser(double d, double e, User user2) {
		Location loc = new Location();
		loc.setLatitude(e);
		loc.setLongitude(d);
		loc.setUser(user2);
		loc.setRace(raceDao.getDefaultRaceForUser(user2));
		loc.setTimestamp(new Date());
		locationDao.saveLocation(loc);
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
