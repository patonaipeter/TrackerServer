package at.ac.tuwien.server.dao;

import org.springframework.transaction.annotation.Transactional;

import at.ac.tuwien.server.domain.Race;
import at.ac.tuwien.server.domain.User;

public interface IRaceDao {

	@Transactional
	public void saveRace(Race location);
	@Transactional
	public Race getDefaultRaceForUser(User u);
	@Transactional
	public Integer getNumberOfRaces(User u);
	@Transactional
	public Double getDistanceInRaces(User u);
	@Transactional
	public Race getRaceById(Integer id);
	@Transactional
	public Race saveNewRace(String raceName);

	
}
