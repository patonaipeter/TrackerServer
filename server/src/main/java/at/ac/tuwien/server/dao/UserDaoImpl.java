package at.ac.tuwien.server.dao;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import at.ac.tuwien.server.dao.interfaces.IUserDao;
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

	
	@Override
	@Transactional
	public User getUser(String username) {
		Query query = sessionFactory.getCurrentSession().createQuery(
				"select u" +
				" from User u" +
				" where username = :username");
		query.setParameter("username", username);

		User user = null;
		
		try {
			user = (User) query.uniqueResult(); // Returns null if not found
		} catch (HibernateException e) { // If there are more than one results (conflicted usernames)
			user = null;
		}

		return user;
	}


	@Override
	public List<User> retrieveAllUsers() {
		List<User> list = (List<User>) sessionFactory.getCurrentSession().createCriteria(User.class).list();
		return list;
	}


	@Override
	@Transactional
	public User getUserById(Integer id) {
		return (User) sessionFactory.getCurrentSession().get(User.class, id);
	}


	@Override
	@Transactional
	public List<User> getTopList() {
		Query query = sessionFactory.getCurrentSession().createQuery("select u from User u order by u.score DESC" );
		query.setMaxResults(10);
		return (List<User>) query.list();
		
	}
	
	
}
