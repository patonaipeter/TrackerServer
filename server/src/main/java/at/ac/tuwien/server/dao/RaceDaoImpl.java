package at.ac.tuwien.server.dao;

import java.util.Set;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import at.ac.tuwien.server.Constants;
import at.ac.tuwien.server.domain.Race;
import at.ac.tuwien.server.domain.User;
import at.ac.tuwien.server.service.stats.StatisticsHelper;

@Repository
public class RaceDaoImpl implements IRaceDao {

	@Autowired
	private SessionFactory sessionFactory;
	@Override
	@Transactional
	public void saveRace(Race race) {
		sessionFactory.getCurrentSession().saveOrUpdate(race);
	}
	@Override
	@Transactional
	public Race getDefaultRaceForUser(User u) {
		for(Race r : u.getRaces()){
			if(r.getRaceName().equals(Constants.defaultRace)){
				return r;
			}
		}
		return null;
	}
	
	@Override
	@Transactional
	public Integer getNumberOfRaces(User u) {
		return new Integer(u.getRaces().size()-1);
	}
	@Override
	@Transactional
	public Double getDistanceInRaces(User u) {
		return StatisticsHelper.getDistanceInRaces(u.getRaces());
	}
	@Override
	@Transactional
	public Race getRaceById(Integer id) {
		Race race = (Race) sessionFactory.getCurrentSession().get(Race.class, id);
		return race;
	}
	@Override
	@Transactional
	public Race saveNewRace(String name) {
		Race race = new Race();
		race.setRaceName(name);
		sessionFactory.getCurrentSession().saveOrUpdate(race);
		sessionFactory.getCurrentSession().flush();
		return race;
	}
	

}
