package at.ac.tuwien.server.dao;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import at.ac.tuwien.server.domain.Race;

@Repository
public class RaceDaoImpl implements IRaceDao {

	@Autowired
	private SessionFactory sessionFactory;
	@Override
	@Transactional
	public void saveRace(Race race) {
		sessionFactory.getCurrentSession().saveOrUpdate(race);
	}

}
