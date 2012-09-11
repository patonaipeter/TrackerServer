package at.ac.tuwien.server.dao;

import org.springframework.transaction.annotation.Transactional;

import at.ac.tuwien.server.domain.Race;
import at.ac.tuwien.server.domain.User;

public interface IRaceDao {

	@Transactional
	public void saveRace(Race location);
	@Transactional
	public Race getDefaultRaceForUser(User u);
	
}
