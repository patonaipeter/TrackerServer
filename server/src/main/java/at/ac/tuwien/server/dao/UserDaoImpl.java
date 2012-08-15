package at.ac.tuwien.server.dao;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import at.ac.tuwien.server.domain.User;

@Repository("userDao")
public class UserDaoImpl implements IUserDao{

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	@Transactional
	public void addUser(User user) {
		/** Example from an other project
		 * 
		  SpringUserMapper mapper = new SpringUserMapper();
			UserDetails userDetails = mapper.mapUser(user);
	        String password = userDetails.getPassword();
	        Object salt = saltSource.getSalt(userDetails);
	        user.setPassword(passwordEncoder.encodePassword(password, salt));

			sessionFactory.getCurrentSession().save(user);
		*/
		//TODO add password encryption
		sessionFactory.getCurrentSession().save(user);
		
	}
	
	
	
}
