package at.ac.tuwien.server.test.db.statistics;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;

import at.ac.tuwien.server.controller.StatisticsController;
import at.ac.tuwien.server.domain.User;
import at.ac.tuwien.server.domain.dtos.StatisticsDTO;
import at.ac.tuwien.server.service.impl.UserService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:root-context-test2.xml")
public class StatTest {

	@Autowired
	UserService userService;
	
	@Test
	@Transactional
	public void StatTest(){
		
		User u = userService.getUser("admin","admin");
		StatisticsDTO stats = userService.getStatisticsForUser(u);
		
		System.out.println(stats.getName());
		
	}
	
}
