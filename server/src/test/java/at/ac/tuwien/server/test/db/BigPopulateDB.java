package at.ac.tuwien.server.test.db;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.TreeSet;

import org.hibernate.exception.ConstraintViolationException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import at.ac.tuwien.server.Constants;
import at.ac.tuwien.server.dao.interfaces.ILocationDao;
import at.ac.tuwien.server.dao.interfaces.IRaceDao;
import at.ac.tuwien.server.dao.interfaces.IUserDao;
import at.ac.tuwien.server.domain.Location;
import at.ac.tuwien.server.domain.Race;
import at.ac.tuwien.server.domain.User;
import at.ac.tuwien.server.service.interfaces.IRaceService;
import at.ac.tuwien.server.service.interfaces.IUserService;
import at.ac.tuwien.server.service.stats.StatisticsHelper;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:root-context-test.xml")
public class BigPopulateDB {

	@Autowired
	IUserDao userDao;
	@Autowired
	IRaceDao raceDao;
	@Autowired
	ILocationDao locationDao;
	
	@Autowired
	IUserService userService;
	
	@Autowired
	IRaceService raceService;
	
	
	//Create Users
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
		

		
		
	}
	
	//Create Friend Connections
	@Test
	@Transactional
	@Rollback(false)
	public void fillDb2(){
		User user1 = userService.getUser("admin", "admin");
		
		User user2 = userService.getUser("peter", "admin");
		User user3 = userService.getUser("androiduser", "admin");
		User user4 = userService.getUser("test", "test");
		User user5 = userService.getUser("chalkidiki", "admin");
		User user6 = userService.getUser("samos", "admin");
		User user7 = userService.getUser("ikarios", "admin");
		
		//everybody is friend with admin 
		userService.addFriend(user1, user2);
		userService.addFriend(user1, user3);
		userService.addFriend(user1, user4);
		userService.addFriend(user1, user5);
		userService.addFriend(user1, user6);
//		userService.addFriend(user1, user7);
		userService.sendFriendRequest(user7, user1);
		List<String> userlist = new ArrayList<String>();
		userlist.add(user1.getId()+"");
		userService.sendMessage(user7, userlist, "Hallo", "Hallo I want to be your friend (this is a message) it is made longer to see whether this thing scales up correctly as i want it to be aaaaaaaaaaaaaaaaaaaaaaaaaaaaaa  aaaaaaaaa aaaaaaaaaaaaaaaaaaaaaa");
		//other connections
		userService.addFriend(user2, user3);
		userService.addFriend(user2, user4);
		
		
		userService.addFriend(user3, user4);
		userService.addFriend(user3, user5);
		userService.addFriend(user3, user6);
		userService.addFriend(user4, user6);
		
		user1.setScore(965);
		user2.setScore(859);
		user3.setScore(711);
		user4.setScore(325);
		user5.setScore(121);
		user6.setScore(76);
		user7.setScore(59);
	}
	
	
	//Add Historical locations
	@Test
	@Transactional
	@Rollback(false)
	public void fillDb3(){
		User user1 = userService.getUser("admin", "admin");
		User user2 = userService.getUser("peter", "admin");
		User user3 = userService.getUser("androiduser", "admin");
		
		//number of locations
		int i = 1000;
		int days = 10;
		int dayinMs = 1000*60*60*24;
		
	
			
			//random beginning coordinates
	
		
		Set<Location> locSet = new TreeSet<Location>();
		
		for(int n = 0; n<days;n++){
				
			Date date = new Date(new Date().getTime()-n*dayinMs);
			//random start coordinates
			double lat = 48.2;
			double lon = 16.3;
			double changeLat = new Random().nextDouble()/1000;
			double changeLon = new Random().nextDouble()/1000;
			
			for(int j = 0; j<i;j++){
				//mutate coordinates
				double newLat = lat + j*changeLat;
				double newLon = lon + j*changeLon;
				//in each 15 minutes
				Date newDate = new Date(date.getTime()+(j*(1000*60*15)));
				
				locSet.add(this.addLocationToUser(newLat,newLon,newDate,user1));
				
			}
				
		}
		
		Race defaultLoggingRace = raceDao.getDefaultRaceForUser(user1);
		defaultLoggingRace = StatisticsHelper.updateRaceStats(defaultLoggingRace, 
				 locSet,
				 locationDao.getLastLocationOfUser(defaultLoggingRace, user1),
				 locationDao.getFirstLocationOfUser(defaultLoggingRace, user1));
		defaultLoggingRace.setLocations(locSet);
		
		locSet.clear();
			
		for(int n = 0; n<days;n++){
			
			Date date = new Date(new Date().getTime()-n*dayinMs);
			//random start coordinates
			double lat = 47 + new Random().nextDouble();
			double lon = 13 + new Random().nextDouble()*2;
			double changeLat = new Random().nextDouble()/1000;
			double changeLon = new Random().nextDouble()/1000;
			
			for(int j = 0; j<i;j++){
				//mutate coordinates
				double newLat = lat + j*changeLat;
				double newLon = lon + j*changeLon;
				//in each 15 minutes
				Date newDate = new Date(date.getTime()+(j*(1000*60*15)));
				
				locSet.add(this.addLocationToUser(newLat,newLon,newDate,user2));
				
			}
				
		}
		
		
		Race defaultLoggingRace2 = raceDao.getDefaultRaceForUser(user2);
		defaultLoggingRace2 = StatisticsHelper.updateRaceStats(defaultLoggingRace2, 
				 locSet,
				 locationDao.getLastLocationOfUser(defaultLoggingRace2, user2),
				 locationDao.getFirstLocationOfUser(defaultLoggingRace2, user2));
		defaultLoggingRace2.setLocations(locSet);
		
		locSet.clear();
			
		
		for(int n = 0; n<days;n++){
			
			Date date = new Date(new Date().getTime()-n*dayinMs);
			//random start coordinates
			double lat = 48 + new Random().nextDouble();
			double lon = 14 + new Random().nextDouble()*2;
			double changeLat = new Random().nextDouble()/1000;
			double changeLon = new Random().nextDouble()/1000;
			
			for(int j = 0; j<i;j++){
				//mutate coordinates
				double newLat = lat + j*changeLat;
				double newLon = lon + j*changeLon;
				//in each 15 minutes
				Date newDate = new Date(date.getTime()+(j*(1000*60*15)));
				
				locSet.add(this.addLocationToUser(newLat,newLon,newDate,user3));
				
			}
				
		}
		
		Race defaultLoggingRace3 = raceDao.getDefaultRaceForUser(user3);
		defaultLoggingRace3 = StatisticsHelper.updateRaceStats(defaultLoggingRace3, 
				 locSet,
				 locationDao.getLastLocationOfUser(defaultLoggingRace3, user3),
				 locationDao.getFirstLocationOfUser(defaultLoggingRace3, user3));
		defaultLoggingRace3.setLocations(locSet);
		
		locSet.clear();
		
	
		
	}
	
	
	
	//Add Races
	@Test
	@Transactional
	@Rollback(false)
	public void fillDb4(){
		
		User user1 = userService.getUser("admin", "admin");
		User user2 = userService.getUser("peter", "admin");
		User user3 = userService.getUser("androiduser", "admin");
//		User user6 = userService.getUser("samos", "admin");
		
		int dayinMs = 1000*60*60*24;
		
	
		Race race = new Race();
		List<User> userList = new ArrayList<User>();
		userList.add(user1);
		userList.add(user2);
		userList.add(user3);
		race.setParticipants(userList);
		
		race.setRaceName("FirstTestRace");
		raceDao.saveRace(race);
		
		//1 race between user 1-2-3
		
	
		
		int i = 50;
		Date date = new Date(new Date().getTime()-13*dayinMs);
		//random start coordinates
		double lat = 47 + new Random().nextDouble();
		double lon = 13 + new Random().nextDouble()*2;
		double changeLat = new Random().nextDouble()/1000;
		double changeLon = new Random().nextDouble()/1000;
		
		for(int j = 0; j<i;j++){
			//mutate coordinates
			double newLat = lat + j*changeLat;
			double newLon = lon + j*changeLon;
			//in each 15 minutes
			Date newDate = new Date(date.getTime()+(j*(1000*60*1)));
			Location loc = new Location();
			loc.setLatitude(newLat);
			loc.setLongitude(newLon);
			loc.setAltitude(new Double(0));
			loc.setUser(user1);
			loc.setRace(race);
			loc.setTimestamp(newDate);
			locationDao.saveLocation(loc);
			
			raceService.setRaceHistoricalLocation(race.getId(),loc);
			
		}
		
		
		 i = 50;
		 date = new Date(new Date().getTime()-13*dayinMs);
		//random start coordinates
		 lat = 47 + new Random().nextDouble();
		 lon = 13 + new Random().nextDouble()*2;
		 changeLat = new Random().nextDouble()/1000;
		 changeLon = new Random().nextDouble()/1000;
		
		for(int j = 0; j<i;j++){
			//mutate coordinates
			double newLat = lat + j*changeLat;
			double newLon = lon + j*changeLon;
			//in each 15 minutes
			Date newDate = new Date(date.getTime()+(j*(1000*60*1)));
			Location loc = new Location();
			loc.setLatitude(newLat);
			loc.setLongitude(newLon);
			loc.setAltitude(new Double(0));
			loc.setUser(user2);
			loc.setRace(race);
			loc.setTimestamp(newDate);
			locationDao.saveLocation(loc);
			
			raceService.setRaceHistoricalLocation(race.getId(),loc);
			
		}
		
		
		 i = 50;
		 date = new Date(new Date().getTime()-13*dayinMs);
		//random start coordinates
		 lat = 47 + new Random().nextDouble();
		 lon = 13 + new Random().nextDouble()*2;
		 changeLat = new Random().nextDouble()/1000;
		 changeLon = new Random().nextDouble()/1000;
		
		for(int j = 0; j<i;j++){
			//mutate coordinates
			double newLat = lat + j*changeLat;
			double newLon = lon + j*changeLon;
			//in each 15 minutes
			Date newDate = new Date(date.getTime()+(j*(1000*60*1)));
			Location loc = new Location();
			loc.setLatitude(newLat);
			loc.setLongitude(newLon);
			loc.setAltitude(new Double(0));
			loc.setUser(user3);
			loc.setRace(race);
			loc.setTimestamp(newDate);
			locationDao.saveLocation(loc);
			
			raceService.setRaceHistoricalLocation(race.getId(),loc);
			
		}
		
		
//		List<String> userids = new ArrayList<String>();
//		userids.add(userService.getUser("admin", "admin").getId().toString());
//		raceService.sendRaceInvitation(user1, userids, "PeterRace");
	}

	@Test
	@Transactional
	@Rollback(false)
	public void testDbLayer1(){
		System.out.println("HALLLLLLLLLLLLLLLLLLOOO");
		
		List<User> users = locationDao.getNearUsers(48.2, 16.3, 0.0001, new Long(1000*60*20));
		System.out.println(users.size());
		
//		System.out.println("HALLLLLLLLLLLLLLLLLLOOO");
//		User useradmin = userService.getUser("admin", "admin");
//		List<User> us = userService.retrieveNearUsers(useradmin, 48.2, 16.29999, 0.0001);
//		System.out.println("Number: "+us.size());
		
	}
	
	
	private Location addLocationToUser(double d, double e, Date timestamp, User user) {
		Location loc = new Location();
		loc.setLatitude(e);
		loc.setLongitude(d);
		loc.setAltitude(new Double(0));
		loc.setUser(user);
		Race defaultLoggingRace = raceDao.getDefaultRaceForUser(user);
		loc.setRace(defaultLoggingRace);
		loc.setTimestamp(timestamp);
		locationDao.saveLocation(loc);
		
		return loc;
	
		
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
