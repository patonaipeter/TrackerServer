package at.ac.tuwien.server.dao;

import java.util.Set;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import at.ac.tuwien.server.domain.Location;
import at.ac.tuwien.server.domain.Race;

@Repository
public class LocationDaoImpl implements ILocationDao {

	@Autowired
	private SessionFactory sessionFactory;
	@Override
	@Transactional
	public void saveLocation(Location location) {
		sessionFactory.getCurrentSession().saveOrUpdate(location);
	}
	@Override
	@Transactional
	public void saveLocations(Set<Location> locations) {
		for(Location l : locations){
			this.saveLocation(l);
		}
		
	}
	@Transactional
	public Location getLastLocation(Race defaultLoggingRace) {
		
		if(defaultLoggingRace.getId() == null) return null;
		
		Query q = sessionFactory.getCurrentSession().createQuery("select l from Location l where l.race.id =:raceid order by l.timestamp desc");
		q.setParameter("raceid", defaultLoggingRace.getId());
		q.setMaxResults(1);
		return (Location) q.uniqueResult();
	}
	
	@Transactional
	public Location getFirstLocation(Race defaultLoggingRace) {
		
		if(defaultLoggingRace.getId() == null) return null;
		
		Query q = sessionFactory.getCurrentSession().createQuery("select l from Location l where l.race.id =:raceid order by l.timestamp asc");
		q.setParameter("raceid", defaultLoggingRace.getId());
		q.setMaxResults(1);
		return (Location) q.uniqueResult();
	}

}
