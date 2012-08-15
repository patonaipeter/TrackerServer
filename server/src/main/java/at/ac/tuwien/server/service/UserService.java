package at.ac.tuwien.server.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import at.ac.tuwien.server.dao.IUserDao;
import at.ac.tuwien.server.domain.User;

@Service("userService")
public class UserService implements IUserService {


	@Autowired
	IUserDao userDao;
	
	@Override
	@Transactional
	public void createUser(User user) {
		user.setLast_activity_date(new Date());
		user.setRegister_date(new Date());
		userDao.addUser(user);
	}

	
	
}
