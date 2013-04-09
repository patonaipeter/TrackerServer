package at.ac.tuwien.server.test.db.racing;

import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import at.ac.tuwien.server.dao.interfaces.ILocationDao;
import at.ac.tuwien.server.dao.interfaces.IRaceDao;
import at.ac.tuwien.server.dao.interfaces.IUserDao;
import at.ac.tuwien.server.domain.Location;
import at.ac.tuwien.server.domain.User;
import at.ac.tuwien.server.service.interfaces.IRaceService;
import at.ac.tuwien.server.service.interfaces.IUserService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:root-context-test2.xml")
public class SimulateRacing2 {

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
	
	@Test
	@Transactional
	@Rollback(false)
	public void saveRaceUpdate1(){
		
		//this test sets one race location in every 2 seconds 
		
		User user1 = userService.getUser("adroiduser", "admin");
		double longitude = 10;
		double latitude = 10;
		for(int i = 0; i< 50; i++){
			longitude += i*0.0003;
			latitude += i*0.0003;
			setNewRaceLocation(user1, new Integer(9), longitude, latitude);
			
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	
	}

	
	
	@Transactional
	@Rollback(false)
	public void setNewRaceLocation(User user, Integer raceId, double longitude , double latitude){
		Location loc = new Location();
		loc.setLatitude(latitude);
		loc.setLongitude(longitude);
		loc.setAltitude(new Double(0));
		loc.setTimestamp(new Date());
		loc.setUser(user);
		
		raceService.setRaceLocation(raceId, loc);
		
	}
	
}
