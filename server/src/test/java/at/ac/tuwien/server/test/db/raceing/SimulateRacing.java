package at.ac.tuwien.server.test.db.raceing;

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
public class SimulateRacing {

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
		
		
		User user1 = userService.getUser("peter", "admin");
	
		Location loc = new Location();
		loc.setLatitude(new Double(10.0012));
		loc.setLongitude(new Double(10.012));
		loc.setAltitude(new Double(0));
		loc.setTimestamp(new Date());
		loc.setUser(user1);
		
		raceService.setRaceLocation(new Integer(9), loc);
	}

	
	
}
