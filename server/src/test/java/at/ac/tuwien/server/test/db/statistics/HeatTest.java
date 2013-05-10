package at.ac.tuwien.server.test.db.statistics;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import at.ac.tuwien.server.domain.Location;
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
		
		List<Location> locList = locationService.getUserLocations(u);
		
		System.out.println(mapLocations(locList));
		
		
	}
	
	private String mapLocations(List<Location> locList) {
		String returnList = "";
		for(Location l : locList){
			returnList += l.getLatitude().toString() +",";
			returnList += l.getLongitude().toString() +",";
		}
		returnList = returnList.substring(0,returnList.length()-1);
		return returnList;
	}
	
	
}
