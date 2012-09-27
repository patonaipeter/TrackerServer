package at.ac.tuwien.server.dao;

import java.util.Date;
import java.util.List;
import java.util.Set;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import at.ac.tuwien.server.domain.Location;
import at.ac.tuwien.server.domain.Race;
import at.ac.tuwien.server.domain.User;

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
	
	@Override
	@Transactional
	public List<User> getNearUsers(Double longitude, Double latitude, Double radius, Long timeinterval) {
		
		Query q = sessionFactory.getCurrentSession().createQuery("select loc.user from Location loc where loc.timestamp > :mintime AND" +
				" loc.timestamp < :maxtime AND loc.longitude > :minlongitude AND loc.longitude < :maxlongitude AND" +
				" loc.latitude > :minlatitude AND loc.latitude < :maxlatitude");
		
		
		q.setParameter("mintime", new Date(new Date().getTime() - timeinterval/2));
		q.setParameter("maxtime", new Date(new Date().getTime() + timeinterval/2));
		q.setParameter("minlongitude", longitude - radius);
		q.setParameter("maxlongitude", longitude + radius);
		q.setParameter("minlatitude", latitude - radius);
		q.setParameter("maxlatitude", latitude + radius);
		
		List<User> userList = q.list();
		return userList;
	}
	@Override
	@Transactional
	public Location getLastPositionOfUser(User u) {
		Query q = sessionFactory.getCurrentSession().createQuery("SELECT l FROM User u JOIN u.races r JOIN r.locations l WHERE u.id = :userid ORDER BY l.timestamp desc");
		q.setParameter("userid", u.getId());
		q.setMaxResults(1);
		Location loc = (Location) q.uniqueResult();
		return loc;
	}
	@Override
	@Transactional
	public Location getUserLocationForDate(User u, Long time) {
		
		Query q = sessionFactory.getCurrentSession().createQuery("select loc from Location loc where loc.timestamp > :mintime AND" +
				" loc.timestamp < :maxtime AND loc.user.id = :userid");
		Date referenceDate = new Date(time);
		//30 min
		long timeinterval = 1000*60*30;
		q.setParameter("mintime", new Date(referenceDate.getTime() - timeinterval/2));
		q.setParameter("maxtime", new Date(referenceDate.getTime() + timeinterval/2));
		q.setParameter("userid", u.getId());
		
		if(!q.list().isEmpty()){
			//TODO if more result give the one in the middle of the range back
			Location l = (Location) q.list().get(0);
			return l;
		}else{
			return null;
		}
			
	}

}
