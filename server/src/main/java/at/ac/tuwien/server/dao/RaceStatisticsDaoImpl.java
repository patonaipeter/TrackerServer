package at.ac.tuwien.server.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import at.ac.tuwien.server.dao.interfaces.IRaceStatisticsDao;
import at.ac.tuwien.server.domain.Location;
import at.ac.tuwien.server.domain.Race;
import at.ac.tuwien.server.domain.RaceStatistics;
import at.ac.tuwien.server.domain.User;

@Repository
public class RaceStatisticsDaoImpl implements IRaceStatisticsDao {

	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	@Transactional
	public void saveRaceStats(RaceStatistics stat) {
		sessionFactory.getCurrentSession().saveOrUpdate(stat);
		
	}

	@Override
	@Transactional
	public List<RaceStatistics> retrieveRaceStatisticsForRace(Integer raceid) {
		
		Query q = sessionFactory.getCurrentSession().createQuery("select r from RaceStatistics r where r.race.id =:raceid");
		q.setParameter("raceid", raceid);
		
		List<RaceStatistics> liste = q.list();
		
		return liste;
	}

	@Override
	public RaceStatistics retrieveRaceStatisticsForRaceAndUser(Race race, User user) {

		if(race == null || user == null) return null;
		
		Query q = sessionFactory.getCurrentSession().createQuery("select r from RaceStatistics r where r.race.id =:raceid and r.user.id =:userid");
		q.setParameter("raceid", race.getId());
		q.setParameter("userid", user.getId());
		q.setMaxResults(1);
		//TODO is there always a uniqeresult?
		return (RaceStatistics) q.uniqueResult();
	

	}

	
}
