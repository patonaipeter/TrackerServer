package at.ac.tuwien.server.dao;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import at.ac.tuwien.server.domain.Location;

@Repository
public class LocationDaoImpl implements ILocationDao {

	@Autowired
	private SessionFactory sessionFactory;
	@Override
	@Transactional
	public void saveLocation(Location location) {
		sessionFactory.getCurrentSession().saveOrUpdate(location);
	}

}
