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

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:root-context-test.xml")
public class PopulateDB {

	@Autowired
	IUserDao userDao;
	@Autowired
	IRaceDao raceDao;
	
	@Test
	@Transactional
	@Rollback(false)
	public void fillDB(){
		
		User u = new User();
		u.setEmail("admin@admin.com");
		u.setLast_activity_date(new Date());
		u.setPassword("admin");
		u.setRegister_date(new Date());
		u.setScore(10);
		u.setUsername("admin");
		
		userDao.addUser(u);
		
		Race race = new Race();
		List<User> userList = new ArrayList<User>();
		userList.add(u);
		race.setParticipants(userList);
		
		race.setRaceName(Constants.defaultRace);
		raceDao.saveRace(race);
	
	}
	
	
}
