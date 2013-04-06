package at.ac.tuwien.server.test.db.basictracking;

import java.io.FileInputStream;
import java.io.IOException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import at.ac.tuwien.server.domain.User;
import at.ac.tuwien.server.service.interfaces.ILocationService;
import at.ac.tuwien.server.service.interfaces.IUserService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:root-context-test2.xml")
public class GPXTest {

	@Autowired
	ILocationService locationService;
	
	@Autowired
	IUserService userService;
	
	
	@Test
	@Transactional
	@Rollback(false)
	public void saveRaceUpdate1() throws IOException{
		
		
		/**
		 *  IMPORTANT!
		 *  
		 *  To run this test the timestamp of the test coordinates must be in the future
		 * 
		 * 
		 */
		
		User user1 = userService.getUser("admin", "admin");
		FileInputStream inputFile = new FileInputStream("src/test/ViennaTestRoute.xml");
		MockMultipartFile file = new MockMultipartFile("file", "ViennaTestRoute.xml", "multipart/form-data", inputFile);
		
		locationService.parseAndSaveGPX(file, user1);
	}

	
	
}
