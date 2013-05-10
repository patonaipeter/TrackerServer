package at.ac.tuwien.server.test.db.statistics;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import at.ac.tuwien.server.domain.User;
import at.ac.tuwien.server.service.interfaces.ILocationService;
import at.ac.tuwien.server.service.interfaces.IUserService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:root-context-test2.xml")
public class HeatTest {

	@Autowired
	ILocationService locationService;
	@Autowired
	IUserService userService;
	
	@Test
	@Transactional
	public void heatTest(){
		
		User u = userService.getUser("admin","admin");
		
		System.out.println(locationService.getUserLocations(u).size());
		
		
	}
	
	
}
