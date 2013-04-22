package at.ac.tuwien.server.test.db.serviceTests;

import java.util.ArrayList;
import java.util.List;

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
import at.ac.tuwien.server.domain.Race;
import at.ac.tuwien.server.domain.User;
import at.ac.tuwien.server.domain.dtos.UserDTO;
import at.ac.tuwien.server.domain.dtos.UserListDTO;
import at.ac.tuwien.server.service.interfaces.IRaceService;
import at.ac.tuwien.server.service.interfaces.IUserService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:root-context-test2.xml")
public class ServiceTest1 {

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
	public void test1(){
		
		User user = userService.getUser("admin", "admin");
		
	
		
		Race race = raceDao.getRaceById(8);
		
		List<User> users = race.getParticipants();
		UserListDTO userListDTO = new UserListDTO();
		List<UserDTO> dtos = new ArrayList<UserDTO>();
		for(User u : users){
			UserDTO dto = new UserDTO();
			
			dto.setEmail(u.getEmail());
			dto.setId(u.getId());
			dto.setLast_activity_date(u.getLast_activity_date().getTime());
			dto.setNumOfFriends(u.getFirends().size());
			dto.setRegister_date(u.getRegister_date().getTime());
			dto.setScore(u.getScore());
			dto.setUsername(u.getUsername());
			
			dtos.add(dto);
		}
		userListDTO.setUserList(dtos);
	
		System.out.println("Size: "+ dtos.size());
	}
		

}
